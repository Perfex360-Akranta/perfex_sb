package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.BdmTlYycountermeasurelink;
import com.akranta.perfex_sb.model.GenTlDocupdates;
import com.akranta.perfex_sb.model.OplTlMst;
import com.fasterxml.jackson.annotation.JsonAlias;

public class OplSaveRequest {

    private OplTlMst oplTlMst;

      @JsonAlias("link")
    private BdmTlYycountermeasurelink bdmTlYycountermeasurelink;

        @JsonAlias("doc")
    private GenTlDocupdates genTlDocupdates;

    public OplTlMst getOplTlMst() { return oplTlMst; }
    public void setOplTlMst(OplTlMst oplTlMst) { this.oplTlMst = oplTlMst; }

    public BdmTlYycountermeasurelink getBdmTlYycountermeasurelink() { return bdmTlYycountermeasurelink; }
    public void setBdmTlYycountermeasurelink(BdmTlYycountermeasurelink bdmTlYycountermeasurelink) {
        this.bdmTlYycountermeasurelink = bdmTlYycountermeasurelink;
    }

    public GenTlDocupdates getGenTlDocupdates() { return genTlDocupdates; }
    public void setGenTlDocupdates(GenTlDocupdates genTlDocupdates) { this.genTlDocupdates = genTlDocupdates; }
}
