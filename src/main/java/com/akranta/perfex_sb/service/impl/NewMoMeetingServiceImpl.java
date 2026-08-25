package com.akranta.perfex_sb.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.dto.MomMstAndDtlDto;
import com.akranta.perfex_sb.dto.momActionPlanDto;
import com.akranta.perfex_sb.model.GenTlActionPlanDtl;
import com.akranta.perfex_sb.model.GenTlActionPlanMst;
import com.akranta.perfex_sb.model.GenTlMomKpiLink;
//import com.akranta.perfex_sb.dto.MstRecallDto;
import com.akranta.perfex_sb.model.GenTlMomattendance;
import com.akranta.perfex_sb.model.GenTlMomdtl;
import com.akranta.perfex_sb.model.GenTlMommst;
import com.akranta.perfex_sb.model.GenTlVisitors;
import com.akranta.perfex_sb.repository.GenTlActionplandtlRepository;
import com.akranta.perfex_sb.repository.GenTlActionplanmstRepository;
import com.akranta.perfex_sb.repository.GenTlMomKpiLinkRepository;
import com.akranta.perfex_sb.repository.GenTlVisitorRepository;
import com.akranta.perfex_sb.repository.MoMeetingAttendanceRepository;
import com.akranta.perfex_sb.repository.MoMeetingDetailRepository;
import com.akranta.perfex_sb.repository.MoMeetingRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;

