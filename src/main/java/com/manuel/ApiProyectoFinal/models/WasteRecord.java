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
@Table(name="wasterecord")
public class WasteRecord {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(name = "person",nullable = false)
	private String person;
	
	@NotBlank
	@Column(name="date",nullable=false)
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
	
	public WasteRecord() {
		super();
	}

	public WasteRecord(Long id, String person, double amount, User user, Signed signed) {
		super();
		this.id = id;
		this.person = person;
		this.amount = amount;
		this.user = user;
		this.signed = signed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "WasteRecord [id=" + id + ", person=" + person + ", date=" + date + ", amount=" + amount + ", user="
				+ user + ", signed=" + signed + "]";
	}
	
	
}

