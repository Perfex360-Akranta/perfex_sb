package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.QtmTlIntrejectiondtl;
import com.akranta.perfex_sb.model.QtmTlIntrejectionmst;

public class internalRejectionMstDtlDto

{
    QtmTlIntrejectiondtl qtmTlIntrejectiondtl;
    QtmTlIntrejectionmst qtmTlIntrejectionmst;
    IntRejEntryDto intRejEntryDto;
    
    public QtmTlIntrejectiondtl getQtmTlIntrejectiondtl() {
        return qtmTlIntrejectiondtl;
    }
    public void setQtmTlIntrejectiondtl(QtmTlIntrejectiondtl qtmTlIntrejectiondtl) {
        this.qtmTlIntrejectiondtl = qtmTlIntrejectiondtl;
    }
    public QtmTlIntrejectionmst getQtmTlIntrejectionmst() {
        return qtmTlIntrejectionmst;
    }
    public void setQtmTlIntrejectionmst(QtmTlIntrejectionmst qtmTlIntrejectionmst) {
        this.qtmTlIntrejectionmst = qtmTlIntrejectionmst;
    }
    public IntRejEntryDto getIntRejEntryDto() {
        return intRejEntryDto;
    }
    public void setIntRejEntryDto(IntRejEntryDto intRejEntryDto) {
        this.intRejEntryDto = intRejEntryDto;
    }
    
}
