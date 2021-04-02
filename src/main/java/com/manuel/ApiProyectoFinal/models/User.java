package com.manuel.ApiProyectoFinal.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
//@JsonIgnoreProperties("listaDiscos")
@Table(name="User_")
public class User {
	
	@Id
	private String uid;
	
	@NotBlank
	@Column(name="name")
	private String name;
	
	@NotBlank
	@Column(name="gmail",unique = true)
	private String gmail;
	@Column(name="avatar")
	private String avatar;
	
	@NotBlank
	@Column(name="phone")
	private String phone;
	
	@NotBlank
	@Column(name="address")
	private String address;
	@NotBlank
	@Column(name="active")
	private boolean active;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGmail() {
		return gmail;
	}
	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", name=" + name + ", gmail=" + gmail + ", avatar=" + avatar + ", phone=" + phone
				+ ", address=" + address + "]";
	}
	
	
	
	
}
