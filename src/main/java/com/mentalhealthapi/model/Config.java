package com.mentalhealthapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="config")
public class Config {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "key", columnDefinition = "VARCHAR(255)")
    private String key;
	@Column(columnDefinition = "TEXT")
	private String value;
	@Column(columnDefinition="VARCHAR(255)")
	private String notes;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	

}
