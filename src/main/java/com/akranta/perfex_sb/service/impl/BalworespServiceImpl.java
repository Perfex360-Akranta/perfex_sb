
package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.model.Balworespmst;
import com.akranta.perfex_sb.model.Balworespdtl;
import com.akranta.perfex_sb.repository.BalworespmstRepository;
import com.akranta.perfex_sb.repository.BalworespdtlRepository;
import com.akranta.perfex_sb.repository.BalPlmTlCalendarRepository;
import com.akranta.perfex_sb.repository.BalPlmTlStandardsRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.BalworespService;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.dto.WorespRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BalworespServiceImpl implements BalworespService {

    private static final Logger logger = LoggerFactory.getLogger(BalworespServiceImpl.class);

    private final BalworespmstRepository masterRepository;
    private final BalworespdtlRepository detailRepository;
    private final BalPlmTlStandardsRepository standardsRepository;
    private final BalPlmTlCalendarRepository calendarRepository;
    private final DbActionTemplate dbActionTemplate; // still needed for getSequenceNumber

    private static final String SEQ_IDENTIFIER_MASTER = "BAL_PLM_TL_WORESPMST";
    private static final String SEQ_IDENTIFIER_DETAIL = "BAL_PLM_TL_WORESPDTL";

    private static final int KEY_LENGTH_MASTER = 15;
    private static final int KEY_LENGTH_DETAIL = 15;

    private static final String PREFIX_MASTER = "PRM";
    private static final String PREFIX_DETAIL = "PWR";

    private static final String DATE_FORMAT = "MMYY";

    public BalworespServiceImpl(
            BalworespmstRepository masterRepository,
            BalworespdtlRepository detailRepository,
            BalPlmTlStandardsRepository standardsRepository,
            BalPlmTlCalendarRepository calendarRepository,
            DbActionTemplate dbActionTemplate) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.standardsRepository = standardsRepository;
        this.calendarRepository = calendarRepository;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<WorespRequest> saveWoresp(WorespRequest request) throws Exception {
        Balworespmst master = request.getMaster();
        List<Balworespdtl> details = request.getDetails();

        if (master == null) {
            throw new RuntimeException("No Work Order Responsibility Master Details");
        }

        WorespRequest result = new WorespRequest();

        String keyid = master.getPwrmkeyid();
        boolean isInsertMode = keyid == null ||
                keyid.trim().isEmpty() ||
                keyid.equals("{}") ||
                keyid.equals("undefined") ||
                !masterRepository.existsById(keyid);

        try {
            if (isInsertMode) {
                String newMstKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER_MASTER, KEY_LENGTH_MASTER, PREFIX_MASTER, DATE_FORMAT, null);

                if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                    logger.error("Failed to generate the Master Key ID");
                    throw new RuntimeException("Failed to generate Master Key ID");
                }

                master.setPwrmkeyid(newMstKeyid);
                if (master.getPwrmcreatedon() == null) {
                    master.setPwrmcreatedon(LocalDateTime.now());
                }
                master.setPwrmmodifiedon(LocalDateTime.now());

                logger.info("Generated new WoResp Master Key ID: {}", newMstKeyid);

                Balworespmst savedMaster = masterRepository.save(master);
                List<Balworespdtl> savedDetailList = new ArrayList<>();

                if (details != null && !details.isEmpty()) {
                    for (Balworespdtl workRespDtl : details) {

                        String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                                SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, null);
                        workRespDtl.setPwrdkeyid(newDetailKeyid);
                        workRespDtl.setPwrdmasterid(savedMaster.getPwrmkeyid());

                        if (workRespDtl.getPwrdcreatedby() == null || workRespDtl.getPwrdcreatedby().trim().isEmpty()) {
                            workRespDtl.setPwrdcreatedby(savedMaster.getPwrmcreatedby());
                        }
                        if (workRespDtl.getPwrdcreatedon() == null) {
                            workRespDtl.setPwrdcreatedon(LocalDateTime.now());
                        }
                        workRespDtl.setPwrdmodifiedon(LocalDateTime.now());

                        if (workRespDtl.getPwrdtempfield1() == null) workRespDtl.setPwrdtempfield1("{}");
                        if (workRespDtl.getPwrdtempfield2() == null) workRespDtl.setPwrdtempfield2("{}");
                        if (workRespDtl.getPwrdtempfield3() == null) workRespDtl.setPwrdtempfield3("{}");
                        if (workRespDtl.getPwrdtempfield4() == null) workRespDtl.setPwrdtempfield4("{}");

                        applyResponsibilityPropagation(savedMaster, workRespDtl);

                        Balworespdtl savedDetail = detailRepository.save(workRespDtl);
                        savedDetailList.add(savedDetail);
                        logger.info("Successfully created WoResp Detail with Key: {}", newDetailKeyid);
                    }
                }

                result.setMaster(savedMaster);
                result.setDetails(savedDetailList);
                result.setFormActionMode(request.getFormActionMode());
                result.setFormMode(request.getFormMode());
                result.setFormHeader(request.getFormHeader());

                return ResponseEntity.status(HttpStatus.CREATED).body(result);

            } else {
                // UPDATE MODE
                Balworespmst existingMaster = masterRepository.findById(master.getPwrmkeyid())
                        .orElseThrow(() -> new ResourceNotFoundException("Work Order Responsibility not found"));

                Balworespmst updatedMaster = existingMaster;
                result.setMaster(updatedMaster);
                logger.info("Using existing WoResp Master with Key ID: {}", updatedMaster.getPwrmkeyid());

                List<Balworespdtl> resultDetails = new ArrayList<>();
                if (details != null && !details.isEmpty()) {
                    for (Balworespdtl workRespDtl : details) {

                        workRespDtl.setPwrdmasterid(updatedMaster.getPwrmkeyid());

                        
                        List<String> existingTradeIds = detailRepository.findTradeIdsByMasterId(workRespDtl.getPwrdmasterid());

                        String tradeId = null;
                        String masterKeyid = updatedMaster.getPwrmkeyid();
                        String incomingTradeId = workRespDtl.getPwrdtradeid();

                        for (String t : existingTradeIds) {
                            if (!"{}".equals(t)) {
                                tradeId = t;
                            }
                            if ((tradeId != null && tradeId.equals(incomingTradeId)) || "{}".equals(incomingTradeId)) {
                                detailRepository.deleteByMasteridAndTradeid(masterKeyid, incomingTradeId);
                            }
                        }

                        String newDetailKeyid = dbActionTemplate.getSequenceNumber(
                                SEQ_IDENTIFIER_DETAIL, KEY_LENGTH_DETAIL, PREFIX_DETAIL, DATE_FORMAT, null);
                        workRespDtl.setPwrdkeyid(newDetailKeyid);
                        

                        if (workRespDtl.getPwrdcreatedby() == null || workRespDtl.getPwrdcreatedby().trim().isEmpty()) {
                            workRespDtl.setPwrdcreatedby(updatedMaster.getPwrmcreatedby());
                        }
                        workRespDtl.setPwrdcreatedon(LocalDateTime.now());
                        workRespDtl.setPwrdmodifiedon(LocalDateTime.now());

                        if (workRespDtl.getPwrdtempfield1() == null) workRespDtl.setPwrdtempfield1("{}");
                        if (workRespDtl.getPwrdtempfield2() == null) workRespDtl.setPwrdtempfield2("{}");
                        if (workRespDtl.getPwrdtempfield3() == null) workRespDtl.setPwrdtempfield3("{}");
                        if (workRespDtl.getPwrdtempfield4() == null) workRespDtl.setPwrdtempfield4("{}");

                       

                        resultDetails.add(detailRepository.save(workRespDtl));
                    }
                }
                result.setDetails(resultDetails);
                result.setFormActionMode(request.getFormActionMode());
                result.setFormMode(request.getFormMode());
                result.setFormHeader(request.getFormHeader());

                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    
    private void applyResponsibilityPropagation(Balworespmst master, Balworespdtl detail) {
        List<Object[]> machExists = masterRepository.findMachineWithoutResponsibility(master.getPwrmmachineid());

        if (!machExists.isEmpty()) {
            List<Object[]> machAvail = masterRepository.findMachineLevelResponsibility(master.getPwrmmachineid());

            if (!machAvail.isEmpty()) {
                if (String.valueOf(master.getPwrmtradewise()).equals("N")) {
                    standardsRepository.updatePreparedByMachineWide(detail.getPwrdempid(), master.getPwrmmachineid());
                    calendarRepository.updateResponsibilityMachineWide(detail.getPwrdempid(), master.getPwrmmachineid());
                } else {
                    standardsRepository.updatePreparedByTradeWise(
                            detail.getPwrdempid(), detail.getPwrdtradeid(), master.getPwrmmachineid());
                    calendarRepository.updateResponsibilityTradeWise(
                            detail.getPwrdempid(), detail.getPwrdtradeid(), master.getPwrmmachineid());
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public WorespRequest getCompleteWorespData(String masterKeyid) {
        Balworespmst master = masterRepository.findById(masterKeyid)
                .orElseThrow(() -> new ResourceNotFoundException("Work Order Responsibility not found for keyid: " + masterKeyid));

        List<Balworespdtl> details = detailRepository.findByPwrdmasterid(masterKeyid);

        return new WorespRequest(master, details);
    }

    @Override
    @Transactional
    public boolean deleteWorespDetail(String detailId) throws Exception {
        if (detailId == null || detailId.trim().isEmpty()) {
            throw new IllegalArgumentException("Detail ID cannot be null or empty");
        }
        if (!detailRepository.existsById(detailId)) {
            throw new ResourceNotFoundException("WoResp detail not found with keyid: " + detailId);
        }
        detailRepository.deleteById(detailId);
        logger.info("Successfully deleted WoResp detail with keyid: {}", detailId);
        return true;
    }
}