package com.akranta.perfex_sb.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.EmployeeAttendanceRequest;
import com.akranta.perfex_sb.dto.EntTlTragcalmstDto;
import com.akranta.perfex_sb.dto.EntTlTtgCalEmpatScoreDto;
import com.akranta.perfex_sb.dto.GridBasedTrainingCalendarRequest;
import com.akranta.perfex_sb.model.EntTlTragcalmst;
import com.akranta.perfex_sb.model.EntTlTrgCalSession;
import com.akranta.perfex_sb.model.EntTlTrgFaculty;
import com.akranta.perfex_sb.model.EntTlTtgCalEmpatScore;
import com.akranta.perfex_sb.model.EntTlTrgCalUnqp;
import com.akranta.perfex_sb.service.GridBasedTrainingCalendarService;
import com.akranta.perfex_sb.util.FlexibleDateParser;

@RestController
@RequestMapping("/api/grid-training-calendar")
public class GridBasedTrainingCalendarController {

    private final GridBasedTrainingCalendarService service;

    public GridBasedTrainingCalendarController(GridBasedTrainingCalendarService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> save(@RequestBody GridBasedTrainingCalendarRequest request) throws Exception {
        List<EntTlTragcalmst> payload = mapRequest(request);
        List<EntTlTragcalmst> saved = service.saveOrUpdate(payload);

        boolean anyUpdate = payload.stream().anyMatch(m -> !isBlank(m.getEtcmKeyid()));
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", anyUpdate ? "Data Updated Successfully" : "Data Saved Successfully");
        List<String> ids = new ArrayList<>();
        for (EntTlTragcalmst m : saved) {
            ids.add(m.getEtcmKeyid());
        }
        resp.put("traCalIds", ids);
        if (!saved.isEmpty()) {
            resp.put("traCalId", saved.get(0).getEtcmKeyid());
            resp.put("traCreateTime", saved.get(0).getEtcmCreatedatetime());
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

      @GetMapping("/{etcmKeyid}/unique")
    public Map<String, Object> fetchUniquePositions(@PathVariable String etcmKeyid) throws Exception {
        List<String[]> data = service.getNewUniqPosData(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("unique", data);
        resp.put("keyid", etcmKeyid);
        return resp;
    }
    @PostMapping("/{etcmKeyid}/grid")
public Map<String, Object> fetchTrainingCalendarGrid(@PathVariable String etcmKeyid,
                                                     @RequestBody(required = false) Map<String, Object> payload) throws Exception {
    List<String[]> data = service.getListTrgCalendar(etcmKeyid, payload);
    Map<String, Object> resp = new HashMap<>();
    resp.put("trainingcalendar", data);
    resp.put("keyid", etcmKeyid);
    if (payload != null && payload.get("totalRecordCnt") != null) {
        resp.put("totalRecordCnt", payload.get("totalRecordCnt"));
    }
    return resp;
}

@PostMapping("/grid/modify")
public Map<String,Object> fetchTrainingCalendarGridModify(@RequestBody(required = false) Map<String,Object> payload) throws Exception {
    List<String[]> data = service.getListTrgCalendarModify(payload);
    Map<String,Object> resp = new HashMap<>();
    resp.put("trainingcalendar", data);
    if (payload != null && payload.get("totalRecordCnt") != null) {
        resp.put("totalRecordCnt", payload.get("totalRecordCnt"));
    }
    resp.put("keyid", payload == null ? null : payload.getOrDefault("trgCalId", payload.get("keyid")));
    return resp;
}
//   private List<EntTlTtgCalEmpatScore> mapScoreList(List<EntTlTtgCalEmpatScoreDto> items) {
//         if (items == null || items.isEmpty()) return null;
//         List<EntTlTtgCalEmpatScore> list = new ArrayList<>();
//         for (EntTlTtgCalEmpatScoreDto dto : items) {
//             list.add(map(dto, EntTlTtgCalEmpatScore.class));
//         }
//         return list;
//     }

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

@PostMapping("/grid/view")
public Map<String,Object> fetchTrainingCalendarGridView(@RequestBody(required = false) Map<String,Object> payload) throws Exception {
    List<String[]> data = service.getListTrgCalendarView(payload);
    Map<String,Object> resp = new HashMap<>();
    resp.put("trainingcalendar", data);
    if (payload != null && payload.get("totalRecordCnt") != null) {
        resp.put("totalRecordCnt", payload.get("totalRecordCnt"));
    }
    resp.put("keyid", payload == null ? null : payload.getOrDefault("trgCalId", payload.get("keyid")));
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
      @GetMapping("/{etcmKeyid}/assessment/check")
    public Map<String, Object> checkAssessmentCompleted(@PathVariable String etcmKeyid) throws Exception {
        String cnt = service.checkAssessmentCompleted(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("keyid", etcmKeyid);
        resp.put("cnt", cnt);
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
     

    @DeleteMapping("/{etcmKeyid}")
    public Map<String, Object> delete(@PathVariable String etcmKeyid) {
        service.deleteById(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Data Deleted Successfully");
        resp.put("keyId", etcmKeyid);
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
    @DeleteMapping("/{etcmKeyid}/details/{gridId}/{keyId}")
    public Map<String, Object> deleteDetail(@PathVariable String etcmKeyid,
                                            @PathVariable String gridId,
                                            @PathVariable String keyId) throws Exception {
        if (isBlank(gridId) || isBlank(keyId)) {
            throw new IllegalArgumentException("Valid gridId and keyId are required for delete");
        }
        String msg = service.deleteDetailRecord(keyId, gridId, etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", msg);
        resp.put("gridid", gridId);
        return resp;
    }

    @PostMapping("/{etcmKeyid}/session")
    public Map<String, Object> saveSession(@PathVariable String etcmKeyid,
                                           @RequestBody Map<String, Object> payload) throws Exception {
        if (payload == null) {
            throw new IllegalArgumentException("Session payload is required");
        }
        EntTlTrgCalSession session = new EntTlTrgCalSession();
        session.setEtcsKeyid(text(payload, "etcsKeyid"));
        session.setEtcsEtcmKeyid(firstNonBlank(text(payload, "etcsEtcmKeyid"), etcmKeyid));
        session.setEtcsEtcmFlid(firstNonBlank(text(payload, "etcsEtcmFlid"), "-"));
        session.setEtcsName(firstNonBlank(text(payload, "etcsName"), "Session"));

        // Dates – accept both typed LocalDateTime or text variants
        LocalDateTime sessionDate = parseDateTime(payload.get("etcsSessiondate"));
        LocalDateTime sessionDateText = parseDate(text(payload, "etcsSessiondateText"), true);
        session.setEtcsSessiondate(firstNonNull(sessionDate, sessionDateText));

        LocalDateTime fromDate = parseDateTime(payload.get("etcsFromdate"));
        LocalDateTime fromDateText = parseDate(text(payload, "etcsFromdateText"), true);
        if (fromDate == null && fromDateText == null) {
            String baseDateText = firstNonBlank(text(payload, "etcsSessiondateText"),
                    firstNonBlank(text(payload, "etcsFromdateText"),
                            firstNonBlank(text(payload, "etcsSessionDate"),
                                    text(payload, "etcsFromDate"))));
            String fromTimeText = text(payload, "sessionFromTime");
            if (baseDateText != null && fromTimeText != null) {
                fromDate = FlexibleDateParser.parseDateTime(baseDateText + " " + fromTimeText);
            }
        }
        session.setEtcsFromdate(firstNonNull(firstNonNull(fromDate, fromDateText), session.getEtcsFromdate()));

        LocalDateTime tillDate = parseDateTime(payload.get("etcsTilldate"));
        LocalDateTime tillDateText = parseDate(text(payload, "etcsTilldateText"), true);
        if (tillDate == null && tillDateText == null) {
            String baseDateText = firstNonBlank(text(payload, "etcsSessiondateText"),
                    firstNonBlank(text(payload, "etcsTilldateText"),
                            firstNonBlank(text(payload, "etcsSessionDate"),
                                    text(payload, "etcsTillDate"))));
            String tillTimeText = text(payload, "sessionTillTime");
            if (baseDateText != null && tillTimeText != null) {
                tillDate = FlexibleDateParser.parseDateTime(baseDateText + " " + tillTimeText);
            }
        }
        session.setEtcsTilldate(firstNonNull(firstNonNull(tillDate, tillDateText), session.getEtcsTilldate()));

        session.setEtcsTempfield1(firstNonBlank(text(payload, "etcsTempfield1"), "-"));
        session.setEtcsTempfield2(firstNonBlank(text(payload, "etcsTempfield2"), "-"));
        session.setEtcsTempfield3(firstNonBlank(text(payload, "etcsTempfield3"), "-"));
        session.setEtcsTempfield4(firstNonBlank(text(payload, "etcsTempfield4"), "-"));
        session.setEtcsTempfield5(firstNonBlank(text(payload, "etcsTempfield5"), "-"));
        session.setEtcsActive(firstNonBlank(text(payload, "etcsActive"), "Y").charAt(0));

        EntTlTrgCalSession saved = isBlank(session.getEtcsKeyid())
                ? service.createSession(session)
                : service.updateSession(session);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", isBlank(text(payload, "etcsKeyid")) ? "Data Saved Successfully" : "Data Updated Successfully");
        resp.put("sessionId", saved.getEtcsKeyid());
        resp.put("traCalId", saved.getEtcsEtcmKeyid());
        return resp;
    }

    @GetMapping("/{etcmKeyid}/sessions/check-duplicate")
    public Map<String, Object> checkSessionDuplicate(@PathVariable String etcmKeyid,
                                                     @RequestParam("sessionDate") String sessionDate,
                                                     @RequestParam("fromTime") String fromTime,
                                                     @RequestParam("toTime") String toTime,
                                                     @RequestParam(value = "sessionId", required = false) String sessionId) {
        int count = service.checkSessionDuplicate(etcmKeyid, sessionDate, fromTime, toTime, sessionId);
        Map<String, Object> resp = new HashMap<>();
        resp.put("sessioncnt", count);
        resp.put("sessiondate", sessionDate);
        resp.put("keyid", etcmKeyid);
        resp.put("frmtime", fromTime);
        resp.put("totime", toTime);
        resp.put("sessionid", sessionId);
        return resp;
    }

    @PostMapping("/{etcmKeyid}/faculty")
    public Map<String, Object> saveFaculty(@PathVariable String etcmKeyid,
                                           @RequestBody EntTlTrgFaculty faculty) throws Exception {
        if (faculty == null) {
            throw new IllegalArgumentException("Faculty payload is required");
        }
        if (isBlank(faculty.getEtcfEtcmKeyid())) {
            faculty.setEtcfEtcmKeyid(etcmKeyid);
        }
        EntTlTrgFaculty saved = service.createOrUpdateFaculty(faculty);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", isBlank(faculty.getEtcfKeyid()) ? "Data Saved Successfully" : "Data Updated Successfully");
        resp.put("facultyId", saved.getEtcfKeyid());
        resp.put("traCalId", saved.getEtcfEtcmKeyid());
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
    @PostMapping("/{etcmKeyid}/unique")
    public Map<String, Object> saveUniquePosition(@PathVariable String etcmKeyid,
                                                  @RequestBody EntTlTrgCalUnqp unique) throws Exception {
        if (unique == null) {
            throw new IllegalArgumentException("Unique position payload is required");
        }
        if (isBlank(unique.getEtcuEtcmKeyid())) {
            unique.setEtcuEtcmKeyid(etcmKeyid);
        }
        boolean isCreate = isBlank(unique.getEtcuKeyid());
        EntTlTrgCalUnqp saved = isCreate
                ? service.createUniquePosition(unique)
                : service.updateUniquePosition(unique);

        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", isCreate ? "Data Saved Successfully" : "Data Updated Successfully");
        resp.put("uniqueId", saved.getEtcuKeyid());
        resp.put("traCalId", saved.getEtcuEtcmKeyid());
        return resp;
    }
      private Character toChar(String value, Character fallback) {
        if (!hasText(value)) {
            return fallback;
        }
        return value.trim().charAt(0);
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
  @GetMapping("/{etcmKeyid}/session")
    public Map<String, Object> fetchSessionGrid(@PathVariable String etcmKeyid) throws Exception {
        List<String[]> data = service.getsession(etcmKeyid);
        Map<String, Object> resp = new HashMap<>();
        resp.put("session", data);
        resp.put("keyid", etcmKeyid);
        return resp;
    }

    private String text(Map<String, Object> m, String key) {
        Object v = m == null ? null : m.get(key);
        return v == null ? null : v.toString();
    }

    private LocalDateTime parseDateTime(Object v) {
        if (v instanceof LocalDateTime ldt) return ldt;
        return parseDate(v == null ? null : v.toString(), false);
    }

    private LocalDateTime firstNonNull(LocalDateTime a, LocalDateTime b) {
        return a != null ? a : b;
    }

    private String firstNonBlank(String a, String b) {
        return isBlank(a) ? b : a;
    }

    private List<EntTlTragcalmst> mapRequest(GridBasedTrainingCalendarRequest request) {
        if (request == null || request.getCalendarDetails() == null || request.getCalendarDetails().isEmpty()) {
            throw new IllegalArgumentException("calendarDetails is required");
        }
        List<EntTlTragcalmst> list = new ArrayList<>();
        for (EntTlTragcalmstDto dto : request.getCalendarDetails()) {
            EntTlTragcalmst entity = mapMasterForGrid(dto);
            if (isBlank(entity.getEtcmFlid())) {
                entity.setEtcmFlid(request.getFlid());
            }
            if (isBlank(entity.getEtcmLocation())) {
                entity.setEtcmLocation(request.getLocationId());
            }
            if (isBlank(entity.getEtcmDmt())) {
                entity.setEtcmDmt(request.getSectionId());
            }
            if (isBlank(entity.getEtcmJh())) {
                entity.setEtcmJh(request.getCellId());
            }
            if (isBlank(entity.getEtcmKeyid()) && !isBlank(request.getCalendarId())) {
                entity.setEtcmKeyid(request.getCalendarId());
            }
            if (isBlank(entity.getEtcmCreatedby())) {
                entity.setEtcmCreatedby(request.getCreatedBy());
            }
            list.add(entity);
        }
        return list;
    }

    private EntTlTragcalmst mapMasterForGrid(EntTlTragcalmstDto dto) {
        EntTlTragcalmst entity = new EntTlTragcalmst();
        if (dto == null) {
            return entity;
        }

        BeanUtils.copyProperties(dto, entity);

        if (entity.getEtcmCaldate() == null) {
            entity.setEtcmCaldate(parseDate(dto.getEtcmCalendarDateText(), true));
        }
        if (entity.getEtcmCreatedatetime() == null) {
            entity.setEtcmCreatedatetime(parseDate(dto.getEtcmCreatedatetimeText(), true));
        }
        if (entity.getEtcmMaxDuration() == null && dto.getEtcmMaxDuration() != null) {
            entity.setEtcmMaxDuration(BigDecimal.valueOf(dto.getEtcmMaxDuration()));
        }
        if (entity.getEtcmPermittedstrength() == null && dto.getEtcmPermittedstrength() != null) {
            entity.setEtcmPermittedstrength(BigDecimal.valueOf(dto.getEtcmPermittedstrength()));
        }

        // chkEtcmGeneral dropdown values: GN/UQ/MS map to three DB flags.
        String generalMode = resolveGeneralMode(dto);
        if ("GN".equals(generalMode)) {
            entity.setEtcmGeneral('Y');
            entity.setEtcmUniquepos('N');
            entity.setEtcmMsd('N');
        } else if ("UQ".equals(generalMode)) {
            entity.setEtcmGeneral('N');
            entity.setEtcmUniquepos('Y');
            entity.setEtcmMsd('N');
        } else if ("MS".equals(generalMode)) {
            entity.setEtcmGeneral('N');
            entity.setEtcmUniquepos('N');
            entity.setEtcmMsd('Y');
        } else {
            entity.setEtcmGeneral(toYnChar(dto.getEtcmGeneral(), entity.getEtcmGeneral()));
            entity.setEtcmUniquepos(toYnChar(dto.getEtcmUniquepos(), entity.getEtcmUniquepos()));
            entity.setEtcmMsd(toYnChar(dto.getEtcmMsd(), entity.getEtcmMsd()));
        }

        entity.setEtcmChkcompleted(toYnChar(dto.getEtcmChkcompleted(), entity.getEtcmChkcompleted()));
        entity.setEtcmMaterialready(toYnChar(dto.getEtcmMaterialready(), entity.getEtcmMaterialready()));
        entity.setEtcmAssessmentrequired(toYnChar(dto.getEtcmAssessmentrequired(), entity.getEtcmAssessmentrequired()));
        entity.setEtcmMarkbased(toYnChar(dto.getEtcmMarkbased(), entity.getEtcmMarkbased()));
        entity.setEtcmActive(toYnChar(dto.getEtcmActive(), entity.getEtcmActive()));
        return entity;
    }

    private Character toYnChar(String value, Character fallback) {
        if (value == null) {
            return fallback;
        }
        String normalized = value.trim();
        if (normalized.isEmpty()
                || "{}".equals(normalized)
                || "-".equals(normalized)
                || "null".equalsIgnoreCase(normalized)
                || "undefined".equalsIgnoreCase(normalized)) {
            return fallback;
        }
        if ("Y".equalsIgnoreCase(normalized) || "N".equalsIgnoreCase(normalized)) {
            return Character.toUpperCase(normalized.charAt(0));
        }
        return fallback;
    }

    private String normalizeModeCode(String value) {
        if (value == null) {
            return "";
        }
        String normalized = value.trim().toUpperCase();
        if ("{}".equals(normalized)
                || "-".equals(normalized)
                || "NULL".equals(normalized)
                || "UNDEFINED".equals(normalized)) {
            return "";
        }
        return normalized;
    }

    private String resolveGeneralMode(EntTlTragcalmstDto dto) {
        String mode = normalizeModeCode(dto == null ? null : dto.getEtcmGeneral());
        if ("Y".equals(mode) || "GENERAL".equals(mode)) {
            return "GN";
        }
        if ("UNIQUE POSITION".equals(mode) || "UNIQUEPOSITION".equals(mode) || "UNIQUE_POS".equals(mode)) {
            return "UQ";
        }
        if ("MSD".equals(mode)) {
            return "MS";
        }
        if ("N".equals(mode) && dto != null) {
            if ("Y".equalsIgnoreCase(normalizeModeCode(dto.getEtcmUniquepos()))) {
                return "UQ";
            }
            if ("Y".equalsIgnoreCase(normalizeModeCode(dto.getEtcmMsd()))) {
                return "MS";
            }
        }
        return mode;
    }

    private LocalDateTime parseDate(String value, boolean allowDateOnly) {
        if (value == null || value.trim().isEmpty() || "{}".equals(value.trim())) {
            return null;
        }
        String v = value.trim();
        List<String> patterns = new ArrayList<>();
        patterns.add("dd-MMM-yyyy HH:mm:ss");
        patterns.add("d-MMM-yyyy HH:mm:ss");
        patterns.add("dd-MMM-yyyy HH:mm");
        patterns.add("d-MMM-yyyy HH:mm");
        patterns.add("yyyy-MM-dd'T'HH:mm:ss");
        patterns.add("yyyy-MM-dd'T'HH:mm:ss.SSS");
        patterns.add("yyyy-MM-dd HH:mm:ss");
        patterns.add("yyyy-MM-dd HH:mm");
        if (allowDateOnly) {
            patterns.add("dd-MMM-yyyy");
            patterns.add("d-MMM-yyyy");
            patterns.add("yyyy-MM-dd");
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
        try {
            return LocalDateTime.parse(v);
        } catch (Exception ignored) {
        }
        return com.akranta.perfex_sb.util.FlexibleDateParser.parseToDateTime(v);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty() || "{}".equals(value.trim()) || "null".equalsIgnoreCase(value.trim());
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
}
