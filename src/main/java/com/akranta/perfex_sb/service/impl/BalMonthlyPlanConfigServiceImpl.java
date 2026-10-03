package com.akranta.perfex_sb.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.BalAllWoGenDataMobileDto;
import com.akranta.perfex_sb.dto.BalCancelAllocatedDto;
import com.akranta.perfex_sb.dto.BalGetScheduleDto;
import com.akranta.perfex_sb.dto.BalSaveObservationsDto;
import com.akranta.perfex_sb.dto.BalSaveWorkOrderDetailsDto;
import com.akranta.perfex_sb.dto.BalUpdateAllocatedDto;
import com.akranta.perfex_sb.dto.BalUpdateWorkOrderDetailsDto;
import com.akranta.perfex_sb.dto.BalWorkOrderDetailsListDto;
import com.akranta.perfex_sb.dto.BalWorkOrderFeedbackDto;
import com.akranta.perfex_sb.model.BalPlmTlCalendar;
import com.akranta.perfex_sb.model.BalPlmTlCbmwocompdtl;
import com.akranta.perfex_sb.model.BalPlmTlMultipleResp;
import com.akranta.perfex_sb.model.BalPlmTlObservations;
import com.akranta.perfex_sb.model.BalPlmTlSpareconsumed;
import com.akranta.perfex_sb.model.BalPlmTlSparecostactual;
import com.akranta.perfex_sb.model.BalPlmTlWoFeedBack;
import com.akranta.perfex_sb.model.BalPlmTlWofeedbackEntry;
import com.akranta.perfex_sb.repository.BalPlmTlCalendarRepo;
import com.akranta.perfex_sb.repository.BalPlmTlCbmwocompdtlRepo;
import com.akranta.perfex_sb.repository.BalPlmTlMultipleRespRepo;
import com.akranta.perfex_sb.repository.BalPlmTlObservationsRepo;
import com.akranta.perfex_sb.repository.BalPlmTlSpareconsumedRepo;
import com.akranta.perfex_sb.repository.BalPlmTlSparecostactualRepo;
import com.akranta.perfex_sb.repository.BalPlmTlWoFeedBackRepo;
import com.akranta.perfex_sb.repository.BalPlmTlWofeedbackEntryRepo;
import com.akranta.perfex_sb.service.BalMonthlyPlanConfigService;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class BalMonthlyPlanConfigServiceImpl implements BalMonthlyPlanConfigService {

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final int KEY_LENGTH = 11;
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    // Tables
    private static final String SEQ_IDENTIFIER_FEEDBACKENTRY = "PLM_TL_WOFEEDBACK_ENTRY";
    private static final String PREFIX_FEEDBACKENTRY = "WFB";

    private static final String SEQ_IDENTIFIER_FEEDBACK = "PLM_TL_WOFEEDBACK";
    private static final String PREFIX_FEEDBACK = "WFB";

    private static final String SEQ_IDENTIFIER_OBSERVATIONS = "PLM_TL_OBSERVATIONS";
    private static final String PREFIX_OBSERVATIONS = "OB";

    private static final String SEQ_IDENTIFIER_CMB = "PLM_TL_CBMWOCOMPDTL";
    private static final String PREFIX_CMB = "CBM";

    private static final String SEQ_IDENTIFIER_SPARECONSUMED = "PLM_TL_SPARECONSUMED";
    private static final String PREFIX_SPARECONSUMED = "PSU";

    private static final String SEQ_IDENTIFIER_SPARECOSTACTUAL = "BAL_PLM_TL_SPARECOSTACTUAL";
    private static final String PREFIX_SPARECOSTACTUAL = "PSCA";

    private static final String SEQ_IDENTIFIER_MULTIPLERESP = "BAL_PLM_TL_MULTIPLE_RESP";
    private static final String PREFIX_MULTIPLERESP = "PMRS";

    private static final String SEQ_IDENTIFIER_PMCALENDAR = "PLM_TL_CALENDAR";
    private static final String PREFIX_PMCALENDAR = "PMC";

    private static final Logger logger = LoggerFactory.getLogger(BalMonthlyPlanConfigServiceImpl.class);

    // Repos

    @Autowired
    private BalPlmTlWoFeedBackRepo feedBackRepo;

    @Autowired
    private BalPlmTlWofeedbackEntryRepo feedBackEntryRepo;

    @Autowired
    private BalPlmTlObservationsRepo observationRepo;

    @Autowired
    private BalPlmTlCbmwocompdtlRepo cbmRepo;

    @Autowired
    private BalPlmTlSparecostactualRepo sparecostactualRepo;

    @Autowired
    private BalPlmTlSpareconsumedRepo spareconsumedRepo;

    @Autowired
    private BalPlmTlMultipleRespRepo multipleRespRepo;

    @Autowired
    private BalPlmTlCalendarRepo calendarRepo;

    @Override
    @Transactional
    public String saveGDWorkOrderDetails(BalSaveWorkOrderDetailsDto model) throws Exception {

        List<BalWorkOrderFeedbackDto> feedbackList = model.getFeedbackList();
        List<BalWorkOrderDetailsListDto> workOrderDetailsListDtos = model.getWorkOrderDetailsLstBean();
        String pmStdId = model.getPmStdId();
        List<BalPlmTlMultipleResp> multipleResps = model.getMultipleResps();

        BalSaveWorkOrderDetailsDto finalResult = new BalSaveWorkOrderDetailsDto();

        String newFeedbackId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_FEEDBACK, KEY_LENGTH, PREFIX_FEEDBACK,
                DATE_FORMAT, FORMAT_RESET);

        String newFeedbackEntryId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_FEEDBACKENTRY, KEY_LENGTH,
                PREFIX_FEEDBACKENTRY, DATE_FORMAT, FORMAT_RESET);

        String newObservationId = null;

        String newcbmId = null;

        String newSpareConsumedKeyId = null;

        for (int i = 0; i < feedbackList.size(); i++) {
            BalWorkOrderFeedbackDto feedbackListValue = feedbackList.get(i);
            BalPlmTlWoFeedBack feedback = feedbackListValue.getFeedback();
            feedback.setFeedbackid(newFeedbackId);

            // Feedback Table Save
            BalPlmTlWoFeedBack resuTlWoFeedBack = feedBackRepo.save(feedback);

            // Feedback Entry table save
            BalPlmTlWofeedbackEntry entry = new BalPlmTlWofeedbackEntry();

            BeanUtils.copyProperties(feedback, entry);

            entry.setFeedbackid(newFeedbackId);

            feedBackEntryRepo.save(entry);

            // Observation Save
            if (ValidationUtil.isValidKeyId(feedback.getObservation().replaceAll("[^a-zA-Z]", ""))) {
                BalPlmTlObservations finalObservations = saveObservation(newObservationId, feedback);

            }
            // Cbm Save
            if (feedbackListValue.getCbmReading() != null
                    && ValidationUtil.isValidKeyId(feedbackListValue.getCbmReading().toString())) {

                feedback.setPmstandId(feedbackListValue.getPmstandId());
                logger.info("Standard id {} ",feedbackListValue.getPmstandId());
                feedback.setObsvTargetDate(feedbackListValue.getObsvTargetDate());
                feedback.setObsvResponsibility(feedbackListValue.getObsvResponsibility());
                feedback.setCbmReadingValue(feedbackListValue.getCbmReading());
                feedback.setCbmMinReading(feedbackListValue.getCbmMinReading());
                feedback.setCbmMaxReading(feedbackListValue.getCbmMaxReading());
                feedback.setPmCalendarId(feedbackListValue.getPmCalendarId());
                logger.info("Calendar id {} ",feedbackListValue.getPmCalendarId());
                feedback.setCbmAdjustedReading(feedbackListValue.getCbmAdjustedReading());
                feedback.setCbmNextDueDate(feedbackListValue.getCbmNextDueDate());

                BalPlmTlCbmwocompdtl finalCbm = saveCbmWoCmp(newcbmId, feedback);
            }

            BalPlmTlSpareconsumed plmTlSpareconsumed = feedbackListValue.getSpareconsumed();
            BalPlmTlSparecostactual plmTlSparecostactual = feedbackListValue.getSparecostactual();

            for (BalWorkOrderDetailsListDto detail : workOrderDetailsListDtos) {
                for (int j = 0; j < feedbackList.size(); j++) {
                    if (plmTlSparecostactual != null) {
                        plmTlSparecostactual.setPmcalendarid(detail.getPmCalendarId());
                        plmTlSparecostactual.setRequestedby(feedback.getCompletedby());
                        String spareActualKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_SPARECOSTACTUAL, 10,
                                PREFIX_SPARECOSTACTUAL, DATE_FORMAT, FORMAT_RESET);
                        plmTlSparecostactual.setPmcalendarid(spareActualKeyid);
                        BalPlmTlSparecostactual finalspareActual = sparecostactualRepo.save(plmTlSparecostactual);

                    }
                    if (plmTlSpareconsumed != null) {
                        newSpareConsumedKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_SPARECONSUMED, 12,
                                PREFIX_SPARECONSUMED, DATE_FORMAT, FORMAT_RESET);
                        plmTlSpareconsumed.setKeyid(newSpareConsumedKeyId);
                        plmTlSpareconsumed.setPmcalendarid(detail.getPmCalendarId());
                        plmTlSpareconsumed.setRemarks(feedback.getRemarks());
                        plmTlSpareconsumed.setWodetailid(feedback.getWodetailid());

                        BalPlmTlSpareconsumed finalSpareConsumed = spareconsumedRepo.save(plmTlSpareconsumed);
                    }
                }
            }

            int updatedWoDetail = feedBackRepo.updateWorkOrderDetail(
                    feedback.getFeedbackid(),
                    feedback.getCompletedby(),
                    LocalDateTime.now(),
                    feedback.getWodetailid());

            if (updatedWoDetail > 0) {

                String calendarId = feedback.getPmCalendarId();

                feedBackRepo.updateCalendarAndWoMasterIfCompleted(calendarId);
            }
        }

        saveMultipleResp(multipleResps, pmStdId, "");
        return "Success";

    }

    @Override
    public String updateGDWorkOrderDetails(BalUpdateWorkOrderDetailsDto model) throws Exception {
        String detailId = model.getWoDetailId();
        String completedBy = model.getCompletedBy();
        LocalDateTime completedDate = model.getCompletedDate();

        int updated = feedBackRepo.updateCompletedDetails(detailId, completedBy, completedDate);
        if (updated < 0) {
            return "Failure";
        }
        return "Success";

    }

    private void saveMultipleResp(List<BalPlmTlMultipleResp> multipleResps, String pmStdId, String allotedTo)
            throws Exception {
        if (ValidationUtil.isValidKeyId(allotedTo)) {
            multipleRespRepo.deleteAlloted(pmStdId);

        } else {
            multipleRespRepo.deleteCompleted(pmStdId);
        }

        if (multipleResps != null && multipleResps.size() > 0) {
            for (int i = 0; i < multipleResps.size(); i++) {
                BalPlmTlMultipleResp plmTlMultipleResp = multipleResps.get(i);
                plmTlMultipleResp.setRefid(pmStdId);
                String newMulRespKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_MULTIPLERESP, 10,
                        PREFIX_MULTIPLERESP, DATE_FORMAT, FORMAT_RESET);
                plmTlMultipleResp.setKeyid(newMulRespKeyId);

                multipleRespRepo.save(plmTlMultipleResp);

            }
        }

    }

    private BalPlmTlObservations saveObservation(String observationId, BalPlmTlWoFeedBack feedback) throws Exception {

        BalPlmTlObservations observation = new BalPlmTlObservations();
        LocalDateTime dateTime = LocalDateTime.now();

        observation.setKeyid(observationId);
        observation.setDate(dateTime);
        observation.setFlid(feedback.getMachineid());
        observation.setObservation(feedback.getObservation());
        observation.setRefid(feedback.getFeedbackid());

        observation.setResponsibility(feedback.getObsvResponsibility());
        observation.setStatus("X");

        if (feedback.getObsvResponsibility() == null
                || feedback.getObsvResponsibility().trim().isEmpty()) {

            observation.setResponsibility("{}");
        }

        observation.setFoundby(feedback.getCompletedby());

        observation.setTargetdate(dateTime);

        observation.setTempfield2("-");
        observation.setTempfield3("-");
        observation.setTempfield4("-");
        observation.setTempfield5("-");

        observation.setActive("Y");

        observation.setCreatedby(feedback.getCreatedby());
        observation.setCreatedon(dateTime);
        observation.setModifiedon(dateTime);

        if (!ValidationUtil.isValidKeyId(observationId)) {
            String newObserveId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_OBSERVATIONS, 10,
                    PREFIX_OBSERVATIONS, DATE_FORMAT, FORMAT_RESET);
            observation.setKeyid(newObserveId);

        }
        observation = observationRepo.save(observation);

        return observation;
    }

    private BalPlmTlCbmwocompdtl saveCbmWoCmp(String cmbId, BalPlmTlWoFeedBack feedback) throws Exception {

        BalPlmTlCbmwocompdtl newPlmcbmwocompdtl = new BalPlmTlCbmwocompdtl();

        newPlmcbmwocompdtl.setActive("Y");
        newPlmcbmwocompdtl.setActivitydate(LocalDateTime.now());
        newPlmcbmwocompdtl.setAdjustedreading(feedback.getCbmAdjustedReading());

        newPlmcbmwocompdtl.setCreatedby(feedback.getCreatedby());
        newPlmcbmwocompdtl.setCreatedon(LocalDateTime.now());
        newPlmcbmwocompdtl.setCurrentreading(feedback.getCbmReadingValue());
        newPlmcbmwocompdtl.setMaximumreading(feedback.getCbmMaxReading());
        newPlmcbmwocompdtl.setMiddlemax("{}");
        newPlmcbmwocompdtl.setMinimumreading(feedback.getCbmMinReading());
        newPlmcbmwocompdtl.setModifiedon(LocalDateTime.now());
        newPlmcbmwocompdtl.setNextduedate(feedback.getCbmNextDueDate());
        newPlmcbmwocompdtl.setPmcalendarid(feedback.getPmCalendarId());
        newPlmcbmwocompdtl.setPmentrytype("P");
        newPlmcbmwocompdtl.setPmjobtype("CBM");
        newPlmcbmwocompdtl.setPmstandardid(feedback.getPmstandId());
        newPlmcbmwocompdtl.setTempfield1("-");
        newPlmcbmwocompdtl.setTempfield2("-");
        newPlmcbmwocompdtl.setTempfield3("-");
        newPlmcbmwocompdtl.setTempfield4("-");
        newPlmcbmwocompdtl.setWofeedbackid(feedback.getFeedbackid());

        if (!ValidationUtil.isValidKeyId(cmbId)) {
            String newKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_CMB, 15, PREFIX_CMB, "YYMM",
                    FORMAT_RESET);
            newPlmcbmwocompdtl.setKeyid(newKeyId);

        }
        newPlmcbmwocompdtl = cbmRepo.save(newPlmcbmwocompdtl);
        return newPlmcbmwocompdtl;

    }

    @Override
    public void saveObservations(BalSaveObservationsDto observationsDto) throws Exception {

        List<BalPlmTlObservations> observationsList = observationsDto.getPlmTlObservationList();
        BalPlmTlCalendar calendarDto = observationsDto.getPlmTlCalendarList();

        for (BalPlmTlObservations observation : observationsList) {
            BalPlmTlCalendar calendar = new BalPlmTlCalendar();
            String newCalendarId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_PMCALENDAR, 12,
                    PREFIX_PMCALENDAR, FORMAT_RESET, DATE_FORMAT);
            calendar.setKeyid(newCalendarId);

            calendar.setFactoryid(calendarDto.getFactoryid());
            calendar.setSectionid(calendarDto.getSectionid());
            calendar.setCellid(calendarDto.getCellid());
            calendar.setMachineid(calendarDto.getMachineid());
            calendar.setAssemblyid(calendarDto.getAssemblyid() == null ? "" : calendarDto.getAssemblyid());
            calendar.setSubassemblyid(calendarDto.getSubassemblyid() == null ? "" : calendarDto.getSubassemblyid());
            calendar.setEntrytype("U");
            calendar.setPmfreq("X");
            calendar.setPmrefid(observation.getKeyid());
            calendar.setJobtype("OBS");
            calendar.setPmsource("I");
            calendar.setTradeid(calendarDto.getTradeid() == null ? "{}" : calendarDto.getTradeid());
            calendar.setWhatactivity(observation.getObservation());
            calendar.setCalendaryear(calendarDto.getCalendaryear());
            calendar.setMonthweek(calendarDto.getMonthweek());
            calendar.setFromdate(calendarDto.getFromdate());
            calendar.setTilldate(calendarDto.getTilldate());
            calendar.setMaxcompletiondate(calendarDto.getMaxcompletiondate());
            calendar.setScheduledfrom(calendarDto.getScheduledfrom());
            calendar.setScheduledtill(calendarDto.getScheduledtill());

            calendar.setScheduledweek(calendarDto.getMonthweek());

            calendar.setLocationid(calendarDto.getLocationid());
            calendar.setFlid(calendarDto.getFlid());
            calendar.setElementid(calendarDto.getElementid());

            calendar.setCreatedby(calendarDto.getCreatedby());
            calendar.setCreatedon(LocalDateTime.now());
            calendar.setModifiedon(LocalDateTime.now());

            calendar.setStatus("X");
            calendar.setCompletedby("{}");
            calendar.setFeedbackid("{}");
            calendar.setResponsibility("{}");
            calendar.setMachinecond("X");

            calendar.setDuration(0.0);
            calendar.setDowntime(0.0);
            calendar.setFrequencyvalue(0.0);

            calendar.setIssparereq("N");
            calendar.setIstoolsreq("N");

            calendar.setRelatedto("MCH");
            calendar.setActive("Y");

            calendarRepo.save(calendar);
            observationRepo.updateObservationStatus(observation.getKeyid());
        }
    }

    @Override
    public List<Map<String, Object>> getAllWoGenDataMobile(BalAllWoGenDataMobileDto dto) {

        String machineId = dto.getMachineId();
        LocalDateTime fromDate = dto.getFromDate();
        LocalDateTime toDate = dto.getToDate();
        List<Map<String, Object>> result = feedBackRepo.getAllWoGenDataMobile(machineId, fromDate, fromDate);
        return result;
    }

    @Override
    public List<Map<String, Object>> getSchedule(BalGetScheduleDto dto) {
        String machineId = dto.getMachineId();
        String workOrderNo = dto.getWorkOrderNo();
        List<Map<String, Object>> result = calendarRepo.getSchedule(machineId, workOrderNo);
        return result;
    }

    @Override
    public String updateAllocated(BalUpdateAllocatedDto dto) {
        String workOrderNo = dto.getWorkorderno();
        String allotedCombo = dto.getAllotedtocombo();
        List<BalPlmTlMultipleResp> responseList = dto.getMultipleResps();
        BalPlmTlWoFeedBack feedbackRes = dto.getNewPlmTlWofeedback();
        int updated = feedBackRepo.updateAllottedTo(workOrderNo, allotedCombo);
        try {
            // saveMultipleResp(responseList, feedbackRes, allotedCombo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Success";

    }

    @Override
    public String cancelAllocated(BalCancelAllocatedDto dto) {
        String workOrderNo = dto.getWorkOrderNo();
        Integer noOfActivities = dto.getNoOfActivities();
        List<String> woIds = dto.getCancelWoIds();

        feedBackRepo.updateNoOfActivities(workOrderNo, noOfActivities);
        feedBackRepo.deleteSummary(workOrderNo);
        feedBackRepo.deleteFeedback(workOrderNo);
        feedBackRepo.deleteSpareCostPlan(workOrderNo);
        feedBackRepo.deleteUtilityCostPlan(workOrderNo);
        feedBackRepo.deleteOtherCostPlan(workOrderNo);
        feedBackRepo.deleteServiceCostPlan(workOrderNo);
        feedBackRepo.deleteManpowerCostPlan(workOrderNo);
        feedBackRepo.deleteWoMaster(workOrderNo);
        feedBackRepo.deleteWoDetails(woIds);
        feedBackRepo.resetCalendar(woIds);
        feedBackRepo.deleteMultipleResp(workOrderNo);

        return "deleted";
    }

    public LocalDateTime checkDateTime(String date) {
        if (!ValidationUtil.isValidKeyId(date))
            return LocalDateTime.now();
        return LocalDateTime.now();
    }

}
