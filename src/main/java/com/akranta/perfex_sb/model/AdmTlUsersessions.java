package com.akranta.perfex_sb.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.io.Serializable;

@Entity
@Table(name = "adm_tl_usersessions", schema = "public")
@IdClass(AdmTlUsersessions.UserSessionId.class)
public class AdmTlUsersessions {
      @Id
    @NotNull
    @Size(max = 8)
    @Column(name = "usse_userid", length = 8, nullable = false)
    private String userid;

    @Id
    @NotNull
    @Column(name = "usse_sessionno", nullable = false)
    private Integer sessionno;

    @NotNull
    @Column(name = "usse_sessiondate", nullable = false)
    private LocalDateTime sessiondate;

    @NotNull
    @Column(name = "usse_logintime", nullable = false)
    private LocalDateTime logintime;

    @NotNull
    @Column(name = "usse_logouttime", nullable = false)
    private LocalDateTime logouttime;

    @NotNull
    @Size(max = 1)
    @Column(name = "usse_sessionstatus", length = 1, nullable = false)
    private String sessionstatus;

    @NotNull
    @Size(max = 35)
    @Column(name = "usse_pcname", length = 35, nullable = false)
    private String pcname;

    @NotNull
    @Size(max = 200)
    @Column(name = "usse_ipaddress", length = 200, nullable = false)
    private String ipaddress;

    @NotNull
    @Size(max = 50)
    @Column(name = "usse_sessionid", length = 50, nullable = false)
    private String sessionid;

    @NotNull
    @Size(max = 2)
    @Column(name = "usse_tempfield1", length = 2, nullable = false)
    private String tempfield1;

    @NotNull
    @Size(max = 2)
    @Column(name = "usse_tempfield2", length = 2, nullable = false)
    private String tempfield2;

    @NotNull
    @Size(max = 2)
    @Column(name = "usse_tempfield3", length = 2, nullable = false)
    private String tempfield3;

    public static class UserSessionId implements Serializable {
        private String userid;
        private Integer sessionno;
        private LocalDateTime sessiondate;


        public UserSessionId() {}

        public UserSessionId(String userid, Integer sessionno,LocalDateTime sessiondate ) {
            this.userid = userid;
            this.sessionno = sessionno;
            this.sessiondate = sessiondate;
        }

        // Getters and Setters
        public String getUserid() { return userid; }
        public void setUserid(String userid) { this.userid = userid; }
        public Integer getSessionno() { return sessionno; }
        public void setSessionno(Integer sessionno) { this.sessionno = sessionno; }
        public LocalDateTime getSessiondate() { return sessiondate; }
        public void setSessiondate(LocalDateTime sessiondate) { this.sessiondate = sessiondate; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserSessionId)) return false;
            UserSessionId that = (UserSessionId) o;
            return userid != null ? userid.equals(that.userid) : that.userid == null &&
                   sessionno != null ? sessionno.equals(that.sessionno) : that.sessionno == null;
        }

        @Override
        public int hashCode() {
            int result = userid != null ? userid.hashCode() : 0;
            result = 31 * result + (sessionno != null ? sessionno.hashCode() : 0);
            return result;
        }
    }

    // Constructors
    public AdmTlUsersessions() {}

    public AdmTlUsersessions(String userid, Integer sessionno, LocalDateTime sessiondate, 
                           LocalDateTime logintime, LocalDateTime logouttime, String sessionstatus, 
                           String pcname, String ipaddress, String sessionid, 
                           String tempfield1, String tempfield2, String tempfield3) {
        this.userid = userid;
        this.sessionno = sessionno;
        this.sessiondate = sessiondate;
        this.logintime = logintime;
        this.logouttime = logouttime;
        this.sessionstatus = sessionstatus;
        this.pcname = pcname;
        this.ipaddress = ipaddress;
        this.sessionid = sessionid;
        this.tempfield1 = tempfield1;
        this.tempfield2 = tempfield2;
        this.tempfield3 = tempfield3;
    }

    // Getters and Setters
    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }

    public Integer getSessionno() { return sessionno; }
    public void setSessionno(Integer sessionno) { this.sessionno = sessionno; }

    public LocalDateTime getSessiondate() { return sessiondate; }
    public void setSessiondate(LocalDateTime sessiondate) { this.sessiondate = sessiondate; }

    public LocalDateTime getLogintime() { return logintime; }
    public void setLogintime(LocalDateTime logintime) { this.logintime = logintime; }

    public LocalDateTime getLogouttime() { return logouttime; }
    public void setLogouttime(LocalDateTime logouttime) { this.logouttime = logouttime; }

    public String getSessionstatus() { return sessionstatus; }
    public void setSessionstatus(String sessionstatus) { this.sessionstatus = sessionstatus; }

    public String getPcname() { return pcname; }
    public void setPcname(String pcname) { this.pcname = pcname; }

    public String getIpaddress() { return ipaddress; }
    public void setIpaddress(String ipaddress) { this.ipaddress = ipaddress; }

    public String getSessionid() { return sessionid; }
    public void setSessionid(String sessionid) { this.sessionid = sessionid; }

    public String getTempfield1() { return tempfield1; }
    public void setTempfield1(String tempfield1) { this.tempfield1 = tempfield1; }

    public String getTempfield2() { return tempfield2; }
    public void setTempfield2(String tempfield2) { this.tempfield2 = tempfield2; }

    public String getTempfield3() { return tempfield3; }
    public void setTempfield3(String tempfield3) { this.tempfield3 = tempfield3; }

    @Override
    public String toString() {
        return "AdmTlUsersessions{" +
                "userid='" + userid + '\'' +
                ", sessionno=" + sessionno +
                ", sessiondate=" + sessiondate +
                ", logintime=" + logintime +
                ", logouttime=" + logouttime +
                ", sessionstatus='" + sessionstatus + '\'' +
                ", pcname='" + pcname + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", sessionid='" + sessionid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdmTlUsersessions)) return false;
        AdmTlUsersessions that = (AdmTlUsersessions) o;
        return userid != null ? userid.equals(that.userid) : that.userid == null &&
               sessionno != null ? sessionno.equals(that.sessionno) : that.sessionno == null;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (sessionno != null ? sessionno.hashCode() : 0);
        return result;
    }
}
