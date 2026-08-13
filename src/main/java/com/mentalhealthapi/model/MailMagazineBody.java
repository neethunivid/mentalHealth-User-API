package com.mentalhealthapi.model;


import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="mail_magazine_body")
public class MailMagazineBody {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 public MailMagazineBody() {
	        
	    }
	@Column(columnDefinition = "VARCHAR(500)")
	 private String subject;
	
	@Column(columnDefinition = "TEXT")
    private String text;
	
	@Column(name="send_out_time")
    private LocalDateTime sendOutTime;
	
	@Column(name="send_status" )
	private int sendStatus;
	
	@Column(name="mail_mag_job_id")
	private int mailMagJobId;
	
	@Column(name = "job_status", columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
	private String jobStatus;
	@Column(name = "job_remark", columnDefinition = "VARCHAR(255) DEFAULT ''")
	private String jobRemark;

	@Column(name="created_id")
	private int createdId;
	
	private int status;
	
	@Column(columnDefinition = "VARCHAR(10)")
	 private String type;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
	 private Date date;

	public MailMagazineBody(String subject, String text, LocalDateTime sendOutTime, 
			int sendStatus, int mailMagJobId,
			int createdId, int status, String type, Date date) {
		super();
		this.subject = subject;
		this.text = text;
		this.sendOutTime = sendOutTime;
		this.sendStatus = sendStatus;
		this.mailMagJobId = mailMagJobId;
		this.createdId = createdId;
		this.status = status;
		this.type = type;
		this.date = date;
	}
	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getJobRemark() {
		return jobRemark;
	}

	public void setJobRemark(String jobRemark) {
		this.jobRemark = jobRemark;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getSendOutTime() {
		return sendOutTime;
	}

	public void setSendOutTime(LocalDateTime sendOutTime) {
		this.sendOutTime = sendOutTime;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public int getMailMagJobId() {
		return mailMagJobId;
	}

	public void setMailMagJobId(int mailMagJobId) {
		this.mailMagJobId = mailMagJobId;
	}

	public int getCreatedId() {
		return createdId;
	}

	public void setCreatedId(int createdId) {
		this.createdId = createdId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
		

}
