package com.manuel.ApiProyectoFinal.models;

import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="production")
public class Production {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(name = "product",nullable = false)
	private String product;
	
	@NotBlank
	@Column(name = "date",nullable = false)
	private Date date;
	
	@NotBlank
	@Column(name = "amount",nullable = false)
	private double amount;
	
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_user")
	private User user;
	
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_signed")
	private Signed signed;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "production_tracmeat", 
      joinColumns = @JoinColumn(name = "traceabilityofmeat_id", referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "production_id", 
      referencedColumnName = "id"))
	private List<TraceabilityOfMeat> listTraceabilityOfMeat;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "production_rawmaterial", 
      joinColumns = @JoinColumn(name = "rawmaterialrecord_id", referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "production_id", 
      referencedColumnName = "id"))
	private List<RawMaterialRecord> listRawMaterialRecord;
	
	public Production() {
		super();
	}

	public Production(Long id, @NotBlank String product, @NotBlank Date date, @NotBlank double amount,
			@NotBlank User user, @NotBlank Signed signed, List<TraceabilityOfMeat> listTraceabilityOfMeat,
			List<RawMaterialRecord> listRawMaterialRecord) {
		super();
		this.id = id;
		this.product = product;
		this.date = date;
		this.amount = amount;
		this.user = user;
		this.signed = signed;
		this.listTraceabilityOfMeat = listTraceabilityOfMeat;
		this.listRawMaterialRecord = listRawMaterialRecord;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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
	

	public List<TraceabilityOfMeat> getListTraceabilityOfMeat() {
		return listTraceabilityOfMeat;
	}

	public void setListTraceabilityOfMeat(List<TraceabilityOfMeat> listTraceabilityOfMeat) {
		this.listTraceabilityOfMeat = listTraceabilityOfMeat;
	}

	public List<RawMaterialRecord> getListRawMaterialRecord() {
		return listRawMaterialRecord;
	}

	public void setListRawMaterialRecord(List<RawMaterialRecord> listRawMaterialRecord) {
		this.listRawMaterialRecord = listRawMaterialRecord;
	}

	@Override
	public String toString() {
		return "Production [id=" + id + ", product=" + product + ", date=" + date + ", amount=" + amount + ", user="
				+ user + ", signed=" + signed + "]";
	}
	
	
	
}
