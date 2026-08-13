package com.mentalhealthapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="campign")
public class Campign {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition="VARCHAR(100)")
	private String name;
	@Column(columnDefinition="VARCHAR(500)")
	private String text;
	@Column(name = "to_email",columnDefinition="VARCHAR(100)")
	private String toEmail;
	public Campign(String name, String text, String toEmail) {
		super();
		this.name = name;
		this.text = text;
		this.toEmail = toEmail;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	
	

}
