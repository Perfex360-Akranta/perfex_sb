package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.Qtmtlcapa;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "qtmtlcapa" })
public class QtmtlcapaDto {
    private Qtmtlcapa qtmtlcapa;

    public Qtmtlcapa getQtmtlcapa() {
        return qtmtlcapa;
    }

    public void setQtmtlcapa(Qtmtlcapa qtmtlcapa) {
        this.qtmtlcapa = qtmtlcapa;
    }
}
