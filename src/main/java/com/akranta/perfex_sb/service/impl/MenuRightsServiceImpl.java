package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.*;
import com.akranta.perfex_sb.exception.BusinessApplicationException;
import com.akranta.perfex_sb.repository.*;
import com.akranta.perfex_sb.service.*;
import com.akranta.perfex_sb.util.CurrentUser;

import tools.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Service
@Transactional
public class MenuRightsServiceImpl implements MenuRightsService {
    private final MenuRightsRepository rights;
    private final MenuManagementRepository menus;
    private final DbActionTemplate administration;
    private final ObjectMapper mapper;
    private static final Set<String> PROTECTED_ROUTES = Set.of("/menu-management", "/role-menu-links",
            "/user-menu-links");

    public MenuRightsServiceImpl(MenuRightsRepository rights, MenuManagementRepository menus,
            DbActionTemplate administration, ObjectMapper mapper) {
        this.rights = rights;
        this.menus = menus;
        this.administration = administration;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuRightsDto.Subject> roles() {
        // CurrentUser.requireMaster();
        return rights.roles().stream().map(r -> subject(r, true)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<MenuRightsDto.Subject> users(String search, int page, int size) {
        // CurrentUser.requireMaster();
        if (page < 0 || size < 1 || size > 100 || search.length() > 100)
            throw problem(HttpStatus.BAD_REQUEST,
                    "Search is limited to 100 characters; page must be non-negative and size 1–100");
        String term = search.trim();
        long total = rights.countUsers(term);
        return new PageDto<>(rights.users(term, page, size).stream().map(r -> subject(r, false)).toList(), total,
                (int) ((total + size - 1) / size), page, size);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public MenuRightsDto roleMenus(String id) {
        // CurrentUser.requireMaster();
        return state(id, true).dto();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public MenuRightsDto userMenus(String id) {
        // CurrentUser.requireMaster();
        return state(id, false).dto();
    }

    @Override
    public MenuRightsDto saveRoleMenus(String id, MenuRightsSaveDto request) {
        return save(id, true, request);
    }

    @Override
    public MenuRightsDto saveUserMenus(String id, MenuRightsSaveDto request) {
        return save(id, false, request);
    }

    private MenuRightsDto save(String id, boolean role, MenuRightsSaveDto request) {
        // CurrentUser.requireMaster();
        administration.lockUserAdministration();
        menus.lock();
        // var actor = CurrentUser.get();
        // if (!rights.actorStillMaster(actor.keyid(), actor.authversion()))
        //     throw problem(HttpStatus.FORBIDDEN, "Your MASTER access changed. Sign in again");
        var state = state(id, role);
        var dto = state.dto();
        if (!MessageDigest.isEqual(dto.snapshot().getBytes(StandardCharsets.UTF_8),
                request.snapshot().getBytes(StandardCharsets.UTF_8)))
            throw problem(HttpStatus.CONFLICT,
                    "Rights, subject or menus changed in another session. Refresh and review before saving");
        Set<Integer> selected = new LinkedHashSet<>(request.menuIds());
        if (selected.size() != request.menuIds().size())
            throw problem(HttpStatus.BAD_REQUEST, "Duplicate menu numbers are not allowed");
        Set<Integer> old = new HashSet<>(dto.directMenuIds());
        Set<Integer> covered = new HashSet<>(selected);
        covered.addAll(dto.inheritedMenuIds());
        Map<Integer, MenuRightsDto.Menu> catalog = new HashMap<>();
        dto.menus().forEach(m -> catalog.put(m.id(), m));
        for (int menu : selected) {
            var row = catalog.get(menu);
            if (row == null)
                throw problem(HttpStatus.BAD_REQUEST, "Unknown menu number: " + menu);
            if (!old.contains(menu) && !row.canGrant())
                throw problem(HttpStatus.BAD_REQUEST, "Cannot grant " + row.caption() + ": " + row.blockedReason());
            // Previously blocked legacy grants may be retained or removed, never newly
            // added.
            // if (row.canGrant())
            //     for (var ancestor : path(menu, state.raw()))
            //         if (!covered.contains(number(ancestor, "mnum_menunumber")))
            //             throw problem(HttpStatus.BAD_REQUEST, "Select every required parent of " + row.caption());
        }
        if (role && "MASTER".equals(dto.subject().roleCode()))
            for (var row : dto.menus())
                if (PROTECTED_ROUTES.contains(row.angularPath()) && row.canGrant())
                    for (var p : path(row.id(), state.raw()))
                        if (!selected.contains(number(p, "mnum_menunumber")))
                            throw problem(HttpStatus.BAD_REQUEST,
                                    "MASTER must retain the menu and parents for " + row.caption());
        var grant = selected.stream().filter(menu -> !old.contains(menu)).toList();
        var revoke = old.stream().filter(menu -> !selected.contains(menu)).toList();
        if (role)
            rights.applyRoleChanges(id, grant, revoke,"");
        else {
            Map<Integer, Integer> levels = new LinkedHashMap<>();
            grant.forEach(menu -> levels.put(menu, catalog.get(menu).level()));
            // Refresh a retained valid grant's derived legacy level after a menu is moved.
            for (int menu : selected)
                if (old.contains(menu) && catalog.get(menu).canGrant()
                        && !Objects.equals(state.levels().get(menu), catalog.get(menu).level()))
                    levels.put(menu, catalog.get(menu).level());
            rights.applyUserChanges(id, levels, revoke, "");
        }
        return state(id, role).dto();
    }

    private record State(MenuRightsDto dto, Map<Integer, Map<String, Object>> raw, Map<Integer, Integer> levels) {
    }

    private State state(String id, boolean role) {
        if (id == null || id.length() > (role ? 15 : 10) || !id.matches("[A-Za-z0-9_-]+"))
            throw problem(HttpStatus.BAD_REQUEST, "Invalid subject key");
        var context = role ? rights.role(id) : rights.user(id);
        if (context == null)
            throw problem(HttpStatus.NOT_FOUND, role ? "Role not found" : "User not found");
        var subject = subject(context, role);
        var rows = menus.all();
        Map<Integer, Map<String, Object>> raw = new LinkedHashMap<>();
        rows.forEach(r -> raw.put(number(r, "mnum_menunumber"), r));
        var own = role ? rights.roleRows(id) : rights.userRows(id);
        String roleId = Objects.toString(context.get("role_id"), "");
        var inheritedRows = role || roleId.isEmpty() ? List.<Map<String, Object>>of() : rights.roleRows(roleId);
        var inherited = subject.canGrant() ? grants(inheritedRows, false) : List.<Integer>of();
        var catalog = rows.stream().map(r -> {
            int key = number(r, "mnum_menunumber");
            String reason = blocked(key, raw, subject);
            return new MenuRightsDto.Menu(key, number(r, "mnum_parentnumber"), number(r, "mnum_menulevel"),
                    number(r, "mnum_rootnumber"), number(r, "mnum_sortnumber"), text(r, "mnum_menucaption"),
                    text(r, "mnum_menuname"), yes(r, "mnum_isparent"), yes(r, "mnum_active"), yes(r, "mnum_isangular"),
                    yes(r, "mnum_isreact"), yes(r, "mnum_ismaster"), text(r, "mnum_angularpath"),
                    text(r, "mnum_angicon"), reason.isEmpty(), reason);
        }).toList();
        // Include inactive rows and all inherited rows: any concurrent change
        // invalidates the editor snapshot.
        String snapshot = hash(Arrays.asList(context, rows, own, inheritedRows));
        Map<Integer, Integer> levels = new HashMap<>();
        if (!role)
            own.forEach(r -> levels.put(number(r, "id"), number(r, "level")));
        return new State(new MenuRightsDto(subject, snapshot, catalog, grants(own, !role), inherited), raw, levels);
    }

    private List<Integer> grants(List<Map<String, Object>> rows, boolean individual) {
        return rows.stream().filter(r -> yes(r, "active") && (!individual || yes(r, "available")))
                .map(r -> number(r, "id")).toList();
    }

    private MenuRightsDto.Subject subject(Map<String, Object> r, boolean role) {
        boolean active = yes(r, "active"), locked = !role && yes(r, "locked");
        String code = text(r, "code");
        String reason = !active ? "Inactive " + (role ? "role" : "user")
                : locked ? "User is locked"
                        : !role && (!yes(r, "link_active") || !yes(r, "role_active")) ? "No active role assignment"
                                : "";
        return new MenuRightsDto.Subject(text(r, "id"), text(r, "label"), role ? "" : text(r, "loginid"), code,
                role ? text(r, "label") : text(r, "role_name"), active, locked, reason.isEmpty(), reason);
    }

    private String blocked(int key, Map<Integer, Map<String, Object>> raw, MenuRightsDto.Subject subject) {
        if (!subject.canGrant())
            return subject.blockedReason();
        List<Map<String, Object>> chain;
        try {
            chain = path(key, raw);
        } catch (BusinessApplicationException e) {
            return e.getMessage();
        }
        for (var r : chain) {
            if (!yes(r, "mnum_active"))
                return "Menu or parent is inactive";
            if (yes(r, "mnum_ismaster") && !"MASTER".equals(subject.roleCode()))
                return "MASTER-only menu or parent";
            if (!yes(r, "mnum_isangular") && !yes(r, "mnum_isreact"))
                return "Menu or parent has no enabled platform";
        }
        var leaf = raw.get(key);
        for (String platform : List.of("mnum_isangular", "mnum_isreact"))
            if (yes(leaf, platform) && chain.stream().allMatch(r -> yes(r, platform)))
                return "";
        return "No platform is enabled through the full parent chain";
    }

    // private List<Map<String, Object>> path(int key, Map<Integer, Map<String, Object>> raw) {
    //     List<Map<String, Object>> result = new ArrayList<>();
    //     Set<Integer> seen = new HashSet<>();
    //     int current = key;
    //  while (true) {
    //     var row = raw.get(current);
    //     if (row == null)
    //         throw problem(HttpStatus.BAD_REQUEST, "Missing parent menu");
    //     if (!seen.add(current))
    //         throw problem(HttpStatus.BAD_REQUEST, "Cyclic menu hierarchy");
    //     result.add(row);
    //     int parent = number(row, "mnum_parentnumber");
    //     if (parent == 0 || parent == current)
    //         break;
    //     current = parent;
    // }
    //     return result;
    // }

    private List<Map<String, Object>> path(
        int key,
        Map<Integer, Map<String, Object>> raw) {

    List<Map<String, Object>> result = new ArrayList<>();
    Set<Integer> seen = new HashSet<>();
    int current = key;

    while (true) {
       

        var row = raw.get(current);

        if (row == null) {
          
            throw problem(
                HttpStatus.BAD_REQUEST,
                "Missing parent menu"
            );
        }

        if (!seen.add(current)) {
            throw problem(
                HttpStatus.BAD_REQUEST,
                "Cyclic menu hierarchy"
            );
        }

        result.add(row);

        int parent = number(row, "mnum_parentnumber");

       

        if (parent == 0 || parent == current) {
            break;
        }

        current = parent;
    }

    return result;
}

    private String hash(Object value) {
        try {
            return HexFormat.of()
                    .formatHex(MessageDigest.getInstance("SHA-256").digest(mapper.writeValueAsBytes(value)));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to create rights snapshot", e);
        }
    }

    private static int number(Map<String, Object> r, String key) {
        return ((Number) r.get(key)).intValue();
    }

    private static String text(Map<String, Object> r, String key) {
        return Objects.toString(r.get(key), "");
    }

    private static boolean yes(Map<String, Object> r, String key) {
        return "Y".equals(text(r, key));
    }

    private static BusinessApplicationException problem(HttpStatus status, String message) {
        return new BusinessApplicationException(status, message);
    }
}
