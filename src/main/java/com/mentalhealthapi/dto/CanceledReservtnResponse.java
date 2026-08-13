package com.mentalhealthapi.dto;

import java.util.Date;

public class CanceledReservtnResponse {

	public CanceledReservtnResponse(Date date, String timeStart, String timeEnd,String age,String type_of_counselling, String name, int id,
			String reservationStatus, int reservationId, String personName, String surname, char gender, String job,
			String postalCode, String pref, String town, String address, String telephone, String email,
			String region) {
		this.date = date;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.age=age;
		this.type_of_counselling=type_of_counselling;
		this.name = name;
		this.id = id;
		this.reservationStatus = reservationStatus;
		this.reservationId = reservationId;
		this.personName = personName;
		this.surname = surname;
		this.gender = gender;
		this.job = job;
		this.postalCode = postalCode;
		this.pref = pref;
		this.town = town;
		this.address = address;
		this.telephone = telephone;
		this.email = email;
		this.region = region;
	}

	public CanceledReservtnResponse() {
	}

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	private String timeStart;
	private String timeEnd;
	private String age;
	private String type_of_counselling;
	private String name;
	private int id;
	private String reservationStatus;
	private int reservationId;
	private String personName;
	private String surname;
	private char gender;
	private String job;
	private String postalCode;
	private String pref;
	private String town;
	private String address;
	private String telephone;
	private String email;
	private String region;

	// Constructors, getters, and setters
}