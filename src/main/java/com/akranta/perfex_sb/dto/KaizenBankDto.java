package com.akranta.perfex_sb.dto;



import com.akranta.perfex_sb.model.KznTlKaizenBankMst;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotNull;

@JsonPropertyOrder({ "kznTlKaizenbankmst" })
 @NotNull(message = "Created date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
public class KaizenBankDto {
    KznTlKaizenBankMst kznTlKaizenbankmst;
   

    public KznTlKaizenBankMst getkznTlKaizenbankmst() {
        return kznTlKaizenbankmst;
    }

    public void setKznTlKaizenBankMst(KznTlKaizenBankMst kznTlKaizenbankmst) {
        this.kznTlKaizenbankmst = kznTlKaizenbankmst;
    }
/*
    public List<GenTlMomdtl> getGenTlMomdtls() {
        return genTlMomdtls;
    }

    public void setGenTlMomdtls(List<GenTlMomdtl> genTlMomdtls) {
        this.genTlMomdtls = genTlMomdtls;
    }

    public List<GenTlMomattendance> getGentlMomAttendanceList() {
        return gentlMomAttendanceList;
    }

    public void setGentlMomAttendanceList(List<GenTlMomattendance> gentlMomAttendanceList) {
        this.gentlMomAttendanceList = gentlMomAttendanceList;
    }
*/
}
