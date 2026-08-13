package com.mentalhealthapi.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.mentalhealthapi.model.Region;
import com.mentalhealthapi.model.ReservationDetails;
import com.mentalhealthapi.model.ReservationRegion;

public class ReservationSlotListResponse {
	
    private int  id;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private int respPerson;
    private String respPersonName;
    private byte status;
    private LocalDateTime createdOn;
    private Integer createdBy;
    private LocalDateTime modifiedOn;
    private Integer modifiedBy;
    private String reservationStatus;
    private int reservationId;
    //private ReservationDetails reservationId;
    private ReservationRegion region;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(LocalTime timeStart) {
		this.timeStart = timeStart;
	}
	public LocalTime getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(LocalTime timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	public String getRespPersonName() {
		return respPersonName;
	}
	public void setRespPersonName(String respPersonName) {
		this.respPersonName = respPersonName;
	}
		
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public ReservationRegion getRegion() {
		return region;
	}
	public void setRegion(ReservationRegion region) {
		this.region = region;
	}
	public int getRespPerson() {
		return respPerson;
	}
	public void setRespPerson(int respPerson) {
		this.respPerson = respPerson;
	}
//	public ReservationDetails getReservationId() {
//		return reservationId;
//	}
//	public void setReservationId(ReservationDetails reservationId) {
//		this.reservationId = reservationId;
//	}
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
                  
                 

}
