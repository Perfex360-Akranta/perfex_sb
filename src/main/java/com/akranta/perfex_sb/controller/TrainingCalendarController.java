package com.akranta.perfex_sb.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import com.akranta.perfex_sb.dto.EntTlTragcalmstDto;
import com.akranta.perfex_sb.dto.EntTlTrgCalEmpDto;
import com.akranta.perfex_sb.dto.EntTlTrgCalSessionDto;
import com.akranta.perfex_sb.dto.EntTlTrgCalUnqpDto;
import com.akranta.perfex_sb.dto.EntTlTrgFacultyDto;
import com.akranta.perfex_sb.dto.EntTlTtgCalEmpatScoreDto;
import com.akranta.perfex_sb.dto.EmployeeAttendanceRequest;
import com.akranta.perfex_sb.dto.TrainingCalendarRequest;
import com.akranta.perfex_sb.model.EntTlTragcalmst;
import com.akranta.perfex_sb.model.EntTlTrgCalEmp;
import com.akranta.perfex_sb.model.EntTlTrgCalSession;
import com.akranta.perfex_sb.model.EntTlTrgCalUnqp;
import com.akranta.perfex_sb.model.EntTlTrgFaculty;
import com.akranta.perfex_sb.model.EntTlTtgCalEmpatScore;
import com.akranta.perfex_sb.service.TrainingCalendarService;
import com.akranta.perfex_sb.util.FlexibleDateParser;

@RestController
@RequestMapping("/api/training-calendar")
public class TrainingCalendarController {

    private final TrainingCalendarService service;
    private static final ConcurrentMap<String, SessionCheckRef> SESSION_CHECK_CACHE = new ConcurrentHashMap<>();
    private static final long SESSION_CHECK_TTL_MILLIS = 15 * 60 * 1000L;

