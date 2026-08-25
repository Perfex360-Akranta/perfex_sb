package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.JHAuidtParameterDto;
import com.akranta.perfex_sb.dto.JhaTlAuditTemplateGridDto;
import com.akranta.perfex_sb.dto.JhaTlAuditmstAndDtlDto;
import com.akranta.perfex_sb.dto.JhauditDto;
import com.akranta.perfex_sb.model.JhaTlAuditmst;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface JhAuditService {

    List<JhaTlAuditmstAndDtlDto> getAllAudits();

    List<JhaTlAuditmst> getAuditByKeyid(JhauditDto jhauditDto);

    JhaTlAuditmstAndDtlDto createOrUpdateAudit(JhaTlAuditmstAndDtlDto jhaTlAuditmstAndDtlDto);

    List<JhaTlAuditTemplateGridDto> getAuditTemplateGrid(String templateId, String jhamKeyid);

    ResponseEntity<JHAuidtParameterDto> saveParameter(JHAuidtParameterDto jhauidtparameterDto) throws Exception;

    // JhaTlAuditmst getExistingkeyid(String templateId, String flId,
    //         String date, String auditType,
    //         String stepId) throws Exception;

    JhaTlAuditmst getExistingkeyid(String templateId, String flId, String date, 
                                String auditType, String stepId) throws Exception;

            void deleteAuditTemplate(String parameterId) throws Exception;


            Long getAuditCount(String flId) throws Exception;

            String getUnassignedAuditTeamsCount(String flid);

            String getAuditLevelCurrent(String jhTemplateId);

            Integer getMinimumPoints(String templateId, String auditLevelId);

            //public JhaTlAuditmstAndDtlDto delete(JhaTlAuditmstAndDtlDto jhaTlAuditmstAndDtlDto) throws Exception;

            // public void deleteByKeyId(String keyId);

            void deleteAudit(String masterId) throws Exception;

           

}