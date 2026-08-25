package com.akranta.perfex_sb.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@IdClass(KkTlFipapprovalslist.FipApprovalsListId.class)
@Table(name = "kk_tl_fipapprovalslist", schema = "public")
public class KkTlFipapprovalslist {
    
     @Id
    @Column(name = "projectno", length = 15)
    private String projectno;

    @Id
    @Column(name = "stage", length = 15)
    private String stage;

    
    @Column(name = "fnlnid", length = 12)
    private String fnlnid;

    @Column(name = "projecttype", length = 10)
    private String projecttype;

    @Column(name = "nextapproval", length = 20)
    private String nextapproval;

    @Column(name = "islastapproval", columnDefinition = "char(1)")
    private Character islastapproval;

    @Id
    @Column(name = "lastapprovalby", length = 20)
    private String lastapprovalby;

    @Column(name = "lastapprovaldate")
    private LocalDateTime lastapprovaldate;

    @Column(name = "menulevelstatus",columnDefinition = "char(1)")
    private Character menulevelstatus;

    @Column(name = "currentstatus",columnDefinition = "char(1)")
    private Character currentstatus;

public static class FipApprovalsListId implements Serializable {
     private String projectno;
     private String stage;
     private String lastapprovalby;

     public FipApprovalsListId() {}

     public FipApprovalsListId(String projectno, String stage,String lastapprovalby) {
            this.projectno = projectno;
            this.stage = stage;
            this.lastapprovalby = lastapprovalby;
           
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FipApprovalsListId)) return false;
            FipApprovalsListId that = (FipApprovalsListId) o;
            return  Objects.equals(projectno, that.projectno)
                && Objects.equals(stage, that.stage) && Objects.equals(lastapprovalby, that.lastapprovalby);
        }

        @Override
        public int hashCode() {
            return Objects.hash(projectno, stage,lastapprovalby);
        }

        public String getProjectno() {
            return projectno;
        }

        public void setProjectno(String projectno) {
            this.projectno = projectno;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getLastapprovalby() {
            return lastapprovalby;
        }

        public void setLastapprovalby(String lastapprovalby) {
            this.lastapprovalby = lastapprovalby;
        }



    }

public String getProjectno() {
    return projectno;
}

public void setProjectno(String projectno) {
    this.projectno = projectno;
}

public String getStage() {
    return stage;
}

public void setStage(String stage) {
    this.stage = stage;
}

public String getFnlnid() {
    return fnlnid;
}

public void setFnlnid(String fnlnid) {
    this.fnlnid = fnlnid;
}

public String getProjecttype() {
    return projecttype;
}

public void setProjecttype(String projecttype) {
    this.projecttype = projecttype;
}

public String getNextapproval() {
    return nextapproval;
}

public void setNextapproval(String nextapproval) {
    this.nextapproval = nextapproval;
}

public Character getIslastapproval() {
    return islastapproval;
}

public void setIslastapproval(Character islastapproval) {
    this.islastapproval = islastapproval;
}

public String getLastapprovalby() {
    return lastapprovalby;
}

public void setLastapprovalby(String lastapprovalby) {
    this.lastapprovalby = lastapprovalby;
}

public LocalDateTime getLastapprovaldate() {
    return lastapprovaldate;
}

public void setLastapprovaldate(LocalDateTime lastapprovaldate) {
    this.lastapprovaldate = lastapprovaldate;
}

public Character getMenulevelstatus() {
    return menulevelstatus;
}

public void setMenulevelstatus(Character menulevelstatus) {
    this.menulevelstatus = menulevelstatus;
}

public Character getCurrentstatus() {
    return currentstatus;
}

public void setCurrentstatus(Character currentstatus) {
    this.currentstatus = currentstatus;
}

    

}
