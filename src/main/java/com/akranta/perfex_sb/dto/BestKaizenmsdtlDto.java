package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.KznTlBestdtl;
import com.akranta.perfex_sb.model.KznTlBestmst;

public class BestKaizenmsdtlDto {
    private KznTlBestmst kznTlBestmst;
    private List<KznTlBestdtl> bestdtls;
    private List<String> keyIds;

    public KznTlBestmst getKznTlBestmst() {
        return kznTlBestmst;
    }

    public void setKznTlBestmst(KznTlBestmst kznTlBestmst) {
        this.kznTlBestmst = kznTlBestmst;
    }

    public List<KznTlBestdtl> getBestdtls() {
        return bestdtls;
    }

    public void setBestdtls(List<KznTlBestdtl> bestdtls) {
        this.bestdtls = bestdtls;
    }

    public List<String> getKeyIds() {
        return keyIds;
    }

    public void setKeyIds(List<String> keyIds) {
        this.keyIds = keyIds;
    }

}
