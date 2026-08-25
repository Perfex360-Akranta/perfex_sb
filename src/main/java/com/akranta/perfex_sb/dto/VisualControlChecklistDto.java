package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.GenTlVisualcntchecklistdtl;
import com.akranta.perfex_sb.model.GenTlVisualcontrolchecklist;



public class VisualControlChecklistDto {


     GenTlVisualcontrolchecklist genTlVisualcontrolchecklist;
    List<GenTlVisualcntchecklistdtl>genTlVisualcntchecklistdtl;
    VisualControlChecklistdtlDto visualControlChecklistdtlDto;
    
    public GenTlVisualcontrolchecklist getGenTlVisualcontrolchecklist() {
        return genTlVisualcontrolchecklist;
    }
    public void setGenTlVisualcontrolchecklist(GenTlVisualcontrolchecklist genTlVisualcontrolchecklist) {
        this.genTlVisualcontrolchecklist = genTlVisualcontrolchecklist;
    }
    public List<GenTlVisualcntchecklistdtl> getGenTlVisualcntchecklistdtl() {
        return genTlVisualcntchecklistdtl;
    }
    public void setGenTlVisualcntchecklistdtl(List<GenTlVisualcntchecklistdtl> genTlVisualcntchecklistdtl) {
        this.genTlVisualcntchecklistdtl = genTlVisualcntchecklistdtl;
    }
    public VisualControlChecklistdtlDto getVisualControlChecklistdtlDto() {
        return visualControlChecklistdtlDto;
    }
    public void setVisualControlChecklistdtlDto(VisualControlChecklistdtlDto visualControlChecklistdtlDto) {
        this.visualControlChecklistdtlDto = visualControlChecklistdtlDto;
    }
    public void setkeyid(Long long1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setkeyid'");
    }

  
    
}
