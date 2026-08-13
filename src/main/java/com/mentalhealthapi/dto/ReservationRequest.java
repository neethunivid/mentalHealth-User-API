package com.mentalhealthapi.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mentalhealthapi.helper.LocalDateDeserializer;
import com.mentalhealthapi.helper.LocalDateSerializer;

public class ReservationRequest {
	private int id;
	private int regionId;
	private int respPerson;
	private byte status;
	private LocalDate date;
	private String timeStart;
	private String timeEnd;
	private String reservationStatus;
	private LocalDateTime createdOn;
	private int createdBy;
	private int modifiedBy;
	private LocalDateTime modifiedOn;
	private String age;
	private String type_of_counselling;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getRespPerson() {
		return respPerson;
	}

	public void setRespPerson(int respPerson) {
		this.respPerson = respPerson;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
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

	public String getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
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
