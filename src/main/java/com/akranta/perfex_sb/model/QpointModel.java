package com.akranta.perfex_sb.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "qtm_tl_qpoint", schema = "public")
public class QpointModel implements Serializable {

	@Id
	@NotNull
	@Size(max = 15)
	@Column(name = "qptm_keyid", length = 15, nullable = false)
	private String keyid;

	@NotNull
	@Size(max = 12)
	@Column(name = "qptm_flid", length = 12, nullable = false)
	private String flid;

	@NotNull
	@Size(max = 12)
	@Column(name = "qptm_elementid", length = 12, nullable = false)
	private String elementid;

	@NotNull
	@Size(max = 300)
	@Column(name = "qptm_area", length = 300, nullable = false)
	private String area;

	@NotNull
	@Size(max = 300)
	@Column(name = "qptm_kpov", length = 300, nullable = false)
	private String kpov;

	@NotNull
	@Size(max = 300)
	@Column(name = "qptm_qpoint", length = 300, nullable = false)
	private String qpoint;

	@NotNull
	@Size(max = 10)
	@Column(name = "qptm_preparedby", length = 10, nullable = false)
	private String preparedby;

	@NotNull
	@Column(name = "qptm_date", nullable = false)
	private LocalDateTime date;

	@NotNull
	@Size(max = 300)
	@Column(name = "qptm_nooflocations", length = 300, nullable = false)
	private String nooflocations;

	@NotNull
	@Column(name = "qptm_tempfield1", length = 1, nullable = false)
	private Character tempfield1;

	@NotNull
	@Column(name = "qptm_tempfield2", length = 1, nullable = false)
	private Character tempfield2;

	@NotNull
	@Column(name = "qptm_tempfield3", length = 1, nullable = false)
	private Character tempfield3;

	@NotNull
	@Column(name = "qptm_tempfield4", length = 1, nullable = false)
	private Character tempfield4;

	@NotNull
	@Column(name = "qptm_tempfield5", length = 1, nullable = false)
	private Character tempfield5;

	@NotNull
	@Column(name = "qptm_active", length = 1, nullable = false)
	private Character active;

	@NotNull
	@Size(max = 8)
	@Column(name = "qptm_createdby", length = 8, nullable = false)
	private String createdby;

	@NotNull
	@Column(name = "qptm_createdon", nullable = false)
	private LocalDateTime createdon;

	@NotNull
	@Column(name = "qptm_modifiedon", nullable = false)
	private LocalDateTime modifiedon;

	@Transient
	private List<QpointdtlsModel> qtm_tl_qpointdtlsList;

	public QpointModel() {
	}

	public QpointModel(String keyid, String flid, String elementid, String area, String kpov, String qpoint,
			String preparedby, String date, String nooflocations, Character tempfield1, Character tempfield2,
			Character tempfield3, Character tempfield4, Character tempfield5, Character active, String createdby,
			String createdon,
			String modifiedon) {
		this.keyid = keyid;
		this.flid = flid;
		this.elementid = elementid;
		this.area = area;
		this.kpov = kpov;
		this.qpoint = qpoint;
		this.preparedby = preparedby;
		this.date = LocalDateTime.now();
		this.nooflocations = nooflocations;
		this.tempfield1 = tempfield1;
		this.tempfield2 = tempfield2;
		this.tempfield3 = tempfield3;
		this.tempfield4 = tempfield4;
		this.tempfield5 = tempfield5;
		this.active = active;
		this.createdby = createdby;
		this.createdon = LocalDateTime.now();
		this.modifiedon = LocalDateTime.now();
	}

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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getKpov() {
		return kpov;
	}

	public void setKpov(String kpov) {
		this.kpov = kpov;
	}

	public String getQpoint() {
		return qpoint;
	}

	public void setQpoint(String qpoint) {
		this.qpoint = qpoint;
	}

	public String getPreparedby() {
		return preparedby;
	}

	public void setPreparedby(String preparedby) {
		this.preparedby = preparedby;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getNooflocations() {
		return nooflocations;
	}

	public void setNooflocations(String nooflocations) {
		this.nooflocations = nooflocations;
	}

	public Character getTempfield1() {
		return tempfield1;
	}

	public void setTempfield1(Character tempfield1) {
		this.tempfield1 = tempfield1;
	}

	public Character getTempfield2() {
		return tempfield2;
	}

	public void setTempfield2(Character tempfield2) {
		this.tempfield2 = tempfield2;
	}

	public Character getTempfield3() {
		return tempfield3;
	}

	public void setTempfield3(Character tempfield3) {
		this.tempfield3 = tempfield3;
	}

	public Character getTempfield4() {
		return tempfield4;
	}

	public void setTempfield4(Character tempfield4) {
		this.tempfield4 = tempfield4;
	}

	public Character getTempfield5() {
		return tempfield5;
	}

	public void setTempfield5(Character tempfield5) {
		this.tempfield5 = tempfield5;
	}

	public Character getActive() {
		return active;
	}

	public void setActive(Character active) {
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

	public List<QpointdtlsModel> getQtm_tl_qpointdtlsList() {
		return qtm_tl_qpointdtlsList;
	}

	public void setQtm_tl_qpointdtlsList(List<QpointdtlsModel> qtm_tl_qpointdtlsList) {
		this.qtm_tl_qpointdtlsList = qtm_tl_qpointdtlsList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(keyid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QpointModel other = (QpointModel) obj;
		return Objects.equals(keyid, other.keyid);
	}

	@Override
	public String toString() {
		return "qtm_tl_qpointModel [keyid=" + keyid + ", flid=" + flid + ", elementid=" + elementid + ", area=" + area
				+ ", kpov=" + kpov + ", qpoint=" + qpoint + ", preparedby=" + preparedby + ", date=" + date
				+ ", nooflocations=" + nooflocations + ", tempfield1=" + tempfield1 + ", tempfield2=" + tempfield2
				+ ", tempfield3=" + tempfield3 + ", tempfield4=" + tempfield4 + ", tempfield5=" + tempfield5
				+ ", active=" + active + ", createdby=" + createdby + ", createdon=" + createdon + ", modifiedon="
				+ modifiedon + "]";
	}

}
