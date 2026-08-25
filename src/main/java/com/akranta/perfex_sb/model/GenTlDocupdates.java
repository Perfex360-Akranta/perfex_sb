package com.akranta.perfex_sb.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "gen_tl_docupdates")
public class GenTlDocupdates {

    @Id
    @Column(name = "dcup_keyid")
    @JsonAlias({"keyid", "dcup_keyid"})
    private String dcupKeyid;

    @Column(name = "dcup_feedbackid")
    @JsonAlias({"feedbackid", "dcup_feedbackid"})
    private String dcupFeedbackid;

    @Column(name = "dcup_refdoctype")
    @JsonAlias({"refdoctype", "dcup_refdoctype"})
    private String dcupRefdoctype;

    @Column(name = "dcup_refdocid")
    @JsonAlias({"refdocid", "dcup_refdocid"})
    private String dcupRefdocid;

    @Column(name = "dcup_updatedoctype")
    @JsonAlias({"updatedoctype", "dcup_updatedoctype"})
    private String dcupUpdatedoctype;

    @Column(name = "dcup_detailid")
    @JsonAlias({"detailid", "dcup_detailid"})
    private String dcupDetailid;

    @Column(name = "dcup_createdby")
    @JsonAlias({"createdby", "dcup_createdby"})
    private String dcupCreatedby;

    @Column(name = "dcup_createdon")
    @JsonAlias({"createdon", "dcup_createdon"})
    private LocalDate dcupCreatedon;

    @Column(name = "dcup_modifiedon")
    @JsonAlias({"modifiedon", "dcup_modifiedon"})
    private LocalDate dcupModifiedon;

    public GenTlDocupdates() {}

    public String getDcupKeyid() { return dcupKeyid; }
    public void setDcupKeyid(String dcupKeyid) { this.dcupKeyid = dcupKeyid; }

    public String getDcupFeedbackid() { return dcupFeedbackid; }
    public void setDcupFeedbackid(String dcupFeedbackid) { this.dcupFeedbackid = dcupFeedbackid; }

    public String getDcupRefdoctype() { return dcupRefdoctype; }
    public void setDcupRefdoctype(String dcupRefdoctype) { this.dcupRefdoctype = dcupRefdoctype; }

    public String getDcupRefdocid() { return dcupRefdocid; }
    public void setDcupRefdocid(String dcupRefdocid) { this.dcupRefdocid = dcupRefdocid; }

    public String getDcupUpdatedoctype() { return dcupUpdatedoctype; }
    public void setDcupUpdatedoctype(String dcupUpdatedoctype) { this.dcupUpdatedoctype = dcupUpdatedoctype; }

    public String getDcupDetailid() { return dcupDetailid; }
    public void setDcupDetailid(String dcupDetailid) { this.dcupDetailid = dcupDetailid; }

    public String getDcupCreatedby() { return dcupCreatedby; }
    public void setDcupCreatedby(String dcupCreatedby) { this.dcupCreatedby = dcupCreatedby; }

    public LocalDate getDcupCreatedon() { return dcupCreatedon; }
    public void setDcupCreatedon(LocalDate dcupCreatedon) { this.dcupCreatedon = dcupCreatedon; }

    public LocalDate getDcupModifiedon() { return dcupModifiedon; }
    public void setDcupModifiedon(LocalDate dcupModifiedon) { this.dcupModifiedon = dcupModifiedon; }
}
