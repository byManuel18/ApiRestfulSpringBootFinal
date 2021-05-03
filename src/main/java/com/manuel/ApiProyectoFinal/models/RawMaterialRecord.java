package com.manuel.ApiProyectoFinal.models;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
@Entity
@Table(name="rawmaterialrecord")
public class RawMaterialRecord {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(name = "commodity",nullable = false)
	private String commodity;
	
	@NotBlank
	@Column(name = "supplier",nullable = false)
	private String supplier;
	
	@NotBlank
	@Column(name = "lote",nullable = false)
	private String lote;
	
	@NotBlank
	@Column(name = "arrival_date",nullable = false)
	private Date arrivaldate;
	
	
	@Column(name = "start_date",nullable = true)
	private Date startdate;
	
	@Column(name = "end_date",nullable = true)
	private Date enddate;
	
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_user")
	private User user;
	
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_signed")
	private Signed signed;
	
	public RawMaterialRecord() {
		super();
	}
	public RawMaterialRecord(Long id, String commodity, String supplier, String lote, Date arrival_date,
			Date start_date, Date end_date, User user, Signed signed) {
		super();
		this.id = id;
		this.commodity = commodity;
		this.supplier = supplier;
		this.lote = lote;
		this.arrivaldate = arrival_date;
		this.startdate = start_date;
		this.enddate = end_date;
		this.user = user;
		this.signed = signed;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public Date getArrival_date() {
		return arrivaldate;
	}
	public void setArrival_date(Date arrival_date) {
		this.arrivaldate = arrival_date;
	}
	public Date getStart_date() {
		return startdate;
	}
	public void setStart_date(Date start_date) {
		this.startdate = start_date;
	}
	public Date getEnd_date() {
		return enddate;
	}
	public void setEnd_date(Date end_date) {
		this.enddate = end_date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Signed getSigned() {
		return signed;
	}
	public void setSigned(Signed signed) {
		this.signed = signed;
	}
	
	
	
	
	

}
