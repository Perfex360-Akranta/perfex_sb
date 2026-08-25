package com.akranta.perfex_sb.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.AttendanceFilter;
import com.akranta.perfex_sb.model.EntTlTragcalmst;
import com.akranta.perfex_sb.model.EntTlTrgCalQuad;
import com.akranta.perfex_sb.model.EntTlTrgCalSession;
import com.akranta.perfex_sb.model.EntTlTrgFaculty;
import com.akranta.perfex_sb.model.EntTlTtgCalEmpatScore;
import com.akranta.perfex_sb.model.EntTlTrgCalUnqp;
import com.akranta.perfex_sb.repository.EntTlTragcalmstRepository;
import com.akranta.perfex_sb.repository.EntTlTrgCalEmpRepository;
import com.akranta.perfex_sb.repository.EntTlTrgCalQuadRepository;
import com.akranta.perfex_sb.repository.EntTlTrgCalSessionRepository;
import com.akranta.perfex_sb.repository.EntTlTrgCalUnqpRepository;
import com.akranta.perfex_sb.repository.EntTlTrgFacultyRepository;
import com.akranta.perfex_sb.repository.EntTlTtgCalEmpatScoreRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.GridBasedTrainingCalendarService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class GridBasedTrainingCalendarServiceImpl implements GridBasedTrainingCalendarService {

     private static final Logger log = LoggerFactory.getLogger(GridBasedTrainingCalendarServiceImpl.class);

    private static final String TBL_TRG_CAL_MST = "ENT_TL_TRGCALMST";
    private static final String TBL_TRG_CAL_SESSION = "ENT_TL_TRGCALSESSION";
    private static final String TBL_TRG_FACULTY = "ENT_TL_TRGFACULTY";
    private static final String TBL_TRG_CAL_UNQP = "ENT_TL_TRGCALUNQP";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX_MASTER = "ETC";
    private static final String PREFIX_SESSION = "ETS";
    private static final String PREFIX_FACULTY = "ETF";
    private static final String PREFIX_UNQP = "ETU";
    private static final String EMPTY_DATE_FORMAT = "";
    private static final String EMPTY_FORMAT_RESET = "";

    private final EntTlTragcalmstRepository masterRepository;
    private final EntTlTrgCalSessionRepository sessionRepository;
        private final EntTlTrgCalUnqpRepository unqpRepository;
         private final EntTlTtgCalEmpatScoreRepository empScoreRepository;
    private final EntTlTrgFacultyRepository facultyRepository;
    private final EntTlTrgCalEmpRepository empRepository;
        private final EntTlTrgCalQuadRepository quadRepository;
    private final DbActionTemplate dbActionTemplate;

      @PersistenceContext
    private EntityManager entityManager;



    public GridBasedTrainingCalendarServiceImpl(EntTlTragcalmstRepository masterRepository,
                                                EntTlTrgCalSessionRepository sessionRepository,
                                                EntTlTrgCalUnqpRepository unqpRepository,
                                                 EntTlTrgCalEmpRepository empRepository,
                                                 EntTlTtgCalEmpatScoreRepository empScoreRepository,
                                                EntTlTrgFacultyRepository facultyRepository,
                                                 EntTlTrgCalQuadRepository quadRepository,
                                                DbActionTemplate dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.sessionRepository = sessionRepository;
        this.unqpRepository = unqpRepository;
        this.facultyRepository = facultyRepository;
        this.empScoreRepository = empScoreRepository;
        this.empRepository = empRepository;
          this.quadRepository = quadRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public List<EntTlTragcalmst> saveOrUpdate(List<EntTlTragcalmst> masters) throws Exception {
        return process(masters, true, true);
    }

    @Override
    @Transactional
    public List<EntTlTragcalmst> create(List<EntTlTragcalmst> masters) throws Exception {
        return process(masters, true, false);
    }

    @Override
    @Transactional
    public List<EntTlTragcalmst> update(List<EntTlTragcalmst> masters) throws Exception {
        return process(masters, false, true);
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
    public EntTlTragcalmst getById(String etcmKeyid) {
        if (isBlank(etcmKeyid)) {
            throw new IllegalArgumentException("Calendar id is required");
        }
        return masterRepository.findById(etcmKeyid.trim())
                .orElseThrow(() -> new IllegalArgumentException("Training calendar not found: " + etcmKeyid));
    }

    @Override
    @Transactional
    public void deleteById(String etcmKeyid) {
        if (isBlank(etcmKeyid)) {
            return;
        }
        masterRepository.deleteCascadeByEtcmKeyid(etcmKeyid.trim());
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
      private BigDecimal toBigDecimal(Object obj) {
        if (obj == null) return BigDecimal.ZERO;
        try {
            if (obj instanceof BigDecimal bd) return bd;
            return new BigDecimal(obj.toString());
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }


    @Override
    @Transactional
    public EntTlTrgCalSession createSession(EntTlTrgCalSession session) throws Exception {
        if (session == null || isBlank(session.getEtcsEtcmKeyid())) {
            throw new IllegalArgumentException("Calendar id (etcsEtcmKeyid) is required for session create");
        }
        // prevent duplicate slot
        if (session.getEtcsSessiondate() != null && session.getEtcsFromdate() != null && session.getEtcsTilldate() != null) {
            String dateStr = session.getEtcsSessiondate().toLocalDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            String fromStr = session.getEtcsFromdate().format(DateTimeFormatter.ofPattern("HH:mm"));
            String toStr   = session.getEtcsTilldate().format(DateTimeFormatter.ofPattern("HH:mm"));
            int dup = checkSessionDuplicate(session.getEtcsEtcmKeyid(), dateStr, fromStr, toStr, null);
            if (dup > 0) {
                throw new IllegalStateException("Session slot already exists for given date/time");
            }
        }
        LocalDateTime now = LocalDateTime.now();
        populateSession(session, now);
        return sessionRepository.save(session);
    }

    @Override
    @Transactional
    public EntTlTrgCalSession updateSession(EntTlTrgCalSession session) throws Exception {
        if (session == null || isBlank(session.getEtcsKeyid())) {
            throw new IllegalArgumentException("Session keyid is required for update");
        }
        EntTlTrgCalSession existing = sessionRepository.findById(session.getEtcsKeyid().trim())
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + session.getEtcsKeyid()));
        mergeMissingSessionFields(session, existing);
        LocalDateTime now = LocalDateTime.now();
        if (session.getEtcsSessiondate() != null && session.getEtcsFromdate() != null && session.getEtcsTilldate() != null) {
            String dateStr = session.getEtcsSessiondate().toLocalDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            String fromStr = session.getEtcsFromdate().format(DateTimeFormatter.ofPattern("HH:mm"));
            String toStr   = session.getEtcsTilldate().format(DateTimeFormatter.ofPattern("HH:mm"));
            int dup = checkSessionDuplicate(session.getEtcsEtcmKeyid(), dateStr, fromStr, toStr, session.getEtcsKeyid());
            if (dup > 0) {
                throw new IllegalStateException("Session slot already exists for given date/time");
            }
        }
        populateSession(session, now);
        return sessionRepository.save(session);
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
    public int checkSessionDuplicate(String etcmKeyid, String sessionDate, String fromTime, String toTime, String sessionId) {
        if (isBlank(etcmKeyid) || isBlank(sessionDate) || isBlank(fromTime) || isBlank(toTime)) {
            throw new IllegalArgumentException("Calendar id, session date, from time, and to time are required");
        }
        String sessionDateStr = sessionDate.trim();
        String fromDateTime = sessionDateStr + " " + fromTime.trim();
        String toDateTime   = sessionDateStr + " " + toTime.trim();
        return sessionRepository.countExactSessionSlot(
                etcmKeyid.trim(),
                sessionDateStr,
                fromDateTime,
                toDateTime,
                sessionId == null ? "" : sessionId.trim());
    }

    @Override
    @Transactional
    public String deleteDetailRecord(String keyId, String gridId, String trainingId) throws Exception {
        if (isBlank(gridId)) {
            throw new IllegalArgumentException("gridId is required");
        }
        if (isBlank(trainingId)) {
            throw new IllegalArgumentException("TrainingId / etcmKeyid is required");
        }
        if (isBlank(keyId)) {
            throw new IllegalArgumentException("Detail key id is required");
        }

      //  String id = keyId.trim();

        // if ("facultyGrid".equalsIgnoreCase(gridId)) {
        //     facultyRepository.deleteById(id);
        // } else if ("calUniquePosGrid".equalsIgnoreCase(gridId)) {
        //     unqpRepository.deleteById(id);
        // } else {
        //     // session grid
        //     deleteSessionSafe(trainingId, id);
        // }
            String calendarId = trainingId.trim();
        String id = keyId.trim();

        if ("facultyGrid".equalsIgnoreCase(gridId)) {
            deleteFacultySafe(calendarId, id);
       } else if ("calUniquePosGrid".equalsIgnoreCase(gridId)) {
            unqpRepository.deleteById(id);
        } else {
            // session grid
            deleteSessionSafe(calendarId, id);
        }

        return "Data Deleted Successfully";
    }

    private void deleteSessionSafe(String etcmKeyid, String sessionKeyid) {
        if (isBlank(etcmKeyid)) {
            throw new IllegalArgumentException("Calendar id is required");
        }
        String calendarId = etcmKeyid.trim();
       final String sesssionIdToDelete;
        if (isBlank(sessionKeyid) || "false".equalsIgnoreCase(sessionKeyid.trim())) {
            // pick first session for this calendar if none provided
            List<EntTlTrgCalSession> sessions = sessionRepository.findByEtcsEtcmKeyid(calendarId);
            if (sessions == null || sessions.isEmpty()) {
                throw new IllegalStateException("No sessions found for calendar " + calendarId);
            }
            sesssionIdToDelete = sessions.get(0).getEtcsKeyid();
        } else {
            sesssionIdToDelete = sessionKeyid.trim();
        }
        EntTlTrgCalSession session = sessionRepository.findById(sesssionIdToDelete)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sesssionIdToDelete));
        if (!calendarId.equals(session.getEtcsEtcmKeyid())) {
            throw new IllegalArgumentException("Session does not belong to calendar: " + calendarId);
        }
        long count = sessionRepository.countByEtcsEtcmKeyid(calendarId);
        if (count <= 1) {
            throw new IllegalStateException("Cannot delete the only session for this training calendar");
        }
        sessionRepository.deleteById(sesssionIdToDelete);
    }

       private void deleteFacultySafe(String etcmKeyid, String facultyKeyid) {
        if (isBlank(etcmKeyid)) {
            throw new IllegalArgumentException("Calendar id is required");
        }
        String calendarId = etcmKeyid.trim();
        final String facultyIdToDelete;
        if (isBlank(facultyKeyid) || "false".equalsIgnoreCase(facultyKeyid.trim())) {
            List<EntTlTrgFaculty> faculties = facultyRepository.findByEtcfEtcmKeyid(calendarId);
            if (faculties == null || faculties.isEmpty()) {
                throw new IllegalStateException("No faculty found for calendar " + calendarId);
            }
            facultyIdToDelete = faculties.get(0).getEtcfKeyid();
        } else {
            facultyIdToDelete = facultyKeyid.trim();
        }

        EntTlTrgFaculty faculty = facultyRepository.findById(facultyIdToDelete)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found: " + facultyIdToDelete));
        if (!calendarId.equals(faculty.getEtcfEtcmKeyid())) {
            throw new IllegalArgumentException("Faculty does not belong to calendar: " + calendarId);
        }
        long count = facultyRepository.countByEtcfEtcmKeyid(calendarId);
        if (count <= 1) {
            throw new IllegalStateException("Cannot delete the only faculty for this training calendar");
        }
        facultyRepository.deleteById(facultyIdToDelete);
    }

    @Override
    @Transactional
    public EntTlTrgFaculty createOrUpdateFaculty(EntTlTrgFaculty faculty) throws Exception {
        if (faculty == null || isBlank(faculty.getEtcfEtcmKeyid())) {
            throw new IllegalArgumentException("Calendar id (etcfEtcmKeyid) is required for faculty save");
        }
        LocalDateTime now = LocalDateTime.now();
        if (isBlank(faculty.getEtcfKeyid())) {
            populateFaculty(faculty, now, true);
            return facultyRepository.save(faculty);
        }
        EntTlTrgFaculty existing = facultyRepository.findById(faculty.getEtcfKeyid().trim())
                .orElseThrow(() -> new IllegalArgumentException("Faculty record not found: " + faculty.getEtcfKeyid()));
        mergeMissingFacultyFields(faculty, existing);
        populateFaculty(faculty, now, false);
        return facultyRepository.save(faculty);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String[]> getFaculty(String etcmKeyid) throws Exception {
        if (isBlank(etcmKeyid)) {
            return new ArrayList<>();
        }
        List<Object[]> rows = facultyRepository.findFacultyGrid(etcmKeyid.trim());
      //  List<String[]> out = new ArrayList<>(rows.size());
           List<String[]> result = new ArrayList<>(rows.size() + 1);
        // legacy header row expected by UIUtils.convertToJqGridTableObject
        result.add(new String[]{"etcf_keyid", "etcf_name", "Faculty","Faculty" ,"Delete"});
        for (Object[] row : rows) {
            String[] arr = new String[5];
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

        int total = empScoreRepository.countEmployeeAttendanceGrid(etcmKeyid.trim(), filterCond);

        if (payload != null) {
            payload.put("totalRecordCnt", total);
        }
        if (total == 0) {
            return new ArrayList<>();
        }

        List<Object[]> rows = empScoreRepository.findEmployeeAttendanceGrid(
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
        boolean uniquePos = "true".equalsIgnoreCase(uniquePosFlag);

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

    // @Override
    // @Transactional(readOnly = true)
    // public int checkUniquePosition(String etcmKeyid, String roleKeyid, String uniqueKeyid) {
    //     if (isBlank(etcmKeyid) || isBlank(roleKeyid)) {
    //         throw new IllegalArgumentException("Calendar id and role key id are required");
    //     }
    //     String uid = isBlank(uniqueKeyid) ? null : uniqueKeyid.trim();
    //     return unqpRepository.countByCalendarAndRole(etcmKeyid.trim(), roleKeyid.trim(), uid);
    // }

     @Override
    public int checkUniquePosition(String etcmKeyid, String roleKeyid, String uniqueKeyid) {
        if (isBlank(etcmKeyid) || isBlank(roleKeyid)) {
            throw new IllegalArgumentException("Calendar id and role key id are required");
        }
        String uid = isBlank(uniqueKeyid) ? null : uniqueKeyid.trim();
        return unqpRepository.countByCalendarAndRole(etcmKeyid.trim(), roleKeyid.trim(), uid);
    }

    @Override
    @Transactional
    public EntTlTrgCalUnqp createUniquePosition(EntTlTrgCalUnqp unique) throws Exception {
        if (unique == null || isBlank(unique.getEtcuEtcmKeyid())) {
            throw new IllegalArgumentException("Calendar id (etcuEtcmKeyid) is required for unique position save");
        }
        LocalDateTime now = LocalDateTime.now();
        populateUnique(unique, now, true);
        return unqpRepository.save(unique);
    }

    @Override
    @Transactional
    public EntTlTrgCalUnqp updateUniquePosition(EntTlTrgCalUnqp unique) throws Exception {
        if (unique == null || isBlank(unique.getEtcuKeyid())) {
            throw new IllegalArgumentException("Unique position keyid is required for update");
        }
        EntTlTrgCalUnqp existing = unqpRepository.findById(unique.getEtcuKeyid().trim())
                .orElseThrow(() -> new IllegalArgumentException("Unique position not found: " + unique.getEtcuKeyid()));
        mergeMissingUniqueFields(unique, existing);
        LocalDateTime now = LocalDateTime.now();
        populateUnique(unique, now, false);
        return unqpRepository.save(unique);
    }

    private List<EntTlTragcalmst> process(List<EntTlTragcalmst> masters,
                                          boolean allowCreate,
                                          boolean allowUpdate) throws Exception {
        if (masters == null || masters.isEmpty()) {
            return new ArrayList<>();
        }
        LocalDateTime now = LocalDateTime.now();
        List<EntTlTragcalmst> out = new ArrayList<>();
        for (EntTlTragcalmst master : masters) {
            boolean hasKey = !isBlank(master.getEtcmKeyid());
            if (hasKey && !allowUpdate) {
                throw new IllegalArgumentException("Update not allowed in create-only operation for " + master.getEtcmKeyid());
            }
            if (!hasKey && !allowCreate) {
                throw new IllegalArgumentException("Create not allowed in update-only operation");
            }
            EntTlTragcalmst saved = hasKey ? handleUpdate(master, now) : handleCreate(master, now);
            out.add(saved);
        }
        return out;
    }

    private EntTlTragcalmst handleCreate(EntTlTragcalmst master, LocalDateTime now) throws Exception {
        validateMandatory(master);
        Character reqGeneral = normalizeYn(master.getEtcmGeneral());
        Character reqUnique = normalizeYn(master.getEtcmUniquepos());
        Character reqMsd = normalizeYn(master.getEtcmMsd());
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
        applyDefaultValues(master, now);
        enforceRequestedMode(master, reqGeneral, reqUnique, reqMsd);
        EntTlTragcalmst saved = masterRepository.saveAndFlush(master);
        saved = verifyAndForceMode(saved, reqGeneral, reqUnique, reqMsd, "create");
        return saved;
    }

    private EntTlTragcalmst handleUpdate(EntTlTragcalmst incoming, LocalDateTime now) throws Exception {
        String key = incoming.getEtcmKeyid();
        EntTlTragcalmst existing = masterRepository.findById(key.trim())
                .orElseThrow(() -> new IllegalArgumentException("Training calendar not found: " + key));
        Character reqGeneral = normalizeYn(incoming.getEtcmGeneral());
        Character reqUnique = normalizeYn(incoming.getEtcmUniquepos());
        Character reqMsd = normalizeYn(incoming.getEtcmMsd());

        log.info("GRID-SAVE update key={} flags(before-merge) G/UQ/MS={}/{}/{}",
                key,
                incoming.getEtcmGeneral(),
                incoming.getEtcmUniquepos(),
                incoming.getEtcmMsd());

        mergeMissingMasterFields(incoming, existing);

        if (incoming.getEtcmCreatedon() == null) {
            incoming.setEtcmCreatedon(existing.getEtcmCreatedon());
        }
        if (incoming.getEtcmCreatedatetime() == null) {
            incoming.setEtcmCreatedatetime(existing.getEtcmCreatedatetime());
        }
        if (isBlank(incoming.getEtcmCreatedby())) {
            incoming.setEtcmCreatedby(existing.getEtcmCreatedby());
        }

        incoming.setEtcmModifiedon(now);
        applyDefaultValues(incoming, now);
        enforceRequestedMode(incoming, reqGeneral, reqUnique, reqMsd);
        log.info("GRID-SAVE update key={} flags(before-save) G/UQ/MS={}/{}/{}",
                key,
                incoming.getEtcmGeneral(),
                incoming.getEtcmUniquepos(),
                incoming.getEtcmMsd());
        validateMandatory(incoming);
        EntTlTragcalmst saved = masterRepository.saveAndFlush(incoming);
        saved = verifyAndForceMode(saved, reqGeneral, reqUnique, reqMsd, "update");
        return saved;
    }

    private void applyDefaultValues(EntTlTragcalmst master, LocalDateTime now) {
        master.setEtcmDmt(defaultString(master.getEtcmDmt(), "{}"));
        master.setEtcmJh(defaultString(master.getEtcmJh(), "{}"));
        master.setEtcmRemarks(defaultString(master.getEtcmRemarks(), "-"));
        master.setEtcmGeneral(defaultChar(master.getEtcmGeneral(), 'N'));
        master.setEtcmUniquepos(defaultChar(master.getEtcmUniquepos(), 'N'));
        master.setEtcmMsd(defaultChar(master.getEtcmMsd(), 'N'));
        normalizeModeFlags(master);
        master.setEtcmChkcompleted(defaultChar(master.getEtcmChkcompleted(), 'N'));
        if (master.getEtcmCompleteddate() == null) {
            // DB column is NOT NULL; fall back to created datetime, otherwise current time
            master.setEtcmCompleteddate(
                    master.getEtcmCreatedatetime() != null ? master.getEtcmCreatedatetime() : now);
        }
        master.setEtcmCompletedby(defaultString(master.getEtcmCompletedby(), "-"));
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
        master.setEtcmActive(defaultChar(master.getEtcmActive(), 'Y'));
        master.setEtcmCreatedby(defaultString(master.getEtcmCreatedby(), "-"));
        if (master.getEtcmCaldate() == null) {
            master.setEtcmCaldate(now);
        }
    }

    private void populateFaculty(EntTlTrgFaculty faculty, LocalDateTime now, boolean isCreate) throws Exception {
        if (isCreate || isBlank(faculty.getEtcfKeyid())) {
            String key = dbActionTemplate.getSequenceNumber(
                    TBL_TRG_FACULTY, KEY_LENGTH, PREFIX_FACULTY, EMPTY_DATE_FORMAT, EMPTY_FORMAT_RESET);
            faculty.setEtcfKeyid(key);
        }
        if (isBlank(faculty.getEtcfEtcmFlid())) {
            EntTlTragcalmst m = masterRepository.findById(faculty.getEtcfEtcmKeyid().trim()).orElse(null);
            faculty.setEtcfEtcmFlid(m != null ? defaultString(m.getEtcmFlid(), "{}") : "{}");
        }
        if (faculty.getEtcfDateadd() == null) faculty.setEtcfDateadd(now);
        if (faculty.getEtcfCreatedon() == null) faculty.setEtcfCreatedon(now);
        faculty.setEtcfModifiedon(now);
        faculty.setEtcfFacultytype(defaultChar(faculty.getEtcfFacultytype(), '-'));
        faculty.setEtcfTempfield1(defaultString(faculty.getEtcfTempfield1(), "-"));
        faculty.setEtcfTempfield2(defaultString(faculty.getEtcfTempfield2(), "-"));
        faculty.setEtcfTempfield3(defaultString(faculty.getEtcfTempfield3(), "-"));
        faculty.setEtcfTempfield4(defaultString(faculty.getEtcfTempfield4(), "-"));
        faculty.setEtcfTempfield5(defaultString(faculty.getEtcfTempfield5(), "-"));
        faculty.setEtcfActive(defaultChar(faculty.getEtcfActive(), 'Y'));
        faculty.setEtcfCreatedby(defaultString(faculty.getEtcfCreatedby(), "-"));
    }

    private void mergeMissingFacultyFields(EntTlTrgFaculty incoming, EntTlTrgFaculty existing) {
        if (isBlank(incoming.getEtcfEtcmKeyid())) incoming.setEtcfEtcmKeyid(existing.getEtcfEtcmKeyid());
        if (isBlank(incoming.getEtcfEtcmFlid())) incoming.setEtcfEtcmFlid(existing.getEtcfEtcmFlid());
        if (isBlank(incoming.getEtcfFacultyid())) incoming.setEtcfFacultyid(existing.getEtcfFacultyid());
        if (isBlank(incoming.getEtcfFacultytype())) incoming.setEtcfFacultytype(existing.getEtcfFacultytype());
        if (incoming.getEtcfDateadd() == null) incoming.setEtcfDateadd(existing.getEtcfDateadd());
        if (incoming.getEtcfCreatedon() == null) incoming.setEtcfCreatedon(existing.getEtcfCreatedon());
        if (incoming.getEtcfModifiedon() == null) incoming.setEtcfModifiedon(existing.getEtcfModifiedon());
        if (isBlank(incoming.getEtcfTempfield1())) incoming.setEtcfTempfield1(existing.getEtcfTempfield1());
        if (isBlank(incoming.getEtcfTempfield2())) incoming.setEtcfTempfield2(existing.getEtcfTempfield2());
        if (isBlank(incoming.getEtcfTempfield3())) incoming.setEtcfTempfield3(existing.getEtcfTempfield3());
        if (isBlank(incoming.getEtcfTempfield4())) incoming.setEtcfTempfield4(existing.getEtcfTempfield4());
        if (isBlank(incoming.getEtcfTempfield5())) incoming.setEtcfTempfield5(existing.getEtcfTempfield5());
        if (isBlank(incoming.getEtcfActive())) incoming.setEtcfActive(existing.getEtcfActive());
        if (isBlank(incoming.getEtcfCreatedby())) incoming.setEtcfCreatedby(existing.getEtcfCreatedby());
    }

    private void mergeMissingUniqueFields(EntTlTrgCalUnqp incoming, EntTlTrgCalUnqp existing) {
        if (isBlank(incoming.getEtcuEtcmKeyid())) incoming.setEtcuEtcmKeyid(existing.getEtcuEtcmKeyid());
        if (isBlank(incoming.getEtcuRoleKeyid())) incoming.setEtcuRoleKeyid(existing.getEtcuRoleKeyid());
        if (isBlank(incoming.getEtcuRoledmt())) incoming.setEtcuRoledmt(existing.getEtcuRoledmt());
        if (isBlank(incoming.getEtcuRolejh())) incoming.setEtcuRolejh(existing.getEtcuRolejh());
        if (incoming.getEtcuDateadd() == null) incoming.setEtcuDateadd(existing.getEtcuDateadd());
        if (incoming.getEtcuCreatedon() == null) incoming.setEtcuCreatedon(existing.getEtcuCreatedon());
        if (incoming.getEtcuModifiedon() == null) incoming.setEtcuModifiedon(existing.getEtcuModifiedon());
        if (isBlank(incoming.getEtcuTempfield1())) incoming.setEtcuTempfield1(existing.getEtcuTempfield1());
        if (isBlank(incoming.getEtcuTempfield2())) incoming.setEtcuTempfield2(existing.getEtcuTempfield2());
        if (isBlank(incoming.getEtcuTempfield3())) incoming.setEtcuTempfield3(existing.getEtcuTempfield3());
        if (isBlank(incoming.getEtcuTempfield4())) incoming.setEtcuTempfield4(existing.getEtcuTempfield4());
        if (isBlank(incoming.getEtcuTempfield5())) incoming.setEtcuTempfield5(existing.getEtcuTempfield5());
        if (isBlank(incoming.getEtcuActive())) incoming.setEtcuActive(existing.getEtcuActive());
        if (isBlank(incoming.getEtcuCreatedby())) incoming.setEtcuCreatedby(existing.getEtcuCreatedby());
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
        List<String[]> result = new ArrayList<>(rows.size());
    //    List<String[]> result = new ArrayList<>(rows.size() + 1);
    //    result.add(new String[]{"", "etcu_keyid", "role_keyid", "uniqposition", "sect_keyid", "cell_keyid"});
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
public List<String[]> getListTrgCalendarModify(Map<String,Object> payload) throws Exception {
    @SuppressWarnings("unchecked")
    Map<String,Object> gridParams = payload == null ? java.util.Collections.emptyMap()
            : (Map<String,Object>) payload.getOrDefault("gridParams", payload);

    int fromRow  = parseInt(gridParams.get("fromRow"), 1);
    int pageSize = parseInt(gridParams.get("pageSize"), 0);
    if (pageSize <= 0) {
        int toRow = parseInt(gridParams.get("toRow"), 0);
        pageSize = (toRow > 0 && toRow >= fromRow) ? Math.min(100, toRow - fromRow + 1) : 100;
    }

    String key        = firstText(payload, "trgCalId", "etcmKeyid", "keyid", "key");
    String sectionId  = firstText(payload, "sectionId", "dmt");
    String cellId     = firstText(payload, "cellId", "jh");
    String tradeId    = firstText(payload, "tradeId", "trarId", "trainingFunction");
    String uniquePos  = firstText(payload, "uniqPostn", "uniquePos");
    String flid       = firstText(payload, "flid");
    String fromDate   = firstText(payload, "fromDate");
    String toDate     = firstText(payload, "toDate", "todate");

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.ENGLISH);
    if (isBlank(fromDate) || isBlank(toDate)) {
        LocalDate now   = LocalDate.now();
        LocalDate start = now.minusMonths(2).withDayOfMonth(1);
        fromDate = start.format(fmt);
        toDate   = now.format(fmt);
    }

    StringBuilder cond = new StringBuilder();
    Map<String,Object> params = new HashMap<>();

    if (!isBlank(key)) {
        cond.append(" AND etcm.etcm_keyid = :key");
        params.put("key", key.trim());
    } else {
        if (!isBlank(sectionId)) { cond.append(" AND etcm.etcm_dmt = :sectionId"); params.put("sectionId", sectionId.trim()); }
        if (!isBlank(cellId))    { cond.append(" AND etcm.etcm_jh  = :cellId");    params.put("cellId", cellId.trim()); }
        if (!isBlank(tradeId))   { cond.append(" AND etcm.etcm_trainingfunction = :tradeId"); params.put("tradeId", tradeId.trim()); }
        if (!isBlank(fromDate) && !isBlank(toDate)) {
            cond.append(" AND etcm.etcm_caldate BETWEEN :fromDate AND :toDate");
            params.put("fromDate", LocalDate.parse(fromDate, fmt));
            params.put("toDate",   LocalDate.parse(toDate, fmt));
        }
        if (!isBlank(uniquePos)) {
            cond.append(" AND etcm.etcm_keyid IN (SELECT etcu_etcm_keyid FROM ent_tl_trgcalunqp WHERE etcu_role_keyid = :uniquePos)");
            params.put("uniquePos", uniquePos.trim());
        }
    }
    if (!isBlank(flid)) {
        cond.append(" AND etcm.etcm_flid = flid AND position(:flid in (parentflids || '/' || flid)) > 0");
        params.put("flid", flid.trim());
    }

    int total = masterRepository.countGridCalendarModify(cond.toString(), params);
    if (payload != null) payload.put("totalRecordCnt", total);
    if (total == 0) return new ArrayList<>();

    List<Object[]> rows = masterRepository.findGridCalendarModify(cond.toString(), params, fromRow, pageSize);

    String[] header = {
            "etcm_keyid","etcm_dmt","etcm_jh","etcm_flid","etcm_location","etcm_createdatetime",
            "etcm_anchoredby","anchoredbyid","topi_name","etcm_topicid","tcat_name","etcm_topiccategory",
            "idenfiedthg","etcm_function","trdm_name","etcm_trainingfunction","venu_name","etcm_venue",
            "uniqpose","uniqposeid","etcm_caldate","etcm_permittedstrength","etcm_max_duration",
            "assessmentrequiredtxt","etcm_assessmentrequired","materialreadytxt","etcm_materialready",
            "markbasedtxt","etcm_markbased","role_name","uniqpos","employeeadd","empattednce",
            "plannedempm_name","assemntcompl","etcm_tempfield6","trncompl","trncomplid","compltdate",
            "completedby","etcm_completedby","etcm_rating","ratingid","etcm_comments","filemgr",
            "planed","attend","adherence","manhourse"
    };

    List<String[]> result = new ArrayList<>(rows.size() + 1);
    result.add(header);
    for (Object[] row : rows) {
        String[] arr = new String[header.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = row != null && row.length > i && row[i] != null ? row[i].toString() : "";
        }
        result.add(arr);
    }
    return result;
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
    @Override
    @Transactional(readOnly = true)
    public List<String[]> getListTrgCalendarView(Map<String,Object> payload) throws Exception {
        final String[] header = {
                "etcm_keyid","etcm_dmt","etcm_jh","etcm_flid","etcm_location","etcm_createdatetime",
                "etcm_anchoredby","anchoredbyid","topi_name","etcm_topicid","tcat_name","etcm_topiccategory",
                "idenfiedthg","etcm_function","trdm_name","etcm_trainingfunction","venu_name","etcm_venue",
                "uniqpose","uniqposeid","role_name","etcm_caldate","sessions","etcm_permittedstrength",
                "etcm_max_duration","ftym_name","assessmentrequiredtxt","etcm_assessmentrequired",
                "materialreadytxt","etcm_materialready","markbasedtxt","etcm_markbased","uniqpos",
                "employeeadd","empattednce","assemntcompl","etcm_tempfield6","trncompl","trncomplid",
                "compltdate","completedby","etcm_completedby","plannedempm_name","presentempm_name",
                "planed","attend","adherence","manhourse","absent","etcm_rating","ratingid",
                "etcm_comments","filemgr"
        };

        List<String[]> result = new ArrayList<>();
        result.add(header);

        @SuppressWarnings("unchecked")
        Map<String,Object> gridParams = payload == null ? java.util.Collections.emptyMap()
                : (Map<String,Object>) payload.getOrDefault("gridParams", payload);

        int fromRow  = parseInt(gridParams.get("fromRow"), 1);
        int pageSize = parseInt(gridParams.get("pageSize"), 0);
        if (pageSize <= 0) {
            int toRow = parseInt(gridParams.get("toRow"), 0);
            pageSize = (toRow > 0 && toRow >= fromRow) ? Math.min(100, toRow - fromRow + 1) : 100;
        }

        String key        = firstText(payload, "trgCalId", "etcmKeyid", "keyid", "key");
        String sectionId  = firstText(payload, "sectionId", "dmt");
        String cellId     = firstText(payload, "cellId", "jh");
        String tradeId    = firstText(payload, "tradeId", "trarId", "trainingFunction");
        String uniquePos  = firstText(payload, "uniqPostn", "uniquePos");
        String flid       = firstText(payload, "flid");
        String fromDate   = firstText(payload, "fromDate");
        String toDate     = firstText(payload, "toDate", "todate");

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.ENGLISH);
        if (isBlank(fromDate) || isBlank(toDate)) {
            LocalDate now   = LocalDate.now();
            LocalDate start = now.minusMonths(2).withDayOfMonth(1);
            fromDate = start.format(fmt);
            toDate   = now.format(fmt);
        }

        StringBuilder cond = new StringBuilder();
        Map<String,Object> params = new HashMap<>();

        if (!isBlank(key)) {
            cond.append(" AND etcm.etcm_keyid = :key");
            params.put("key", key.trim());
        } else {
            if (!isBlank(sectionId)) { cond.append(" AND etcm.etcm_dmt = :sectionId"); params.put("sectionId", sectionId.trim()); }
            if (!isBlank(cellId))    { cond.append(" AND etcm.etcm_jh  = :cellId");    params.put("cellId", cellId.trim()); }
            if (!isBlank(tradeId))   { cond.append(" AND etcm.etcm_trainingfunction = :tradeId"); params.put("tradeId", tradeId.trim()); }
            if (!isBlank(fromDate) && !isBlank(toDate)) {
                cond.append(" AND etcm.etcm_caldate BETWEEN :fromDate AND :toDate");
                params.put("fromDate", LocalDate.parse(fromDate, fmt));
                params.put("toDate",   LocalDate.parse(toDate, fmt));
            }
            if (!isBlank(uniquePos)) {
                cond.append(" AND etcm.etcm_keyid IN (SELECT etcu_etcm_keyid FROM ent_tl_trgcalunqp WHERE etcu_role_keyid = :uniquePos)");
                params.put("uniquePos", uniquePos.trim());
            }
        }
        if (!isBlank(flid)) {
            cond.append(" AND etcm.etcm_flid = flid AND position(:flid in (parentflids || '/' || flid)) > 0");
            params.put("flid", flid.trim());
        }

        int total = masterRepository.countGridCalendarView(cond.toString(), params);
        if (payload != null) payload.put("totalRecordCnt", total);
        if (total == 0) return result;

        List<Object[]> rows = masterRepository.findGridCalendarView(cond.toString(), params, fromRow, pageSize);

        for (Object[] row : rows) {
            String[] arr = new String[header.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = row != null && row.length > i && row[i] != null ? row[i].toString() : "";
            }
            result.add(arr);
        }
        return result;
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

    private void populateSession(EntTlTrgCalSession session, LocalDateTime now) throws Exception {
        if (isBlank(session.getEtcsKeyid())) {
            String key = dbActionTemplate.getSequenceNumber(
                    TBL_TRG_CAL_SESSION, KEY_LENGTH, PREFIX_SESSION, EMPTY_DATE_FORMAT, EMPTY_FORMAT_RESET);
            session.setEtcsKeyid(key);
        }
        if (isBlank(session.getEtcsName()) || "Session".equalsIgnoreCase(session.getEtcsName())) {
            long count = sessionRepository.countByEtcsEtcmKeyid(session.getEtcsEtcmKeyid());
            session.setEtcsName("Session" + (count + 1));
        }
        if (session.getEtcsSessiondate() == null ||
                session.getEtcsFromdate() == null ||
                session.getEtcsTilldate() == null) {
            throw new IllegalArgumentException("Session date/from/to time are required");
        }
        if (session.getEtcsDateadd() == null) session.setEtcsDateadd(now);
        if (session.getEtcsCreatedon() == null) session.setEtcsCreatedon(now);
        session.setEtcsModifiedon(now);
        session.setEtcsEtcmFlid(defaultString(session.getEtcsEtcmFlid(), "-"));
        session.setEtcsTempfield1(defaultString(session.getEtcsTempfield1(), "-"));
        session.setEtcsTempfield2(defaultString(session.getEtcsTempfield2(), "-"));
        session.setEtcsTempfield3(defaultString(session.getEtcsTempfield3(), "-"));
        session.setEtcsTempfield4(defaultString(session.getEtcsTempfield4(), "-"));
        session.setEtcsTempfield5(defaultString(session.getEtcsTempfield5(), "-"));
        if (isBlank(session.getEtcsActive())) {
            session.setEtcsActive(defaultChar(session.getEtcsActive(), 'Y'));
        }
    }

    private void populateUnique(EntTlTrgCalUnqp unqp, LocalDateTime now, boolean isCreate) throws Exception {
        if (isBlank(unqp.getEtcuKeyid())) {
            String key = dbActionTemplate.getSequenceNumber(
                    TBL_TRG_CAL_UNQP, KEY_LENGTH, PREFIX_UNQP, EMPTY_DATE_FORMAT, EMPTY_FORMAT_RESET);
            unqp.setEtcuKeyid(key);
        }
        unqp.setEtcuEtcmKeyid(defaultString(unqp.getEtcuEtcmKeyid(), "{}"));
        unqp.setEtcuRoleKeyid(defaultString(unqp.getEtcuRoleKeyid(), "-"));
        unqp.setEtcuRoledmt(defaultString(unqp.getEtcuRoledmt(), "-"));
        unqp.setEtcuRolejh(defaultString(unqp.getEtcuRolejh(), "-"));
        if (unqp.getEtcuDateadd() == null) unqp.setEtcuDateadd(now);
        if (unqp.getEtcuCreatedon() == null) unqp.setEtcuCreatedon(now);
        unqp.setEtcuModifiedon(now);
        unqp.setEtcuTempfield1(defaultString(unqp.getEtcuTempfield1(), "-"));
        unqp.setEtcuTempfield2(defaultString(unqp.getEtcuTempfield2(), "-"));
        unqp.setEtcuTempfield3(defaultString(unqp.getEtcuTempfield3(), "-"));
        unqp.setEtcuTempfield4(defaultString(unqp.getEtcuTempfield4(), "-"));
        unqp.setEtcuTempfield5(defaultString(unqp.getEtcuTempfield5(), "-"));
        if (isBlank(unqp.getEtcuActive())) {
            unqp.setEtcuActive(defaultChar(unqp.getEtcuActive(), 'Y'));
        }
        unqp.setEtcuCreatedby(defaultString(unqp.getEtcuCreatedby(), "-"));
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

    private void mergeMissingSessionFields(EntTlTrgCalSession incoming, EntTlTrgCalSession existing) {
        if (isBlank(incoming.getEtcsEtcmKeyid())) incoming.setEtcsEtcmKeyid(existing.getEtcsEtcmKeyid());
        if (isBlank(incoming.getEtcsEtcmFlid())) incoming.setEtcsEtcmFlid(existing.getEtcsEtcmFlid());
        if (isBlank(incoming.getEtcsName())) incoming.setEtcsName(existing.getEtcsName());
        if (incoming.getEtcsSessiondate() == null) incoming.setEtcsSessiondate(existing.getEtcsSessiondate());
        if (incoming.getEtcsFromdate() == null) incoming.setEtcsFromdate(existing.getEtcsFromdate());
        if (incoming.getEtcsTilldate() == null) incoming.setEtcsTilldate(existing.getEtcsTilldate());
        if (incoming.getEtcsDateadd() == null) incoming.setEtcsDateadd(existing.getEtcsDateadd());
        if (isBlank(incoming.getEtcsTempfield1())) incoming.setEtcsTempfield1(existing.getEtcsTempfield1());
        if (isBlank(incoming.getEtcsTempfield2())) incoming.setEtcsTempfield2(existing.getEtcsTempfield2());
        if (isBlank(incoming.getEtcsTempfield3())) incoming.setEtcsTempfield3(existing.getEtcsTempfield3());
        if (isBlank(incoming.getEtcsTempfield4())) incoming.setEtcsTempfield4(existing.getEtcsTempfield4());
        if (isBlank(incoming.getEtcsTempfield5())) incoming.setEtcsTempfield5(existing.getEtcsTempfield5());
        if (isBlank(incoming.getEtcsActive())) incoming.setEtcsActive(existing.getEtcsActive());
        if (incoming.getEtcsCreatedon() == null) incoming.setEtcsCreatedon(existing.getEtcsCreatedon());
    }

    private void validateMandatory(EntTlTragcalmst master) {
        if (isBlank(master.getEtcmFlid())) {
            throw new IllegalArgumentException("Factory (flid) is required");
        }
        if (isBlank(master.getEtcmLocation())) {
            throw new IllegalArgumentException("Location is required");
        }
        if (isBlank(master.getEtcmTopicid())) {
            throw new IllegalArgumentException("Topic id is required");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty() || "{}".equals(value.trim()) || "null".equalsIgnoreCase(value.trim());
    }

    private boolean isBlank(Character value) {
        return value == null;
    }
   private int parseInt(Object obj, int defaultVal) {
        int v = toInt(obj);
        return v == 0 ? defaultVal : v;
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

    private String defaultString(String value, String fallback) {
        return isBlank(value) ? fallback : value;
    }

    private Character defaultChar(Character value, char fallback) {
        return value == null ? fallback : value;
    }

    private void normalizeModeFlags(EntTlTragcalmst master) {
        Character general = normalizeYn(master.getEtcmGeneral());
        Character unique = normalizeYn(master.getEtcmUniquepos());
        Character msd = normalizeYn(master.getEtcmMsd());

        if (general != null && general == 'Y') {
            master.setEtcmGeneral('Y');
            master.setEtcmUniquepos('N');
            master.setEtcmMsd('N');
            return;
        }
        if (unique != null && unique == 'Y') {
            master.setEtcmGeneral('N');
            master.setEtcmUniquepos('Y');
            master.setEtcmMsd('N');
            return;
        }
        if (msd != null && msd == 'Y') {
            master.setEtcmGeneral('N');
            master.setEtcmUniquepos('N');
            master.setEtcmMsd('Y');
            return;
        }

        // Legacy UI always chooses one mode; default to General when mode is ambiguous.
        master.setEtcmGeneral('Y');
        master.setEtcmUniquepos('N');
        master.setEtcmMsd('N');
    }

    private Character normalizeYn(Character value) {
        if (value == null) {
            return null;
        }
        char c = Character.toUpperCase(value);
        return (c == 'Y' || c == 'N') ? c : null;
    }

    private void enforceRequestedMode(EntTlTragcalmst master,
                                      Character reqGeneral,
                                      Character reqUnique,
                                      Character reqMsd) {
        if (master == null) {
            return;
        }
        if (reqGeneral != null && reqGeneral == 'Y') {
            master.setEtcmGeneral('Y');
            master.setEtcmUniquepos('N');
            master.setEtcmMsd('N');
            return;
        }
        if (reqUnique != null && reqUnique == 'Y') {
            master.setEtcmGeneral('N');
            master.setEtcmUniquepos('Y');
            master.setEtcmMsd('N');
            return;
        }
        if (reqMsd != null && reqMsd == 'Y') {
            master.setEtcmGeneral('N');
            master.setEtcmUniquepos('N');
            master.setEtcmMsd('Y');
        }
    }

    private boolean isRequestedModeMismatch(Character dbGeneral,
                                            Character dbUnique,
                                            Character dbMsd,
                                            Character reqGeneral,
                                            Character reqUnique,
                                            Character reqMsd) {
        if (reqGeneral != null && reqGeneral == 'Y') {
            return dbGeneral == null || Character.toUpperCase(dbGeneral) != 'Y';
        }
        if (reqUnique != null && reqUnique == 'Y') {
            return dbUnique == null || Character.toUpperCase(dbUnique) != 'Y';
        }
        if (reqMsd != null && reqMsd == 'Y') {
            return dbMsd == null || Character.toUpperCase(dbMsd) != 'Y';
        }
        return false;
    }

    private EntTlTragcalmst verifyAndForceMode(EntTlTragcalmst saved,
                                                Character reqGeneral,
                                                Character reqUnique,
                                                Character reqMsd,
                                                String flow) {
        if (saved == null || isBlank(saved.getEtcmKeyid())) {
            return saved;
        }
        if ((reqGeneral == null || reqGeneral != 'Y')
                && (reqUnique == null || reqUnique != 'Y')
                && (reqMsd == null || reqMsd != 'Y')) {
            return saved;
        }

        Character[] dbFlags = readModeFlagsFromDb(saved.getEtcmKeyid().trim());
        Character dbGeneral = dbFlags[0];
        Character dbUnique = dbFlags[1];
        Character dbMsd = dbFlags[2];

        if (!isRequestedModeMismatch(dbGeneral, dbUnique, dbMsd, reqGeneral, reqUnique, reqMsd)) {
            saved.setEtcmGeneral(dbGeneral);
            saved.setEtcmUniquepos(dbUnique);
            saved.setEtcmMsd(dbMsd);
            return saved;
        }

        log.warn("GRID-SAVE {} mode mismatch key={} req(G/UQ/MS)={}/{}/{} db(G/UQ/MS)={}/{}/{}; forcing DB update",
                flow,
                saved.getEtcmKeyid(),
                reqGeneral,
                reqUnique,
                reqMsd,
                dbGeneral,
                dbUnique,
                dbMsd);
        System.out.println("GRID-SAVE " + flow + " mode mismatch key=" + saved.getEtcmKeyid()
                + " req(G/UQ/MS)=" + reqGeneral + "/" + reqUnique + "/" + reqMsd
                + " db(G/UQ/MS)=" + dbGeneral + "/" + dbUnique + "/" + dbMsd + " -> forcing");

        forceModeInDb(saved.getEtcmKeyid().trim(), reqGeneral, reqUnique, reqMsd);
        Character[] forcedFlags = readModeFlagsFromDb(saved.getEtcmKeyid().trim());
        saved.setEtcmGeneral(forcedFlags[0]);
        saved.setEtcmUniquepos(forcedFlags[1]);
        saved.setEtcmMsd(forcedFlags[2]);

        return saved;
    }

    private Character[] readModeFlagsFromDb(String keyid) {
        Object[] row = (Object[]) entityManager.createNativeQuery("""
                SELECT etcm_general, etcm_uniquepos, etcm_msd
                FROM ent_tl_trgcalmst
                WHERE etcm_keyid = :key
                """)
                .setParameter("key", keyid)
                .getSingleResult();
        Character g = toYnFlag(row[0]);
        Character u = toYnFlag(row[1]);
        Character m = toYnFlag(row[2]);
        return new Character[] { g, u, m };
    }

    private void forceModeInDb(String keyid, Character reqGeneral, Character reqUnique, Character reqMsd) {
        char g = 'N';
        char u = 'N';
        char m = 'N';
        if (reqGeneral != null && reqGeneral == 'Y') {
            g = 'Y';
        } else if (reqUnique != null && reqUnique == 'Y') {
            u = 'Y';
        } else if (reqMsd != null && reqMsd == 'Y') {
            m = 'Y';
        }

        entityManager.createNativeQuery("""
                UPDATE ent_tl_trgcalmst
                SET etcm_general = :g,
                    etcm_uniquepos = :u,
                    etcm_msd = :m
                WHERE etcm_keyid = :key
                """)
                .setParameter("g", String.valueOf(g))
                .setParameter("u", String.valueOf(u))
                .setParameter("m", String.valueOf(m))
                .setParameter("key", keyid)
                .executeUpdate();
        entityManager.flush();
    }

    private Character toYnFlag(Object dbVal) {
        if (dbVal == null) {
            return null;
        }
        String s = String.valueOf(dbVal).trim();
        if (s.isEmpty()) {
            return null;
        }
        char c = Character.toUpperCase(s.charAt(0));
        return (c == 'Y' || c == 'N') ? c : null;
    }
   
    @Transactional(readOnly = true)
    public List<String[]> getListTrgCalendar(String etcmKeyid, java.util.Map<String, Object> payload) throws Exception {
        if (isBlank(etcmKeyid)) {
            return new ArrayList<>();
        }

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

        int total = masterRepository.countGridCalendar(etcmKeyid.trim(), filterCond);
        if (payload != null) {
            payload.put("totalRecordCnt", total);
        }
        if (total == 0) {
            return new ArrayList<>();
        }

        List<Object[]> rows = masterRepository.findGridCalendar(
                etcmKeyid.trim(),
                filterCond,
                fromRow,
                toRow > 0 ? toRow : null
        );

        // Build header to mimic legacy DbActionTemplate behavior
        String[] header = {
                "etcm_keyid","etcm_dmt","etcm_jh","etcm_flid","etcm_location","etcm_createdatetime",
                "etcm_anchoredby","anchoredbyid","topi_name","etcm_topicid","tcat_name","etcm_topiccategory",
                "idenfiedthg","etcm_function","trdm_name","etcm_trainingfunction","venu_name","etcm_venue",
                "uniqpose","uniqposeid","etcm_caldate","etcm_permittedstrength","etcm_max_duration",
                "assessment_text","etcm_assessmentrequired","material_text","etcm_materialready",
                "markbased_text","etcm_markbased","role_name","uniqpos","employeeadd","empattednce",
                "plannedempm_name","assemntcompl","etcm_tempfield6","trncompl","trncomplid","compltdate",
                "completedby","etcm_completedby","etcm_rating","ratingid","etcm_comments","filemgr",
                "planedemp","attndempl","adherence","manhourse"
        };

        List<String[]> result = new ArrayList<>(rows.size() + 1);
        result.add(header);
        for (Object[] row : rows) {
            String[] arr = new String[header.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = row != null && row.length > i && row[i] != null ? row[i].toString() : "";
            }
            result.add(arr);
        }
        return result;
    }
}
