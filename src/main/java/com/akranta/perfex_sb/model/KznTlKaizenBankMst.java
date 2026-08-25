package com.akranta.perfex_sb.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import java.time.LocalDate;

@Entity
@Table(name="KZN_TL_KAIZENBANKMST",schema="public")

public class KznTlKaizenBankMst {


    @Id
    @Column(name = "kzbn_keyid" , length = 20, nullable = false)
    private String keyid;

    @Column(name = "kzbn_flid" , length = 12, nullable = false)
    private String flid;

    @Column(name = "kzbn_elementid", length = 500, nullable = false)
    private String elementid;

    @Column(name = "kzbn_date", nullable=false)
    private LocalDateTime date;

    @Column(name = "kzbn_kaizen",length=500,nullable=false )
    private String kaizen;

    @Column(name = "kzbn_benefit", length=50,nullable=false)
    private String benefit;

    @Column(name = "kzbn_targetdate", nullable=false)
    private LocalDateTime targetdate;

    @Column(name = "kzbn_pqcdsme",length = 15,nullable = false)
    private String pqcdsme;

    @Column(name = "kzbn_suggestedby", nullable = false,length = 8)
    private String suggestedby;

    @Column(name = "kzbn_responsibility", length=8,nullable=false)
    private String responsibility;

    @Column(name = "kzbn_completedon",nullable = false)
    private LocalDateTime completedon;

    @Column(name = "kzbn_status",length=1,nullable=false)
    private String status;

    @Column(name = "kzbn_accrejremarks", length=500,nullable=false)
    private String accrejremarks;

    @Column(name = "kzbn_acrejby", length=8,nullable=false)
    private String acrejby;

    @Column(name = "kzbn_implementedby" , length=8,nullable=false)
    private String implementedby;

    @Column(name = "kzbn_verifyremarks" , length=500,nullable=false)
    private String verifyremarks;

    @Column(name = "kzbn_impremarks", length=500,nullable=false)
    private String impremarks;

    @Column(name = "kzbn_compremarks", length=500,nullable=false)
    private String compremarks;

    @Column(name = "kzbn_acceptrejon", nullable = false)
    private LocalDateTime acceptrejon;

    @Column(name = "kzbn_implementedon",nullable = false)
    private LocalDateTime implementedon;

    @Column(name = "kzbn_verifiedon",nullable = false)
    private LocalDateTime verifiedon;

    @Column(name = "kzbn_verifiedby", length=8,nullable=false)
    private String verifiedby;

    @Column(name = "kzbn_completedby", length=8,nullable=false)
    private String completedby;

    @Column(name = "kzbn_ehsrelated", length=1,nullable=false)
    private String ehsrelated;

    @Column(name = "kzbn_ehsstatus", length=1,nullable=false)
    private String ehsstatus;

    @Column(name = "kzbn_refdoctype", length=10,nullable=false)
    private String refdoctype;

    @Column(name = "kzbn_refdocno", length=50,nullable=false)
    private String refdocno;

    @Column(name = "kzbn_others", length=1,nullable=false)
    private String others;

    @Column(name = "kzbn_implementcost", nullable=false)
    private BigDecimal implementcost;

    @Column(name = "kzbn_approvalflag", length=1,nullable=false)
    private String approvalflag;

    @Column(name = "kzbn_mocrequired", length=1,nullable=false)
    private String mocrequired;

    @Column(name = "kzbn_active", length=1,nullable=false)
    private String active;

    @Column(name = "kzbn_createdby" , length=8,nullable=false)
    private String createdby;

