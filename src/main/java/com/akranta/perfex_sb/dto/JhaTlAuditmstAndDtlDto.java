package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.JhaTlAuditdtl;
import com.akranta.perfex_sb.model.JhaTlAuditmst;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "jhaTlAuditmst", "jhaTlAuditdtl" })
public class JhaTlAuditmstAndDtlDto {

    JhaTlAuditmst jhaTlAuditmst;
    List<JhaTlAuditdtl>jhaTlAuditdtl;

    public JhaTlAuditmst getJhaTlAuditmst() {
        return jhaTlAuditmst;
    }

    public void setJhaTlAuditmst(JhaTlAuditmst jhaTlAuditmst) {
        this.jhaTlAuditmst = jhaTlAuditmst;
    }

  
    public List<JhaTlAuditdtl> getJhaTlAuditdtl() {
        return jhaTlAuditdtl;
    }

    public void setJhaTlAuditdtl(List<JhaTlAuditdtl> jhaTlAuditdtl) {
        this.jhaTlAuditdtl = jhaTlAuditdtl;
    }
    
    
   
}
