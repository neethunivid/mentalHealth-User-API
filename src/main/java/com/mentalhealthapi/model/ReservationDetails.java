package com.mentalhealthapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="reservation_details")
public class ReservationDetails {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private int reservationId;
	@Column(name="id")	
	private Integer id;
	@Column(name="person_name",columnDefinition = "VARCHAR(50)")
	 private String personName;
	
	@Column(columnDefinition = "VARCHAR(50)")
	 private String surname;
	
	@Column(columnDefinition = "VARCHAR(1)")
	 private String gender;
	
	@Column(columnDefinition = "VARCHAR(50)")
	 private String job;
	
	@Column(columnDefinition = "VARCHAR(9)")
	 private String postalcode;
	
	@Column(columnDefinition = "VARCHAR(100)")
	 private String pref;
	
	@Column(columnDefinition = "VARCHAR(200)")
	 private String town;
	
	@Column(columnDefinition = "VARCHAR(200)")
	 private String address;
	
	@Column(columnDefinition = "VARCHAR(12)")
	 private String telephone;
	
	@Column(columnDefinition = "VARCHAR(50)")
	 private String email;
	
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;
	
	@Column(name = "modified_by")
	private int modifiedBy;
	
	@Column(columnDefinition = "tinyint")
    private String mobile;
	
	@Column(columnDefinition = "VARCHAR(50)")
	 private String age;
	
	@Column(columnDefinition = "VARCHAR(50)")
	 private String type_of_counselling;

	public ReservationDetails(String personName, String surname, String gender, String job, String postalcode,
			String pref, String town, String address, String telephone, String email, LocalDateTime createdOn,
			int createdBy, LocalDateTime modifiedOn, int modifiedBy, String mobile,String age, String type_of_counselling, Integer id) {
		super();
		this.personName = personName;
		this.surname = surname;
		this.gender = gender;
		this.job = job;
		this.postalcode = postalcode;
		this.pref = pref;
		this.town = town;
		this.address = address;
		this.telephone = telephone;
		this.email = email;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.mobile = mobile;
		this.age= age;
		this.type_of_counselling=type_of_counselling;
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public ReservationDetails() {}
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String string) {
		this.mobile = string;
	}
	
	

}
