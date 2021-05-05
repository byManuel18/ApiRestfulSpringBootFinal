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
@Table(name="traceabilityofmeat")
public class TraceabilityOfMeat {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
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
	
	@JsonIgnoreProperties("user")
	@OneToOne
	@JoinColumn(name="id_meatrecord")
	private MeatRecord meatrecord;
	
	public TraceabilityOfMeat() {
		super();
	}

	public TraceabilityOfMeat(Long id, @NotBlank Date arrivaldate, Date startdate, Date enddate, @NotBlank User user,
			@NotBlank Signed signed, MeatRecord meatrecord) {
		super();
		this.id = id;
		this.arrivaldate = arrivaldate;
		this.startdate = startdate;
		this.enddate = enddate;
		this.user = user;
		this.signed = signed;
		this.meatrecord = meatrecord;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getArrivaldate() {
		return arrivaldate;
	}

	public void setArrivaldate(Date arrivaldate) {
		this.arrivaldate = arrivaldate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
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

	public MeatRecord getMeatrecord() {
		return meatrecord;
	}

	public void setMeatrecord(MeatRecord meatrecord) {
		this.meatrecord = meatrecord;
	}

	@Override
	public String toString() {
		return "TraceabilityOfMeat [id=" + id + ", arrivaldate=" + arrivaldate + ", startdate=" + startdate
				+ ", enddate=" + enddate + ", user=" + user + ", signed=" + signed + ", meatrecord=" + meatrecord + "]";
	}
	
	
	
	

}
