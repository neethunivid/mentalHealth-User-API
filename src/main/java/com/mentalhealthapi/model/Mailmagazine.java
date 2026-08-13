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
@Table(name="mailmagazine")
public class Mailmagazine {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	@Column(columnDefinition = "VARCHAR(255)")
	 private String title;	
	@Column(columnDefinition = "TEXT")
    private String text;	
	@Column(name="text_mob",columnDefinition = "TEXT")
    private String textMob;
	@Column(name="sendtime")
	private LocalDateTime sendTime;
	@Column(name="sendstatus")
	private int sendStatus;	
	@Column(name = "job_status", columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
	private String jobStatus;
	public LocalDateTime getSendTime() {
		return sendTime;
	}

	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
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

	@Column(name = "job_remark", columnDefinition = "VARCHAR(255) DEFAULT ''")
	private String jobRemark;
	private LocalDate date;	
	private int status;	
	@Column(name="non" ,columnDefinition = "tinyint")
    private byte non;
	
	private int type;
	 public Mailmagazine() {
	    }

	public Mailmagazine(String title, String text, String textMob, LocalDateTime sendtime, int sendstatus, LocalDate date,
			int status, byte non, int type) {
		super();
		this.title = title;
		this.text = text;
		this.textMob = textMob;
		this.sendTime = sendtime;
		this.sendStatus = sendstatus;
		this.setDate(date);
		this.status = status;
		this.non = non;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTextMob() {
		return textMob;
	}

	public void setTextMob(String textMob) {
		this.textMob = textMob;
	}

	public LocalDateTime getSendtime() {
		return sendTime;
	}

	public void setSendtime(LocalDateTime sendtime) {
		this.sendTime = sendtime;
	}

	public int getSendstatus() {
		return sendStatus;
	}

	public void setSendstatus(int sendstatus) {
		this.sendStatus = sendstatus;
	}

	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public byte getNon() {
		return non;
	}

	public void setNon(byte non) {
		this.non = non;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	

}
