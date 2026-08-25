
package com.akranta.perfex_sb.dto;


import com.akranta.perfex_sb.model.JhaTlAuditmst;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "jhaTlAuditmst" })
public class JhaTlAudituploadDto {

    JhaTlAuditmst jhaTlAuditmst;
    

    public JhaTlAuditmst getJhaTlAuditmst() {
        return jhaTlAuditmst;
    }

    public void setJhaTlAuditmst(JhaTlAuditmst jhaTlAuditmst) {
        this.jhaTlAuditmst = jhaTlAuditmst;
    }
    
    
   
}
