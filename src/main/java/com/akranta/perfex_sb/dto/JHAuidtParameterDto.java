package com.akranta.perfex_sb.dto;

import java.util.List;
import com.akranta.perfex_sb.model.JhaTlAuditparameter;


import com.akranta.perfex_sb.model.JhaTlAudittemplate;
import com.akranta.perfex_sb.model.JhaTlTemplatelevellink;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "jhatlauditparameter", "jhatltemplatelevellink", "jhatlaudittemplate" })
public class JHAuidtParameterDto {

    JhaTlAuditparameter jhatlauditparameter;
    List<JhaTlTemplatelevellink> jhatltemplatelevellink;
    List<JhaTlAudittemplate> jhatlaudittemplate;
    
    
    public JhaTlAuditparameter getJhatlauditparameter() {
        return jhatlauditparameter;
    }
    public void setJhatlauditparameter(JhaTlAuditparameter jhatlauditparameter) {
        this.jhatlauditparameter = jhatlauditparameter;
    }
    public List<JhaTlTemplatelevellink> getJhatltemplatelevellink() {
        return jhatltemplatelevellink;
    }
    public void setJhatltemplatelevellink(List<JhaTlTemplatelevellink> jhatltemplatelevellink) {
        this.jhatltemplatelevellink = jhatltemplatelevellink;
    }
    public List<JhaTlAudittemplate> getJhatlaudittemplate() {
        return jhatlaudittemplate;
    }
    public void setJhatlaudittemplate(List<JhaTlAudittemplate> jhatlaudittemplate) {
        this.jhatlaudittemplate = jhatlaudittemplate;
    }



    
}
