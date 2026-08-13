package com.mentalhealthapi.model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="mail_magazine_email")
public class MailMagazineEmail {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition = "VARCHAR(250)")
	 private String name;
	
	@Column(columnDefinition = "VARCHAR(500)")
	 private String email;
	
	private int status;
	
	@Column(name="created_date")
	 private LocalDateTime createdDate;
	
	@Column(name = "send_status", columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
	private String sendStatus;
	
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	@Column(name="send_date")
	 private Date sendDate;
	
	@Column(name="mail_mag_job")
	private int mailMagJob;
	
	public MailMagazineEmail(String name, String email, int status, LocalDateTime createdDate, Date sendDate, int mailMagJob) {
		super();
		this.name = name;
		this.email = email;
		this.status = status;
		this.createdDate = createdDate;
		this.sendDate = sendDate;
		this.mailMagJob = mailMagJob;
	}
	public MailMagazineEmail() {
		
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime localDateTime) {
		this.createdDate = localDateTime;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public int getMailMagJob() {
		return mailMagJob;
	}

	public void setMailMagJob(int mailMagJob) {
		this.mailMagJob = mailMagJob;
	}
	

}
