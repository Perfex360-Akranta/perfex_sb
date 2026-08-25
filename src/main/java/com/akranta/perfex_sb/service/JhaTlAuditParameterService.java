package com.akranta.perfex_sb.service;


import com.akranta.perfex_sb.dto.JhaTlAuditParameterDto;
import com.akranta.perfex_sb.dto.JhaTlAuditTemplateDto;
//import com.akranta.perfex_sb.dto.JhaTlAudituploadDto;
import com.akranta.perfex_sb.model.JhaTlAuditparameter;
import com.akranta.perfex_sb.model.JhaTlTemplatelevellink;

import java.util.List;

public interface JhaTlAuditParameterService {
    
    /**
     * Get audit parameters grid by template/master ID
     * @param templateId The master ID to filter audit parameters
     * @return List of audit parameter DTOs
     */
    List<JhaTlAuditParameterDto> getJhAuditParameterGrid(String templateId);

    JhaTlAuditparameter getParameterByKeyid(String keyid);

    JhaTlAuditTemplateDto createOrUpdateTemplate(JhaTlAuditTemplateDto jhaTlAuditTemplateDto) ;
    

    List<JhaTlTemplatelevellink> getAuditLevels(String templateId, String flId, String jhStepId);


Integer getMinimumMarks(String auditLevel, String auditTemplate);

}



