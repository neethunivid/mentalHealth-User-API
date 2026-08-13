package com.mentalhealthapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="admin_update_history")
public class AdminUpdateHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition="VARCHAR(100)")
	private String username;
	@Column(columnDefinition="VARCHAR(30)")
	private String sex;
	private LocalDateTime date;
	public AdminUpdateHistory(String username, String sex, LocalDateTime date) {
		super();
		this.username = username;
		this.sex = sex;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	

}
