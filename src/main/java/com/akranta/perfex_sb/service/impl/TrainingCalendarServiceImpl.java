package com.akranta.perfex_sb.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.akranta.perfex_sb.dto.AttendanceFilter;
import com.akranta.perfex_sb.model.EntTlTragcalmst;
import com.akranta.perfex_sb.model.EntTlTrgCalEmp;
import com.akranta.perfex_sb.model.EntTlTrgCalQuad;
import com.akranta.perfex_sb.model.EntTlTrgCalSession;
import com.akranta.perfex_sb.model.EntTlTrgCalUnqp;
import com.akranta.perfex_sb.model.EntTlTrgFaculty;
import com.akranta.perfex_sb.model.EntTlTtgCalEmpatScore;
import com.akranta.perfex_sb.repository.EntTlTragcalmstRepository;
import com.akranta.perfex_sb.repository.EntTlTrgCalEmpRepository;
import com.akranta.perfex_sb.repository.EntTlTrgCalQuadRepository;
import com.akranta.perfex_sb.repository.EntTlTrgCalSessionRepository;
import com.akranta.perfex_sb.repository.EntTlTrgCalUnqpRepository;
import com.akranta.perfex_sb.repository.EntTlTtgCalEmpatScoreRepository;

import com.akranta.perfex_sb.repository.EntTlTrgFacultyRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.TrainingCalendarService;

@Service
public class TrainingCalendarServiceImpl implements TrainingCalendarService {

    private static final Logger log = LoggerFactory.getLogger(TrainingCalendarServiceImpl.class);

    private static final String TBL_TRG_CAL_MST = "ENT_TL_TRGCALMST";
    private static final String TBL_TRG_CAL_SESSION = "ENT_TL_TRGCALSESSION";
    private static final String TBL_TRG_FACULTY = "ENT_TL_TRGFACULTY";
    private static final String TBL_TRG_CAL_UNQP = "ENT_TL_TRGCALUNQP";

    private static final int KEY_LENGTH = 15;
    private static final String EMPTY_DATE_FORMAT = "";
    private static final String EMPTY_FORMAT_RESET = "";

    private static final String PREFIX_MASTER = "ETC";
    private static final String PREFIX_SESSION = "ETS";
    private static final String PREFIX_FACULTY = "ETF";
    private static final String PREFIX_UNQP = "ETU";

    private final EntTlTragcalmstRepository masterRepository;
    private final EntTlTrgCalSessionRepository sessionRepository;
    private final EntTlTrgFacultyRepository facultyRepository;
    private final EntTlTrgCalUnqpRepository unqpRepository;
    private final EntTlTrgCalEmpRepository empRepository;
    private final EntTlTtgCalEmpatScoreRepository empScoreRepository;
    private final EntTlTrgCalQuadRepository quadRepository;
    private final DbActionTemplate dbActionTemplate;
    

    @PersistenceContext
    private EntityManager entityManager;

