package com.mentalhealthapi.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.mentalhealthapi.model.ReservationSlot;

public class ReservationSlotResponse {
	private List<String> date_time;
	private List<LocalTime> timeslot_colspan;
	private List<ReservationSlot> list;
	private List<LocalTime> finaltimelist;
	
	private LocalDate date;
	private String timeStart;
	private String timeEnd;
	private String name;
	private Integer id;
	
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
	
	
	public List<String> getDate_time() {
		return date_time;
	}
	public void setDate_time(List<String> dateTimeList) {
		this.date_time = dateTimeList;
	}
	public List<LocalTime> getTimeslot_colspan() {
		return timeslot_colspan;
	}
	public void setTimeslot_colspan(List<LocalTime> timeslot_colspan) {
		this.timeslot_colspan = timeslot_colspan;
	}
	public List<ReservationSlot> getList() {
		return list;
	}
	public void setList(List<ReservationSlot> list) {
		this.list = list;
	}
	public List<LocalTime> getFinaltimelist() {
		return finaltimelist;
	}
	public void setFinaltimelist(List<LocalTime> finaltimelist) {
		this.finaltimelist = finaltimelist;
	}
	public List<LocalDate> getFinaldatelist() {
		return finaldatelist;
	}
	public void setFinaldatelist(List<LocalDate> finaldatelist) {
		this.finaldatelist = finaldatelist;
	}
	private List<LocalDate> finaldatelist;
	
	 
}
