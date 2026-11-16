package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.MenuManagementSaveDto;
import com.akranta.perfex_sb.exception.BusinessApplicationException;
import com.akranta.perfex_sb.repository.MenuManagementRepository;
import com.akranta.perfex_sb.service.MenuManagementService;
import com.akranta.perfex_sb.util.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class MenuManagementServiceImpl implements MenuManagementService {
    private final MenuManagementRepository menus;

    public MenuManagementServiceImpl(MenuManagementRepository menus) {
        this.menus = menus;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getMenus() {
       // CurrentUser.requireMaster();
        return menus.all();
    }

    @Override
    public Map<String, Object> create(MenuManagementSaveDto r) {
        //CurrentUser.requireMaster();
        menus.lock();
        if (menus.find(r.mnum_menunumber()) != null)
            throw problem(HttpStatus.CONFLICT, "Menu number already exists");
        return save(r, null);
    }

    @Override
    public Map<String, Object> update(long id, MenuManagementSaveDto r) {
        //CurrentUser.requireMaster();
        menus.lock();
        if (id != r.mnum_menunumber())
            throw problem(HttpStatus.BAD_REQUEST, "Menu number cannot change");
        var current = required(id);
        return save(r, current);
    }

    private Map<String, Object> save(MenuManagementSaveDto r, Map<String, Object> current) {
        var all = menus.all();
        var id = r.mnum_menunumber();
        boolean parent = "Y".equals(r.mnum_isparent());
        boolean active = "Y".equals(r.mnum_active());
        boolean web = "Y".equals(r.mnum_isangular()), mobile = "Y".equals(r.mnum_isreact());
        if (active && !web && !mobile)
            throw problem(HttpStatus.BAD_REQUEST, "An active menu must enable Web or Mobile");
        if (!parent && active && web && !route(r.mnum_angularpath()))
            throw problem(HttpStatus.BAD_REQUEST, "Enter a valid Angular path beginning with /");
        if (!parent && active && mobile && !route(r.mnum_reactpath()))
            throw problem(HttpStatus.BAD_REQUEST, "Enter a valid React path beginning with /");
        if (all.stream().anyMatch(m -> number(m, "mnum_menunumber") != id
                && r.mnum_menuname().trim().equalsIgnoreCase(text(m, "mnum_menuname"))))
            throw problem(HttpStatus.CONFLICT, "Menu name already exists");
        if (current != null && number(current, "mnum_version") != r.mnum_version())
            throw problem(HttpStatus.CONFLICT, "Menu changed in another session. Refresh before saving");
        boolean children = all.stream()
                .anyMatch(m -> number(m, "mnum_parentnumber") == id && number(m, "mnum_menunumber") != id);
        if (children && (current == null || !parent
                || (number(current, "mnum_parentnumber") == id ? 0 : number(current, "mnum_parentnumber")) != r
                        .mnum_parentnumber()))
            throw problem(HttpStatus.BAD_REQUEST, "A menu with children must remain a parent in its existing location");
        long root = id;
        int level = 1;
        if (r.mnum_parentnumber() != 0) {
            if (r.mnum_parentnumber().equals(id))
                throw problem(HttpStatus.BAD_REQUEST, "Menu cannot be its own parent");
            var p = required(r.mnum_parentnumber());
            if (!"Y".equals(text(p, "mnum_isparent")))
                throw problem(HttpStatus.BAD_REQUEST, "Choose an existing parent menu");
            Set<Long> visited = new HashSet<>(Set.of(id));
            var ancestor = p;
            while (ancestor != null) {
                long key = number(ancestor, "mnum_menunumber");
                if (!visited.add(key))
                    throw problem(HttpStatus.BAD_REQUEST, "Parent selection creates a cycle");
                long next = number(ancestor, "mnum_parentnumber");
                if (next == 0 || next == key)
                    break;
                ancestor = menus.find(next);
                if (ancestor == null)
                    throw problem(HttpStatus.BAD_REQUEST, "Parent hierarchy is incomplete");
            }
            root = number(p, "mnum_rootnumber");
            level = (int) number(p, "mnum_menulevel") + 1;
        }
        var base = r.baseValues();
        base.put("mnum_menuname", r.mnum_menuname().trim());
        base.put("mnum_menucaption", r.mnum_menucaption().trim());
        base.put("mnum_rootnumber", root);
        base.put("mnum_menulevel", level);

        var detail = r.detailValues();
        if (parent) {
            detail.put("mnum_angularpath", "");
            detail.put("mnum_reactpath", "");
        }
        var now = LocalDateTime.now();
        var user = r.mnum_createdby();
        if (current == null) {
            // NOT NULL columns of the legacy table that this screen does not manage
            base.put("mnum_tablename", "");
            base.put("mnum_mastintegsql", "");
            base.put("mnum_mastintegorderbysql", "");
            base.put("mnum_similarcolumn", "");
            base.put("mnum_createdon", now);
            menus.insertBase(base);
            writeDetail(id, 0, detail, user, now, 0);
            // menus.grantMaster(id, user);
        } else {
            menus.updateBase(base);
            writeDetail(id, number(current, "mnum_version"), detail, user, now, r.mnum_version());
        }
        return required(id);
    }

    /**
     * Version 0 means the menu has no detail row yet (legacy production menu or brand-new menu),
     * so the row is inserted; otherwise it is updated with an optimistic version check.
     */
    private void writeDetail(long id, long currentVersion, Map<String, Object> detail, String user,
            LocalDateTime now, long expectedVersion) {
        detail.put("mnum_menunumber", id);
        if (currentVersion == 0) {
            detail.put("mnum_createdby", user);
            detail.put("mnum_createdon", now);
            menus.insertDetail(detail);
        } else {
            detail.put("mnum_modifiedby", user);
            detail.put("mnum_modifiedon", now);
            if (!menus.updateDetail(detail, expectedVersion))
                throw problem(HttpStatus.CONFLICT, "Menu changed in another session");
        }
    }

    @Override
    public void deactivate(long id, long version) {
        CurrentUser.requireMaster();
        menus.lock();
        var current = required(id);
        if (menus.all().stream()
                .anyMatch(m -> number(m, "mnum_parentnumber") == id && number(m, "mnum_menunumber") != id))
            throw problem(HttpStatus.BAD_REQUEST, "Delete children first, or turn Active off to disable the branch");
        if (number(current, "mnum_version") != version)
            throw problem(HttpStatus.CONFLICT, "Menu changed in another session. Refresh before deleting");
        var base = new LinkedHashMap<String, Object>();
        base.put("mnum_menunumber", id);
        base.put("mnum_active", "N");
        menus.updateBase(base);
        writeDetail(id, version, new LinkedHashMap<>(), Objects.toString(CurrentUser.get().keyid(), ""),
                LocalDateTime.now(), version);
    }

    private Map<String, Object> required(long id) {
        var row = menus.find(id);
        if (row == null)
            throw problem(HttpStatus.NOT_FOUND, "Menu not found");
        return row;
    }

    static long number(Map<String, Object> row, String key) {
        return ((Number) row.get(key)).longValue();
    }

    static String text(Map<String, Object> row, String key) {
        return Objects.toString(row.get(key), "");
    }

    static boolean route(String path) {
        return path != null && path.matches("^/(?![/\\\\])[^\\s\\\\?#]+$");
    }

    static BusinessApplicationException problem(HttpStatus status, String message) {
        return new BusinessApplicationException(status, message);
    }
}