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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="meatrecord")
public class MeatRecord {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(name = "product",nullable = false)
	private String product;
	
	@NotBlank
	@Column(name = "supplier",nullable = false)
	private String supplier;
	
	@NotBlank
	@Column(name = "date",nullable = false)
	private Date date;
	
	@NotBlank
	@Column(name = "lote",nullable = false)
	private String lote;
	
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_signed")
	private Signed signed;
	
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_user")
	private User user;
	
	@JsonIgnoreProperties()
	@OneToOne(mappedBy = "meatrecord")
	private TraceabilityOfMeat traceability;
	
	public MeatRecord() {
		super();
	}
	
	public MeatRecord(@NotBlank String product, @NotBlank String supplier, @NotBlank Date date, @NotBlank String lote,
			@NotBlank Signed signed, @NotBlank User user) {
		super();
		this.product = product;
		this.supplier = supplier;
		this.date = date;
		this.lote = lote;
		this.signed = signed;
		this.user = user;
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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Signed getSigned() {
		return signed;
	}

	public void setSigned(Signed signed) {
		this.signed = signed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TraceabilityOfMeat getTraceability() {
		return traceability;
	}

	public void setTraceability(TraceabilityOfMeat traceability) {
		this.traceability = traceability;
	}

	@Override
	public String toString() {
		return "MeatRecord [id=" + id + ", product=" + product + ", supplier=" + supplier + ", date=" + date + ", lote="
				+ lote + ", signed=" + signed + ", user=" + user + "]";
	}
	
	
}
