package com.akranta.perfex_sb.dto;

import java.math.BigDecimal;

import com.akranta.perfex_sb.model.BAL_PcsEntryDtl;
import com.akranta.perfex_sb.model.BAL_PcsEntryMst;
import com.akranta.perfex_sb.model.BAL_PcsLossCapture;
import com.akranta.perfex_sb.model.BAL_PcsLossReasonLink;

public class BalPcsLossSaveDto {

    private BAL_PcsEntryMst master;
    private BAL_PcsEntryDtl detail;
    private BAL_PcsLossCapture lossCapture;
    private BAL_PcsLossReasonLink lossReasonLink;
    private EntryBean entryBean = new EntryBean();

    public BAL_PcsEntryMst getMaster() { return master; }
    public void setMaster(BAL_PcsEntryMst master) { this.master = master; }

    public BAL_PcsEntryDtl getDetail() { return detail; }
    public void setDetail(BAL_PcsEntryDtl detail) { this.detail = detail; }

    public BAL_PcsLossCapture getLossCapture() { return lossCapture; }
    public void setLossCapture(BAL_PcsLossCapture lossCapture) { this.lossCapture = lossCapture; }

    public BAL_PcsLossReasonLink getLossReasonLink() { return lossReasonLink; }
    public void setLossReasonLink(BAL_PcsLossReasonLink lossReasonLink) { this.lossReasonLink = lossReasonLink; }

    public EntryBean getEntryBean() { return entryBean; }
    public void setEntryBean(EntryBean entryBean) { this.entryBean = entryBean; }

    /** Replacement of legacy BAL_PcsEntryBean (only what the loss save uses). */
    public static class EntryBean {
        /** Available time for the hour (legacy: getAvailtimehourly) */
        private BigDecimal availtimehourly;

        public BigDecimal getAvailtimehourly() { return availtimehourly; }
        public void setAvailtimehourly(BigDecimal availtimehourly) { this.availtimehourly = availtimehourly; }
    }
}