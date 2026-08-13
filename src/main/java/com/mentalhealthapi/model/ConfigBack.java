package com.mentalhealthapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="config_back")
public class ConfigBack {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition="VARCHAR(255)")
	private String key;
	@Column(columnDefinition = "TEXT")
	private String value;
	@Column(columnDefinition="VARCHAR(255)")
	private String notes;
	public ConfigBack(String key, String value, String notes) {
		super();
		this.key = key;
		this.value = value;
		this.notes = notes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
