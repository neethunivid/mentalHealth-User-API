package com.mentalhealthapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Uploads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "file_name")
    private String fileName;
	@Column(name = "upload_time")
	private LocalDateTime uploadTime;
	

    public LocalDateTime getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(LocalDateTime uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
}
