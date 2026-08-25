package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.GenTlEmployeedtl;
import com.akranta.perfex_sb.model.GenTlEmployeemst;

public class RecallEmployeeDto 
{
    private GenTlEmployeemst master;
    private GenTlEmployeedtl detail;
    public GenTlEmployeemst getMaster() {
        return master;
    }
    public void setMaster(GenTlEmployeemst master) {
        this.master = master;
    }
    public GenTlEmployeedtl getDetail() {
        return detail;
    }
    public void setDetail(GenTlEmployeedtl detail) {
        this.detail = detail;
    }

    

}