    @Column(name = "kzbn_createdon",nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kzbn_modifiedon",nullable = false)
    private LocalDateTime modifiedon;

    @Column(name = "kzbn_espsname",length = 300,nullable = false)
    private String espsname;

    @Column(name = "kzbn_mocitem",length = 10,nullable = false)
    private String mocitem;

    @Column(name = "kzbn_tempfiled2",length = 10,nullable = false)
    private String tempfield2;

    @Column(name = "kzbn_tempfiled3",length = 10,nullable = false)
    private String tempfield3;

    @Column(name = "kzbn_nonjhesp", length = 1,nullable = false)
    private String nonjhesp;


	 public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getKaizen() {
        return kaizen;
    }

    public void setKaizen(String kaizen) {
        this.kaizen = kaizen;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public LocalDateTime getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(LocalDateTime targetdate) {
        this.targetdate = targetdate;
    }

    public String getPqcdsme() {
        return pqcdsme;
    }

    public void setPqcdsme(String pqcdsme) {
        this.pqcdsme = pqcdsme;
    }

    public String getSuggestedby() {
        return suggestedby;
    }

    public void setSuggestedby(String suggestedby) {
        this.suggestedby = suggestedby;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public LocalDateTime getCompletedon() {
        return completedon;
    }

    public void setCompletedon(LocalDateTime completedon) {
        this.completedon = completedon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccrejremarks() {
        return accrejremarks;
    }

    public void setAccrejremarks(String accrejremarks) {
        this.accrejremarks = accrejremarks;
    }

    public String getAcrejby() {
        return acrejby;
    }

    public void setAcrejby(String acrejby) {
        this.acrejby = acrejby;
    }

    public String getImplementedby() {
        return implementedby;
    }

    public void setImplementedby(String implementedby) {
        this.implementedby = implementedby;
    }

    public String getVerifyremarks() {
        return verifyremarks;
    }

    public void setVerifyremarks(String verifyremarks) {
        this.verifyremarks = verifyremarks;
    }

    public String getImpremarks() {
        return impremarks;
    }

    public void setImpremarks(String impremarks) {
        this.impremarks = impremarks;
    }

    public String getCompremarks() {
        return compremarks;
    }

    public void setCompremarks(String compremarks) {
        this.compremarks = compremarks;
    }

    public LocalDateTime getAcceptrejon() {
        return acceptrejon;
    }

    public void setAcceptrejon(LocalDateTime acceptrejon) {
        this.acceptrejon = acceptrejon;
    }

    public LocalDateTime getImplementedon() {
        return implementedon;
    }

    public void setImplementedon(LocalDateTime implementedon) {
        this.implementedon = implementedon;
    }

    public LocalDateTime getVerifiedon() {
        return verifiedon;
    }

    public void setVerifiedon(LocalDateTime verifiedon) {
        this.verifiedon = verifiedon;
    }

    public String getVerifiedby() {
        return verifiedby;
    }

    public void setVerifiedby(String verifiedby) {
        this.verifiedby = verifiedby;
    }

    public String getCompletedby() {
        return completedby;
    }

    public void setCompletedby(String completedby) {
        this.completedby = completedby;
    }

    public String getEhsrelated() {
        return ehsrelated;
    }

    public void setEhsrelated(String ehsrelated) {
        this.ehsrelated = ehsrelated;
    }

    public String getEhsstatus() {
        return ehsstatus;
    }

    public void setEhsstatus(String ehsstatus) {
        this.ehsstatus = ehsstatus;
    }

    public String getRefdoctype() {
        return refdoctype;
    }

    public void setRefdoctype(String refdoctype) {
        this.refdoctype = refdoctype;
    }

    public String getRefdocno() {
        return refdocno;
    }

    public void setRefdocno(String refdocno) {
        this.refdocno = refdocno;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public BigDecimal getImplementcost() {
        return implementcost;
    }

    public void setImplementcost(BigDecimal implementcost) {
        this.implementcost = implementcost;
    }

    public String getApprovalflag() {
        return approvalflag;
    }

    public void setApprovalflag(String approvalflag) {
        this.approvalflag = approvalflag;
    }

    public String getMocrequired() {
        return mocrequired;
    }

    public void setMocrequired(String mocrequired) {
        this.mocrequired = mocrequired;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDateTime getCreatedon() {
        return createdon;
    }

    public void setCreatedon(LocalDateTime createdon) {
        this.createdon = createdon;
    }

    public LocalDateTime getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(LocalDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }

    public void  setEspsname(String espsname){
        this.espsname=espsname;
    }

     public String  getEspsname(){
        return espsname;
    }

     public void  setMocitem(String mocitem){
        this.mocitem=mocitem;
    }

     public String  getMocitem(){
        return mocitem;
    }

     public void  setTempfield2(String tempfield2){
        this.tempfield2=tempfield2;
    }

     public String  getTempfield2(){
        return tempfield2;
    }

     public void  setTempfield3(String tempfield3){
        this.tempfield3=tempfield3;
    }

     public String  getTempfield3(){
        return tempfield3;
    }

     public void  setNonjhesp(String nonjhesp){
        this.nonjhesp=nonjhesp;
    }

     public String  getNonjhesp(){
        return nonjhesp;
    }
}




 