package com.mentalhealthapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="route_to_counselling")
public class RouteToCounselling {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
   @Column(name = "tel_id")
   private int telId;
   @Column(columnDefinition = "VARCHAR(200)")
   private String name;

	public RouteToCounselling() {
	}
   
   public RouteToCounselling(int telId, String name) {
	super();
	this.telId = telId;
	this.name = name;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getTelId() {
	return telId;
}
public void setTelId(int telId) {
	this.telId = telId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

}
