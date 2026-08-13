package com.mentalhealthapi.dto;

import java.time.LocalDateTime;

public class ReservationAddDTO {
	private int id;
	private String name;
	private String furigana;
	private String sex;
	private String age;
	private String job;
	private String postalcode;
	private String pref;
	private String town;
	private String address;
	private String phone;
	private String email;
	private String dbaction;
	private int region_id;
	private int reservation_id;
	private String type_of_counselling;
	private LocalDateTime date;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFurigana() {
		return furigana;
	}
	public void setFurigana(String furigana) {
		this.furigana = furigana;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getPref() {
		return pref;
	}
	public void setPref(String pref) {
		this.pref = pref;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDbaction() {
		return dbaction;
	}
	public void setDbaction(String dbaction) {
		this.dbaction = dbaction;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRegion_id() {
		return region_id;
	}
	public void setRegion_id(int region_id) {
		this.region_id = region_id;
	}
	public int getReservation_id() {
		return reservation_id;
	}
	public void setReservation_id(int reservation_id) {
		this.reservation_id = reservation_id;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getType_of_counselling() {
		return type_of_counselling;
	}
	public void setType_of_counselling(String type_of_counselling) {
		this.type_of_counselling = type_of_counselling;
	}
	
	
	
	
	

}
