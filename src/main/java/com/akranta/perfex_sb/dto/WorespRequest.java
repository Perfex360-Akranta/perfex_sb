package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.Balworespmst;
import com.akranta.perfex_sb.model.Balworespdtl;
import java.util.List;

public class WorespRequest {



   private Balworespmst master;
   private List<Balworespdtl> details;
    private String formActionMode;
    private String formMode;
    private String formHeader;

    public WorespRequest() {}

    public WorespRequest(Balworespmst master, List<Balworespdtl> details) {
        this.master = master;
        this.details = details;
    }

    public Balworespmst getMaster() {
        return master;
    }

    public void setMaster(Balworespmst master) {
        this.master = master;
    }

    public List<Balworespdtl> getDetails() {
        return details;
    }

    public void setDetails(List<Balworespdtl> details) {
        this.details = details;
    }

    public String getFormActionMode() {
        return formActionMode;
    }

    public void setFormActionMode(String formActionMode) {
        this.formActionMode = formActionMode;
    }

    public String getFormMode() {
        return formMode;
    }

    public void setFormMode(String formMode) {
        this.formMode = formMode;
    }

    public String getFormHeader() {
        return formHeader;
    }

    public void setFormHeader(String formHeader) {
        this.formHeader = formHeader;
    }
}