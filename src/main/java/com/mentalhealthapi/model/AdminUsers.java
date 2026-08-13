package com.mentalhealthapi.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="admin_users")
public class AdminUsers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition="VARCHAR(50)")
	private String username;
	@Column(columnDefinition="VARCHAR(50)")
	private String password;
	@Column(columnDefinition="VARCHAR(50)")
	private String region;
	@Column(columnDefinition = "tinyint")
	private byte status;
	public AdminUsers(String username, String password, String region, byte status) {
		super();
		this.username = username;
		this.password = password;
		this.region = region;
		this.status = status;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	
	

}