import com.akranta.perfex_sb.service.NewMoMeetingService;
import com.akranta.perfex_sb.util.ValidationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NewMoMeetingServiceImpl implements NewMoMeetingService {

    @Autowired
    private MoMeetingRepository repository;

    @Autowired
    private MoMeetingDetailRepository detailRepository;

    @Autowired
    private MoMeetingAttendanceRepository attendanceRepository;

    @Autowired
    private GenTlVisitorRepository visitorRepository;

    @Autowired
    private GenTlMomKpiLinkRepository kpiLinkRepository;

    @Autowired
    private GenTlActionplanmstRepository actionplanmstRepository;

    @Autowired
    private GenTlActionplandtlRepository actionplandtlRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MoMeetingServiceImpl.class);

    private static final String SEQ_IDENTIFIER = "GEN_TL_MOMMST";
    private static final int KEY_LENGTH = 10;
    private static final String PREFIX = "MOM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_DTL = "GEN_TL_MOMDTL";
    private static final String PREFIX_DTL = "MOD";

    private static final String SEQ_IDENTIFIER_ATT = "GEN_TL_MOMATTENDANCE";
    private static final String PREFIX_ATT = "MOA";

    private static final String SEQ_IDENTIFIER_VISI = "GEN_TL_VISITORS";
    private static final String PREFIX_VISI = "VISI";

    private static final String SEQ_IDENTIFIER_KPI = "GEN_TL_MOM_KPI_LINK";
    private static final String PREFIX_KPI = "MOK";

    @Override
    @Transactional
    public ResponseEntity<MomMstAndDtlDto> saveMOM(MomMstAndDtlDto momMstAndDtlDto) throws Exception {

        GenTlMommst genTlMommst = momMstAndDtlDto.getGenTlMommst();
        List<GenTlMomdtl> genTlMomdtls = momMstAndDtlDto.getGenTlMomdtls();
        List<GenTlMomattendance> genTlMomattendances = momMstAndDtlDto.getGentlMomAttendanceList();
        List<GenTlMomKpiLink> genTlMomKpiLinks = momMstAndDtlDto.getGenTlMomKpiLinks();

        if (genTlMommst == null) {
            throw new RuntimeException("No MOM Master Details");
        }

        MomMstAndDtlDto result = new MomMstAndDtlDto();
        // Master Table Create
        if (genTlMommst.getKeyid() == null || genTlMommst.getKeyid().trim().isEmpty()) {
            String newMstKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER, KEY_LENGTH, PREFIX,
                    FORMAT_RESET, DATE_FORMAT);

            if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                logger.info("Failed To Generate the Key Id", newMstKeyid);
                throw new RuntimeException("Failed to generate Master Key ID");
            }
            genTlMommst.setKeyid(newMstKeyid);
            genTlMommst.setMeetingno(newMstKeyid);
            logger.info("Generated new Key ID: {} for In If Loop", newMstKeyid);
        } else {
            // Master Table Update
            if (repository.existsById(genTlMommst.getKeyid())) {
                MomMstAndDtlDto updateDto = new MomMstAndDtlDto();

                GenTlMommst updateEntity = repository.save(genTlMommst);
                updateDto.setGenTlMommst(updateEntity);

                logger.info("Successfully updated Mom with Key ID: {}", updateEntity.getKeyid());

                List<GenTlMomdtl> resultsDetail = new ArrayList<>();

                for (GenTlMomdtl detail : genTlMomdtls) {
                    // Detail Table Create
                    if (detail.getKeyid() == null || detail.getKeyid().trim().isEmpty()) {
                        detail.setMomskeyid(genTlMommst.getKeyid());
                        String newDetailKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                                PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);
                        detail.setKeyid(newDetailKeyid);
                        GenTlMomdtl value = detailRepository.save(detail);
                        resultsDetail.add(value);

                    } else {
                        // Detail Table Update
                        GenTlMomdtl detailValue = detailRepository.save(detail);
                        resultsDetail.add(detailValue);

                    }

                    List<GenTlMomKpiLink> resultsKpiLink = new ArrayList<>();

                    for (GenTlMomKpiLink link : genTlMomKpiLinks) {
                        // Detail Table Create
                        if (link.getKeyid() == null || link.getKeyid().trim().isEmpty()) {
                            link.setMoms_keyid(genTlMommst.getKeyid());
                            link.setMomd_keyid(detail.getKeyid());
                            String KPINewKeyId = dbActionTemplate.getSequenceNumber(
                                    SEQ_IDENTIFIER_KPI, KEY_LENGTH,
                                    PREFIX_KPI, FORMAT_RESET, DATE_FORMAT);
                            link.setKeyid(KPINewKeyId);
                            GenTlMomKpiLink value = kpiLinkRepository.save(link);
                            resultsKpiLink.add(value);

                        } else {
                            // Detail Table Update
                            GenTlMomKpiLink detailValue = kpiLinkRepository.save(link);
                            resultsKpiLink.add(detailValue);

                        }

                    }
                    updateDto.setGenTlMomKpiLinks(resultsKpiLink);

                }
                updateDto.setGenTlMomdtls(resultsDetail);
                List<GenTlMomattendance> resultsAttendance = new ArrayList<>();
                for (GenTlMomattendance attendance : genTlMomattendances) {
                    // Detail Table Create
                    if (!ValidationUtil.isValidKeyId(attendance.getKeyid())) {
                        attendance.setMoms_keyid(genTlMommst.getKeyid());
                        String newAttendanceKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_ATT, KEY_LENGTH,
                                PREFIX_ATT, FORMAT_RESET, DATE_FORMAT);
                        attendance.setKeyid(newAttendanceKeyId);
                        GenTlMomattendance value = attendanceRepository.save(attendance);
                        resultsAttendance.add(value);

                    } else {
                        // Detail Table Update
                        GenTlMomattendance attendanceValue = attendanceRepository.save(attendance);
                        resultsAttendance.add(attendanceValue);

                    }

                }
                updateDto.setGentlMomAttendanceList(resultsAttendance);

                return ResponseEntity.status(HttpStatus.OK).body(updateDto);
            }
        }

        GenTlMommst savedEntity = repository.save(genTlMommst);
        List<GenTlMomdtl> savedDtlList = new ArrayList<>();
        List<GenTlMomKpiLink> savedKPIList = new ArrayList<>();

        if (genTlMomdtls != null && !genTlMomdtls.isEmpty()) {

            for (GenTlMomdtl genTlMomdtl : genTlMomdtls) {
                logger.info("Generated new Key ID: {} for Outside If Loop", genTlMommst.getKeyid());

                genTlMomdtl.setMomskeyid(genTlMommst.getKeyid());
                String newDtlKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_DTL, KEY_LENGTH,
                        PREFIX_DTL, FORMAT_RESET, DATE_FORMAT);

                if (newDtlKeyid == null || newDtlKeyid.trim().isEmpty()) {
                    logger.info("Failed To Generate the Mom Detail Key Id", newDtlKeyid);
                    throw new RuntimeException("Failed to generate Detail Key ID");
                }
                logger.info("Successfully created Mom Detail Key: {}", newDtlKeyid);
                genTlMomdtl.setKeyid(newDtlKeyid);

                GenTlMomdtl savedDtlEntity = detailRepository.save(genTlMomdtl);
                savedDtlList.add(savedDtlEntity);
                if (genTlMomKpiLinks != null && !genTlMomKpiLinks.isEmpty()) {

                    for (GenTlMomKpiLink link : genTlMomKpiLinks) {
                        logger.info("Generated new Key ID: {} for Outside If Loop", genTlMommst.getKeyid());

                        link.setMoms_keyid(genTlMommst.getKeyid());
                        link.setMomd_keyid(savedDtlEntity.getKeyid());
                        String KPINewKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_KPI, KEY_LENGTH,
                                PREFIX_KPI, FORMAT_RESET, DATE_FORMAT);

                        if (KPINewKeyId == null || KPINewKeyId.trim().isEmpty()) {
                            logger.info("Failed To Generate the Mom Attendance Key Id", KPINewKeyId);
                            throw new RuntimeException("Failed to generate Detail Key ID");
                        }
                        logger.info("Successfully created Mom Attendance Key: {}", KPINewKeyId);
                        link.setKeyid(KPINewKeyId);

                        GenTlMomKpiLink savedKpiEntity = kpiLinkRepository.save(link);
                        savedKPIList.add(savedKpiEntity);

                    }
                }

            }
        }
        List<GenTlMomattendance> savedAttList = new ArrayList<>();
        if (genTlMomattendances != null && !genTlMomattendances.isEmpty()) {

            for (GenTlMomattendance genTlMomattendance : genTlMomattendances) {
                logger.info("Generated new Key ID: {} for Outside If Loop", genTlMommst.getKeyid());

                genTlMomattendance.setMoms_keyid(genTlMommst.getKeyid());
                String newAttKeyID = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_ATT, KEY_LENGTH,
                        PREFIX_ATT, FORMAT_RESET, DATE_FORMAT);

                if (newAttKeyID == null || newAttKeyID.trim().isEmpty()) {
                    logger.info("Failed To Generate the Mom Attendance Key Id", newAttKeyID);
                    throw new RuntimeException("Failed to generate Detail Key ID");
                }
                logger.info("Successfully created Mom Attendance Key: {}", newAttKeyID);
                genTlMomattendance.setKeyid(newAttKeyID);

                GenTlMomattendance savedAttEntity = attendanceRepository.save(genTlMomattendance);
                savedAttList.add(savedAttEntity);

            }
        }

        result.setGenTlMommst(savedEntity);
        result.setGenTlMomdtls(savedDtlList);
        result.setGentlMomAttendanceList(savedAttList);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @Override
    @Transactional
    public ResponseEntity<MomMstAndDtlDto> saveAttendance(MomMstAndDtlDto momMstAndDtlDto)
            throws Exception {

        List<GenTlMomattendance> genTlMomattendances = momMstAndDtlDto.getGentlMomAttendanceList();
        List<GenTlMomdtl> genTlMomdtls = momMstAndDtlDto.getGenTlMomdtls();
        GenTlMommst genTlMommst = momMstAndDtlDto.getGenTlMommst();
        genTlMommst.setMeetingno(genTlMommst.getKeyid());
        List<GenTlMomattendance> resultsAttendance = new ArrayList<>();
        MomMstAndDtlDto resultMain = new MomMstAndDtlDto();

        for (GenTlMomattendance attendance : genTlMomattendances) {
            if (!ValidationUtil.isValidKeyId(attendance.getKeyid())) {

                String newAttKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_ATT, KEY_LENGTH,
                        PREFIX_ATT,
                        FORMAT_RESET, DATE_FORMAT);
                logger.info("Successfully created Mom Attendance Key: {}", newAttKeyId);
                attendance.setKeyid(newAttKeyId);
                GenTlMomattendance value = attendanceRepository.save(attendance);
                resultsAttendance.add(value);

            } else {
                GenTlMomattendance attendanceValue = attendanceRepository.save(attendance);
                resultsAttendance.add(attendanceValue);

            }
            resultMain.setGentlMomAttendanceList(resultsAttendance);
            resultMain.setGenTlMomdtls(genTlMomdtls);
            resultMain.setGenTlMommst(genTlMommst);

        }
        return ResponseEntity.status(HttpStatus.CREATED).body(resultMain);

    }

    public List<Map<String, Object>> mommstRecall(LocalDate mstDate, String flid, String shift, String type,String pillarId) {
        // Shift Id SFT001 mstDate 03-Jan-2026 flid FNLN00000979 type Pillar pillarid
        // TGT001
        // [, Y, SAFETY, REMARJS, TITLE, J, AGENDA, , ]

        List<Map<String, Object>> result = repository.recallMomMst(mstDate, flid, shift, type,pillarId);

        if (result == null || result.isEmpty()) {
            throw new RuntimeException("Result is empty");
        }
        return result;

    }

    // @Override
    // public List<Map<String, Object>> newMomGrid(String momsKeyId, String flid, String momdate, String shift,
    //         String type) {

    //     List<Map<String, Object>> result = repository.getNewMomGrid(momsKeyId, flid, momdate, shift, type);

    //     if (result == null || result.isEmpty()) {
    //         throw new RuntimeException("Result is empty");
    //     }
    //     return result;

    // }
    // @Override
    // public List<Map<String, Object>> newMomGrid(String momsKeyId, String flid, String momdate, String shift,
    //         String type, String pillarid) {

    //     List<Map<String, Object>> result = repository.getNewMomGrid(momsKeyId, flid, type, momdate, shift, pillarid);

    //     if (result == null || result.isEmpty()) {
    //         throw new RuntimeException("Result is empty");
    //     }
    //     return result;

    // }

     @Override
    public List<Map<String, Object>> newMomGrid(String momsKeyId, String flid, String momdate, String shift,
            String type, String pillarid) {

        // List<Map<String, Object>> result = repository.getNewMomGrid(momsKeyId, flid,
        // momdate, shift, type,pillarid);
        List<Map<String, Object>> result = repository.getNewMomGrid(momsKeyId, flid, type, momdate, shift, pillarid);//Order change

        for (Map<String, Object> row : result) {
            logger.info("Row: {}", row);
        }
        if (result == null || result.isEmpty()) {
            throw new RuntimeException("Result is empty");
        }
        return result;

    }
    @Override
    public String getFild(String originalId) {
        String flid = repository.findFunctionAllocnKeyId(originalId);
        if (flid == null) {
            logger.info("No FLID");
        }
        return flid;
    }

    @Override
    public List<Map<String, Object>> momGridVisitor(String masterKeyid, String flid, String date, String shift,
            String type, String pillarid, String recall) {

        boolean isValidMasterKeyid = ValidationUtil.isValidKeyId(masterKeyid);
        boolean isValidRecall = ValidationUtil.isValidKeyId(recall);

        return repository.momGridVisitors(isValidMasterKeyid, isValidRecall, masterKeyid, flid, date, type, shift,
                pillarid);
    }

    @Override
    public GenTlVisitors saveVisitors(GenTlVisitors genTlVisitors) throws Exception {
        if (!ValidationUtil.isValidKeyId(genTlVisitors.getKeyid())) {
            String newVisiKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_VISI, KEY_LENGTH, PREFIX_VISI,
                    FORMAT_RESET, DATE_FORMAT);

            if (!ValidationUtil.isValidKeyId(newVisiKeyId)) {
                throw new RuntimeException("Unable to Generate new KeyID");
            }
            genTlVisitors.setKeyid(newVisiKeyId);
        } else {
            if (visitorRepository.existsById(genTlVisitors.getKeyid())) {
                GenTlVisitors updateEntity = visitorRepository.save(genTlVisitors);
                return updateEntity;
            }
        }
        GenTlVisitors newVisitors = visitorRepository.save(genTlVisitors);
        return newVisitors;

    }

    @Override
    public GenTlMommst getMommstById(String keyId) {

        if (keyId == null || keyId.trim().isEmpty()) {
            logger.warn("Invalid keyId provided: {}", keyId);
            return null;
        }

        Optional<GenTlMommst> mst = repository.findById(keyId);
        if (mst.isPresent()) {
            GenTlMommst mstt = mst.get();
            logger.debug("Found MOM record for keyId: {}", keyId);
            return mstt;
        } else {
            logger.warn("No MOM record found for keyId: {}", keyId);
            return null;
        }

    }

    @Override
    public List<Map<String, Object>> fillagendadata(String date, String location) {
        return repository.fillagendadata(date, location);
    }

    @Override
    public void deleteDetails(String mstKeyId, String actionPlanMstId) {
        int deletedKPI = repository.deleteFromMomKpiLink(mstKeyId);
        logger.info("Deleted KPI {}", deletedKPI);

        int DeletedDTL = repository.deleteFromMomdtl(mstKeyId);
        logger.info("Deleted Mom Detail {}", DeletedDTL);

        int DeletedMst = repository.deleteFromActionPlanMst(actionPlanMstId);
        logger.info("Deleted Action Plan Mst {}", DeletedMst);

        int DeletedActDtl = repository.deleteFromActionPlanDtl(actionPlanMstId);
        logger.info("Deleted Action Plan Dtl {}", DeletedActDtl);
    }

    @Override
    public void deleteVisitor(String visiKeyid) {
        visitorRepository.deleteByVisiKeyId(visiKeyid);
    }

    @Override
    public GenTlActionPlanMst createActionPlan(momActionPlanDto dto) throws Exception {

        GenTlActionPlanMst actionPlanMst = dto.getActionPlanMst();
        List<GenTlActionPlanDtl> actionPlanDtls = dto.getActionPlanDtls();
        GenTlMommst mommst = dto.getGenTlMommst();
        GenTlActionPlanDtl actionPlanDtl = dto.getActionPlanDtl();

        String elementId = actionPlanMst.getElementid();
        String seqIdentfr = "GEN_TL_ACTIONPLANMST";
        String location = null;

        if (elementId != null && elementId.length() > 10) {
            location = elementId.substring(11, 21); /* location id starts from 11 */
            seqIdentfr += location;
        }

        String newKeyId = dbActionTemplate.getSequenceNumber(seqIdentfr, 10, "AP", DATE_FORMAT, FORMAT_RESET);
        actionPlanMst.setKeyid(newKeyId);
        logger.info("Generated new Key ID: {} for Action plan Mst", newKeyId);
        String momDetailId = repository.getMomDetailIdActionPlan(mommst.getKeyid());
        actionPlanMst.setDetailrefid(momDetailId);
        actionPlanMst.setMasterrefid(mommst.getKeyid());
        GenTlActionPlanMst savedEntity = actionplanmstRepository.save(actionPlanMst);

        logger.info("Successfully created Action Plan Mst with Key ID: {}", savedEntity.getKeyid());

        if (actionPlanDtls != null && actionPlanDtls.size() > 0) {
            for (GenTlActionPlanDtl detail : actionPlanDtls) {
                logger.info("Entered into Detail Save {}", savedEntity.getKeyid());
                String newDetailKeyId = dbActionTemplate.getSequenceNumber("GEN_TL_ACTIONPLANDTL", 10, "APLD", "",
                        "");
                detail.setKeyid(newDetailKeyId);
                detail.setAplm_keyid(savedEntity.getKeyid());
                detail.setHowtodo(actionPlanDtl.getHowtodo());
                detail.setActionplan(actionPlanDtl.getActionplan());
                detail.setTradeid(actionPlanDtl.getTradeid());
                detail.setStatus(actionPlanDtl.getStatus());
                detail.setTargetdate(actionPlanDtl.getTargetdate());
                detail.setCompleatedon(actionPlanDtl.getCompleatedon());
                detail.setCompletedby(actionPlanDtl.getCompletedby());
                detail.setCountermeasure(actionPlanDtl.getCountermeasure());
                detail.setCreatedby(actionPlanDtl.getCreatedby());
                detail.setCreatedon(actionPlanDtl.getCreatedon());
                detail.setRemarks(actionPlanDtl.getRemarks());
                detail.setOthers(actionPlanDtl.getOthers());
                detail.setTempfiled2(actionPlanDtl.getTempfiled2());
                detail.setTempfiled3(actionPlanDtl.getTempfiled3());
                detail.setTempfiled4(actionPlanDtl.getTempfiled4());
                detail.setTempfiled5(actionPlanDtl.getTempfiled5());
                detail.setActive(actionPlanDtl.getActive());
                detail.setModifiedon(actionPlanDtl.getModifiedon());
                detail.setResponsibility(detail.getResponsibility());

                GenTlActionPlanDtl singleSave = actionplandtlRepository.save(detail);
            }
        } else {
            if (!ValidationUtil.isValidKeyId(actionPlanDtl.getKeyid())) {
                String newDetailId = dbActionTemplate.getSequenceNumber("GEN_TL_ACTIONPLANDTL", 10, "APLD", "", "");
                GenTlActionPlanDtl saveSingleNoList = actionplandtlRepository.save(actionPlanDtl);
            }
        }
        int updateStatus = repository.updateStatusFromDetail(savedEntity.getKeyid());
        if (updateStatus > 0) {
            logger.info("Status updated");
        }
        return savedEntity;

    }

    @Override
    public GenTlActionPlanMst updateActionPlan(momActionPlanDto dto) throws Exception {
        GenTlActionPlanMst actionPlanMst = dto.getActionPlanMst();
        List<GenTlActionPlanDtl> actionPlanDtls = dto.getActionPlanDtls();
        GenTlMommst mommst = dto.getGenTlMommst();
        GenTlActionPlanDtl actionPlanDtl = dto.getActionPlanDtl();
        String rowId = dto.getRowId();
        String actionPlanMstId = dto.getActionPlanMstId();
        String actionPlanDetailId = dto.getActionPlanDetailId();
        GenTlActionPlanMst savedEntity = new GenTlActionPlanMst();

        String elementId = actionPlanMst.getElementid();
        String seqIdentfr = "GEN_TL_ACTIONPLANMST";
        String location = null;

        if (ValidationUtil.isValidKeyId(rowId)) {
            GenTlActionPlanDtl updateDetail = actionplandtlRepository.save(actionPlanDtl);

        } else if (actionPlanMstId.length() > 0 && actionPlanDetailId.length() > 0) {
            actionPlanDtl.setKeyid(actionPlanDetailId);
            actionPlanDtl.setAplm_keyid(actionPlanMstId);
            GenTlActionPlanDtl updateentity = actionplandtlRepository.save(actionPlanDtl);

        } else {
            if (elementId != null && elementId.length() > 10) {
                location = elementId.substring(11, 21); /* location id starts from 11 */
                seqIdentfr += location;
            }

            String newKeyId = dbActionTemplate.getSequenceNumber(seqIdentfr, 10, "AP", DATE_FORMAT, FORMAT_RESET);
            actionPlanMst.setKeyid(newKeyId);
            logger.info("Generated new Key ID: {} for Action plan Mst", newKeyId);
            String momDetailId = repository.getMaxMomDetailId(mommst.getKeyid());
            actionPlanMst.setDetailrefid(momDetailId);
            actionPlanMst.setMasterrefid(mommst.getKeyid());
            savedEntity = actionplanmstRepository.save(actionPlanMst);

            logger.info("Successfully created Action Plan Mst with Key ID: {}", savedEntity.getKeyid());

            if (actionPlanDtls != null && actionPlanDtls.size() > 0) {
                for (GenTlActionPlanDtl detail : actionPlanDtls) {
                    logger.info("Entered into Detail Save {}", savedEntity.getKeyid());
                    String newDetailKeyId = dbActionTemplate.getSequenceNumber("GEN_TL_ACTIONPLANDTL", 10, "APLD",
                            "",
                            "");
                    detail.setKeyid(newDetailKeyId);
                    detail.setAplm_keyid(savedEntity.getKeyid());
                    detail.setHowtodo(actionPlanDtl.getHowtodo());
                    detail.setActionplan(actionPlanDtl.getActionplan());
                    detail.setTradeid(actionPlanDtl.getTradeid());
                    detail.setStatus(actionPlanDtl.getStatus());
                    detail.setTargetdate(actionPlanDtl.getTargetdate());
                    detail.setCompleatedon(actionPlanDtl.getCompleatedon());
                    detail.setCompletedby(actionPlanDtl.getCompletedby());
                    detail.setCountermeasure(actionPlanDtl.getCountermeasure());
                    detail.setCreatedby(actionPlanDtl.getCreatedby());
                    detail.setCreatedon(actionPlanDtl.getCreatedon());
                    detail.setRemarks(actionPlanDtl.getRemarks());
                    detail.setOthers(actionPlanDtl.getOthers());
                    detail.setTempfiled2(actionPlanDtl.getTempfiled2());
                    detail.setTempfiled3(actionPlanDtl.getTempfiled3());
                    detail.setTempfiled4(actionPlanDtl.getTempfiled4());
                    detail.setTempfiled5(actionPlanDtl.getTempfiled5());
                    detail.setActive(actionPlanDtl.getActive());
                    detail.setModifiedon(actionPlanDtl.getModifiedon());
                    detail.setResponsibility(detail.getResponsibility());

                    GenTlActionPlanDtl singleSave = actionplandtlRepository.save(detail);
                }
            } else {
                if (!ValidationUtil.isValidKeyId(actionPlanDtl.getKeyid())) {
                    String newDetailId = dbActionTemplate.getSequenceNumber("GEN_TL_ACTIONPLANDTL", 10, "APLD", "",
                            "");
                    GenTlActionPlanDtl saveSingleNoList = actionplandtlRepository.save(actionPlanDtl);
                }
            }
            int updateStatus = repository.updateStatusFromDetail(savedEntity.getKeyid());
            if (updateStatus > 0) {
                logger.info("Status updated");
            }

        }
        return savedEntity != null ? savedEntity : actionPlanMst;
    }

}
