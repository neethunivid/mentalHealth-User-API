package com.mentalhealthapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="mail_mag_job")
public class MailMagJob {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private LocalDateTime date;
	
	private int status;
	
	 @Column(name="send_date")
	 private LocalDate sendDate;
	 
	 @Column(name="file_name",columnDefinition = "VARCHAR(500)")
	 private String fileName;
	 
	 @Column(columnDefinition = "VARCHAR(10)")
	 private String type;
	 
	 @Column(columnDefinition = "VARCHAR(500)")
	 private String topic;
	 
	public MailMagJob(LocalDateTime date, int status, LocalDate sendDate, String fileName, String type, String topic) {
		super();
		this.setDate(date);
		this.status = status;
		this.sendDate = sendDate;
		this.fileName = fileName;
		this.type = type;
		this.topic = topic;
	}

	public MailMagJob() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDate getSendDate() {
		return sendDate;
	}

	public void setSendDate(LocalDate sendDate) {
		this.sendDate = sendDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	 
	 
}
