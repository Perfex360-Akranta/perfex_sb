package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.JHAuidtParameterDto;
import com.akranta.perfex_sb.dto.JhaTlAuditTemplateGridDto;
import com.akranta.perfex_sb.dto.JhaTlAuditmstAndDtlDto;
import com.akranta.perfex_sb.dto.JhauditDto;
import com.akranta.perfex_sb.model.JhaTlAuditdtl;
import com.akranta.perfex_sb.model.JhaTlAuditmst;
import com.akranta.perfex_sb.model.JhaTlAuditparameter;
import com.akranta.perfex_sb.model.JhaTlAudittemplate;
import com.akranta.perfex_sb.model.JhaTlTemplatelevellink;
import com.akranta.perfex_sb.repository.JhaTlAuditParameterRepository;
import com.akranta.perfex_sb.repository.JhaTlAuditdtlRepository;
import com.akranta.perfex_sb.repository.JhaTlAuditmstRepository;
import com.akranta.perfex_sb.repository.JhaTlAudittemplateRepository;
import com.akranta.perfex_sb.repository.JhaTlTemplatelevellinkRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.JhAuditService;
import com.akranta.perfex_sb.util.ValidationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JhAuditServiceImpl implements JhAuditService {

    private static final Logger logger = LoggerFactory.getLogger(JhAuditServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private JhaTlAuditmstRepository repository;
    private JhaTlAuditdtlRepository detailRepository;

    private JhaTlAuditParameterRepository parameterRepositotry;
    private JhaTlTemplatelevellinkRepository templevellinkRepositotry;

    private JhaTlAudittemplateRepository templateRepository;

    private DbActionTemplate dbActionTemplate;

    private static final String SEQ_IDENTIFIER = "JHA_TL_AUDITMST";
    private static final int KEY_LENGTH = 15;
    private static final String PREFIX = "JHM";
    private static final String DATE_FORMAT = "YY";
    private static final String FORMAT_RESET = "Y";

    private static final String SEQ_IDENTIFIER_DTL = "JHA_TL_AUDITDTL";
    private static final String PREFIX_DTL = "JHD";

    private static final String SEQ_IDENTIFIER_PARAM = "JHA_TL_AUDITPARAMETER";
    private static final String PREFIX_PARAM = "JHP";

    // private static final String SEQ_IDENTIFIER_LINK = "JHA_TL_TEMPLATELEVELLINK";
    // private static final String PREFIX_LINK = "JHP";

    private static final String SEQ_IDENTIFIER_TEMP = "JHA_TL_AUDITTEMPLATE";
    private static final String PREFIX_TEMP = "JHT";

    public JhAuditServiceImpl(
            JhaTlAuditmstRepository repository,
            JhaTlAuditdtlRepository detailRepository,
            JhaTlAudittemplateRepository templateRepository,
            JhaTlAuditParameterRepository parameterRepositotry,
            JhaTlTemplatelevellinkRepository templevellinkRepositotry,
            DbActionTemplate dbActionTemplate) {
        this.repository = repository;
        this.detailRepository = detailRepository;
        this.templateRepository = templateRepository;
        this.parameterRepositotry = parameterRepositotry;
        this.templevellinkRepositotry = templevellinkRepositotry;
        this.dbActionTemplate = dbActionTemplate;
    }

    @Override
    public List<JhaTlAuditmstAndDtlDto> getAllAudits() {
        try {
            logger.info("Fetching all audits with details");

            List<JhaTlAuditmst> jhaTlAuditmstList = repository.findAll();
            List<JhaTlAuditmstAndDtlDto> resultList = new ArrayList<>();

            for (JhaTlAuditmst jhaTlAuditmst : jhaTlAuditmstList) {
                JhaTlAuditmstAndDtlDto result = new JhaTlAuditmstAndDtlDto();
                result.setJhaTlAuditmst(jhaTlAuditmst);

                List<JhaTlAuditdtl> jhaTlAuditdtl = detailRepository.findByJhauditmasterid(jhaTlAuditmst.getKeyid());
                result.setJhaTlAuditdtl(jhaTlAuditdtl);

                resultList.add(result);
            }

            logger.info("Retrieved {} audit records", resultList.size());
            return resultList;

        } catch (Exception e) {
            logger.error("Error fetching all audits: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch audits: " + e.getMessage(), e);
        }
    }

    // @Override
    // public JhaTlAuditmstAndDtlDto getAuditByKeyid(String keyid) {
    // try {
    // logger.info("Fetching audit by keyid: {}", keyid);

    // if (!isValidKeyId(keyid)) {
    // throw new IllegalArgumentException("Invalid keyid provided");
    // }

    // // Find master record by keyid
    // JhaTlAuditmst jhaTlAuditmst = repository.findById(keyid)
    // .orElseThrow(() -> new RuntimeException("Audit not found with keyid: " +
    // keyid));

    // // Find associated detail records
    // List<JhaTlAuditdtl> jhaTlAuditdtl =
    // detailRepository.findByJhauditmasterid(keyid);

    // // Build response DTO
    // JhaTlAuditmstAndDtlDto result = new JhaTlAuditmstAndDtlDto();
    // result.setJhaTlAuditmst(jhaTlAuditmst);
    // result.setJhaTlAuditdtl(jhaTlAuditdtl);

    // logger.info("Successfully retrieved audit with {} detail records",
    // jhaTlAuditdtl.size());
    // return result;

    // } catch (IllegalArgumentException e) {
    // logger.error("Validation error fetching audit: {}", e.getMessage());
    // throw e;
    // } catch (Exception e) {
    // logger.error("Error fetching audit by keyid {}: {}", keyid, e.getMessage(),
    // e);
    // throw new RuntimeException("Failed to fetch audit: " + e.getMessage(), e);
    // }
    // }

    @Override
    public List<JhaTlAuditmst> getAuditByKeyid(JhauditDto jhauditDto) {

        // JhaTlAuditmst jhaTlAuditmst = new JhaTlAuditmst();

        // StringBuffer whereClause = new StringBuffer();
        // String keyId = jhaTlAuditmst.getKeyid();
        // String Flid = jhaTlAuditmst.getFlid();
        // String AuditTeamid = jhaTlAuditmst.getAuditteamid();
        // String AuditType = jhaTlAuditmst.getAudittype();
        // String Auditpillar = jhaTlAuditmst.getAuditpillar();
        // String Jhstepid = jhaTlAuditmst.getJhstepid();
        // LocalDateTime Auditdate = jhaTlAuditmst.getAuditdate();
        // String Auidtortype = jhaTlAuditmst.getAuditortype();

        String keyId = jhauditDto.getKeyId();
        String Flid = jhauditDto.getFlId();
        String AuditTeamid = jhauditDto.getAuditTeamId();
        String AuditType = jhauditDto.getAuditType();
        String Auditpillar = jhauditDto.getAuditPillar();
        String Jhstepid = jhauditDto.getJhStepId();
        String Auditdate = jhauditDto.getAuditDate();
        LocalDateTime Auditdate2 = LocalDateTime.parse("1801-01-01T00:00:00");
        String Auidtortype = jhauditDto.getAuditorType();

        if (ValidationUtil.isValidKeyId(Auditdate)) {
            Auditdate2 = LocalDateTime.parse(jhauditDto.getAuditDate());
        }

        logger.info("Fetching audit Flid : {}, jhamKeyid: {},{},{},{},{},{},{},{}", Flid, keyId, AuditTeamid, AuditType,
                Auditpillar, Jhstepid, Auditdate, Auidtortype, Auditdate2);
        return repository.findByAuditParams(keyId, Flid, AuditTeamid, AuditType, Auditpillar, Jhstepid, Auditdate,
                Auidtortype, Auditdate2);
        // if (ValidationUtil.isValidKeyId(keyId)) {
        // whereClause.append(" and jham_keyid='").append(keyId).append("'");
        // }

        // if (ValidationUtil.isValidKeyId(Flid)) {
        // whereClause.append(" and jham_flid='").append(Flid).append("'");
        // }

        // if (ValidationUtil.isValidKeyId(AuditTeamid)) {
        // whereClause.append(" and
        // jham_auditteamid='").append(AuditTeamid).append("'");
        // }

        // if (ValidationUtil.isValidKeyId(AuditType)) {
        // whereClause.append(" and jham_audittype='").append(AuditType).append("'");
        // }

        // if (ValidationUtil.isValidKeyId(Auditpillar)) {
        // whereClause.append(" and
        // jham_auditpillar='").append(Auditpillar).append("'");
        // }

        // if (ValidationUtil.isValidKeyId(Jhstepid)) {
        // whereClause.append(" and jham_jhstepid='").append(Jhstepid).append("'");
        // }
        // // AND DATE(m.JHAM_AUDITDATE) = CAST(? AS DATE)
        // // if ((Auditdate) != null) {
        // // whereClause.append(" and
        // DATE(jham_auditdate)=CAST('").append(Auditdate).append("' AS DATE)");
        // // }

        // if (ValidationUtil.isValidKeyId(Auidtortype)) {
        // whereClause.append(" and
        // jham_auditortype='").append(Auidtortype).append("'");
        // }

        // String finalWhereClause = whereClause.toString();
        // logger.info("WHERE Clause: {}", finalWhereClause);

    }

    @Override
    @Transactional
    public JhaTlAuditmstAndDtlDto createOrUpdateAudit(JhaTlAuditmstAndDtlDto jhaTlAuditmstAndDtlDto) {
        try {
            if (jhaTlAuditmstAndDtlDto == null) {
                throw new IllegalArgumentException("Audit data cannot be null");
            }

            JhaTlAuditmst jhaTlAuditmst = jhaTlAuditmstAndDtlDto.getJhaTlAuditmst();
            List<JhaTlAuditdtl> jhaTlAuditdtlList = jhaTlAuditmstAndDtlDto.getJhaTlAuditdtl();

            if (jhaTlAuditmst == null) {
                throw new IllegalArgumentException("Audit master data cannot be null");
            }

            // Handle master record
            jhaTlAuditmst = saveMasterRecord(jhaTlAuditmst);

            // Handle detail records

            List<JhaTlAuditdtl> savedDtlList = saveDetailRecords(jhaTlAuditdtlList, jhaTlAuditmst.getKeyid());

            // Prepare response
            JhaTlAuditmstAndDtlDto response = new JhaTlAuditmstAndDtlDto();
            response.setJhaTlAuditmst(jhaTlAuditmst);
            response.setJhaTlAuditdtl(savedDtlList);

            return response;

        } catch (IllegalArgumentException e) {
            logger.error("Validation error creating/updating audit: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error creating/updating audit: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create/update audit: " + e.getMessage(), e);
        }
    }

    @Override
    public List<JhaTlAuditTemplateGridDto> getAuditTemplateGrid(String templateId, String jhamKeyid) {
        try {
            logger.info("Fetching audit template grid - templateId: {}, jhamKeyid: {}", templateId, jhamKeyid);

            List<Object[]> results = templateRepository.getAuditTemplateGrid(
                    isValidKeyId(templateId) ? templateId : null,
                    isValidKeyId(jhamKeyid) ? jhamKeyid : null);

            List<JhaTlAuditTemplateGridDto> dtoList = new ArrayList<>();

            for (Object[] row : results) {
                JhaTlAuditTemplateGridDto dto = new JhaTlAuditTemplateGridDto(
                        (String) row[0], // keyid
                        (String) row[1], // reviewptslno
                        (String) row[2], // parametername
                        (String) row[3], // criteriaslno
                        (String) row[4], // parameterdescription
                        (String) row[5], // evidence
                        (Integer) row[6], // maximumpoints
                        (String) row[7], // parameterId
                        (String) row[8], // parameterName
                        (Integer) row[9], // pointsscored
                        (String) row[10], // remarks
                        (String) row[11], // detailKeyid
                        (String) row[12], // ncremarks
                        (String) row[13], // ncactionplan
                        (String) row[14], // ncactionplanKeyId
                        (String) row[15], // ncstatus
                        (String) row[16] // ncclosed
                );
                dtoList.add(dto);
            }

            logger.info("Successfully retrieved {} template grid records", dtoList.size());
            return dtoList;

        } catch (Exception e) {
            logger.error("Error fetching audit template grid: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch audit template grid: " + e.getMessage(), e);
        }
    }

    // ==========================================
    // PRIVATE HELPER METHODS
    // ==========================================

    private JhaTlAuditmst saveMasterRecord(JhaTlAuditmst jhaTlAuditmst) {
        if (jhaTlAuditmst.getKeyid() == null || jhaTlAuditmst.getKeyid().trim().isEmpty()) {
            // Generate new ID for insert
            try {
                String newMstKeyid = dbActionTemplate.getSequenceNumber(
                        SEQ_IDENTIFIER, KEY_LENGTH, PREFIX, DATE_FORMAT, FORMAT_RESET);

                if (newMstKeyid == null || newMstKeyid.trim().isEmpty()) {
                    throw new IllegalStateException("Failed to generate Master Key ID - sequence returned null");
                }

                jhaTlAuditmst.setKeyid(newMstKeyid);
                jhaTlAuditmst.setCreatedon(LocalDateTime.now());
                logger.info("Generated new Master Key ID: {}", newMstKeyid);

            } catch (Exception e) {
                throw new IllegalStateException("Failed to generate Master Key ID: " + e.getMessage(), e);
            }
        } else {
            // UPDATE - else block added
            if (repository.existsById(jhaTlAuditmst.getKeyid())) {
                jhaTlAuditmst.setModifiedon(LocalDateTime.now());
                JhaTlAuditmst updateEntity = repository.save(jhaTlAuditmst);
                logger.info("Successfully updated Audit with Key ID: {}", updateEntity.getKeyid());
                return updateEntity;
            }
        }

        jhaTlAuditmst.setModifiedon(LocalDateTime.now());

        // Save master (handles both insert and update)
        JhaTlAuditmst savedEntity = repository.save(jhaTlAuditmst);
        logger.info("Saved Master with Key ID: {}", savedEntity.getKeyid());

        return savedEntity;
    }

    private List<JhaTlAuditdtl> saveDetailRecords(List<JhaTlAuditdtl> jhaTlAuditdtlList, String masterKeyId) {
        List<JhaTlAuditdtl> savedDtlList = new ArrayList<>();

        if (jhaTlAuditdtlList == null || jhaTlAuditdtlList.isEmpty()) {
            logger.info("No detail records to save for master: {}", masterKeyId);
            return savedDtlList;
        }

        for (JhaTlAuditdtl jhaTlAuditdtl : jhaTlAuditdtlList) {
            jhaTlAuditdtl.setJhauditmasterid(masterKeyId);

            if (jhaTlAuditdtl.getKeyid() == null || jhaTlAuditdtl.getKeyid().trim().isEmpty()) {
                // Generate new ID for insert
                try {
                    String newDtlKeyid = dbActionTemplate.getSequenceNumber(
                            SEQ_IDENTIFIER_DTL, KEY_LENGTH, PREFIX_DTL, DATE_FORMAT, FORMAT_RESET);

                    if (newDtlKeyid == null || newDtlKeyid.trim().isEmpty()) {
                        throw new IllegalStateException("Failed to generate Detail Key ID - sequence returned null");
                    }

                    jhaTlAuditdtl.setKeyid(newDtlKeyid);
                    jhaTlAuditdtl.setCreatedon(LocalDateTime.now());
                    logger.info("Generated new Detail Key ID: {}", newDtlKeyid);
                } catch (Exception e) {
                    throw new IllegalStateException("Failed to generate Detail Key ID: " + e.getMessage(), e);
                }
            } else {
                // UPDATE - else block added
                if (detailRepository.existsById(jhaTlAuditdtl.getKeyid())) {
                    jhaTlAuditdtl.setModifiedon(LocalDateTime.now());
                    JhaTlAuditdtl updateDtlEntity = detailRepository.save(jhaTlAuditdtl);
                    logger.info("Successfully updated Audit Detail with Key ID: {}", updateDtlEntity.getKeyid());
                    savedDtlList.add(updateDtlEntity);
                    continue; // Skip the rest of the loop iteration
                }
            }

            jhaTlAuditdtl.setModifiedon(LocalDateTime.now());

            // Save detail (handles both insert and update)
            JhaTlAuditdtl savedDtlEntity = detailRepository.save(jhaTlAuditdtl);
            savedDtlList.add(savedDtlEntity);
        }

        logger.info("Saved {} detail records for master: {}", savedDtlList.size(), masterKeyId);
        return savedDtlList;
    }

    /**
     * Utility method to validate keyId (replicates CommonFunctions.isValidKeyId)
     */
    private boolean isValidKeyId(String keyId) {
        return keyId != null && !keyId.trim().isEmpty() && !keyId.equalsIgnoreCase("null");
    }

    /*************************************************************
     * 11-jan*************************
     */
    @Override
    @Transactional
    public ResponseEntity<JHAuidtParameterDto> saveParameter(JHAuidtParameterDto jhauidtparameterDto) throws Exception {
        // jhatlauditparameter
        JhaTlAuditparameter jhatlauditparameter = jhauidtparameterDto.getJhatlauditparameter();
        List<JhaTlTemplatelevellink> jhatltemplatelevellink = jhauidtparameterDto.getJhatltemplatelevellink();
        List<JhaTlAudittemplate> jhatlaudittemplate = jhauidtparameterDto.getJhatlaudittemplate();

        if (jhatlauditparameter == null) {
            throw new RuntimeException("No Audit Parameter Details");
        }

        JHAuidtParameterDto result = new JHAuidtParameterDto();

        // Master Table (JhaTlAuditparameter) - Create or Update
        if (jhatlauditparameter.getKeyid() == null || jhatlauditparameter.getKeyid().trim().isEmpty()) {
            // CREATE NEW PARAMETER
            String newparamKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_PARAM, KEY_LENGTH, PREFIX_PARAM,
                    FORMAT_RESET, DATE_FORMAT);

            if (newparamKeyid == null || newparamKeyid.trim().isEmpty()) {
                logger.info("Failed To Generate the Parameter Key Id", newparamKeyid);
                throw new RuntimeException("Failed to generate Parameter Key ID");
            }
            jhatlauditparameter.setKeyid(newparamKeyid);
            // jhatlauditparameter.setCreatedon(LocalDateTime.now());
            logger.info("Generated new Parameter Key ID: {}", newparamKeyid);

        } else {
            // UPDATE EXISTING PARAMETER
            if (parameterRepositotry.existsById(jhatlauditparameter.getKeyid())) {
                // jhatlauditparameter.setModifiedon(LocalDateTime.now());
                logger.info("Updating Parameter with Key ID: {}", jhatlauditparameter.getKeyid());

                // Save Parameter Master
                // jhatlauditparameter.setModifiedon(LocalDateTime.now());
                JhaTlAuditparameter savedParameter = parameterRepositotry.save(jhatlauditparameter);
                result.setJhatlauditparameter(savedParameter);
                logger.info("Successfully saved Parameter with Key ID: {}", savedParameter.getKeyid());

                // // Handle JhaTlTemplatelevellink - Only UPDATE if keyid exists
                // if (jhatltemplatelevellink != null && !jhatltemplatelevellink.isEmpty()) {
                // List<JhaTlTemplatelevellink> savedLinkList = new ArrayList<>();

                // for (JhaTlTemplatelevellink link : jhatltemplatelevellink) {
                // // Only process if keyid exists (UPDATE only, no INSERT)
                // if (link.getTemplateId() != null && !link.getTemplateId().trim().isEmpty()) {
                // if (templevellinkRepositotry.existsById(link.getTemplateId())) {
                // // link.setTemplateid(link.getTempl);
                // // link.setModifiedOn(LocalDateTime.now());

                // JhaTlTemplatelevellink savedLink = templevellinkRepositotry.save(link);
                // savedLinkList.add(savedLink);
                // logger.info("Successfully updated template level link with Key ID: {}",
                // savedLink.getTemplateId());
                // } else {
                // logger.warn("Template level link with Key ID {} not found, skipping",
                // link.getTemplateId());
                // }
                // } else {
                // logger.warn("Template level link without keyid found, skipping (no insert
                // allowed)");
                // }
                // }

                // result.setJhaTlTemplatelevellink(savedLinkList);
                // }

                // // Handle JhaTlTemplatelevellink - DELETE and INSERT pattern
                // if (jhatltemplatelevellink != null && !jhatltemplatelevellink.isEmpty()) {
                // // First delete all existing links for this template
                // templevellinkRepositotry.deleteById(savedParameter.getKeyid());
                // logger.info("Deleted existing template level links for template: {}",
                // savedParameter.getKeyid());

                // List<JhaTlTemplatelevellink> savedLinkList = new ArrayList<>();

                // // Then insert all new links
                // for (JhaTlTemplatelevellink link : jhatltemplatelevellink) {
                // link.setTemplateId(savedParameter.getKeyid());

                // JhaTlTemplatelevellink savedLink = templevellinkRepositotry.save(link);
                // savedLinkList.add(savedLink);
                // logger.info("Inserted template level link for template: {}",
                // savedParameter.getKeyid());
                // }

                // result.setJhaTlTemplatelevellink(savedLinkList);
                // }

                // // Handle JhaTlTemplatelevellink - Smart UPDATE/INSERT pattern
                // if (jhatltemplatelevellink != null && !jhatltemplatelevellink.isEmpty()) {
                // List<JhaTlTemplatelevellink> savedLinkList = new ArrayList<>();

                // for (JhaTlTemplatelevellink link : jhatltemplatelevellink) {

                // if (link.getTemplateId() != null && !link.getTemplateId().trim().isEmpty()) {
                // // UPDATE existing link
                // if (templevellinkRepositotry.existsById(link.getTemplateId())) {
                // link.setModifiedOn(LocalDateTime.now());
                // JhaTlTemplatelevellink updatedLink = templevellinkRepositotry.save(link);
                // savedLinkList.add(updatedLink);
                // logger.info("Updated template level link with ID: {}",
                // updatedLink.getTemplateId());
                // } else {
                // logger.warn("Template level link with ID {} not found, skipping",
                // link.getTemplateId());
                // }
                // } else {
                // // CREATE new link - generate new ID
                // String newLinkKeyid = dbActionTemplate.getSequenceNumber(
                // "JHA_TL_TEMPLATELEVELLINK", KEY_LENGTH, "JTLL", FORMAT_RESET, DATE_FORMAT);

                // if (newLinkKeyid == null || newLinkKeyid.trim().isEmpty()) {
                // throw new RuntimeException("Failed to generate Template Level Link Key ID");
                // }

                // link.setTemplateId(newLinkKeyid);
                // link.setCreatedOn(LocalDateTime.now());
                // link.setModifiedOn(LocalDateTime.now());

                // JhaTlTemplatelevellink savedLink = templevellinkRepositotry.save(link);
                // savedLinkList.add(savedLink);
                // logger.info("Created new template level link with ID: {}",
                // savedLink.getTemplateId());
                // }
                // }

                // result.setJhatltemplatelevellink(savedLinkList);
                // }

                // Handle JhaTlTemplatelevellink - DELETE ALL existing and SAVE NEW with SAME
                // IDs
                if (jhatltemplatelevellink != null && !jhatltemplatelevellink.isEmpty()) {
                    List<JhaTlTemplatelevellink> savedLinkList = new ArrayList<>();

                    String templateId = savedParameter.getKeyid();

                    // List<JhaTlTemplatelevellink> existingLinks = templevellinkRepositotry
                    // .findAllByTemplateId(templateId);

                    // if (existingLinks != null && !existingLinks.isEmpty()) {
                    // templevellinkRepositotry.deleteAll(existingLinks);
                    // logger.info("Deleted {} existing template level links for templateId: {}",
                    // existingLinks.size(), templateId);
                    // }

                    if (templateId != null) {

                        templevellinkRepositotry.deleteByTemplateId(templateId);
                        logger.info("Deleted  existing template level links for templateId: {}",
                                templateId);
                    }

                    /* ✅ ADD THESE TWO LINES — THIS IS THE FIX */
                    templevellinkRepositotry.flush();
                    // templevellinkRepositotry.clear();
                    /* ✅ END */

                    for (JhaTlTemplatelevellink link : jhatltemplatelevellink) {
                        logger.info(" template level link with ID: {}", link.getTemplateid());
                        logger.info(" aduit level link with ID: {}", link.getAuditlevelid());
                        link.setTemplateid(templateId);

                        // Set timestamps (keep the existing templateId)
                        // link.setCreatedOn(LocalDateTime.now());
                        // link.setModifiedOn(LocalDateTime.now());

                        // Save the link with the same ID it came with
                        JhaTlTemplatelevellink savedLink = templevellinkRepositotry.save(link);
                        savedLinkList.add(savedLink);
                        logger.info("Saved template level link with ID: {}", savedLink.getTemplateid());
                    }

                    result.setJhatltemplatelevellink(savedLinkList);
                    logger.info("Successfully saved {} template level links", savedLinkList.size());
                }

                // Handle JhaTlAudittemplate - INSERT or UPDATE based on keyid
                if (jhatlaudittemplate != null && !jhatlaudittemplate.isEmpty()) {
                    List<JhaTlAudittemplate> savedTemplateList = new ArrayList<>();

                    for (JhaTlAudittemplate template : jhatlaudittemplate) {
                        template.setMasterid(savedParameter.getKeyid());

                        // Check if template has keyid - if yes UPDATE, if no CREATE
                        if (template.getKeyid() == null || template.getKeyid().trim().isEmpty()) {
                            // CREATE NEW TEMPLATE
                            String newTemplateKeyid = dbActionTemplate.getSequenceNumber(SEQ_IDENTIFIER_TEMP,
                                    KEY_LENGTH,
                                    PREFIX_TEMP, FORMAT_RESET, DATE_FORMAT);

                            if (newTemplateKeyid == null || newTemplateKeyid.trim().isEmpty()) {
                                logger.info("Failed To Generate the Template Key Id", newTemplateKeyid);
                                throw new RuntimeException("Failed to generate Template Key ID");
                            }
                            template.setKeyid(newTemplateKeyid);
                            template.setCreatedon(LocalDateTime.now());
                            logger.info("Generated new Template Key ID: {}", newTemplateKeyid);

                            // } else {
                            // // UPDATE EXISTING TEMPLATE
                            // if (templateRepository.existsById(template.getKeyid())) {
                            // template.setModifiedon(LocalDateTime.now());
                            // JhaTlAudittemplate updatedTemplate = templateRepository.save(template);
                            // savedTemplateList.add(updatedTemplate);
                            // logger.info("Successfully updated Template with Key ID: {}",
                            // updatedTemplate.getKeyid());
                            // } else {
                            // logger.warn("Template with Key ID {} not found, skipping",
                            // template.getKeyid());
                            // }
                            // }

                        } else {
                            // UPDATE EXISTING TEMPLATE
                            if (!templateRepository.existsById(template.getKeyid())) {
                                logger.warn("Template with Key ID {} not found, treating as new", template.getKeyid());
                                template.setCreatedon(LocalDateTime.now());
                            }
                        }

                        // Single save operation - handles both CREATE and UPDATE
                        template.setModifiedon(LocalDateTime.now());
                        JhaTlAudittemplate savedTemplate = templateRepository.save(template);
                        savedTemplateList.add(savedTemplate);
                        logger.info("Successfully saved Template with Key ID: {}", savedTemplate.getKeyid());

                        // template.setModifiedon(LocalDateTime.now());
                        // JhaTlAudittemplate savedTemplate = templateRepository.save(template);
                        // savedTemplateList.add(savedTemplate);
                        // logger.info("Successfully saved Template with Key ID: {}",
                        // savedTemplate.getKeyid());
                    }

                    result.setJhatlaudittemplate(savedTemplateList);
                }
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // public JhaTlAuditmst getExistingkeyid(String templateId, String flId,
    // String date, String auditType,
    // String stepId) throws Exception {

    // JhaTlAuditmst jhaTlAuditmst = null;

    // // Condition checking for date
    // if (date != null && !date.equalsIgnoreCase("null") && !date.isEmpty()) {
    // // Query with date condition
    // jhaTlAuditmst = repository.findExistingWithDate(
    // templateId, flId, date, auditType, stepId);
    // } else {
    // // Query without date condition
    // jhaTlAuditmst = repository.findExistingWithoutDate(
    // templateId, flId, auditType, stepId);
    // }

    // // If no result found, try without date and clear JHAM_KEYID
    // if (jhaTlAuditmst == null) {
    // jhaTlAuditmst = repository.findExistingWithoutDate(
    // templateId, flId, auditType, stepId);

    // if (jhaTlAuditmst != null) {
    // jhaTlAuditmst.setKeyid(""); // Clear keyid as per your logic
    // }
    // }

    // return jhaTlAuditmst;
    // }

    // @Override
    // public JhaTlAuditmst getExistingkeyid(String templateId, String flId,
    // String date, String auditType,
    // String stepId) throws Exception {

    // // Treat "-" as empty/null
    // if (stepId != null && stepId.equals("-")) {
    // stepId = null;
    // }

    // JhaTlAuditmst jhaTlAuditmst = null;

    // // Try with date first (if date is valid)
    // if (date != null && !date.equalsIgnoreCase("null") && !date.isEmpty()) {
    // jhaTlAuditmst = repository.findExistingWithDate(
    // templateId, flId, date, auditType, stepId);
    // }

    // // Fallback: try without date
    // if (jhaTlAuditmst == null) {
    // jhaTlAuditmst = repository.findExistingWithoutDate(
    // templateId, flId, auditType, stepId);

    // if (jhaTlAuditmst != null) {
    // jhaTlAuditmst.setKeyid("");
    // }
    // }

    // return jhaTlAuditmst;

    // @Override
    // public JhaTlAuditmst getExistingkeyid(String templateId, String flId,
    // String date, String auditType,
    // String stepId) throws Exception {

    // logger.info("🔍 getExistingkeyid called: templateId={}, flId={}, date={},
    // auditType={}, stepId={}",
    // templateId, flId, date, auditType, stepId);

    // // Treat "-" as null
    // if (stepId != null && stepId.equals("-")) {
    // stepId = null;
    // }

    // JhaTlAuditmst jhaTlAuditmst = null;

    // // Try with date first (if date is valid)
    // if (date != null && !date.equalsIgnoreCase("null") && !date.isEmpty()) {
    // logger.info("🔍 Trying query WITH date");
    // jhaTlAuditmst = repository.findExistingWithDate(
    // templateId, flId, date, auditType, stepId);
    // logger.info("🔍 Result with date: {}", jhaTlAuditmst != null ? "FOUND" : "NOT
    // FOUND");
    // }

    // // Fallback: try without date
    // if (jhaTlAuditmst == null) {
    // logger.info("🔍 Trying query WITHOUT date");
    // jhaTlAuditmst = repository.findExistingWithoutDate(
    // templateId, flId, auditType, stepId);
    // logger.info("🔍 Result without date: {}", jhaTlAuditmst != null ? "FOUND" :
    // "NOT FOUND");

    // if (jhaTlAuditmst != null) {
    // logger.info("🔍 Found in fallback, clearing keyid");
    // jhaTlAuditmst.setKeyid("");
    // }
    // }

    // if (jhaTlAuditmst != null) {
    // logger.info("✅ Returning audit with keyid: {}", jhaTlAuditmst.getKeyid());
    // } else {
    // logger.warn("⚠️ No audit found - returning null");
    // }

    // return jhaTlAuditmst;
    // }
    // old working
    // @Override
    // public JhaTlAuditmst getExistingkeyid(String templateId, String flId,
    // String date, String auditType,
    // String stepId) throws Exception {

    // logger.info("🔍 getExistingkeyid: templateId={}, flId={}, date={},
    // auditType={}, stepId={}",
    // templateId, flId, date, auditType, stepId);

    // // Clean stepId - treat "-" as null
    // if (stepId != null && stepId.equals("-")) {
    // stepId = null;
    // }

    // JhaTlAuditmst jhaTlAuditmst = null;

    // // Try with date first (if date is valid)
    // if (date != null && !date.equalsIgnoreCase("null") && !date.isEmpty()) {
    // logger.info("🔍 Trying query WITH date");
    // jhaTlAuditmst = repository.findExistingWithDate(
    // templateId, flId, date, auditType, stepId);
    // logger.info("🔍 Result with date: {}", jhaTlAuditmst != null ? "FOUND" : "NOT
    // FOUND");
    // }

    // // Fallback: try without date (for last Auditor Name)
    // if (jhaTlAuditmst == null) {
    // logger.info("🔍 Trying query WITHOUT date (fallback)");
    // jhaTlAuditmst = repository.findExistingWithoutDate(
    // templateId, flId, auditType, stepId);
    // logger.info("🔍 Result without date: {}", jhaTlAuditmst != null ? "FOUND" :
    // "NOT FOUND");

    // // Clear keyid if found in fallback (as per original logic)
    // if (jhaTlAuditmst != null) {
    // logger.info("🔍 Found in fallback, clearing keyid");
    // jhaTlAuditmst.setKeyid("");
    // }
    // }

    // if (jhaTlAuditmst != null) {
    // logger.info("✅ Returning audit with keyid: {}", jhaTlAuditmst.getKeyid());
    // } else {
    // logger.warn("⚠️ No audit found - returning null");
    // }

    // return jhaTlAuditmst;
    // }

    // 13-jan fetch with date present data on today or else empty templates working
    // fine
    @Override
    public JhaTlAuditmst getExistingkeyid(String templateId, String flId,
            String date, String auditType,
            String stepId) throws Exception {

        logger.info("🔍 getExistingkeyid: templateId={}, flId={}, date={}, auditType={}, stepId={}",
                templateId, flId, date, auditType, stepId);

        // Clean stepId - treat "-" as null
        if (stepId != null && stepId.equals("-")) {
            stepId = null;
        }

        JhaTlAuditmst jhaTlAuditmst = new JhaTlAuditmst(); // ✅ Initialize here

        // Try with date first (if date is valid)
        if (date != null && !date.equalsIgnoreCase("null") && !date.isEmpty()) {
            logger.info("🔍 Trying query WITH date");
            JhaTlAuditmst result = repository.findExistingWithDate(
                    templateId, flId, date, auditType, stepId);
            logger.info("🔍 Result with date: {}", result != null ? "FOUND" : "NOT FOUND");

            if (result != null) {
                return result; // ✅ Return immediately if found with date
            }
        }

        // Fallback: try without date (for last Auditor Name)
        logger.info("🔍 Trying query WITHOUT date (fallback)");
        JhaTlAuditmst fallbackResult = repository.findExistingWithoutDate(
                templateId, flId, auditType, stepId);
        logger.info("🔍 Result without date: {}", fallbackResult != null ? "FOUND" : "NOT FOUND");

        if (fallbackResult != null) {
            // Found in fallback - clear keyid and return
            logger.info("🔍 Found in fallback, clearing keyid");
            fallbackResult.setKeyid("");
            return fallbackResult;
        }

        // ✅ Nothing found - return empty object (matching original logic)
        logger.warn("⚠️ No audit found - returning empty JhaTlAuditmst object");
        return jhaTlAuditmst; // Returns empty object, not null
    }

    @Transactional
    public void deleteAuditTemplate(String parameterId) throws Exception {
        try {
            logger.info("Deleting audit template with parameterId: {}", parameterId);

            if (parameterId == null || parameterId.trim().isEmpty()) {
                throw new IllegalArgumentException("Parameter ID cannot be null or empty");
            }

            templateRepository.deleteByParameterId(parameterId);

            logger.info("Successfully deleted audit template: {}", parameterId);

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error deleting audit template {}: {}", parameterId, e.getMessage(), e);
            throw new Exception("Failed to delete audit template: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getAuditCount(String flId) throws Exception {
        try {
            logger.info("Counting audits for flId: {}", flId);

            if (flId == null || flId.trim().isEmpty()) {
                throw new IllegalArgumentException("flId cannot be null or empty");
            }

            Long count = repository.countAuditsByFlId(flId);

            logger.info("Found {} audits for flId: {}", count, flId);

            return count;

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error counting audits for flId {}: {}", flId, e.getMessage(), e);
            throw new Exception("Failed to count audits: " + e.getMessage(), e);
        }
    }

    @Override
    public String getUnassignedAuditTeamsCount(String flid) {
        Long count = repository.countUnassignedAuditTeams(flid);
        return String.valueOf(count); // Convert Long to String
    }

    @Override
    public String getAuditLevelCurrent(String jhTemplateId) {
        if (jhTemplateId == null || jhTemplateId.trim().isEmpty()) {
            throw new IllegalArgumentException("jhTemplateId is required and cannot be empty");
        }
        return repository.getAuditLevelCurrent(jhTemplateId);
    }

    @Override
    public Integer getMinimumPoints(String templateId, String auditLevelId) {
        logger.info("🔍 Getting minimum points for templateId: {}, auditLevelId: {}",
                templateId, auditLevelId);

        try {
            Integer minPoints = repository.findMinimumPoints(templateId, auditLevelId);

            if (minPoints != null) {
                logger.info("✅ Minimum points found: {}", minPoints);
            } else {
                logger.warn("⚠️ No minimum points found for templateId: {}, auditLevelId: {}",
                        templateId, auditLevelId);
            }

            return minPoints;

        } catch (Exception e) {
            return 0;

        }
    }

    // delete the all detail records and master record

    @Override
    @Transactional
    public void deleteAudit(String masterId) throws Exception {

        // Step 1 → Delete Detail Records
        detailRepository.deleteByMasterId(masterId);

        // Step 2 → Delete Master Record
        repository.deleteAuditMaster(masterId);
    }
    // @Transactional
    // public JhaTlAuditmstAndDtlDto delete(JhaTlAuditmstAndDtlDto
    // jhaTlAuditmstAndDtlDto) throws Exception {

    // try {

    // JhaTlAuditmst jhaTlAuditmst = jhaTlAuditmstAndDtlDto.getJhaTlAuditmst();
    // List<JhaTlAuditdtl> jhaTlAuditdtlList =
    // jhaTlAuditmstAndDtlDto.getJhaTlAuditdtl();

    // // 1️⃣ Delete all DETAIL records first
    // if (jhaTlAuditdtlList != null && jhaTlAuditdtlList.size() > 0) {

    // for (JhaTlAuditdtl jhaTlAuditdtl : jhaTlAuditdtlList) {

    // // same as original logic
    // jhaTlAuditdtl.setJhauditmasterid(
    // jhaTlAuditmst.getKeyid()
    // );

    // detailRepository.deleteAuditDetail(
    // jhaTlAuditdtl.getKeyid()
    // );
    // }
    // }

    // // 2️⃣ Delete MASTER record last
    // repository.deleteAuditMaster(
    // jhaTlAuditmst.getKeyid()
    // );

    // } catch (Exception e) {
    // throw new Exception(e.getMessage());
    // }

    // return jhaTlAuditmstAndDtlDto;
    // }

}
