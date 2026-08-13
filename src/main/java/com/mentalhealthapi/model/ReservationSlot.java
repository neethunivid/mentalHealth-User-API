package com.mentalhealthapi.model;

import java.util.Date;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "reservation_slot")
public class ReservationSlot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private LocalDate date;

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "time_start", columnDefinition = "VARCHAR(50)")
	private String timeStart;

	@Column(name = "time_end", columnDefinition = "VARCHAR(50)")
	private String timeEnd;

	//@Column(name = "resp_person")
	@ManyToOne
	@JoinColumn(name = "resp_person")
	private RespPerson respPerson;

	@Column(columnDefinition = "tinyint")
	private byte status;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "created_by")
	private int createdBy;

	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;
	
	@Column(name = "modified_by")
	private int modifiedBy;
	
	@Column(name = "age")
	private String age;
	
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

	@Column(name = "type_of_counselling")
	private String type_of_counselling;
	
	
	@Transient
	private String respPersonName;

	public String getRespPersonName() {
		return respPersonName;
	}

	public void setRespPersonName(String respPersonName) {
		this.respPersonName = respPersonName;
	}

	@Column(name = "reservation_status", columnDefinition = "VARCHAR(50)")
	private String reservationStatus;

	@ManyToOne
	@JoinColumn(name = "reservation_id")
	private ReservationDetails reservationId;

	@ManyToOne
	@JoinColumn(name = "region_id")
	private ReservationRegion region;

	public ReservationRegion getRegion() {
		return region;
	}

	public void setRegion(ReservationRegion region) {
		this.region = region;
	}

	public ReservationSlot(LocalDate date, String timeStart, String timeEnd, RespPerson respPerson, byte status,
			LocalDateTime createdOn, int createdBy, LocalDateTime modifiedOn, int modifiedBy, String reservationStatus,
			ReservationDetails reservationId, ReservationRegion region,String respPersonName,String age,String type_of_counselling) {
		super();
		this.date = date;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.respPerson = respPerson;
		this.reservationStatus = reservationStatus;
		this.status=status;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.reservationStatus = reservationStatus;
		this.reservationId = reservationId;
		this.respPersonName=respPersonName;
		this.region = region;
		this.age=age;
		this.type_of_counselling=type_of_counselling;
	}
	public ReservationSlot() {}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public RespPerson getRespPerson() {
		return respPerson;
	}

	public void setRespPerson(RespPerson respPerson) {
		this.respPerson = respPerson;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
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

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public ReservationDetails getReservationId() {
		return reservationId;
	}

	public void setReservationId(ReservationDetails reservationId) {
		this.reservationId = reservationId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
