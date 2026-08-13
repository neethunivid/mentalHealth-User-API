package com.mentalhealthapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="prefecture")
public class Prefecture {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition = "VARCHAR(50)")
	 private String name;
	
	@Column(name="region_id")
	private int regionId;
	
	@Column(name="list_order")
	private int listOrder;
	public Prefecture() {
        // Empty constructor
    }

	public Prefecture(String name, int regionId, int listOrder) {
		super();
		this.name = name;
		this.regionId = regionId;
		this.listOrder = listOrder;
	}

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

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getListOrder() {
		return listOrder;
	}

	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}
	
	

}
