package com.mentalhealthapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="mag_user")
public class MagUser {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int mailmagazine;
	
	@Column(name="mailmagazine_user")
	private int mailmagazineUser;
	
	private LocalDateTime sendtime;
	
	private int status;
	
	private LocalDateTime sent;

	public MagUser(int mailmagazine, int mailmagazineUser, LocalDateTime sendtime, int status, LocalDateTime sent) {
		super();
		this.mailmagazine = mailmagazine;
		this.mailmagazineUser = mailmagazineUser;
		this.sendtime = sendtime;
		this.status = status;
		this.sent = sent;
	}
	public MagUser() {}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMailmagazine() {
		return mailmagazine;
	}

	public void setMailmagazine(int mailmagazine) {
		this.mailmagazine = mailmagazine;
	}

	public int getMailmagazineUser() {
		return mailmagazineUser;
	}

	public void setMailmagazineUser(int mailmagazineUser) {
		this.mailmagazineUser = mailmagazineUser;
	}

	public LocalDateTime getSendtime() {
		return sendtime;
	}

	public void setSendtime(LocalDateTime sendtime) {
		this.sendtime = sendtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getSent() {
		return sent;
	}

	public void setSent(LocalDateTime sent) {
		this.sent = sent;
	}
	
	

}
