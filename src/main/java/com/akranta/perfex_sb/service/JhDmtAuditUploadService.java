package com.akranta.perfex_sb.service;



import com.akranta.perfex_sb.dto.JhaTlAudituploadDto;

public interface JhDmtAuditUploadService {
    
    JhaTlAudituploadDto getAuditByKeyid(String keyid);
    
    public JhaTlAudituploadDto createOrUpdateAuditupload(JhaTlAudituploadDto jhaTlAudituploadDto) ;
} 
