package com.mentalhealthapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name=" reservation_region")
public class ReservationRegion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition = "VARCHAR(50)")
	   private String name;

	
	public ReservationRegion(String name) {
		super();
		this.name = name;
	}
	public ReservationRegion() {}
	
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
	

}
