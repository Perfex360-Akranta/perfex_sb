package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.QpointModel;
import com.akranta.perfex_sb.model.QpointdtlsModel;

public class qtm_tl_qpointDto {
    private QpointModel qtmTlmst;
    private QpointdtlsModel qtmTldtl;

    public QpointModel getQtmTlmst() {
        return qtmTlmst;
    }

    public void setQtmTlmst(QpointModel qtmTlmst) {
        this.qtmTlmst = qtmTlmst;
    }

    public QpointdtlsModel getQtmTldtl() {
        return qtmTldtl;
    }

    public void setQtmTldtl(QpointdtlsModel qtmTldtl) {
        this.qtmTldtl = qtmTldtl;
    }

}
