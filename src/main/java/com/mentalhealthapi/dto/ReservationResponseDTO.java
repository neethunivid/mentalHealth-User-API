package com.mentalhealthapi.dto;

import java.time.LocalDate;

public class ReservationResponseDTO {
	private LocalDate date;
	private String timeStart;
	private String timeEnd;
	private String name;
	private Integer id;
	private String reservationstatus;
	private Integer regionId;
	private String regionName;
	private String typeOfCounselling;
	public String getTypeOfCounselling() {
		return typeOfCounselling;
	}
	public void setTypeOfCounselling(String typeOfCounselling) {
		this.typeOfCounselling = typeOfCounselling;
	}
	public String getReservationstatus() {
		return reservationstatus;
	}
	public void setReservationstatus(String reservationstatus) {
		this.reservationstatus = reservationstatus;
	}
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
