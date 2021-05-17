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
@Table(name="temperaturerecord")
public class TemperatureRecord {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "temperature",nullable = false)
	private double temperature;
	
	@NotBlank
	@Column(name = "date",nullable = false)
	private Date date;
	
	@NotBlank
	@ManyToOne(cascade= CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_appliance")
	private Appliance appliance;
	
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_user")
	private User user;
	
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_signed")
	private Signed signed;
	
	public TemperatureRecord() {
		super();
	}

	public TemperatureRecord(Long id, @NotBlank double temperature, @NotBlank Date date, @NotBlank Appliance appliance,
			@NotBlank User user, @NotBlank Signed signed) {
		super();
		this.id = id;
		this.temperature = temperature;
		this.date = date;
		this.appliance = appliance;
		this.user = user;
		this.signed = signed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Appliance getAppliance() {
		return appliance;
	}

	public void setAppliance(Appliance appliance) {
		this.appliance = appliance;
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

	@Override
	public String toString() {
		return "TemperatureRecord [id=" + id + ", temperature=" + temperature + ", date=" + date + ", appliance="
				+ appliance + ", user=" + user + ", signed=" + signed + "]";
	}
	
	
	
}
