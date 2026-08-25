package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.EntTlSkillindexassessdtl;
import com.akranta.perfex_sb.model.EntTlSkillindexassessmst;

public class saveSkillDto {
    EntTlSkillindexassessmst skillAssessmentmstList;
    private List<EntTlSkillindexassessdtl> skillindexassessdtlsList;

    public EntTlSkillindexassessmst getSkillAssessmentmstList() {
        return skillAssessmentmstList;
    }

    public void setSkillAssessmentmstList(EntTlSkillindexassessmst skillAssessmentmstList) {
        this.skillAssessmentmstList = skillAssessmentmstList;
    }

    public List<EntTlSkillindexassessdtl> getSkillindexassessdtlsList() {
        return skillindexassessdtlsList;
    }

    public void setSkillindexassessdtlsList(List<EntTlSkillindexassessdtl> skillindexassessdtlsList) {
        this.skillindexassessdtlsList = skillindexassessdtlsList;
    }

}
