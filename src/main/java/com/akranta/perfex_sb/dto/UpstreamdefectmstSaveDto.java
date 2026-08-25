package com.akranta.perfex_sb.dto;



import com.akranta.perfex_sb.model.UpstreamdefectDet;
import com.akranta.perfex_sb.model.Upstreamdefectmst;

public class UpstreamdefectmstSaveDto {

    private Upstreamdefectmst upstreamdefectmst;
    private UpstreamdefectDet upstreamdefectDet;//upstreamdefectDet

    //private List<UpstreamdefectDet> upstreamdefectDet;

    //getter seter
    public Upstreamdefectmst getUpstreamdefectmst() {
        return upstreamdefectmst;
    }
    public void setUpstreamdefectmst(Upstreamdefectmst upstreamdefectmst) {
        this.upstreamdefectmst = upstreamdefectmst;
    }
    public UpstreamdefectDet getUpstreamdefectDet() {
        return upstreamdefectDet;
    }
    public void setUpstreamdefectDet(UpstreamdefectDet upstreamdefectDet) {
        this.upstreamdefectDet = upstreamdefectDet;
    }
    


}
