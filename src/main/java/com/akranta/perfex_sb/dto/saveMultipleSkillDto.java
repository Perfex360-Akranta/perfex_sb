package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.EntTlSkillindexassessdtl;
import com.akranta.perfex_sb.model.EntTlSkillindexassessmst;

public class saveMultipleSkillDto 
{
    List<EntTlSkillindexassessmst>  entTlSkillindexassessmsts;
    List<EntTlSkillindexassessdtl> entTlSkillindexassessdtls;
    public List<EntTlSkillindexassessmst> getEntTlSkillindexassessmsts() {
        return entTlSkillindexassessmsts;
    }
    public void setEntTlSkillindexassessmsts(List<EntTlSkillindexassessmst> entTlSkillindexassessmsts) {
        this.entTlSkillindexassessmsts = entTlSkillindexassessmsts;
    }
    public List<EntTlSkillindexassessdtl> getEntTlSkillindexassessdtls() {
        return entTlSkillindexassessdtls;
    }
    public void setEntTlSkillindexassessdtls(List<EntTlSkillindexassessdtl> entTlSkillindexassessdtls) {
        this.entTlSkillindexassessdtls = entTlSkillindexassessdtls;
    }

    

}
