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
	private Date arrival_date;
	
	
	@Column(name = "start_date",nullable = true)
	private Date start_date;
	
	@Column(name = "end_date",nullable = true)
	private Date end_date;
	
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
		this.arrival_date = arrival_date;
		this.start_date = start_date;
		this.end_date = end_date;
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
		return arrival_date;
	}
	public void setArrival_date(Date arrival_date) {
		this.arrival_date = arrival_date;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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
