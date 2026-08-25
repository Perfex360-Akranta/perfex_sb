package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.GenTlMomKpiLink;
import com.akranta.perfex_sb.model.GenTlMomattendance;
import com.akranta.perfex_sb.model.GenTlMomdtl;
import com.akranta.perfex_sb.model.GenTlMommst;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "genTlMommst", "genTlMomdtls", "gentlMomAttendanceList" })
public class MomMstAndDtlDto {
    GenTlMommst genTlMommst;
    List<GenTlMomdtl> genTlMomdtls;
    List<GenTlMomattendance> gentlMomAttendanceList;
    List<GenTlMomKpiLink> genTlMomKpiLinks;

    public GenTlMommst getGenTlMommst() {
        return genTlMommst;
    }

    public void setGenTlMommst(GenTlMommst genTlMommst) {
        this.genTlMommst = genTlMommst;
    }

    public List<GenTlMomdtl> getGenTlMomdtls() {
        return genTlMomdtls;
    }

    public void setGenTlMomdtls(List<GenTlMomdtl> genTlMomdtls) {
        this.genTlMomdtls = genTlMomdtls;
    }

    public List<GenTlMomattendance> getGentlMomAttendanceList() {
        return gentlMomAttendanceList;
    }

    public void setGentlMomAttendanceList(List<GenTlMomattendance> gentlMomAttendanceList) {
        this.gentlMomAttendanceList = gentlMomAttendanceList;
    }

    public List<GenTlMomKpiLink> getGenTlMomKpiLinks() {
        return genTlMomKpiLinks;
    }

    public void setGenTlMomKpiLinks(List<GenTlMomKpiLink> genTlMomKpiLinks) {
        this.genTlMomKpiLinks = genTlMomKpiLinks;
    }

}
