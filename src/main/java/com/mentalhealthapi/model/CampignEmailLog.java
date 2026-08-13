package com.mentalhealthapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="campign_email_log")
public class CampignEmailLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition="VARCHAR(200)")
	private String file_name;
	private LocalDateTime date;
	public CampignEmailLog(String file_name, LocalDateTime date) {
		super();
		this.file_name = file_name;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	
	

}
