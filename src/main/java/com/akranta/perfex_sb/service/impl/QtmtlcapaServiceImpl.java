package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.QtmTlCapaActionmstDTO;
import com.akranta.perfex_sb.dto.QtmTlCapaWorkflowDTO;
import com.akranta.perfex_sb.model.Qtmtlcapa;
import com.akranta.perfex_sb.model.QtmTlCapaWorkflow;
import com.akranta.perfex_sb.model.QtmTlCapaActionmst;
import com.akranta.perfex_sb.repository.QtmtlcapaRepository;
import com.akranta.perfex_sb.repository.QtmTlcapaworkflowRepository;
import com.akranta.perfex_sb.repository.QtmTlCapaActionmstRepository;
import com.akranta.perfex_sb.service.QtmtlcapaService;
import com.akranta.perfex_sb.service.QtmTlCapaWorkflowService;
import com.akranta.perfex_sb.service.QtmTlCapaActionmstService;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QtmtlcapaServiceImpl implements QtmtlcapaService, QtmTlCapaWorkflowService, QtmTlCapaActionmstService {

    private static final Logger logger = LoggerFactory.getLogger(QtmtlcapaServiceImpl.class);

    @Autowired
    private QtmtlcapaRepository qtmtlcapaRepository;

    @Autowired
    private QtmTlcapaworkflowRepository qtmTlcapaworkflowRepository;

    @Autowired
    private QtmTlCapaActionmstRepository qtmTlCapaActionmstRepository;

    @Autowired
    private DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER_MASTER = "QTM_TL_CAPA";
    private static final String SEQ_IDENTIFIER_WORKFLOW = "QTM_TL_CAPAWORKFLOW";
    private static final String SEQ_IDENTIFIER_ACTIONMST = "QTM_TL_CAPACTIONMST";
    private static final int KEY_LENGTH = 12;
    private static final int KEY_LENGTH_WORKFLOW = 12;
    private static final int KEY_LENGTH_ACTIONMST = 12;
    private static final String PREFIX = "CAPA";
    private static final String PREFIX_WORKFLOW = "CAPW";
    private static final String PREFIX_ACTIONMST = "CAP";

    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    @Override
    @Transactional
    public Qtmtlcapa save(Qtmtlcapa model) throws Exception {

        if (model == null) {
            throw new RuntimeException("Master record (Qtmtlcapa) is missing");
        }

        if (!ValidationUtil.isValidKeyId(model.getKeyid())) {

            String newKeyId = dbActionTemplate.getSequenceNumber(
                    SEQ_IDENTIFIER_MASTER,
                    KEY_LENGTH,
                    PREFIX,
                    FORMAT_RESET,
                    DATE_FORMAT);

            if (newKeyId == null || newKeyId.trim().isEmpty()) {
                logger.error("Failed to generate Master Key ID");
                throw new RuntimeException("Failed to generate Master Key ID");
            }

            logger.info("Generated new Master Key ID: {}", newKeyId);
            model.setKeyid(newKeyId);

            if (model.getCreatedon() == null) {
                model.setCreatedon(LocalDateTime.now());
            }

            model.setModifiedon(LocalDateTime.now());

            return qtmtlcapaRepository.save(model);

        } else {

            if (qtmtlcapaRepository.existsById(model.getKeyid())) {
                return qtmtlcapaRepository.save(model);
            }
        }

        return qtmtlcapaRepository.save(model);
    }

    @Override
    @Transactional
    public QtmTlCapaWorkflowDTO save(QtmTlCapaWorkflowDTO dto) throws Exception {

        try {

            QtmTlCapaWorkflow model = dto.getWorkflow();

            if (model == null) {
                throw new RuntimeException("Workflow record is missing");
            }
            String newKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_WORKFLOW, KEY_LENGTH_WORKFLOW,
                    PREFIX_WORKFLOW,
                    FORMAT_RESET, DATE_FORMAT);

            model.setKeyid(newKeyId);

            // Link with Master (important)
            model.setCaparefid(dto.getCapaId());

            model.setCreatedon(LocalDateTime.now());
            model.setModifiedon(LocalDateTime.now());

            qtmTlcapaworkflowRepository.save(model);

            dto.setWorkflow(model);

            return dto;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public QtmTlCapaActionmstDTO save(QtmTlCapaActionmstDTO dto) throws Exception {

        List<QtmTlCapaActionmst> list = dto.getActionList();

        if (list == null || list.isEmpty()) {
            throw new RuntimeException("Action list is empty");
        }

        for (QtmTlCapaActionmst model : list) {

            // If keyid is null → NEW record
            if (model.getKeyid() == null || model.getKeyid().isBlank()) {

                String seqNo = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER_ACTIONMST,
                        KEY_LENGTH_ACTIONMST,
                        PREFIX_ACTIONMST,
                        FORMAT_RESET,
                        DATE_FORMAT);

                model.setKeyid(seqNo);
                model.setCreatedby(dto.getCreatedBy());
                model.setMasterid(dto.getCapaMasterKeyid());

                if (model.getCreatedon() == null) {
                    model.setCreatedon(LocalDateTime.now());
                }

                model.setModifiedon(LocalDateTime.now());

            } else {

                // EXISTING record
                if (qtmTlCapaActionmstRepository.existsById(model.getKeyid())) {
                    qtmTlCapaActionmstRepository.save(model);
                    continue;
                }
            }
        }

        qtmTlCapaActionmstRepository.saveAll(list);

        return dto;
    }

    @Override
    public int updateCapaWorkflow(String empId,
            String remarks,
            String status,
            String date,
            String capaId) {

        return qtmTlcapaworkflowRepository.updateCapaWorkflow(
                empId,
                remarks,
                status,
                date,
                capaId);
    }

    @Override
    public String getFnlnKeyIdByOriginalId(String targetJH) {
        return qtmTlCapaActionmstRepository.getFnlnKeyIdByOriginalId(targetJH);
    }

    @Override
    @Transactional
    public void deleteCapaActionPlanDetail(String qcpaKeyid) throws Exception {

        qtmTlCapaActionmstRepository.deleteByQcpaKeyid(qcpaKeyid);

    }

}