    public TrainingCalendarController(TrainingCalendarService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> create(@RequestBody TrainingCalendarRequest request,
                                      HttpServletRequest httpRequest) throws Exception {
        EntTlTragcalmst master = mapMaster(request.getMaster());
        EntTlTrgCalSession session = mapSession(request.getSession());
        if (master == null) {
            master = new EntTlTragcalmst();
        }
        String fallbackCalendarId = firstNonBlank(
                firstNonBlank(request.getCalendarId(), master.getEtcmKeyid()),
                session == null ? null : session.getEtcsEtcmKeyid());
        if (hasText(fallbackCalendarId) && !hasText(master.getEtcmKeyid())) {
            master.setEtcmKeyid(fallbackCalendarId.trim());
        }
        if (session != null && !hasText(session.getEtcsEtcmKeyid()) && hasText(master.getEtcmKeyid())) {
            session.setEtcsEtcmKeyid(master.getEtcmKeyid());
        }
        if (!hasText(master.getEtcmKeyid()) && session != null) {
            String keyFromCheck = resolveCalendarIdFromRecentSessionCheck(session, httpRequest);
            if (hasText(keyFromCheck)) {
                master.setEtcmKeyid(keyFromCheck);
                if (!hasText(session.getEtcsEtcmKeyid())) {
                    session.setEtcsEtcmKeyid(keyFromCheck);
                }
            }
        }
        EntTlTrgFaculty faculty = map(request.getFaculty(), EntTlTrgFaculty.class);
        List<EntTlTrgCalUnqp> uniquePositions = mapUnqpList(request.getUniquePositions());

        EntTlTragcalmst saved = service.create(master, session, faculty, uniquePositions);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Data Saved Successfully");
        resp.put("traCalId", saved.getEtcmKeyid());
        return resp;
    }

    @GetMapping("/{etcmKeyid}")
    public Map<String, Object> fetchMaster(@PathVariable String etcmKeyid) {
        EntTlTragcalmst master = service.getById(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("master", toLegacyMaster(master));
        resp.put("traCalId", etcmKeyid);
        return resp;
    }

    @GetMapping("/{etcmKeyid}/empdata")
    public Map<String, Object> fetchEmpDataCount(@PathVariable String etcmKeyid) throws Exception {
        String cnt = service.getEmpDataCount(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("empcount", cnt);
        resp.put("keyid", etcmKeyid);
        return resp;
    }

    @GetMapping("/{etcmKeyid}/empattn")
    public Map<String, Object> fetchEmpAttendanceCount(@PathVariable String etcmKeyid) throws Exception {
        String cnt = service.getEmpAttendanceCount(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("empattn", cnt);
        resp.put("keyid", etcmKeyid);
        return resp;
    }

    @GetMapping("/{etcmKeyid}/maxmarks")
    public Map<String, Object> fetchMaxMarks(@PathVariable String etcmKeyid) throws Exception {
        String max = service.getMaxMarks(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("maxmarks", max);
        resp.put("keyid", etcmKeyid);
        return resp;
    }

    @GetMapping("/{etcmKeyid}/cutoff")
    public Map<String, Object> fetchCutoff(@PathVariable String etcmKeyid) throws Exception {
        String cutoff = service.getCutoff(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("cutoff", cutoff);
        resp.put("keyid", etcmKeyid);
        return resp;
    }

    @GetMapping("/{etcmKeyid}/assestype")
    public Map<String, Object> fetchAssesType(@PathVariable String etcmKeyid) throws Exception {
        String assesType = service.getAssesType(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("assesType", assesType);
        resp.put("keyid", etcmKeyid);
        return resp;
    }

    @GetMapping("/{etcmKeyid}/faculty")
    public Map<String, Object> fetchFaculty(@PathVariable String etcmKeyid) throws Exception {
        List<String[]> data = service.getFaculty(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("faculty", data);
        resp.put("keyid", etcmKeyid);
        return resp;
    }

  @GetMapping("/{etcmKeyid}/session")
    public Map<String, Object> fetchSessionGrid(@PathVariable String etcmKeyid) throws Exception {
        List<String[]> data = service.getsession(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("session", data);
        resp.put("keyid", etcmKeyid);
        return resp;
    }

      @GetMapping("/{etcmKeyid}/unique")
    public Map<String, Object> fetchUniquePositions(@PathVariable String etcmKeyid) throws Exception {
        List<String[]> data = service.getNewUniqPosData(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("unique", data);
        resp.put("keyid", etcmKeyid);
        return resp;
    }

    @GetMapping("/{etcmKeyid}/unique/roles")
    public Map<String, Object> fetchUniqueRoleSelection(@PathVariable String etcmKeyid,
                                                        @RequestParam(value = "Calendarflid", required = false) String calendarFlid) throws Exception {
        List<String[]> data = service.gwtJHRoleUniquePos(calendarFlid, etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("uniquerole", data);
        resp.put("keyid", etcmKeyid);
        resp.put("flid", calendarFlid);
        return resp;
    }

    @PostMapping("/{etcmKeyid}/unique/employees")
    public Map<String, Object> fetchUniqueEmployees(@PathVariable String etcmKeyid,
                                                    @RequestBody Map<String, Object> payload) throws Exception {
        List<String[]> data = service.getAllUniqueEmployee(etcmKeyid, payload);
        Map<String, Object> resp = new HashMap<>();
        resp.put("uniqueemployees", data);
        resp.put("keyid", etcmKeyid);
        if (payload != null && payload.get("totalRecordCnt") != null) {
            resp.put("totalRecordCnt", payload.get("totalRecordCnt"));
        }
        return resp;
    }

    @PostMapping("/unique/employees/popup")
    public Map<String, Object> fetchUniqueEmployeesPopup(@RequestBody Map<String, Object> payload) throws Exception {
        List<String[]> data = service.getAllUniqueEmployeePopup(payload);
        Map<String, Object> resp = new HashMap<>();
        resp.put("uniqueemployeepopup", data);
        if (payload != null) {
            Object total = payload.get("totalRecordCnt");
            if (total != null) {
                resp.put("totalRecordCnt", total);
            }
            Object key = payload.getOrDefault("key", payload.get("etcmKeyid"));
            if (key != null) {
                resp.put("keyid", key);
            }
        }
        return resp;
    }

    @PostMapping("/{etcmKeyid}/attendance/fetch")
    public Map<String, Object> fetchEmployeeAttendance(@PathVariable String etcmKeyid,
                                                       @RequestBody(required = false) Map<String, Object> payload) throws Exception {
        List<String[]> data = service.getEmployeeAttendance(etcmKeyid, payload);
        Map<String, Object> resp = new HashMap<>();
        resp.put("attendance", data);
        resp.put("keyid", etcmKeyid);
        if (payload != null && payload.get("totalRecordCnt") != null) {
            resp.put("totalRecordCnt", payload.get("totalRecordCnt"));
        }
        return resp;
    }

                                                        
    @PutMapping("/{etcmKeyid}")
    public Map<String, Object> update(@PathVariable String etcmKeyid,
                                      @RequestBody TrainingCalendarRequest request) throws Exception {
        EntTlTragcalmst master = mapMaster(request.getMaster());
        if (master == null) {
            master = new EntTlTragcalmst();
        }
        if (master.getEtcmKeyid() == null || master.getEtcmKeyid().isBlank()) {
            master.setEtcmKeyid(etcmKeyid);
        }
        EntTlTrgCalSession session = mapSession(request.getSession());
        EntTlTrgFaculty faculty = map(request.getFaculty(), EntTlTrgFaculty.class);
        List<EntTlTrgCalUnqp> uniquePositions = mapUnqpList(request.getUniquePositions());

        EntTlTragcalmst saved = service.update(master, session, faculty, uniquePositions);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Data Updated Successfully");
        resp.put("traCalId", saved.getEtcmKeyid());
        return resp;
    }

    @DeleteMapping("/{etcmKeyid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String etcmKeyid) throws Exception {
        service.delete(etcmKeyid);
    }

    @DeleteMapping("/{etcmKeyid}/sessions/{sessionKeyid}")
    public Map<String, Object> deleteSession(@PathVariable String etcmKeyid,
                                             @PathVariable String sessionKeyid) {
        service.deleteSession(etcmKeyid, sessionKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Session deleted successfully");
        resp.put("sessionId", sessionKeyid);
        return resp;
    }

    @GetMapping("/{etcmKeyid}/sessions/check-duplicate")
    public Map<String, Object> checkSessionDuplicate(@PathVariable String etcmKeyid,
                                                     @RequestParam("sessionDate") String sessionDate,
                                                     @RequestParam("fromTime") String fromTime,
                                                     @RequestParam("toTime") String toTime,
                                                     @RequestParam(value = "sessionId", required = false) String sessionId,
                                                     HttpServletRequest httpRequest) {
        int count;
        try {
            count = service.checkSessionDuplicate(etcmKeyid, sessionDate, fromTime, toTime, sessionId);
        } catch (Exception e) {
            count = 0;
        }
        rememberSessionCheck(etcmKeyid, sessionDate, fromTime, toTime, httpRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("sessioncnt", count);
        resp.put("sessiondate", sessionDate);
        resp.put("keyid", etcmKeyid);
        resp.put("frmtime", fromTime);
        resp.put("totime", toTime);
        resp.put("sessionid", sessionId);
        return resp;
    }

    @DeleteMapping("/{etcmKeyid}/details/{gridId}/{keyId}")
    public Map<String, Object> deleteDetail(@PathVariable String etcmKeyid,
                                            @PathVariable String gridId,
                                            @PathVariable String keyId) {
        Map<String, Object> resp = new HashMap<>();
        try {
            String msg = service.deleteDetailRecord(keyId, gridId, etcmKeyid);
            resp.put("msg", msg);
            resp.put("gridid", gridId);
        } catch (Exception e) {
            resp.put("msg", "Record Can't be Deleted Reference Found");
            resp.put("gridid", gridId);
        }
        return resp;
    }

    @GetMapping("/{etcmKeyid}/unique/check")
    public Map<String, Object> checkUniquePosition(@PathVariable String etcmKeyid,
                                                   @RequestParam("roleKeyid") String roleKeyid,
                                                   @RequestParam(value = "uniqueKeyid", required = false) String uniqueKeyid,
                                                   @RequestParam(value = "chkuniq", required = false) String chkuniq) {
        int count;
        try {
            count = service.checkUniquePosition(etcmKeyid, roleKeyid, uniqueKeyid);
        } catch (Exception e) {
            count = 0;
        }
        Map<String, Object> resp = new HashMap<>();
        resp.put("uniquecnt", count);
        resp.put("chkuniq", chkuniq);
        resp.put("keyid", etcmKeyid);
        resp.put("Upid", roleKeyid);
        resp.put("uniqukeyid", uniqueKeyid);
        return resp;
    }

    @GetMapping("/roles/{roleKeyid}/jh")
    public Map<String, Object> getJhForRole(@PathVariable String roleKeyid) {
        List<String[]> rows = service.checkJHForRole(roleKeyid);
        Map<String, Object> success = new HashMap<>();
        if (!rows.isEmpty()) {
            success.put("sect", rows.get(0)[0]);
            success.put("cell", rows.get(0)[1]);
        }
        Map<String, Object> resp = new HashMap<>();
        resp.put("successData", success);
        return resp;
    }

    @PostMapping("/{etcmKeyid}/unique/bulk")
    public Map<String, Object> saveMultipleUnique(@PathVariable String etcmKeyid,
                                                  @RequestBody List<EntTlTrgCalUnqpDto> uniqueDtos) throws Exception {
        List<EntTlTrgCalUnqp> uniques = mapUnqpList(uniqueDtos);
        if (uniques == null) {
            uniques = new ArrayList<>();
        }
        for (EntTlTrgCalUnqp unqp : uniques) {
            if (unqp.getEtcuEtcmKeyid() == null || unqp.getEtcuEtcmKeyid().isBlank()) {
                unqp.setEtcuEtcmKeyid(etcmKeyid);
            }
        }
        service.createMultipleUnique(uniques);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Data Saved Successfully");
        resp.put("traCalId", etcmKeyid);
        resp.put("count", uniques.size());
        return resp;
    }

    @PostMapping("/{etcmKeyid}/sessions/{sessionKeyid}/employees")
    public Map<String, Object> saveSessionEmployee(@PathVariable String etcmKeyid,
                                                   @PathVariable String sessionKeyid,
                                                   @RequestBody EntTlTrgCalEmpDto empDto) throws Exception {
        EntTlTrgCalEmp emp = map(empDto, EntTlTrgCalEmp.class);
        if (emp == null) {
            emp = new EntTlTrgCalEmp();
        }
        if (emp.getEtceEtcmKeyid() == null || emp.getEtceEtcmKeyid().isBlank()) {
            emp.setEtceEtcmKeyid(etcmKeyid);
        }
        if (emp.getEtceEtcsKeyid() == null || emp.getEtceEtcsKeyid().isBlank()) {
            emp.setEtceEtcsKeyid(sessionKeyid);
        }

        EntTlTrgCalEmp saved = service.createEmployee(emp);

        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Data Saved Successfully");
        resp.put("traCalId", etcmKeyid);
        resp.put("sessionId", sessionKeyid);
        resp.put("empKeyId", saved.getEtceKeyid());
        return resp;
    }

    @PostMapping("/{etcmKeyid}/sessions/{sessionKeyid}/employees/bulk")
    public Map<String, Object> saveSessionEmployees(@PathVariable String etcmKeyid,
                                                    @PathVariable String sessionKeyid,
                                                    @RequestBody List<EntTlTrgCalEmpDto> empDtos) throws Exception {
        List<EntTlTrgCalEmp> emps = mapEmpList(empDtos);
        if (emps == null) {
            emps = new ArrayList<>();
        }
        for (EntTlTrgCalEmp emp : emps) {
            if (emp.getEtceEtcmKeyid() == null || emp.getEtceEtcmKeyid().isBlank()) {
                emp.setEtceEtcmKeyid(etcmKeyid);
            }
            if (emp.getEtceEtcsKeyid() == null || emp.getEtceEtcsKeyid().isBlank()) {
                emp.setEtceEtcsKeyid(sessionKeyid);
            }
        }
        service.createSessionEmployee(emps);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Data Saved Successfully");
        resp.put("traCalId", etcmKeyid);
        resp.put("sessionId", sessionKeyid);
        resp.put("count", emps.size());
        return resp;
    }

    @PostMapping("/{etcmKeyid}/attendance")
    public Map<String, Object> saveEmployeeAttendance(@PathVariable String etcmKeyid,
                                                      @RequestBody EmployeeAttendanceRequest request) throws Exception {
        List<EntTlTtgCalEmpatScore> scores = mapScoreList(request.getScores());
        if (scores == null) {
            scores = new ArrayList<>();
        }

        // apply UI defaults similar to legacy flow
        for (EntTlTtgCalEmpatScore score : scores) {
            if (score.getEtcaEtcmKeyid() == null || score.getEtcaEtcmKeyid().isBlank()) {
                score.setEtcaEtcmKeyid(etcmKeyid);
            }
            // carry grid params onto each score
            score.setEtcaAssessmentCom(toChar(request.getAssess(), null));
            if (request.getMax() != null && !request.getMax().isBlank()) {
                try { score.setEtcaMaxMarks(new java.math.BigDecimal(request.getMax())); } catch (Exception ignored) {}
            }
            if (request.getCutoff() != null && !request.getCutoff().isBlank()) {
                try { score.setEtcaCutOff(new java.math.BigDecimal(request.getCutoff())); } catch (Exception ignored) {}
            }
            if (request.getTyp() != null) {
                score.setEtcaType(toChar(request.getTyp(), null));
            }
        }

        com.akranta.perfex_sb.dto.AttendanceFilter filter = new com.akranta.perfex_sb.dto.AttendanceFilter();
        filter.setKey(etcmKeyid);
        filter.setFlid(request.getFlid());
        filter.setLossId(request.getLocnid());
        filter.setTopicid(request.getTopicid());
        for (EntTlTtgCalEmpatScore score : scores) {
            if (filter.getChkExternal() == null || filter.getChkExternal().isBlank()) {
                filter.setChkExternal(score.getEtcaCreatedby());
            }
        }
        // createdby will be set per score if present; otherwise service will fallback

        service.createEmployeeAttendance(scores, filter);

        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Data Saved Successfully");
        resp.put("traCalId", etcmKeyid);
        resp.put("count", scores.size());
        return resp;
    }

    @GetMapping("/{etcmKeyid}/assessment/check")
    public Map<String, Object> checkAssessmentCompleted(@PathVariable String etcmKeyid) throws Exception {
        String cnt = service.checkAssessmentCompleted(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("keyid", etcmKeyid);
        resp.put("cnt", cnt);
        return resp;
    }

    private <S, T> T map(S source, Class<T> targetType) {
        if (source == null) return null;
        try {
            T target = targetType.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to map request body", e);
        }
    }

    private List<EntTlTrgCalUnqp> mapUnqpList(List<EntTlTrgCalUnqpDto> items) {
        if (items == null || items.isEmpty()) return null;
        List<EntTlTrgCalUnqp> list = new ArrayList<>();
        for (EntTlTrgCalUnqpDto dto : items) {
            list.add(map(dto, EntTlTrgCalUnqp.class));
        }
        return list;
    }

    private List<EntTlTrgCalEmp> mapEmpList(List<EntTlTrgCalEmpDto> items) {
        if (items == null || items.isEmpty()) return null;
        List<EntTlTrgCalEmp> list = new ArrayList<>();
        for (EntTlTrgCalEmpDto dto : items) {
            list.add(map(dto, EntTlTrgCalEmp.class));
        }
        return list;
    }

    // private List<EntTlTtgCalEmpatScore> mapScoreList(List<EntTlTtgCalEmpatScoreDto> items) {
    //     if (items == null || items.isEmpty()) return null;
    //     List<EntTlTtgCalEmpatScore> list = new ArrayList<>();
    //     for (EntTlTtgCalEmpatScoreDto dto : items) {
    //         list.add(map(dto, EntTlTtgCalEmpatScore.class));
    //     }
    //     return list;
    // }

    private List<EntTlTtgCalEmpatScore> mapScoreList(List<EntTlTtgCalEmpatScoreDto> items) {
        if (items == null || items.isEmpty())
            return null;
        List<EntTlTtgCalEmpatScore> list = new ArrayList<>();
        for (EntTlTtgCalEmpatScoreDto dto : items) {
            EntTlTtgCalEmpatScore score = map(dto, EntTlTtgCalEmpatScore.class);
            // BeanUtils does not reliably convert String->Character
            score.setEtcaPrsentAbsent(toChar(dto.getEtcaPrsentAbsent(), score.getEtcaPrsentAbsent()));
            score.setEtcaResult(toChar(dto.getEtcaResult(), score.getEtcaResult()));
            list.add(score);
        }
        return list;
    }

    private EntTlTragcalmst mapMaster(EntTlTragcalmstDto dto) {
        if (dto == null) return null;
        EntTlTragcalmst master = map(dto, EntTlTragcalmst.class);

        // BeanUtils does not reliably convert String->Character and Number->BigDecimal.
        master.setEtcmGeneral(toChar(dto.getEtcmGeneral(), master.getEtcmGeneral()));
        master.setEtcmUniquepos(toChar(dto.getEtcmUniquepos(), master.getEtcmUniquepos()));
        master.setEtcmMsd(toChar(dto.getEtcmMsd(), master.getEtcmMsd()));
        master.setEtcmChkcompleted(toChar(dto.getEtcmChkcompleted(), master.getEtcmChkcompleted()));
        master.setEtcmMaterialready(toChar(dto.getEtcmMaterialready(), master.getEtcmMaterialready()));
        master.setEtcmAssessmentrequired(toChar(dto.getEtcmAssessmentrequired(), master.getEtcmAssessmentrequired()));
        master.setEtcmMarkbased(toChar(dto.getEtcmMarkbased(), master.getEtcmMarkbased()));
        master.setEtcmActive(toChar(dto.getEtcmActive(), master.getEtcmActive()));

        if (master.getEtcmMaxDuration() == null && dto.getEtcmMaxDuration() != null) {
            master.setEtcmMaxDuration(BigDecimal.valueOf(dto.getEtcmMaxDuration()));
        }
        if (master.getEtcmPermittedstrength() == null && dto.getEtcmPermittedstrength() != null) {
            master.setEtcmPermittedstrength(BigDecimal.valueOf(dto.getEtcmPermittedstrength()));
        }
        if (master.getEtcmCaldate() == null) {
            master.setEtcmCaldate(parseDate(dto.getEtcmCalendarDateText(), true));
        }
        if (master.getEtcmCreatedatetime() == null) {
            master.setEtcmCreatedatetime(parseDate(dto.getEtcmCreatedatetimeText(), true));
        }
        return master;
    }

    private EntTlTrgCalSession mapSession(EntTlTrgCalSessionDto dto) {
        if (dto == null) return null;
        EntTlTrgCalSession session = map(dto, EntTlTrgCalSession.class);
        // parse date fields if not already bound (handles 6-Feb-2026 HH:mm etc.)
        LocalDateTime parsedSessionDate = FlexibleDateParser.parseDateTime(dto.getEtcsSessionDateText());
        LocalDateTime parsedFromDate = FlexibleDateParser.parseDateTime(dto.getEtcsFromDateText());
        LocalDateTime parsedTillDate = FlexibleDateParser.parseDateTime(dto.getEtcsTillDateText());

        // fallback: build from/to using session date + separate time strings if direct parse failed
        String baseDateText = firstNonBlank(dto.getEtcsSessionDateText(), dto.getEtcsFromDateText());
        if (parsedFromDate == null && baseDateText != null && dto.getSessionFromTime() != null) {
            parsedFromDate = FlexibleDateParser.parseDateTime(baseDateText + " " + dto.getSessionFromTime());
        }
        if (parsedTillDate == null && baseDateText != null && dto.getSessionTillTime() != null) {
            parsedTillDate = FlexibleDateParser.parseDateTime(baseDateText + " " + dto.getSessionTillTime());
        }

        session.setEtcsSessiondate(firstNonNull(session.getEtcsSessiondate(), firstNonNull(parsedSessionDate, parsedFromDate)));
        session.setEtcsFromdate(firstNonNull(session.getEtcsFromdate(), parsedFromDate));
        session.setEtcsTilldate(firstNonNull(session.getEtcsTilldate(), parsedTillDate));
        session.setEtcsDateadd(firstNonNull(
                session.getEtcsDateadd(), FlexibleDateParser.parseDateTime(dto.getEtcsDateAddText())));
        session.setEtcsCreatedon(firstNonNull(session.getEtcsCreatedon(), session.getEtcsDateadd()));
        session.setEtcsModifiedon(firstNonNull(session.getEtcsModifiedon(), session.getEtcsDateadd()));
        return session;
    }

    private LocalDateTime parseDate(String value, boolean allowDateOnly) {
        if (value == null || value.trim().isEmpty() || "{}".equals(value.trim())) {
            return null;
        }
        String v = value.trim();
        List<String> patterns = new ArrayList<>();
        patterns.add("yyyy-MM-dd'T'HH:mm:ss");
        patterns.add("yyyy-MM-dd'T'HH:mm:ss.SSS");
        patterns.add("d-MMM-yyyy HH:mm:ss");
        patterns.add("d-MMM-yyyy HH:mm");
        patterns.add("dd-MMM-yyyy HH:mm");
        if (allowDateOnly) {
            patterns.add("d-MMM-yyyy");
            patterns.add("dd-MMM-yyyy");
        }
        for (String p : patterns) {
            try {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern(p);
                if (p.contains("HH")) {
                    return LocalDateTime.parse(v, fmt);
                } else {
                    return LocalDate.parse(v, fmt).atStartOfDay();
                }
            } catch (Exception ignored) {
            }
        }
        // fallback ISO
        try {
            return LocalDateTime.parse(v);
        } catch (Exception ignored) {
        }
        return FlexibleDateParser.parseDateTime(v);
    }

    private LocalDateTime firstNonNull(LocalDateTime a, LocalDateTime b) {
        return Optional.ofNullable(a).orElse(b);
    }

        private String firstNonBlank(String a, String b) {
        if (hasText(a)) {
            return a;
        }
        return b;
    }

    private boolean hasText(String value) {
        if (value == null) {
            return false;
        }
        String trimmed = value.trim();
        return !trimmed.isEmpty()
                && !"{}".equals(trimmed)
                && !"-".equals(trimmed)
                && !"null".equalsIgnoreCase(trimmed)
                && !"undefined".equalsIgnoreCase(trimmed);
    }

    private void rememberSessionCheck(String etcmKeyid, String sessionDate, String fromTime, String toTime, HttpServletRequest request) {
        String token = extractToken(request);
        String slotKey = buildSlotKey(sessionDate, fromTime, toTime);
        if (!hasText(token) || !hasText(slotKey) || !hasText(etcmKeyid)) {
            return;
        }
        SESSION_CHECK_CACHE.put(token + "|" + slotKey, new SessionCheckRef(etcmKeyid.trim(), System.currentTimeMillis()));
        cleanupExpiredSessionChecks();
    }

    private String resolveCalendarIdFromRecentSessionCheck(EntTlTrgCalSession session, HttpServletRequest request) {
        if (session == null) {
            return null;
        }
        String token = extractToken(request);
        if (!hasText(token) || session.getEtcsFromdate() == null || session.getEtcsTilldate() == null) {
            return null;
        }
        String sessionDate = session.getEtcsFromdate().toLocalDate().format(DateTimeFormatter.ofPattern("d-MMM-yyyy"));
        String fromTime = session.getEtcsFromdate().format(DateTimeFormatter.ofPattern("HH:mm"));
        String toTime = session.getEtcsTilldate().format(DateTimeFormatter.ofPattern("HH:mm"));
        String slotKey = buildSlotKey(sessionDate, fromTime, toTime);
        if (!hasText(slotKey)) {
            return null;
        }
        String cacheKey = token + "|" + slotKey;
        SessionCheckRef ref = SESSION_CHECK_CACHE.get(cacheKey);
        if (ref == null) {
            return null;
        }
        if (System.currentTimeMillis() - ref.savedAtMillis > SESSION_CHECK_TTL_MILLIS) {
            SESSION_CHECK_CACHE.remove(cacheKey);
            return null;
        }
        return ref.etcmKeyid;
    }

    private String buildSlotKey(String sessionDate, String fromTime, String toTime) {
        LocalDateTime parsedSessionDate = parseDate(sessionDate, true);
        String normalizedDate = parsedSessionDate != null
                ? parsedSessionDate.toLocalDate().format(DateTimeFormatter.ofPattern("d-MMM-yyyy"))
                : trimToNull(sessionDate);
        String normalizedFrom = normalizeTime(fromTime);
        String normalizedTo = normalizeTime(toTime);
        if (!hasText(normalizedDate) || !hasText(normalizedFrom) || !hasText(normalizedTo)) {
            return null;
        }
        return normalizedDate + "|" + normalizedFrom + "|" + normalizedTo;
    }

    private String normalizeTime(String value) {
        String v = trimToNull(value);
        if (!hasText(v)) {
            return null;
        }
        String[] patterns = {"HH:mm", "H:mm", "HH:mm:ss", "H:mm:ss"};
        for (String p : patterns) {
            try {
                LocalTime t = LocalTime.parse(v, DateTimeFormatter.ofPattern(p));
                return t.format(DateTimeFormatter.ofPattern("HH:mm"));
            } catch (Exception ignored) {
            }
        }
        return v.length() >= 5 ? v.substring(0, 5) : v;
    }

    private String extractToken(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String auth = trimToNull(request.getHeader("Authorization"));
        if (hasText(auth)) {
            if (auth.regionMatches(true, 0, "Bearer ", 0, 7)) {
                return trimToNull(auth.substring(7));
            }
            return auth;
        }
        return trimToNull(request.getHeader("JwtToken"));
    }

    private void cleanupExpiredSessionChecks() {
        long now = System.currentTimeMillis();
        if (SESSION_CHECK_CACHE.size() < 1000) {
            return;
        }
        SESSION_CHECK_CACHE.entrySet().removeIf(e -> now - e.getValue().savedAtMillis > SESSION_CHECK_TTL_MILLIS);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private static final class SessionCheckRef {
        private final String etcmKeyid;
        private final long savedAtMillis;

        private SessionCheckRef(String etcmKeyid, long savedAtMillis) {
            this.etcmKeyid = etcmKeyid;
            this.savedAtMillis = savedAtMillis;
        }
    }

    private Character toChar(String value, Character fallback) {
        if (!hasText(value)) {
            return fallback;
        }
        return value.trim().charAt(0);
    }

    private Map<String, Object> toLegacyMaster(EntTlTragcalmst m) {
        if (m == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("etcmKeyid", m.getEtcmKeyid());
        map.put("etcmFlid", m.getEtcmFlid());
        map.put("etcmLocation", m.getEtcmLocation());
        map.put("etcmDmt", m.getEtcmDmt());
        map.put("etcmJh", m.getEtcmJh());
        map.put("etcmTopicid", m.getEtcmTopicid());
        // legacy bean uses EtcmCreatedDateTime (camel-case DT)
        map.put("etcmCreatedDateTime", m.getEtcmCreatedatetime());
        map.put("etcmRemarks", m.getEtcmRemarks());
        map.put("etcmCalendarDate", m.getEtcmCaldate());
        map.put("etcmGeneral", m.getEtcmGeneral());
        map.put("etcmUniqueposition", m.getEtcmUniquepos());
        map.put("etcmMSD", m.getEtcmMsd());
        map.put("etcmChkCompleted", m.getEtcmChkcompleted());
        map.put("etcmCompletedDate", m.getEtcmCompleteddate());
        map.put("etcmCompletedBy", m.getEtcmCompletedby());
        map.put("etcmMaxDuration", m.getEtcmMaxDuration());
        map.put("etcmFunction", m.getEtcmFunction());
        map.put("etcmVenue", m.getEtcmVenue());
        map.put("etcmPermittedStrength", m.getEtcmPermittedstrength());
        map.put("etcmMaterialsReady", m.getEtcmMaterialready());
        map.put("etcmAssessmentReq", m.getEtcmAssessmentrequired());
        map.put("etcmMarksBased", m.getEtcmMarkbased());
        map.put("etcmFileManagedId", m.getEtcmFilemgnid());
        map.put("etcmAnchoredby", m.getEtcmAnchoredby());
        map.put("etcmTrainingfunction", m.getEtcmTrainingfunction());
        map.put("etcmRating", m.getEtcmRating());
        map.put("etcmComments", m.getEtcmComments());
        map.put("etcmTopiccategory", m.getEtcmTopiccategory());
        map.put("etcmTempfield6", m.getEtcmTempfield6());
        map.put("etcmTempfield7", m.getEtcmTempfield7());
        map.put("etcmTempfield8", m.getEtcmTempfield8());
        map.put("etcmTempfield9", m.getEtcmTempfield9());
        map.put("etcmTempfield10", m.getEtcmTempfield10());
        map.put("etcmActive", m.getEtcmActive());
        map.put("etcmCreatedBy", m.getEtcmCreatedby());
        map.put("etcmCreatedOn", m.getEtcmCreatedon());
        map.put("etcmModifiedOn", m.getEtcmModifiedon());
        return map;
    }


    @PutMapping("/{etcmKeyid}/assessment/reset")
public Map<String, Object> resetAssessmentForMaintenance(@PathVariable String etcmKeyid) throws Exception {
    String result = service.resetAssessmentForMaintenance(etcmKeyid);
    Map<String, Object> resp = new HashMap<>();
    resp.put("keyid", etcmKeyid);
    resp.put("result", result);
    resp.put("msg", "Training Calendar reopened for modification");
    return resp;
}
@GetMapping("/{etcmKeyid}/assessment/status")
public Map<String, Object> getAssessmentStatus(@PathVariable String etcmKeyid) throws Exception {
    // pure read — no DB write, just check current etca_assessmentcom values
    String cnt = service.getAssessmentComStatus(etcmKeyid);
    Map<String, Object> resp = new HashMap<>();
    resp.put("keyid", etcmKeyid);
    resp.put("cnt", cnt);
    return resp;
}
}
