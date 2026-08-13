package com.mentalhealthapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="resp_person")
public class RespPerson {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "region_id")
    private ReservationRegion region;
	
	public ReservationRegion getRegion() {
		return region;
	}

	public void setRegion(ReservationRegion region) {
		this.region = region;
	}

	@Column(columnDefinition = "VARCHAR(100)")
	   private String name;
	
	@Column(columnDefinition = "int")
    private int status;
	
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	
	@Column(name = "created_by")
	private int createdBy;

	public RespPerson(ReservationRegion region, String name, byte status, LocalDateTime createdOn, int createdBy) {
		super();
		this.region = region;
		this.name = name;
		this.status = status;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}
	public RespPerson() {}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	
	

}