    public TrainingCalendarServiceImpl(
            EntTlTragcalmstRepository masterRepository,
            EntTlTrgCalSessionRepository sessionRepository,
            EntTlTrgFacultyRepository facultyRepository,
            EntTlTrgCalUnqpRepository unqpRepository,
            EntTlTrgCalEmpRepository empRepository,
            EntTlTtgCalEmpatScoreRepository empScoreRepository,
            EntTlTrgCalQuadRepository quadRepository,
            DbActionTemplate dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.sessionRepository = sessionRepository;
        this.facultyRepository = facultyRepository;
        this.unqpRepository = unqpRepository;
        this.empRepository = empRepository;
        this.empScoreRepository = empScoreRepository;
        this.quadRepository = quadRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public EntTlTragcalmst create(EntTlTragcalmst master,
                                  EntTlTrgCalSession session,
                                  EntTlTrgFaculty faculty,
                                  List<EntTlTrgCalUnqp> uniquePositions) throws Exception {

        if (master == null) {
            throw new IllegalArgumentException("Master record (EntTlTragcalmst) is required");
        }

        // Session-save flows from legacy UI may call CREATE with an existing calendar id.
        // In that case, route through update semantics and avoid faculty re-validation.
        String effectiveKey = !isBlank(master.getEtcmKeyid())
                ? master.getEtcmKeyid().trim()
                : (session != null && !isBlank(session.getEtcsEtcmKeyid()) ? session.getEtcsEtcmKeyid().trim() : null);
        if (isBlank(effectiveKey) && session != null && !isBlank(session.getEtcsKeyid())) {
            effectiveKey = sessionRepository.findById(session.getEtcsKeyid().trim())
                    .map(EntTlTrgCalSession::getEtcsEtcmKeyid)
                    .filter(v -> !isBlank(v))
                    .map(String::trim)
                    .orElse(null);
        }
        if (!isBlank(effectiveKey)) {
            master.setEtcmKeyid(effectiveKey);
            if (session != null && isBlank(session.getEtcsEtcmKeyid())) {
                session.setEtcsEtcmKeyid(effectiveKey);
            }
            return update(master, session, faculty, uniquePositions);
        }

        if (session != null && !hasFacultyPayload(faculty)) {
            throw new IllegalArgumentException("Calendar id (etcmKeyid) is required for session save mode");
        }

        requireFacultyForCreate(faculty);

        LocalDateTime now = LocalDateTime.now();
        populateMaster(master, now);
        EntTlTragcalmst persistedMaster = masterRepository.save(master);

        if (session != null) {
            populateSession(session, persistedMaster, now);
            sessionRepository.save(session);
        }

        populateFaculty(faculty, persistedMaster, now);
        facultyRepository.save(faculty);

        if (uniquePositions != null) {
            for (EntTlTrgCalUnqp unqp : uniquePositions) {
                populateUnqp(unqp, persistedMaster, now);
                unqpRepository.save(unqp);
            }
        }

        log.info("Training calendar created with key {}", persistedMaster.getEtcmKeyid());
        return persistedMaster;
    }

    @Override
    @Transactional
    public EntTlTragcalmst update(EntTlTragcalmst master,
                                  EntTlTrgCalSession session,
                                  EntTlTrgFaculty faculty,
                                  List<EntTlTrgCalUnqp> uniquePositions) throws Exception {
        if (master == null || isBlank(master.getEtcmKeyid())) {
            throw new IllegalArgumentException("Master key is required for update");
        }

        EntTlTragcalmst existing = masterRepository.findById(master.getEtcmKeyid())
                .orElseThrow(() -> new IllegalArgumentException("Training calendar not found: " + master.getEtcmKeyid()));

        LocalDateTime now = LocalDateTime.now();

        mergeMissingMasterFields(master, existing);
        // carry forward immutable audit fields when missing
        if (master.getEtcmCreatedon() == null) {
            master.setEtcmCreatedon(existing.getEtcmCreatedon());
        }
        if (master.getEtcmCreatedatetime() == null) {
            master.setEtcmCreatedatetime(existing.getEtcmCreatedatetime());
        }
        if (isBlank(master.getEtcmCreatedby())) {
            master.setEtcmCreatedby(existing.getEtcmCreatedby());
        }
        master.setEtcmModifiedon(now);

        populateMaster(master, now);
        EntTlTragcalmst persistedMaster = masterRepository.save(master);

        if (session != null) {
            boolean hasSessionKey = !isBlank(session.getEtcsKeyid());
            boolean hasValidTimings = hasValidSessionTimings(session);
            boolean hasCreateIntent = !hasSessionKey && !isBlank(session.getEtcsName());

            if (!hasValidTimings) {
                if (hasSessionKey) {
                    log.warn("Skipping session update for {} because timings are missing or invalid", session.getEtcsKeyid());
                } else {
                    log.info("Skipping session insert for calendar {} because timings are missing or invalid", persistedMaster.getEtcmKeyid());
                }
            } else if (!hasSessionKey && !hasCreateIntent) {
                log.info("Skipping session insert for calendar {} because session key/name is not provided", persistedMaster.getEtcmKeyid());
            } else {
                int duplicateCnt = countSessionDuplicateSlots(
                        session,
                        persistedMaster.getEtcmKeyid(),
                        hasSessionKey ? session.getEtcsKeyid() : null);
                if (duplicateCnt > 0) {
                    log.info("Skipping session {} for calendar {} because same date/time slot already exists",
                            hasSessionKey ? "update" : "insert",
                            persistedMaster.getEtcmKeyid());
                } else {
                    handleSessionUpsert(session, persistedMaster, now);
                }
            }
        }

        // if (faculty != null) {
        //     if (!isBlank(faculty.getEtcfKeyid())) {
        //         handleFacultyUpsert(faculty, persistedMaster, now);
        //     } else {
        //         log.info("Skipping faculty insert for calendar {} during update because no faculty key is present", persistedMaster.getEtcmKeyid());
        //     }
        // }

        if (faculty != null) {

            boolean hasPayload = hasFacultyPayload(faculty);
            long existingFaculty = facultyRepository.countByEtcfEtcmKeyid(persistedMaster.getEtcmKeyid());

            if (hasPayload && (!isBlank(faculty.getEtcfKeyid()) || existingFaculty == 0)) {
                // Insert/update when payload present; generate a key if none exists and no faculty is currently linked.
                 handleFacultyUpsert(faculty, persistedMaster, now);

            } else if (!hasPayload) {
                 log.info("Skipping faculty insert for calendar {} during update because faculty id is missing", persistedMaster.getEtcmKeyid());
            } else {
               log.info("Skipping faculty insert for calendar {} during update because a faculty already exists and no faculty key was provided", persistedMaster.getEtcmKeyid());
             }
    
            }

        if (uniquePositions != null) {
            // replace existing unique positions
            // unqpRepository.deleteByEtcuEtcmKeyid(persistedMaster.getEtcmKeyid());
            for (EntTlTrgCalUnqp unqp : uniquePositions) {
                populateUnqp(unqp, persistedMaster, now);
                unqpRepository.save(unqp);
            }
        }

        return persistedMaster;
    }

    private int countSessionDuplicateSlots(EntTlTrgCalSession session, String etcmKeyid, String sessionId) {
        String sessionDate = session.getEtcsSessiondate()
                .toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH));
        String fromTime = session.getEtcsFromdate().format(DateTimeFormatter.ofPattern("HH:mm"));
        String toTime = session.getEtcsTilldate().format(DateTimeFormatter.ofPattern("HH:mm"));
        return checkSessionDuplicate(etcmKeyid, sessionDate, fromTime, toTime, sessionId);
    }

    @Override
    @Transactional
    public void delete(String etcmKeyid) throws Exception {
        if (isBlank(etcmKeyid)) {
            return;
        }
        // child tables first (matches legacy order)
        deleteByEtcm("DELETE FROM ent_tl_trgcalsession WHERE etcs_etcm_keyid = :etcmKeyid", etcmKeyid);
        deleteByEtcm("DELETE FROM ent_tl_trgcalunqp WHERE etcu_etcm_keyid = :etcmKeyid", etcmKeyid);
        deleteByEtcm("DELETE FROM ent_tl_trgcalemp WHERE etce_etcm_keyid = :etcmKeyid", etcmKeyid);
        deleteByEtcm("DELETE FROM ent_tl_trgcalempatscore WHERE etca_etcm_keyid = :etcmKeyid", etcmKeyid);
        deleteByEtcm("DELETE FROM ent_tl_trgcalquad WHERE etcq_l1_trgcalid = :etcmKeyid", etcmKeyid);
        deleteByEtcm("DELETE FROM ent_tl_trgfaculty WHERE etcf_etcm_keyid = :etcmKeyid", etcmKeyid);
        deleteByEtcm("DELETE FROM ent_tl_trgcalmst WHERE etcm_keyid = :etcmKeyid", etcmKeyid);
    }

    @Override
    @Transactional
    public void deleteSession(String etcmKeyid, String sessionKeyid) {
        if (isBlank(etcmKeyid) || isBlank(sessionKeyid)) {
            throw new IllegalArgumentException("Calendar id and session id are required");
        }
        EntTlTrgCalSession session = sessionRepository.findById(sessionKeyid)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionKeyid));
        if (!etcmKeyid.equals(session.getEtcsEtcmKeyid())) {
            throw new IllegalArgumentException("Session does not belong to calendar: " + etcmKeyid);
        }
        long count = sessionRepository.countByEtcsEtcmKeyid(etcmKeyid);
        if (count <= 1) {
            throw new IllegalStateException("Cannot delete the only session for this training calendar");
        }
        sessionRepository.deleteById(sessionKeyid);
    }

    @Override
    @Transactional(readOnly = true)
    public EntTlTragcalmst getById(String etcmKeyid) {
        if (isBlank(etcmKeyid)) {
            throw new IllegalArgumentException("Calendar id is required");
        }
        return masterRepository.findById(etcmKeyid.trim())
                .orElseThrow(() -> new IllegalArgumentException("Training calendar not found: " + etcmKeyid));
    }

    @Override
    @Transactional(readOnly = true)
    public String getEmpDataCount(String etcmKeyid) throws Exception {
        if (isBlank(etcmKeyid)) {
            return "0";
        }
        String sql = "select count(*) from ent_tl_trgcalempatscore where etca_etcm_keyid = :key";
        Object result = entityManager.createNativeQuery(sql)
                .setParameter("key", etcmKeyid.trim())
                .getSingleResult();
        // preserve legacy string return
        return result != null ? String.valueOf(result) : "0";
    }

    @Override
    @Transactional(readOnly = true)
    public String getEmpAttendanceCount(String etcmKeyid) throws Exception {
        if (isBlank(etcmKeyid)) {
            return "0";
        }
        String sql = "select count(*) from ent_tl_trgcalempatscore where etca_etcm_keyid = :key";
        Object result = entityManager.createNativeQuery(sql)
                .setParameter("key", etcmKeyid.trim())
                .getSingleResult();
        return result != null ? String.valueOf(result) : "0";
    }

    @Override
    @Transactional(readOnly = true)
    public String getMaxMarks(String etcmKeyid) throws Exception {
        if (isBlank(etcmKeyid)) {
            return "0";
        }
        String sql = """
            select etca_maxmarks
              from ent_tl_trgcalempatscore
             where etca_keyid = (
                 select min(etca_keyid) from ent_tl_trgcalempatscore where etca_etcm_keyid = :key
             )
        """;
        Object result = entityManager.createNativeQuery(sql)
                .setParameter("key", etcmKeyid.trim())
                .getSingleResult();
        return result != null ? String.valueOf(result) : "0";
    }

    @Override
    @Transactional(readOnly = true)
    public String getCutoff(String etcmKeyid) throws Exception {
        if (isBlank(etcmKeyid)) {
            return "0";
        }
        String sql = """
            select etca_cutoff
              from ent_tl_trgcalempatscore
             where etca_keyid = (
                 select min(etca_keyid) from ent_tl_trgcalempatscore where etca_etcm_keyid = :key
             )
        """;
        Object result = entityManager.createNativeQuery(sql)
                .setParameter("key", etcmKeyid.trim())
                .getSingleResult();
        return result != null ? String.valueOf(result) : "0";
    }

    @Override
    @Transactional(readOnly = true)
    public String getAssesType(String etcmKeyid) throws Exception {
        if (isBlank(etcmKeyid)) {
            return "";
        }
        String sql = """
            select etca_type
              from ent_tl_trgcalempatscore
             where etca_etcm_keyid = :key
             order by etca_keyid
             limit 1
        """;
        Object result = entityManager.createNativeQuery(sql)
                .setParameter("key", etcmKeyid.trim())
                .getSingleResult();
        return result != null ? String.valueOf(result) : "";
    }

    @Override
    @Transactional(readOnly = true)
    public List<String[]> getFaculty(String etcmKeyid) throws Exception {
        if (isBlank(etcmKeyid)) {
            return new ArrayList<>();
        }
        String sql = """
            select '' as col0,
                   ftym_empm_keyid,
                   etcf_keyid,
                   ftym_name as faculty,
                   '' as deletecol
              from ent_tl_trgfaculty f
              join ent_tl_facultymst m on f.etcf_facultyid = m.ftym_keyid
              join ent_tl_trgcalmst c on f.etcf_etcm_keyid = c.etcm_keyid
             where c.etcm_keyid = :key
        """;
        List<?> rows = entityManager.createNativeQuery(sql)
                .setParameter("key", etcmKeyid.trim())
                .getResultList();
        List<String[]> out = new ArrayList<>();
        for (Object row : rows) {
            Object[] cols = (Object[]) row;
            String[] arr = new String[5];
            for (int i = 0; i < cols.length; i++) {
                arr[i] = cols[i] != null ? cols[i].toString() : "";
            }
            out.add(arr);
        }
        return out;
    }

    // @Override
    // public int checkSessionDuplicate(String etcmKeyid, String sessionDate, String fromTime, String toTime) {
    //     if (isBlank(etcmKeyid) || isBlank(sessionDate) || isBlank(fromTime) || isBlank(toTime)) {
    //         throw new IllegalArgumentException("Calendar id, session date, from time, and to time are required");
    //     }
    //     String sessionDateStr = sessionDate.trim();
    //     String fromDateTime = (sessionDate.trim() + " " + fromTime.trim());
    //     String toDateTime = (sessionDate.trim() + " " + toTime.trim());
    //     return sessionRepository.countExactSessionSlot(etcmKeyid.trim(), sessionDateStr, fromDateTime, toDateTime);
    // }
    @Override
    public int checkSessionDuplicate(String etcmKeyid,
                                     String sessionDate,
                                     String fromTime,
                                     String toTime,
                                     String sessionId) {

    if (isBlank(etcmKeyid) || isBlank(sessionDate) || isBlank(fromTime) || isBlank(toTime)) {
        throw new IllegalArgumentException("Calendar id, session date, from time, and to time are required");
    }

    String sessionDateStr = sessionDate.trim();
    String fromDateTime = sessionDateStr + " " + fromTime.trim();  // "21-Jan-2026 09:59"
    String toDateTime   = sessionDateStr + " " + toTime.trim();    // "21-Jan-2026 10:59"

    return sessionRepository.countExactSessionSlot(
            etcmKeyid.trim(),
            sessionDateStr,
            fromDateTime,
            toDateTime,
            (sessionId == null ? "" : sessionId.trim())
    );
}

    @Override
    @Transactional
    public String deleteDetailRecord(String keyId, String gridId, String trainingId) {
        if (isBlank(keyId)) {
            throw new IllegalArgumentException("Detail key id is required");
        }
        if ("Gengrid".equalsIgnoreCase(gridId)) {
            facultyRepository.deleteById(keyId);
            return "Data Deleted Successfully";
        }
        if ("UniqueGrid".equalsIgnoreCase(gridId)) {
            unqpRepository.deleteById(keyId);
            return "Data Deleted Successfully";
        }
        if (isBlank(trainingId)) {
            throw new IllegalArgumentException("Training id is required for session delete");
        }
        deleteSession(trainingId, keyId);
        return "Data Deleted Successfully";
    }

    @Override
    public int checkUniquePosition(String etcmKeyid, String roleKeyid, String uniqueKeyid) {
        if (isBlank(etcmKeyid) || isBlank(roleKeyid)) {
            throw new IllegalArgumentException("Calendar id and role key id are required");
        }
        String uid = isBlank(uniqueKeyid) ? null : uniqueKeyid.trim();
        return unqpRepository.countByCalendarAndRole(etcmKeyid.trim(), roleKeyid.trim(), uid);
    }

    @Override
    public List<String[]> checkJHForRole(String roleKeyid) {
        if (isBlank(roleKeyid)) {
            throw new IllegalArgumentException("Role key id is required");
        }
        String sql = """
                SELECT sect_keyid, cell_keyid
                  FROM gen_vw_fnln
                 WHERE fnln_keyid IN (
                        SELECT role_flid FROM gen_tl_rolemst WHERE role_keyid = :roleKeyid
                 )
                """;
        List<Object[]> rows = entityManager.createNativeQuery(sql)
                .setParameter("roleKeyid", roleKeyid.trim())
                .getResultList();
        List<String[]> result = new ArrayList<>();
        for (Object[] row : rows) {
            String sect = row != null && row.length > 0 && row[0] != null ? row[0].toString() : null;
            String cell = row != null && row.length > 1 && row[1] != null ? row[1].toString() : null;
            result.add(new String[]{sect, cell});
        }
        return result;
    }

    @Override
    @Transactional
    public List<EntTlTrgCalUnqp> createMultipleUnique(List<EntTlTrgCalUnqp> uniqueList) throws Exception {
        if (uniqueList == null || uniqueList.isEmpty()) {
            return new ArrayList<>();
        }
        LocalDateTime now = LocalDateTime.now();
        List<EntTlTrgCalUnqp> toSave = new ArrayList<>();
        for (EntTlTrgCalUnqp item : uniqueList) {
            if (isBlank(item.getEtcuEtcmKeyid())) {
                throw new IllegalArgumentException("Training calendar id (etcuEtcmKeyid) is required for unique position");
            }
            populateUnqpStandalone(item, now);
            toSave.add(item);
        }
        return unqpRepository.saveAll(toSave);
    }
     @Override
    @Transactional(readOnly = true)
    public List<String[]> getsession(String etcmKeyid) throws Exception {
        if (isBlank(etcmKeyid)) {
            return new ArrayList<>();
        }
        List<Object[]> rows = sessionRepository.findSessionGrid(etcmKeyid.trim());
        // Vignesh for adding header row as per legacy behavior
      //  List<String[]> result = new ArrayList<>(rows.size());
       List<String[]> result = new ArrayList<>(rows.size() + 1);
        // legacy header row expected by UIUtils.convertToJqGridTableObject
        result.add(new String[]{"etcs_keyid", "etcs_name", "Session", "From Time", "To Time", "Delete"});
        for (Object[] row : rows) {
            String[] arr = new String[6];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = row != null && row.length > i && row[i] != null ? row[i].toString() : "";
            }
            result.add(arr);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String[]> getNewUniqPosData(String etcmKeyid) throws Exception {
        if (isBlank(etcmKeyid)) {
            return new ArrayList<>();
        }
        List<Object[]> rows = unqpRepository.findUniquePositionGrid(etcmKeyid.trim());
        List<String[]> result = new ArrayList<>(rows.size() + 1);
        result.add(new String[]{"etcu_keyid", "etcu_role_keyid", "Unique Position", "DMT", "JH", "Delete"});
        for (Object[] row : rows) {
            String[] arr = new String[6];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = row != null && row.length > i && row[i] != null ? row[i].toString() : "";
            }
            result.add(arr);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String[]> gwtJHRoleUniquePos(String calendarFlid, String calendarId) throws Exception {
        if (isBlank(calendarId)) {
            return new ArrayList<>();
        }
        List<Object[]> rows = unqpRepository.findUniqueRoleSelection(
                calendarId.trim(),
                isBlank(calendarFlid) ? null : calendarFlid.trim()
        );
        List<String[]> result = new ArrayList<>(rows.size() + 1);
        result.add(new String[]{"", "etcu_keyid", "role_keyid", "uniqposition", "sect_keyid", "cell_keyid"});
        for (Object[] row : rows) {
            String[] arr = new String[6];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = row != null && row.length > i && row[i] != null ? row[i].toString() : "";
            }
            result.add(arr);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String[]> getAllUniqueEmployee(String etcmKeyid, java.util.Map<String, Object> payload) throws Exception {
        if (isBlank(etcmKeyid)) {
            return new ArrayList<>();
        }

        @SuppressWarnings("unchecked")
        java.util.Map<String, Object> gridParams = payload == null ? java.util.Collections.emptyMap()
                : (java.util.Map<String, Object>) payload.getOrDefault("gridParams", payload);

        int fromRow = parseInt(gridParams.get("fromRow"), 1);
        int toRow = parseInt(gridParams.get("toRow"), 0); // 0 means no upper bound

        @SuppressWarnings("unchecked")
        java.util.List<java.util.Map<String, Object>> gridFilters =
                gridParams == null ? java.util.Collections.emptyList()
                        : (java.util.List<java.util.Map<String, Object>>) gridParams.getOrDefault("gridFilters", java.util.Collections.emptyList());

        String filterCond = buildFilterCond(gridFilters);

        int total = unqpRepository.countUniqueEmployees(etcmKeyid.trim(), filterCond);

        if (payload != null) {
            payload.put("totalRecordCnt", total);
        }
        if (total == 0) {
            List<String[]> empty = new ArrayList<>();
            // -- Vignesh removing header
         //   empty.add(new String[]{"slno", "etcekeyid", "attnkeyid", "keyid", "empcode", "name", "emptype", "gender", "ses", "rolename", "currlevel", "lastupdate", "dmt", "jh", "roleid"});
            return empty;
        }

        List<Object[]> rows = unqpRepository.findUniqueEmployees(
                etcmKeyid.trim(),
                filterCond,
                fromRow,
                toRow > 0 ? toRow : null
        );
        
           List<String[]> result = new ArrayList<>(rows.size());
           // Vignesh for adding header row as per legacy behavior -- Removing header
        // List<String[]> result = new ArrayList<>(rows.size() + 1);
        // result.add(new String[]{"slno", "etcekeyid", "attnkeyid", "keyid", "empcode", "name", "emptype", "gender", "ses", "rolename", "currlevel", "lastupdate", "dmt", "jh", "roleid"});

        for (Object[] row : rows) {
            String[] arr = new String[15];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = row != null && row.length > i && row[i] != null ? row[i].toString() : "";
            }
            result.add(arr);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String[]> getAllUniqueEmployeePopup(java.util.Map<String, Object> payload) throws Exception {
        if (payload == null) {
            return new ArrayList<>();
        }

        String etcmKeyid = firstText(payload, "key", "etcmKeyid", "TrainingId");
        String refdocid = firstText(payload, "refdocid", "pillarId");
        String flid = firstText(payload, "flid");
        String factoryId = firstText(payload, "factoryId", "fnln");
        String uniquePosFlag = firstText(payload, "uniquePos", "uniq");
        String empType = firstText(payload, "empwiseType", "EmployeeType");
        String empGender = firstText(payload, "empch", "EmployeeGender");
        String roleLevel = firstText(payload, "roleLevel", "roleKeyId");

        @SuppressWarnings("unchecked")
        java.util.Map<String, Object> gridParams = payload == null ? java.util.Collections.emptyMap()
                : (java.util.Map<String, Object>) payload.getOrDefault("gridParams", payload);

        int fromRow = parseInt(gridParams.get("fromRow"), 1);
        int toRow = parseInt(gridParams.get("toRow"), 0);

        @SuppressWarnings("unchecked")
        java.util.List<java.util.Map<String, Object>> gridFilters =
                gridParams == null ? java.util.Collections.emptyList()
                        : (java.util.List<java.util.Map<String, Object>>) gridParams.getOrDefault("gridFilters", java.util.Collections.emptyList());

        String filterCond = buildFilterCond(gridFilters);
        boolean uniquePos = !"false".equalsIgnoreCase(uniquePosFlag);

        int total = empRepository.countUniqueEmployeePopup(
                trimToNull(etcmKeyid),
                trimToNull(refdocid),
                trimToNull(flid),
                trimToNull(factoryId),
                uniquePos,
                trimToNull(empType),
                trimToNull(empGender),
                trimToNull(roleLevel),
                filterCond
        );

        payload.put("totalRecordCnt", total);
        if (total == 0) {
            return new ArrayList<>();
        }

        List<Object[]> rows = empRepository.findUniqueEmployeePopup(
                trimToNull(etcmKeyid),
                trimToNull(refdocid),
                trimToNull(flid),
                trimToNull(factoryId),
                uniquePos,
                trimToNull(empType),
                trimToNull(empGender),
                trimToNull(roleLevel),
                filterCond,
                fromRow,
                toRow > 0 ? toRow : null
        );

        List<String[]> result = new ArrayList<>(rows.size());
        for (Object[] row : rows) {
            if (row == null) {
                continue;
            }
            String[] arr = new String[row.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = row[i] != null ? row[i].toString() : "";
            }
            result.add(arr);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String[]> getEmployeeAttendance(String etcmKeyid, java.util.Map<String, Object> payload) throws Exception {
        if (isBlank(etcmKeyid)) {
            return new ArrayList<>();
        }

        @SuppressWarnings("unchecked")
        java.util.Map<String, Object> gridParams = payload == null ? java.util.Collections.emptyMap()
                : (java.util.Map<String, Object>) payload.getOrDefault("gridParams", payload);

        int fromRow = parseInt(gridParams.get("fromRow"), 1);
        int toRow = parseInt(gridParams.get("toRow"), 0); // 0 means no upper bound

        @SuppressWarnings("unchecked")
        java.util.List<java.util.Map<String, Object>> gridFilters =
                gridParams == null ? java.util.Collections.emptyList()
                        : (java.util.List<java.util.Map<String, Object>>) gridParams.getOrDefault("gridFilters", java.util.Collections.emptyList());

        String filterCond = buildFilterCond(gridFilters);

        int total = empScoreRepository.countEmployeeAttendance(etcmKeyid.trim(), filterCond);

        if (payload != null) {
            payload.put("totalRecordCnt", total);
        }
        if (total == 0) {
            return new ArrayList<>();
        }

        List<Object[]> rows = empScoreRepository.findEmployeeAttendance(
                etcmKeyid.trim(),
                filterCond,
                fromRow,
                toRow > 0 ? toRow : null
        );

        List<String[]> result = new ArrayList<>(rows.size());
        for (Object[] row : rows) {
            String[] arr = new String[16];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = row != null && row.length > i && row[i] != null ? row[i].toString() : "";
            }
            result.add(arr);
        }
        return result;
    }


    @Override
    @Transactional
    public EntTlTrgCalEmp createEmployee(EntTlTrgCalEmp employee) throws Exception {
        if (employee == null) {
            throw new IllegalArgumentException("Employee record (EntTlTrgCalEmp) is required");
        }
        LocalDateTime now = LocalDateTime.now();
        populateEmp(employee, now);
        return empRepository.save(employee);
    }

    @Override
    @Transactional
    public List<EntTlTrgCalEmp> createSessionEmployee(List<EntTlTrgCalEmp> employees) throws Exception {
        if (employees == null || employees.isEmpty()) {
            return new ArrayList<>();
        }
        LocalDateTime now = LocalDateTime.now();
        List<EntTlTrgCalEmp> prepared = new ArrayList<>();
        for (EntTlTrgCalEmp emp : employees) {
            populateEmp(emp, now);
            prepared.add(emp);
        }
        return empRepository.saveAll(prepared);
    }

    @Override
    @Transactional
    public List<EntTlTtgCalEmpatScore> createEmployeeAttendance(List<EntTlTtgCalEmpatScore> scores,
                                                                AttendanceFilter filter) throws Exception {
        if (scores == null || scores.isEmpty()) {
            return new ArrayList<>();
        }
        LocalDateTime now = LocalDateTime.now();
        List<EntTlTtgCalEmpatScore> persisted = new ArrayList<>();

        for (EntTlTtgCalEmpatScore score : scores) {
            populateEmpScore(score, filter, now);
            boolean isUpdate = !isBlank(score.getEtcaKeyid());

            if (isUpdate) {
                // keep createdon if already present in DB
                empScoreRepository.findById(score.getEtcaKeyid()).ifPresent(existing -> {
                    if (score.getEtcaCreatedon() == null) {
                        score.setEtcaCreatedon(existing.getEtcaCreatedon());
                    }
                    if (isBlank(score.getEtcaCreatedby())) {
                        score.setEtcaCreatedby(existing.getEtcaCreatedby());
                    }
                });
            } else {
                String key = dbActionTemplate.getSequenceNumber("ENT_TL_TRGCALEMPATSCORE", KEY_LENGTH, "ETA", EMPTY_DATE_FORMAT, EMPTY_FORMAT_RESET);
                score.setEtcaKeyid(key);
                if (score.getEtcaCreatedon() == null) {
                    score.setEtcaCreatedon(now);
                }
                if (score.getEtcaModifiedon() == null) {
                    score.setEtcaModifiedon(now);
                }
            }

            score.setEtcaModifiedon(now);
            EntTlTtgCalEmpatScore savedScore = empScoreRepository.save(score);
            persisted.add(savedScore);

            upsertQuad(savedScore, filter, now);
        }
        return persisted;
    }

    @Override
    @Transactional
    public String checkAssessmentCompleted(String etcmKeyid) throws Exception {
        if (isBlank(etcmKeyid)) {
            log.info("chkAssesmentComplted – no key, returning 0");
            return "0";
        }

        // Detect no-assessment (max=0 and cutoff=0)
        String maxMarksSql = """
            SELECT COALESCE(MAX(etca_maxmarks), 0) FROM ent_tl_trgcalempatscore WHERE etca_etcm_keyid = :key
        """;
        String cutOffSql = """
            SELECT COALESCE(MAX(etca_cutoff), 0) FROM ent_tl_trgcalempatscore WHERE etca_etcm_keyid = :key
        """;
        BigDecimal maxMarks = toBigDecimal(entityManager.createNativeQuery(maxMarksSql).setParameter("key", etcmKeyid).getSingleResult());
        BigDecimal cutOff = toBigDecimal(entityManager.createNativeQuery(cutOffSql).setParameter("key", etcmKeyid).getSingleResult());
        boolean noAssessment = maxMarks.compareTo(BigDecimal.ZERO) == 0 && cutOff.compareTo(BigDecimal.ZERO) == 0;

        // planned employee count
        String empCntSql = "SELECT COUNT(*) FROM ent_tl_trgcalemp WHERE etce_etcm_keyid = :key";
        int empCnt = toInt(entityManager.createNativeQuery(empCntSql).setParameter("key", etcmKeyid).getSingleResult());

        // completed count
        String completedCntSql;
        if (noAssessment) {
            completedCntSql = """
                SELECT COUNT(DISTINCT etca_etce_keyid)
                  FROM ent_tl_trgcalempatscore
                 WHERE etca_etcm_keyid = :key
            """;
        } else {
            completedCntSql = """
                SELECT COUNT(DISTINCT etca_etce_keyid)
                  FROM ent_tl_trgcalempatscore
                 WHERE etca_etcm_keyid = :key
                   AND etca_prsentabsent IS NOT NULL
                   AND etca_result IN ('P','F')
            """;
        }
        int completedCnt = toInt(entityManager.createNativeQuery(completedCntSql).setParameter("key", etcmKeyid).getSingleResult());

        log.info("chkAssesmentComplted – key={}, empCnt={}, completedCnt={}, noAssessment={}", etcmKeyid, empCnt, completedCnt, noAssessment);

        if (completedCnt != empCnt) {
            // NEW lines — DB la explicit ah N ku reset pannudhu
        String resetSql = """
            UPDATE ent_tl_trgcalempatscore
           SET etca_assessmentcom = 'N'
         WHERE etca_etcm_keyid = :key
    """;
    entityManager.createNativeQuery(resetSql).setParameter("key", etcmKeyid).executeUpdate();
            return "0";
        }


        // update assessment complete flag
        String updateSql = """
            UPDATE ent_tl_trgcalempatscore
               SET etca_assessmentcom = 'Y'
             WHERE etca_etcm_keyid = :key
        """;
        entityManager.createNativeQuery(updateSql).setParameter("key", etcmKeyid).executeUpdate();
        log.info("chkAssesmentComplted – updated ETCA_ASSESSMENTCOM = 'Y' for key={}", etcmKeyid);
        return "1";
    }



    private void populateSession(EntTlTrgCalSession session, EntTlTragcalmst master, LocalDateTime now) throws Exception {
        if (isBlank(session.getEtcsKeyid())) {
            String key = dbActionTemplate.getSequenceNumber(
                    TBL_TRG_CAL_SESSION, KEY_LENGTH, PREFIX_SESSION, EMPTY_DATE_FORMAT, EMPTY_FORMAT_RESET);
            session.setEtcsKeyid(key);
        }
        if (isBlank(session.getEtcsEtcmKeyid())) {
            session.setEtcsEtcmKeyid(master.getEtcmKeyid());
        }
        if (isBlank(session.getEtcsEtcmFlid())) {
            session.setEtcsEtcmFlid(master.getEtcmFlid());
        }
        if (isBlank(session.getEtcsName()) || "Session".equalsIgnoreCase(session.getEtcsName())) {
            String resolvedName = null;
            if (!isBlank(session.getEtcsKeyid())) {
                resolvedName = sessionRepository.findById(session.getEtcsKeyid())
                        .map(EntTlTrgCalSession::getEtcsName)
                        .orElse(null);
            }
            if (resolvedName == null && !isBlank(session.getEtcsEtcmKeyid())) {
                long count = sessionRepository.countByEtcsEtcmKeyid(session.getEtcsEtcmKeyid());
                resolvedName = "Session" + (count + 1);
            }
            session.setEtcsName(resolvedName != null ? resolvedName : "Session1");
        }
        // require explicit session timings; don't silently replace with current time
        if (session.getEtcsSessiondate() == null ||
                session.getEtcsFromdate() == null ||
                session.getEtcsTilldate() == null) {
            throw new IllegalArgumentException("Session date/from/to time are required");
        }
        if (session.getEtcsDateadd() == null) {
            session.setEtcsDateadd(now);
        }
        if (session.getEtcsCreatedon() == null) {
            session.setEtcsCreatedon(now);
        }
        if (session.getEtcsModifiedon() == null) {
            session.setEtcsModifiedon(now);
        }
        if (isBlank(session.getEtcsActive())) {
            session.setEtcsActive(defaultChar(session.getEtcsActive(), 'Y'));
        }
        session.setEtcsTempfield1(defaultString(session.getEtcsTempfield1(), "-"));
        session.setEtcsTempfield2(defaultString(session.getEtcsTempfield2(), "-"));
        session.setEtcsTempfield3(defaultString(session.getEtcsTempfield3(), "-"));
        session.setEtcsTempfield4(defaultString(session.getEtcsTempfield4(), "-"));
        session.setEtcsTempfield5(defaultString(session.getEtcsTempfield5(), "-"));
    }

    private void populateFaculty(EntTlTrgFaculty faculty, EntTlTragcalmst master, LocalDateTime now) throws Exception {
        if (isBlank(faculty.getEtcfKeyid())) {
            String key = dbActionTemplate.getSequenceNumber(
                    TBL_TRG_FACULTY, KEY_LENGTH, PREFIX_FACULTY, EMPTY_DATE_FORMAT, EMPTY_FORMAT_RESET);
            faculty.setEtcfKeyid(key);
        }
        if (isBlank(faculty.getEtcfEtcmKeyid())) {
            faculty.setEtcfEtcmKeyid(master.getEtcmKeyid());
        }
        if (isBlank(faculty.getEtcfEtcmFlid())) {
            faculty.setEtcfEtcmFlid(master.getEtcmFlid());
        }
        if (isBlank(faculty.getEtcfFacultytype())) {
            faculty.setEtcfFacultytype(defaultChar(faculty.getEtcfFacultytype(), '-'));
        }
        if (faculty.getEtcfDateadd() == null) {
            faculty.setEtcfDateadd(now);
        }
        if (faculty.getEtcfCreatedon() == null) {
            faculty.setEtcfCreatedon(now);
        }
        if (faculty.getEtcfModifiedon() == null) {
            faculty.setEtcfModifiedon(now);
        }
        if (isBlank(faculty.getEtcfActive())) {
            faculty.setEtcfActive(defaultChar(faculty.getEtcfActive(), 'Y'));
        }
        faculty.setEtcfTempfield1(defaultString(faculty.getEtcfTempfield1(), "-"));
        faculty.setEtcfTempfield2(defaultString(faculty.getEtcfTempfield2(), "-"));
        faculty.setEtcfTempfield3(defaultString(faculty.getEtcfTempfield3(), "-"));
        faculty.setEtcfTempfield4(defaultString(faculty.getEtcfTempfield4(), "-"));
        faculty.setEtcfTempfield5(defaultString(faculty.getEtcfTempfield5(), "-"));
        faculty.setEtcfCreatedby(defaultString(faculty.getEtcfCreatedby(), "-"));
    }

    private void populateUnqp(EntTlTrgCalUnqp unqp, EntTlTragcalmst master, LocalDateTime now) throws Exception {
        if (isBlank(unqp.getEtcuKeyid())) {
            String key = dbActionTemplate.getSequenceNumber(
                    TBL_TRG_CAL_UNQP, KEY_LENGTH, PREFIX_UNQP, EMPTY_DATE_FORMAT, EMPTY_FORMAT_RESET);
            unqp.setEtcuKeyid(key);
        }
        if (isBlank(unqp.getEtcuEtcmKeyid())) {
            unqp.setEtcuEtcmKeyid(master.getEtcmKeyid());
        }
        unqp.setEtcuRoleKeyid(defaultString(unqp.getEtcuRoleKeyid(), "-"));
        unqp.setEtcuRoledmt(defaultString(unqp.getEtcuRoledmt(), "-"));
        unqp.setEtcuRolejh(defaultString(unqp.getEtcuRolejh(), "-"));
        if (unqp.getEtcuDateadd() == null) {
            unqp.setEtcuDateadd(now);
        }
        if (unqp.getEtcuCreatedon() == null) {
            unqp.setEtcuCreatedon(now);
        }
        if (unqp.getEtcuModifiedon() == null) {
            unqp.setEtcuModifiedon(now);
        }
        if (isBlank(unqp.getEtcuActive())) {
            unqp.setEtcuActive(defaultChar(unqp.getEtcuActive(), 'Y'));
        }
        unqp.setEtcuTempfield1(defaultString(unqp.getEtcuTempfield1(), "-"));
        unqp.setEtcuTempfield2(defaultString(unqp.getEtcuTempfield2(), "-"));
        unqp.setEtcuTempfield3(defaultString(unqp.getEtcuTempfield3(), "-"));
        unqp.setEtcuTempfield4(defaultString(unqp.getEtcuTempfield4(), "-"));
        unqp.setEtcuTempfield5(defaultString(unqp.getEtcuTempfield5(), "-"));
        unqp.setEtcuCreatedby(defaultString(unqp.getEtcuCreatedby(), "-"));
    }

    private void populateEmp(EntTlTrgCalEmp emp, LocalDateTime now) throws Exception {
        if (isBlank(emp.getEtceKeyid())) {
            String key = dbActionTemplate.getSequenceNumber(
                    "ENT_TL_TRGCALEMP", KEY_LENGTH, "ETE", EMPTY_DATE_FORMAT, EMPTY_FORMAT_RESET);
            emp.setEtceKeyid(key);
        }
        emp.setEtceEtcmKeyid(defaultString(emp.getEtceEtcmKeyid(), "{}"));
        emp.setEtceEtcsKeyid(defaultString(emp.getEtceEtcsKeyid(), "{}"));
        emp.setEtceEmpmKeyid(defaultString(emp.getEtceEmpmKeyid(), "{}"));
        emp.setEtceRoleKeyid(defaultString(emp.getEtceRoleKeyid(), "{}"));
        emp.setEtceRoleDmt(defaultString(emp.getEtceRoleDmt(), "{}"));
        emp.setEtceRoleJh(defaultString(emp.getEtceRoleJh(), "{}"));
        emp.setEtceTempfield1(defaultString(emp.getEtceTempfield1(), "-"));
        emp.setEtceTempfield2(defaultString(emp.getEtceTempfield2(), "-"));
        emp.setEtceTempfield3(defaultString(emp.getEtceTempfield3(), "-"));
        emp.setEtceTempfield4(defaultString(emp.getEtceTempfield4(), "-"));
        emp.setEtceTempfield5(defaultString(emp.getEtceTempfield5(), "-"));
        emp.setEtceCreatedby(defaultString(emp.getEtceCreatedby(), "-"));
        if (emp.getEtceDateadd() == null) {
            emp.setEtceDateadd(now);
        }
        if (emp.getEtceCreatedon() == null) {
            emp.setEtceCreatedon(now);
        }
        if (emp.getEtceModifiedon() == null) {
            emp.setEtceModifiedon(now);
        }
        if (isBlank(emp.getEtceActive())) {
            emp.setEtceActive(defaultChar(emp.getEtceActive(), 'Y'));
        }
    }

    private void populateEmpScore(EntTlTtgCalEmpatScore score, AttendanceFilter filter, LocalDateTime now) {
        score.setEtcaEtcmKeyid(defaultString(score.getEtcaEtcmKeyid(), filter != null ? filter.getKey() : "-"));
        score.setEtcaEtceEmpmKeyid(defaultString(score.getEtcaEtceEmpmKeyid(), "{}"));
        score.setEtcaEtceKeyid(defaultString(score.getEtcaEtceKeyid(), "{}"));
        score.setEtcaAssessmentCom(defaultChar(score.getEtcaAssessmentCom(), 'N'));
        score.setEtcaMaxMarks(score.getEtcaMaxMarks() == null ? BigDecimal.ZERO : score.getEtcaMaxMarks());
        score.setEtcaCutOff(score.getEtcaCutOff() == null ? BigDecimal.ZERO : score.getEtcaCutOff());
        score.setEtcaType(defaultChar(score.getEtcaType(), 'O'));
        score.setEtcaDept(defaultString(score.getEtcaDept(), "{}"));
        score.setEtcaPrsentAbsent(defaultChar(score.getEtcaPrsentAbsent(), 'P'));
        if (score.getEtcaAttDate() == null) {
            score.setEtcaAttDate(now);
        }
        score.setEtcaScore(score.getEtcaScore() == null ? BigDecimal.ZERO : score.getEtcaScore());
        score.setEtcaResult(defaultChar(score.getEtcaResult(), 'P'));
        score.setEtcaRemarks(defaultString(score.getEtcaRemarks(), "-"));
        if (score.getEtcaScoreDate() == null) {
            score.setEtcaScoreDate(score.getEtcaAttDate());
        }
        score.setEtcaFilemgnid(defaultString(score.getEtcaFilemgnid(), "-"));
        score.setEtcaTempfield1(defaultString(score.getEtcaTempfield1(), "-"));
        score.setEtcaTempfield2(defaultString(score.getEtcaTempfield2(), "-"));
        score.setEtcaTempfield3(defaultString(score.getEtcaTempfield3(), "-"));
        score.setEtcaTempfield4(defaultString(score.getEtcaTempfield4(), "-"));
        score.setEtcaTempfield5(defaultString(score.getEtcaTempfield5(), "-"));
        score.setEtcaCreatedby(defaultString(score.getEtcaCreatedby(), filter != null ? filter.getChkExternal() : "-"));
        score.setEtcaActive(defaultChar(score.getEtcaActive(), 'Y'));
        if (score.getEtcaCreatedon() == null) {
            score.setEtcaCreatedon(now);
        }
        if (score.getEtcaModifiedon() == null) {
            score.setEtcaModifiedon(now);
        }
    }

    private void upsertQuad(EntTlTtgCalEmpatScore score, AttendanceFilter filter, LocalDateTime now) throws Exception {
        if (score == null || filter == null) {
            return;
        }
        String empKey = score.getEtcaEtceEmpmKeyid();
        String topicId = filter.getTopicid();
        String trgCalId = filter.getKey();

        EntTlTrgCalQuad quad = quadRepository
                .findFirst(empKey, topicId, trgCalId)
                .orElseGet(EntTlTrgCalQuad::new);

        boolean isNew = isBlank(quad.getKeyid());
        if (isNew) {
            String quadKey = dbActionTemplate.getSequenceNumber("ENT_TL_TRGCALQUAD", KEY_LENGTH, "ETQ", EMPTY_DATE_FORMAT, EMPTY_FORMAT_RESET);
            quad.setKeyid(quadKey);
        }

        quad.setEmpm_keyid(empKey);
        quad.setEmpm_roleid(defaultString(quad.getEmpm_roleid(), fetchEmpRole(empKey)));
        quad.setEmpm_topicid(defaultString(quad.getEmpm_topicid(), topicId));
        quad.setFlid(defaultString(quad.getFlid(), filter.getFlid()));
        quad.setLocation(defaultString(quad.getLocation(), filter.getLossId()));
        quad.setDmt(defaultString(quad.getDmt(), "{}"));
        quad.setJh(defaultString(quad.getJh(), "{}"));

        Character result = defaultChar(score.getEtcaResult(), 'F');
        int level = (result != null && Character.toUpperCase(result) == 'P') ? 2 : 1;
        quad.setCurrentlevel(BigDecimal.valueOf(level));

        LocalDateTime attDate = score.getEtcaAttDate() != null ? score.getEtcaAttDate() : now;
        quad.setCurrleveldate(attDate);
        quad.setL1date(attDate);
        quad.setL2date(attDate);
        quad.setL3date(attDate);
        quad.setL4date(now);
        quad.setL1pass(result);
        quad.setL2pass(result);
        quad.setL3pass('F');
        quad.setL4pass(defaultChar(quad.getL4pass(), result != null ? result : 'F'));

        quad.setL1trgcalid(trgCalId);
        quad.setL2trgcalid(trgCalId);
        quad.setL1remarks(defaultString(quad.getL1remarks(), "{}"));
        quad.setL2remarks(defaultString(quad.getL2remarks(), "{}"));
        quad.setL3remarks(defaultString(quad.getL3remarks(), "{}"));
        quad.setL4remarks(defaultString(quad.getL4remarks(), "{}"));
        quad.setL3updby(defaultString(quad.getL3updby(), filter.getChkExternal()));
        quad.setL4updby(defaultString(quad.getL4updby(), filter.getChkExternal()));
        quad.setTempfield1(defaultString(quad.getTempfield1(), "-"));
        quad.setTempfield2(defaultString(quad.getTempfield2(), "-"));
        quad.setTempfield3(defaultString(quad.getTempfield3(), "-"));
        quad.setTempfield4(defaultString(quad.getTempfield4(), "-"));
        quad.setTempfield5(defaultString(quad.getTempfield5(), "-"));
        quad.setCreatedby(defaultString(quad.getCreatedby(), filter.getChkExternal()));
        quad.setActive(defaultChar(quad.getActive(), 'Y'));
        if (quad.getCreatedon() == null) {
            quad.setCreatedon(now);
        }
        quad.setModifiedon(now);

        quadRepository.save(quad);
    }

    private String fetchEmpRole(String empKeyid) {
        if (isBlank(empKeyid)) {
            return "{}";
        }
        try {
            Object roleObj = entityManager.createNativeQuery("SELECT empm_roleid FROM gen_tl_employeemst WHERE empm_keyid = :emp")
                    .setParameter("emp", empKeyid.trim())
                    .getSingleResult();
            return roleObj != null ? roleObj.toString() : "{}";
        } catch (Exception e) {
            return "{}";
        }
    }

    private BigDecimal toBigDecimal(Object obj) {
        if (obj == null) return BigDecimal.ZERO;
        try {
            if (obj instanceof BigDecimal bd) return bd;
            return new BigDecimal(obj.toString());
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private int toInt(Object obj) {
        if (obj == null) return 0;
        try {
            if (obj instanceof Number n) return n.intValue();
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    private int parseInt(Object obj, int defaultVal) {
        int v = toInt(obj);
        return v == 0 ? defaultVal : v;
    }

    private String firstText(java.util.Map<String, Object> payload, String... keys) {
        if (payload == null || keys == null) return null;
        for (String key : keys) {
            Object val = payload.get(key);
            if (val == null) continue;
            String str = val.toString();
            if (!isBlank(str)) {
                return str;
            }
        }
        return null;
    }

    private String trimToNull(String value) {
        return isBlank(value) ? null : value.trim();
    }

    private String buildFilterCond(java.util.List<java.util.Map<String, Object>> filters) {
        if (filters == null || filters.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (java.util.Map<String, Object> f : filters) {
            if (f == null) continue;
            Object fieldObj = f.get("field");
            Object dataObj = f.get("data");
            if (fieldObj == null || dataObj == null) continue;
            String field = fieldObj.toString();
            String data = dataObj.toString();
            if (isBlank(field) || isBlank(data)) continue;
            sb.append(" AND upper(")
              .append(field)
              .append(") like '")
              .append(data.toUpperCase().replace("*", "%"))
              .append("'");
        }
        return sb.toString();
    }

    private void handleSessionUpsert(EntTlTrgCalSession session, EntTlTragcalmst master, LocalDateTime now) throws Exception {
        boolean hasKey = !isBlank(session.getEtcsKeyid());
        if (hasKey) {
            // retain name if missing (edit scenario)
            sessionRepository.findById(session.getEtcsKeyid()).ifPresent(existing -> {
                if (isBlank(session.getEtcsName())) {
                    session.setEtcsName(existing.getEtcsName());
                }
                if (session.getEtcsCreatedon() == null) {
                    session.setEtcsCreatedon(existing.getEtcsCreatedon());
                }
            });
            populateSession(session, master, now);
            sessionRepository.save(session);
        } else {
            populateSession(session, master, now);
            sessionRepository.save(session);
        }
    }

    private boolean hasValidSessionTimings(EntTlTrgCalSession session) {
        if (session == null) {
            return false;
        }
        LocalDateTime sessionDate = session.getEtcsSessiondate();
        LocalDateTime from = session.getEtcsFromdate();
        LocalDateTime to = session.getEtcsTilldate();
        if (sessionDate == null || from == null || to == null) {
            return false;
        }
        return from.isBefore(to);
    }

    private void handleFacultyUpsert(EntTlTrgFaculty faculty, EntTlTragcalmst master, LocalDateTime now) throws Exception {
        boolean hasKey = !isBlank(faculty.getEtcfKeyid());
        if (hasKey) {
            facultyRepository.findById(faculty.getEtcfKeyid()).ifPresent(existing -> {
                if (faculty.getEtcfCreatedon() == null) {
                    faculty.setEtcfCreatedon(existing.getEtcfCreatedon());
                }
                if (isBlank(faculty.getEtcfCreatedby())) {
                    faculty.setEtcfCreatedby(existing.getEtcfCreatedby());
                }
            });
            populateFaculty(faculty, master, now);
            facultyRepository.save(faculty);
        } else {
            populateFaculty(faculty, master, now);
            facultyRepository.save(faculty);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty() || "{}".equals(value.trim()) || "null".equalsIgnoreCase(value.trim());
    }

    private boolean isBlank(Character value) {
        return value == null;
    }

    private String defaultString(String value, String fallback) {
        return isBlank(value) ? fallback : value;
    }

    private Character defaultChar(Character value, char fallback) {
        return value == null ? fallback : value;
    }

    private void requireFacultyForCreate(EntTlTrgFaculty faculty) {
        if (faculty == null || isBlank(faculty.getEtcfFacultyid())) {
            throw new IllegalArgumentException("Faculty details with a valid faculty id are required for initial save");
        }
    }

    private boolean hasFacultyPayload(EntTlTrgFaculty faculty) {
        return faculty != null && !isBlank(faculty.getEtcfFacultyid());
    }

    private void handleFacultyUpdateWithFallback(EntTlTrgFaculty faculty, EntTlTragcalmst master, LocalDateTime now) throws Exception {
        boolean hasPayload = faculty != null && !isBlank(faculty.getEtcfFacultyid());
        boolean hasExisting = facultyRepository.existsByEtcfEtcmKeyid(master.getEtcmKeyid());

        if (!hasPayload && !hasExisting) {
            throw new IllegalArgumentException("Faculty details with a valid faculty id are required for initial save");
        }

        if (hasPayload) {
            handleFacultyUpsert(faculty, master, now);
        }
        // if no payload and existing rows, skip; faculty already present
    }

    private void populateUnqpStandalone(EntTlTrgCalUnqp unqp, LocalDateTime now) throws Exception {
        if (isBlank(unqp.getEtcuKeyid())) {
            String key = dbActionTemplate.getSequenceNumber(
                    TBL_TRG_CAL_UNQP, KEY_LENGTH, PREFIX_UNQP, EMPTY_DATE_FORMAT, EMPTY_FORMAT_RESET);
            unqp.setEtcuKeyid(key);
        }
        unqp.setEtcuRoleKeyid(defaultString(unqp.getEtcuRoleKeyid(), "-"));
        unqp.setEtcuRoledmt(defaultString(unqp.getEtcuRoledmt(), "-"));
        unqp.setEtcuRolejh(defaultString(unqp.getEtcuRolejh(), "-"));
        if (unqp.getEtcuDateadd() == null) {
            unqp.setEtcuDateadd(now);
        }
        if (unqp.getEtcuCreatedon() == null) {
            unqp.setEtcuCreatedon(now);
        }
        if (unqp.getEtcuModifiedon() == null) {
            unqp.setEtcuModifiedon(now);
        }
        if (isBlank(unqp.getEtcuActive())) {
            unqp.setEtcuActive(defaultChar(unqp.getEtcuActive(), 'Y'));
        }
        unqp.setEtcuTempfield1(defaultString(unqp.getEtcuTempfield1(), "-"));
        unqp.setEtcuTempfield2(defaultString(unqp.getEtcuTempfield2(), "-"));
        unqp.setEtcuTempfield3(defaultString(unqp.getEtcuTempfield3(), "-"));
        unqp.setEtcuTempfield4(defaultString(unqp.getEtcuTempfield4(), "-"));
        unqp.setEtcuTempfield5(defaultString(unqp.getEtcuTempfield5(), "-"));
        unqp.setEtcuCreatedby(defaultString(unqp.getEtcuCreatedby(), "-"));
    }

    private void deleteByEtcm(String sql, String etcmKeyid) {
        entityManager.createNativeQuery(sql)
                .setParameter("etcmKeyid", etcmKeyid)
                .executeUpdate();
    }

    private void mergeMissingMasterFields(EntTlTragcalmst incoming, EntTlTragcalmst existing) {
        if (isBlank(incoming.getEtcmFlid())) incoming.setEtcmFlid(existing.getEtcmFlid());
        if (isBlank(incoming.getEtcmLocation())) incoming.setEtcmLocation(existing.getEtcmLocation());
        if (isBlank(incoming.getEtcmDmt())) incoming.setEtcmDmt(existing.getEtcmDmt());
        if (isBlank(incoming.getEtcmJh())) incoming.setEtcmJh(existing.getEtcmJh());
        if (isBlank(incoming.getEtcmTopicid())) incoming.setEtcmTopicid(existing.getEtcmTopicid());
        if (incoming.getEtcmCaldate() == null) incoming.setEtcmCaldate(existing.getEtcmCaldate());
        if (isBlank(incoming.getEtcmRemarks())) incoming.setEtcmRemarks(existing.getEtcmRemarks());
        if (isBlank(incoming.getEtcmGeneral())) incoming.setEtcmGeneral(existing.getEtcmGeneral());
        if (isBlank(incoming.getEtcmUniquepos())) incoming.setEtcmUniquepos(existing.getEtcmUniquepos());
        if (isBlank(incoming.getEtcmMsd())) incoming.setEtcmMsd(existing.getEtcmMsd());
        if (isBlank(incoming.getEtcmChkcompleted())) incoming.setEtcmChkcompleted(existing.getEtcmChkcompleted());
        if (incoming.getEtcmCompleteddate() == null) incoming.setEtcmCompleteddate(existing.getEtcmCompleteddate());
        if (isBlank(incoming.getEtcmCompletedby())) incoming.setEtcmCompletedby(existing.getEtcmCompletedby());
        if (incoming.getEtcmMaxDuration() == null) incoming.setEtcmMaxDuration(existing.getEtcmMaxDuration());
        if (isBlank(incoming.getEtcmFunction())) incoming.setEtcmFunction(existing.getEtcmFunction());
        if (isBlank(incoming.getEtcmVenue())) incoming.setEtcmVenue(existing.getEtcmVenue());
        if (incoming.getEtcmPermittedstrength() == null) incoming.setEtcmPermittedstrength(existing.getEtcmPermittedstrength());
        if (isBlank(incoming.getEtcmMaterialready())) incoming.setEtcmMaterialready(existing.getEtcmMaterialready());
        if (isBlank(incoming.getEtcmAssessmentrequired())) incoming.setEtcmAssessmentrequired(existing.getEtcmAssessmentrequired());
        if (isBlank(incoming.getEtcmMarkbased())) incoming.setEtcmMarkbased(existing.getEtcmMarkbased());
        if (isBlank(incoming.getEtcmFilemgnid())) incoming.setEtcmFilemgnid(existing.getEtcmFilemgnid());
        if (isBlank(incoming.getEtcmAnchoredby())) incoming.setEtcmAnchoredby(existing.getEtcmAnchoredby());
        if (isBlank(incoming.getEtcmTrainingfunction())) incoming.setEtcmTrainingfunction(existing.getEtcmTrainingfunction());
        if (isBlank(incoming.getEtcmComments())) incoming.setEtcmComments(existing.getEtcmComments());
        if (isBlank(incoming.getEtcmTopiccategory())) incoming.setEtcmTopiccategory(existing.getEtcmTopiccategory());
        if (isBlank(incoming.getEtcmTempfield6())) incoming.setEtcmTempfield6(existing.getEtcmTempfield6());
        if (isBlank(incoming.getEtcmTempfield7())) incoming.setEtcmTempfield7(existing.getEtcmTempfield7());
        if (isBlank(incoming.getEtcmTempfield8())) incoming.setEtcmTempfield8(existing.getEtcmTempfield8());
        if (isBlank(incoming.getEtcmTempfield9())) incoming.setEtcmTempfield9(existing.getEtcmTempfield9());
        if (isBlank(incoming.getEtcmTempfield10())) incoming.setEtcmTempfield10(existing.getEtcmTempfield10());
        if (isBlank(incoming.getEtcmActive())) incoming.setEtcmActive(existing.getEtcmActive());
        if (isBlank(incoming.getEtcmCreatedby())) incoming.setEtcmCreatedby(existing.getEtcmCreatedby());
    }

    private void populateMaster(EntTlTragcalmst master, LocalDateTime now) throws Exception {
        if (isBlank(master.getEtcmKeyid())) {
            String key = dbActionTemplate.getSequenceNumber(
                    TBL_TRG_CAL_MST, KEY_LENGTH, PREFIX_MASTER, EMPTY_DATE_FORMAT, EMPTY_FORMAT_RESET);
            master.setEtcmKeyid(key);
        }
        if (master.getEtcmCreatedon() == null) {
            master.setEtcmCreatedon(now);
        }
        if (master.getEtcmModifiedon() == null) {
            master.setEtcmModifiedon(now);
        }
        if (master.getEtcmCreatedatetime() == null) {
            master.setEtcmCreatedatetime(now);
        }
        if (isBlank(master.getEtcmActive())) {
            master.setEtcmActive(defaultChar(master.getEtcmActive(), 'Y'));
        }
        // Persist empty JSON markers when not provided (legacy DB uses '{}' instead of NULL)
        master.setEtcmDmt(defaultString(master.getEtcmDmt(), "{}"));
        master.setEtcmJh(defaultString(master.getEtcmJh(), "{}"));
        master.setEtcmRemarks(defaultString(master.getEtcmRemarks(), "-"));
        master.setEtcmGeneral(defaultChar(master.getEtcmGeneral(), 'N'));
        master.setEtcmUniquepos(defaultChar(master.getEtcmUniquepos(), 'N'));
        master.setEtcmMsd(defaultChar(master.getEtcmMsd(), 'N'));
        master.setEtcmChkcompleted(defaultChar(master.getEtcmChkcompleted(), 'N'));
        master.setEtcmCompletedby(defaultString(master.getEtcmCompletedby(), "-"));
        if (master.getEtcmCompleteddate() == null) {
            master.setEtcmCompleteddate(now);
        }
        if (master.getEtcmMaxDuration() == null) {
            master.setEtcmMaxDuration(BigDecimal.ZERO);
        }
        master.setEtcmFunction(defaultString(master.getEtcmFunction(), "-"));
        master.setEtcmVenue(defaultString(master.getEtcmVenue(), "-"));
        if (master.getEtcmPermittedstrength() == null) {
            master.setEtcmPermittedstrength(BigDecimal.ZERO);
        }
        master.setEtcmMaterialready(defaultChar(master.getEtcmMaterialready(), 'N'));
        master.setEtcmAssessmentrequired(defaultChar(master.getEtcmAssessmentrequired(), 'N'));
        master.setEtcmMarkbased(defaultChar(master.getEtcmMarkbased(), 'N'));
        master.setEtcmFilemgnid(defaultString(master.getEtcmFilemgnid(), "-"));
        master.setEtcmAnchoredby(defaultString(master.getEtcmAnchoredby(), "-"));
        master.setEtcmTrainingfunction(defaultString(master.getEtcmTrainingfunction(), "-"));
        master.setEtcmComments(defaultString(master.getEtcmComments(), "-"));
        master.setEtcmTopiccategory(defaultString(master.getEtcmTopiccategory(), "-"));
        master.setEtcmTempfield6(defaultString(master.getEtcmTempfield6(), "-"));
        master.setEtcmTempfield7(defaultString(master.getEtcmTempfield7(), "-"));
        master.setEtcmTempfield8(defaultString(master.getEtcmTempfield8(), "-"));
        master.setEtcmTempfield9(defaultString(master.getEtcmTempfield9(), "-"));
        master.setEtcmTempfield10(defaultString(master.getEtcmTempfield10(), "-"));
        master.setEtcmCreatedby(defaultString(master.getEtcmCreatedby(), "-"));
        if (master.getEtcmCaldate() == null) {
            master.setEtcmCaldate(now);
        }
    }

    @Override
@Transactional
public String resetAssessmentForMaintenance(String etcmKeyid) throws Exception {
    if (isBlank(etcmKeyid)) {
        return "0";
    }

    // Application Maintenance la uncheck panna, child-level assessment flag ah force N
    String resetChildSql = """
        UPDATE ent_tl_trgcalempatscore
           SET etca_assessmentcom = 'N'
         WHERE etca_etcm_keyid = :key
    """;
    entityManager.createNativeQuery(resetChildSql)
            .setParameter("key", etcmKeyid.trim())
            .executeUpdate();

    // master status ah um N ah reset pannanum, appothaan Modification grid la varum
    String resetMasterSql = """
        UPDATE ent_tl_trgcalmst
           SET etcm_chkcompleted = 'N'
         WHERE etcm_keyid = :key
    """;
    entityManager.createNativeQuery(resetMasterSql)
            .setParameter("key", etcmKeyid.trim())
            .executeUpdate();

    log.info("Application Maintenance reset – ETCA_ASSESSMENTCOM & ETCM_CHKCOMPLETED reset to 'N' for key={}", etcmKeyid);
    return "1";
}
@Override
@Transactional(readOnly = true)
public String getAssessmentComStatus(String etcmKeyid) throws Exception {
    if (isBlank(etcmKeyid)) return "0";
    String sql = """
        SELECT CASE WHEN COUNT(*) FILTER (WHERE etca_assessmentcom = 'Y') = COUNT(*) 
                    AND COUNT(*) > 0 THEN '1' ELSE '0' END
          FROM ent_tl_trgcalempatscore
         WHERE etca_etcm_keyid = :key
    """;
    Object result = entityManager.createNativeQuery(sql)
            .setParameter("key", etcmKeyid.trim())
            .getSingleResult();
    return result != null ? result.toString() : "0";
}
}