// @Override
// public Qtmtlcapa findById(String id) {
// return qtmtlcapaRepository.findById(id).orElse(null);
// }

// @Override
// public QtmtlcapaDto findDtoById(String id) {
// Qtmtlcapa master = findById(id);
// if (master == null)
// return null;

// QtmtlcapaDto dto = new QtmtlcapaDto();
// dto.setQtmtlcapa(master);
// return dto;
// }

// @Override
// public List<Qtmtlcapa> findAll() {
// return qtmtlcapaRepository.findAll();
// }

// @Override
// public void deleteById(String id) {
// qtmtlcapaRepository.deleteById(id);
// }

// @Override
// @Transactional
// public QtmTlCapaWorkflowDTO save(QtmTlCapaWorkflowDTO dto) throws Exception {
// QtmTlCapaWorkflow model = dto.getWorkflow();
// if (model == null) {
// throw new RuntimeException("Workflow record is missing");
// }

// if (!ValidationUtil.isValidKeyId(model.getKeyid())) {
// String newKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_WORKFLOW,
// KEY_LENGTH_WORKFLOW,
// PREFIX_WORKFLOW,
// FORMAT_RESET, DATE_FORMAT);

// if (newKeyId == null || newKeyId.trim().isEmpty()) {
// logger.error("Failed to generate Workflow Key ID");
// throw new RuntimeException("Failed to generate Workflow Key ID");
// }

// logger.info("Generated new Workflow Key ID: {}", newKeyId);
// model.setKeyid(newKeyId);
// if (model.getCreatedon() == null) {
// model.setCreatedon(LocalDateTime.now());
// }
// }
// model.setModifiedon(LocalDateTime.now());
// dto.setWorkflow(qtmTlcapaworkflowRepository.save(model));
// return dto;
// }

// @Override
// @Transactional
// public QtmTlCapaActionmstDTO save(QtmTlCapaActionmstDTO dto) throws Exception
// {
// QtmTlCapaActionmst model = dto.getActionmst();
// if (model == null) {
// throw new RuntimeException("Action Master record is missing");
// }

// if (!ValidationUtil.isValidKeyId(model.getKeyid())) {
// String newKeyId =
// dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_ACTIONMST,
// KEY_LENGTH_ACTIONMST,
// PREFIX_ACTIONMST,
// FORMAT_RESET, DATE_FORMAT);

// if (newKeyId == null || newKeyId.trim().isEmpty()) {
// logger.error("Failed to generate Action Master Key ID");
// throw new RuntimeException("Failed to generate Action Master Key ID");
// }

// logger.info("Generated new Action Master Key ID: {}", newKeyId);
// model.setKeyid(newKeyId);
// if (model.getCreatedon() == null) {
// model.setCreatedon(LocalDateTime.now());
// }
// }
// model.setModifiedon(LocalDateTime.now());
// dto.setActionmst(qtmTlCapaActionmstRepository.save(model));
// return dto;
// }

// public Qtmtlcapa save(Qtmtlcapa model) throws Exception {

// if (model == null) {
// throw new RuntimeException("Master record (Qtmtlcapa) is missing");
// }

// if (!ValidationUtil.isValidKeyId(model.getKeyid())) {
// String newKeyId = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_MASTER,
// KEY_LENGTH, PREFIX,
// FORMAT_RESET, DATE_FORMAT);

// if (newKeyId == null || newKeyId.trim().isEmpty()) {
// logger.error("Failed to generate Master Key ID");
// throw new RuntimeException("Failed to generate Master Key ID");
// }

// logger.info("Generated new Master Key ID: {}", newKeyId);
// model.setKeyid(newKeyId);
// if (model.getCreatedon() == null) {
// model.setCreatedon(LocalDateTime.now());
// }
// }
// model.setModifiedon(LocalDateTime.now());
// return qtmtlcapaRepository.save(model);
// }

// @Override
// public int updateCapaWorkflow(String empId, String remarks, String status,
// LocalDateTime date, String capaId) {
// try {
// StringBuffer sql = new StringBuffer();
// sql.append(" UPDATE QTM_TL_CAPAWORKFLOW SET QCWF_APPROVEDBY='" + empId + "',
// QCWF_REMARKS='" + remarks
// + "',QCWF_STATUS='" + status + "',QCWF_DATE='" + date + "' WHERE
// QCWF_CAPAREFID='" + capaId + "' ");
// logger.info("Executing Update SQL: {}", sql);
// jdbcTemplate.execute(sql.toString());
// return 1;
// } catch (Exception e) {
// logger.error("Error in updateCapaWorkflow: {}", e.getMessage());
// return 0;
// }
// }