package com.akranta.perfex_sb.dto;

import java.math.BigDecimal;

import com.akranta.perfex_sb.model.BAL_PcsEntryDtl;
import com.akranta.perfex_sb.model.BAL_PcsEntryMst;
import com.akranta.perfex_sb.model.BAL_PcsEntryProductiondata;

/**
 * Request body for the BAL PCS production entry save (replacement of the legacy
 * DAO createNew(pcsTlProductiondata, pcsTlMst, pcsTlDtl, pcsEntryBean)).
 *
 * The three payloads reuse your JPA entities directly, because BAL_PcsEntryDtl
 * alone has 120+ columns and a copy DTO would only duplicate them.
 * If you prefer strict DTOs, create them the same way as PcsSaveRequestDto.
 */
public class BalPcsEntrySaveDto {

    private BAL_PcsEntryMst master;
    private BAL_PcsEntryDtl detail;
    private BAL_PcsEntryProductiondata productionData;
    private EntryBean entryBean = new EntryBean();

    public BAL_PcsEntryMst getMaster() {
        return master;
    }

    public void setMaster(BAL_PcsEntryMst master) {
        this.master = master;
    }

    public BAL_PcsEntryDtl getDetail() {
        return detail;
    }

    public void setDetail(BAL_PcsEntryDtl detail) {
        this.detail = detail;
    }

    public BAL_PcsEntryProductiondata getProductionData() {
        return productionData;
    }

    public void setProductionData(BAL_PcsEntryProductiondata productionData) {
        this.productionData = productionData;
    }

    public EntryBean getEntryBean() {
        return entryBean;
    }

    public void setEntryBean(EntryBean entryBean) {
        this.entryBean = entryBean;
    }

    /** Replacement of legacy BAL_PcsEntryBean (only the fields createNew uses). */
    public static class EntryBean {

        /** Shift duration in minutes (legacy: getSftDuration) */
        private Integer sftDuration;
        /** Available time for the hour (legacy: getAvailtimehourly) */
        private BigDecimal availtimehourly;
        /** Unreported time for the hour (legacy: getUnreptimehourly) */
        private BigDecimal unreptimehourly;
        /** Production time for the hour (legacy: getProdtimehourly) */
        private BigDecimal prodtimehourly;
        /** Legacy: getAddrowtoGrid */
        private String addrowtoGrid;

        public Integer getSftDuration() {
            return sftDuration;
        }

        public void setSftDuration(Integer sftDuration) {
            this.sftDuration = sftDuration;
        }

        public BigDecimal getAvailtimehourly() {
            return availtimehourly;
        }

        public void setAvailtimehourly(BigDecimal availtimehourly) {
            this.availtimehourly = availtimehourly;
        }

        public BigDecimal getUnreptimehourly() {
            return unreptimehourly;
        }

        public void setUnreptimehourly(BigDecimal unreptimehourly) {
            this.unreptimehourly = unreptimehourly;
        }

        public BigDecimal getProdtimehourly() {
            return prodtimehourly;
        }

        public void setProdtimehourly(BigDecimal prodtimehourly) {
            this.prodtimehourly = prodtimehourly;
        }

        public String getAddrowtoGrid() {
            return addrowtoGrid;
        }

        public void setAddrowtoGrid(String addrowtoGrid) {
            this.addrowtoGrid = addrowtoGrid;
        }
    }
}