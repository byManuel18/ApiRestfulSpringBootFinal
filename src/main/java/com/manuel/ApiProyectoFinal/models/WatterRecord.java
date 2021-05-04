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
@Table(name="watterrecord")
public class WatterRecord {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(name = "sampling_point",nullable = false)
	private double sampling_point;
	
	@NotBlank
	@Column(name = "organoleptic_control",nullable = false)
	private double organoleptic_control;
	
	@NotBlank
	@Column(name = "date",nullable = false)
	private Date date;
	
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_signed")
	private Signed signed;
	
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_user")
	private User user;
	
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_condition")
	private Condition condition;
	
	public WatterRecord() {
		super();
	}

	public WatterRecord(Long id, double sampling_point, double organoleptic_control, Date date, Signed signed,
			User user, Condition condition) {
		super();
		this.id = id;
		this.sampling_point = sampling_point;
		this.organoleptic_control = organoleptic_control;
		this.date = date;
		this.signed = signed;
		this.user = user;
		this.condition = condition;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getSampling_point() {
		return sampling_point;
	}

	public void setSampling_point(double sampling_point) {
		this.sampling_point = sampling_point;
	}

	public double getOrganoleptic_control() {
		return organoleptic_control;
	}

	public void setOrganoleptic_control(double organoleptic_control) {
		this.organoleptic_control = organoleptic_control;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "WatterRecord [id=" + id + ", sampling_point=" + sampling_point + ", organoleptic_control="
				+ organoleptic_control + ", date=" + date + ", signed=" + signed + ", user=" + user + ", condition="
				+ condition + "]";
	}


}
