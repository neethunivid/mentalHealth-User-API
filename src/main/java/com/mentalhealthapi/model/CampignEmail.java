package com.mentalhealthapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="campign_email")
public class CampignEmail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition="VARCHAR(50)")
	private String email;
	private int campign;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;
	@Column(columnDefinition = "tinyint")
	private byte status;
	@Column(name = "is_sent",columnDefinition = "tinyint")
	private byte isSent;
	public CampignEmail(String email, int campign, LocalDateTime createdOn, LocalDateTime modifiedOn, byte status,
			byte isSent) {
		super();
		this.email = email;
		this.campign = campign;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.status = status;
		this.isSent = isSent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCampign() {
		return campign;
	}
	public void setCampign(int campign) {
		this.campign = campign;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public byte getIsSent() {
		return isSent;
	}
	public void setIsSent(byte isSent) {
		this.isSent = isSent;
	}
	

}
