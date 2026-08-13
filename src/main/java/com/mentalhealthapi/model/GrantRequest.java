package com.mentalhealthapi.model;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="grant_request")
public class GrantRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(columnDefinition="VARCHAR(200)")
	private String location1;
	@Column(columnDefinition="VARCHAR(200)")
	private String prefectures1;
	@Column(columnDefinition="VARCHAR(200)")
	private String municipality1;
	@Column(columnDefinition="VARCHAR(200)")
	private String address1;
	@Column(columnDefinition="VARCHAR(200)")
	private String building1;
	@Column(columnDefinition="VARCHAR(200)")
	private String institution;
	@Column(columnDefinition="VARCHAR(200)")
	private String dept;
	@Column(columnDefinition="VARCHAR(200)")
	private String fullname;
	@Column(columnDefinition="VARCHAR(200)")
	private String furigana;
	@Column(columnDefinition="VARCHAR(200)")
	private String dob;
	@Column(columnDefinition="VARCHAR(200)")
	private String age;
	@Column(columnDefinition="VARCHAR(200)")
	private String home;
	@Column(columnDefinition="VARCHAR(200)")
	private String prefectures2;
	@Column(columnDefinition="VARCHAR(200)")
	private String municipality2;
	@Column(columnDefinition = "TEXT")
	private String address2;
	@Column(columnDefinition="VARCHAR(200)")
	private String building2;
	@Column(columnDefinition="VARCHAR(200)")
	private String phone;
	@Column(columnDefinition="VARCHAR(200)")
	private String email2;
	@Column(columnDefinition="VARCHAR(200)")
	private String representative;
	@Column(name="rep_location",columnDefinition="VARCHAR(200)")
	private String repLocation;
	@Column(name="rep_prefectures",columnDefinition="VARCHAR(200)")
	private String repPrefectures;
	@Column(name="rep_municipality",columnDefinition="VARCHAR(200)")
	private String repMunicipality;
	@Column(name="rep_address",columnDefinition="VARCHAR(200)")
	private String repAddress;
	@Column(name="rep_building",columnDefinition="VARCHAR(200)")
	private String repBuilding;
	@Column(name="rep_phone",columnDefinition="VARCHAR(200)")
	private String repPhone;
	@Column(name="rep_email",columnDefinition="VARCHAR(200)")
	private String repEmail;
	@Column(name="in_charge",columnDefinition="VARCHAR(200)")
	private String inCharge;
	@Column(name="recomd_pern_inst",columnDefinition="VARCHAR(200)")
	private String recomdPernInst;
	@Column(name="recomd_pern_job",columnDefinition="VARCHAR(200)")
	private String recomdPernJob;
	@Column(name="recomd_pern_fullname",columnDefinition="VARCHAR(200)")
	private String recomdPernFullname;
	@Column(name="recomd_pern_location",columnDefinition="VARCHAR(200)")
	private String recomdPernLocation;
	@Column(name="recomd_pern_prefectures4",columnDefinition="VARCHAR(200)")
	private String recomdPernPrefectures4;
	@Column(name="recomd_pern_municipality",columnDefinition="VARCHAR(200)")
	private String recomdPernMunicipality;
	@Column(name="recomd_pern_address",columnDefinition="VARCHAR(200)")
	private String recomdPernAddress;
	@Column(name="recomd_pern_building",columnDefinition="VARCHAR(200)")
	private String recomdPernBuilding;
	@Column(name="recomd_pern_phone",columnDefinition="VARCHAR(200)")
	private String recomdPernPhone;
	@Column(columnDefinition="VARCHAR(200)")
	private String file1;
	@Column(columnDefinition="VARCHAR(200)")
	private String biography;
	@Column(columnDefinition="VARCHAR(200)")
	private String application;
	@Column(columnDefinition="VARCHAR(200)")
	private String classification;
	@Column(name="new_cont",columnDefinition="VARCHAR(200)")
	private String newCont;
	
	@Column(name="research_name",columnDefinition="VARCHAR(200)")
	private String researchName;
	@Column(name="exp_equipment_costs",columnDefinition="VARCHAR(200)")
	private String expEquipmentCosts;
	@Column(name="exp_consumables_costs",columnDefinition="VARCHAR(200)")
	private String expConsumablesCosts;
	@Column(name="exp_travel_expenses",columnDefinition="VARCHAR(200)")
	private String expTravelExpenses;
	@Column(name="exp_reward_money",columnDefinition="VARCHAR(200)")
	private String expRewardMoney;
	@Column(name="exp_other",columnDefinition="VARCHAR(200)")
	private String expOther;
	@Column(name="exp_total",columnDefinition="VARCHAR(200)")
	private String expTotal;
	@Column(name="sub_equipment_costs",columnDefinition="VARCHAR(200)")
	private String subEquipmentCosts;
	@Column(name="sub_consumables_costs",columnDefinition="VARCHAR(200)")
	private String subConsumablesCosts;
	@Column(name="sub_travel_expenses",columnDefinition="VARCHAR(200)")
	private String subTravelExpenses;
	@Column(name="sub_reward_money",columnDefinition="VARCHAR(200)")
	private String subRewardMoney;
	@Column(name="sub_other",columnDefinition="VARCHAR(200)")
	private String subOther;
	@Column(name="sub_total",columnDefinition="VARCHAR(200)")
	private String subTotal;
	@Column(columnDefinition = "TEXT")
	private String details;
	@Column(columnDefinition = "TEXT")
	private String purpose;
	@Column(columnDefinition = "TEXT")
	private String  target;
	@Column(columnDefinition = "TEXT")
	private String contents;
	@Column(name ="expected_results",columnDefinition = "TEXT")
	private String expectedResults;
	@Column(columnDefinition="VARCHAR(500)")
	private String approval;
	@Column(name="research_subjects",columnDefinition="VARCHAR(500)")
	private String researchSubjects;
	@Column(columnDefinition="VARCHAR(500)")
	private String keywords;
	@Column(columnDefinition = "TEXT")
	private String originality;
	@Column(columnDefinition = "TEXT")
	private String achievements;
	@Column(name="org_principal",columnDefinition="VARCHAR(200)")
	private String orgPrincipal;
	@Column(columnDefinition="VARCHAR(200)")
	private String affiliation;
	@Column(columnDefinition="VARCHAR(200)")
	private String age0;
	@Column(columnDefinition="VARCHAR(200)")
	private String role0;
	@Column(columnDefinition="VARCHAR(200)")
	private String coordinator1;
	@Column(columnDefinition="VARCHAR(200)")
	private String affiliation1;
	@Column(columnDefinition="VARCHAR(200)")
	private String age1;
	@Column(columnDefinition="VARCHAR(200)")
	private String role1;
	@Column(columnDefinition="VARCHAR(200)")
	private String coordinator2;
	@Column(columnDefinition="VARCHAR(200)")
	private String affiliation2;
	@Column(columnDefinition="VARCHAR(200)")
	private String age2;
	@Column(columnDefinition="VARCHAR(200)")
	private String role2;
	@Column(columnDefinition="VARCHAR(200)")
	private String coordinator3;
	@Column(columnDefinition="VARCHAR(200)")
	private String affiliation3;
	@Column(columnDefinition="VARCHAR(200)")
	private String age3;
	@Column(columnDefinition="VARCHAR(200)")
	private String role3;
	@Column(columnDefinition="VARCHAR(200)")
	private String coordinator4;
	@Column(columnDefinition="VARCHAR(200)")
	private String affiliation4;
	@Column(columnDefinition="VARCHAR(200)")
	private String age4;
	@Column(columnDefinition="VARCHAR(200)")
	private String role4;
	@Column(columnDefinition="VARCHAR(200)")
	private String coordinator5;
	@Column(columnDefinition="VARCHAR(200)")
	private String affiliation5;
	@Column(columnDefinition="VARCHAR(200)")
	private String age5;
	@Column(columnDefinition="VARCHAR(200)")
	private String role5;
	@Column(name="research_contents",columnDefinition = "TEXT")
	private String researchContents;
	@Column(columnDefinition = "TEXT")
	private String file2;
	@Column(name="Date_Created")	
	private LocalDate dateCreated;
	
	@Column(columnDefinition="VARCHAR(250)")
	private String country1;
	@Column(columnDefinition="VARCHAR(250)")
	private String country2;
	@Column(columnDefinition="VARCHAR(250)")
	private String country3;
	@Column(columnDefinition="VARCHAR(250)")
	private String country4;
	@Column(columnDefinition="VARCHAR(200)")
	private String remark;	
	
	
	public String getLocation1() {
		return location1;
	}
	public void setLocation1(String location1) {
		this.location1 = location1;
	}
	public String getPrefectures1() {
		return prefectures1;
	}
	public void setPrefectures1(String prefectures1) {
		this.prefectures1 = prefectures1;
	}
	public String getMunicipality1() {
		return municipality1;
	}
	public void setMunicipality1(String municipality1) {
		this.municipality1 = municipality1;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String addresss1) {
		this.address1 = addresss1;
	}
	public String getBuilding1() {
		return building1;
	}
	public void setBuilding1(String building1) {
		this.building1 = building1;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getFurigana() {
		return furigana;
	}
	public void setFurigana(String furigana) {
		this.furigana = furigana;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getPrefectures2() {
		return prefectures2;
	}
	public void setPrefectures2(String prefectures2) {
		this.prefectures2 = prefectures2;
	}
	public String getMunicipality2() {
		return municipality2;
	}
	public void setMunicipality2(String municipality2) {
		this.municipality2 = municipality2;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getBuilding2() {
		return building2;
	}
	public void setBuilding2(String building2) {
		this.building2 = building2;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getRepresentative() {
		return representative;
	}
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	public String getRepLocation() {
		return repLocation;
	}
	public void setRepLocation(String repLocation) {
		this.repLocation = repLocation;
	}
	public String getRepPrefectures() {
		return repPrefectures;
	}
	public void setRepPrefectures(String repPrefectures) {
		this.repPrefectures = repPrefectures;
	}
	public String getRepMunicipality() {
		return repMunicipality;
	}
	public void setRepMunicipality(String repMunicipality) {
		this.repMunicipality = repMunicipality;
	}
	public String getRepAddress() {
		return repAddress;
	}
	public void setRepAddress(String repAddress) {
		this.repAddress = repAddress;
	}
	public String getRepBuilding() {
		return repBuilding;
	}
	public void setRepBuilding(String repBuilding) {
		this.repBuilding = repBuilding;
	}
	public String getRepPhone() {
		return repPhone;
	}
	public void setRepPhone(String repPhone) {
		this.repPhone = repPhone;
	}
	public String getRepEmail() {
		return repEmail;
	}
	public void setRepEmail(String repEmail) {
		this.repEmail = repEmail;
	}
	public String getInCharge() {
		return inCharge;
	}
	public void setInCharge(String inCharge) {
		this.inCharge = inCharge;
	}
	public String getRecomdPernInst() {
		return recomdPernInst;
	}
	public void setRecomdPernInst(String recomdPernInst) {
		this.recomdPernInst = recomdPernInst;
	}
	public String getRecomdPernJob() {
		return recomdPernJob;
	}
	public void setRecomdPernJob(String recomdPernJob) {
		this.recomdPernJob = recomdPernJob;
	}
	public String getRecomdPernFullname() {
		return recomdPernFullname;
	}
	public void setRecomdPernFullname(String recomdPernFullname) {
		this.recomdPernFullname = recomdPernFullname;
	}
	public String getRecomdPernLocation() {
		return recomdPernLocation;
	}
	public void setRecomdPernLocation(String recomdPernLocation) {
		this.recomdPernLocation = recomdPernLocation;
	}
	public String getRecomdPernPrefectures4() {
		return recomdPernPrefectures4;
	}
	public void setRecomdPernPrefectures4(String recomdPernPrefectures4) {
		this.recomdPernPrefectures4 = recomdPernPrefectures4;
	}
	public String getRecomdPernMunicipality() {
		return recomdPernMunicipality;
	}
	public void setRecomdPernMunicipality(String recomdPernMunicipality) {
		this.recomdPernMunicipality = recomdPernMunicipality;
	}
	public String getRecomdPernAddress() {
		return recomdPernAddress;
	}
	public void setRecomdPernAddress(String recomdPernAddress) {
		this.recomdPernAddress = recomdPernAddress;
	}
	public String getRecomdPernBuilding() {
		return recomdPernBuilding;
	}
	public void setRecomdPernBuilding(String recomdPernBuilding) {
		this.recomdPernBuilding = recomdPernBuilding;
	}
	public String getRecomdPernPhone() {
		return recomdPernPhone;
	}
	public void setRecomdPernPhone(String recomdPernPhone) {
		this.recomdPernPhone = recomdPernPhone;
	}
	public String getFile1() {
		return file1;
	}
	public void setFile1(String file1) {
		this.file1 = file1;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getNewCont() {
		return newCont;
	}
	public void setNewCont(String newCont) {
		this.newCont = newCont;
	}
	public String getResearchName() {
		return researchName;
	}
	public void setResearchName(String researchName) {
		this.researchName = researchName;
	}
	public String getExpEquipmentCosts() {
		return expEquipmentCosts;
	}
	public void setExpEquipmentCosts(String expEquipmentCosts) {
		this.expEquipmentCosts = expEquipmentCosts;
	}
	public String getExpConsumablesCosts() {
		return expConsumablesCosts;
	}
	public void setExpConsumablesCosts(String expConsumablesCosts) {
		this.expConsumablesCosts = expConsumablesCosts;
	}
	public String getExpTravelExpenses() {
		return expTravelExpenses;
	}
	public void setExpTravelExpenses(String expTravelExpenses) {
		this.expTravelExpenses = expTravelExpenses;
	}
	public String getExpRewardMoney() {
		return expRewardMoney;
	}
	public void setExpRewardMoney(String expRewardMoney) {
		this.expRewardMoney = expRewardMoney;
	}
	public String getExpOther() {
		return expOther;
	}
	public void setExpOther(String expOther) {
		this.expOther = expOther;
	}
	public String getExpTotal() {
		return expTotal;
	}
	public void setExpTotal(String expTotal) {
		this.expTotal = expTotal;
	}
	public String getSubEquipmentCosts() {
		return subEquipmentCosts;
	}
	public void setSubEquipmentCosts(String subEquipmentCosts) {
		this.subEquipmentCosts = subEquipmentCosts;
	}
	public String getSubConsumablesCosts() {
		return subConsumablesCosts;
	}
	public void setSubConsumablesCosts(String subConsumablesCosts) {
		this.subConsumablesCosts = subConsumablesCosts;
	}
	public String getSubTravelExpenses() {
		return subTravelExpenses;
	}
	public void setSubTravelExpenses(String subTravelExpenses) {
		this.subTravelExpenses = subTravelExpenses;
	}
	public String getSubRewardMoney() {
		return subRewardMoney;
	}
	public void setSubRewardMoney(String subRewardMoney) {
		this.subRewardMoney = subRewardMoney;
	}
	public String getSubOther() {
		return subOther;
	}
	public void setSubOther(String subOther) {
		this.subOther = subOther;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getExpectedResults() {
		return expectedResults;
	}
	public void setExpectedResults(String expectedResults) {
		this.expectedResults = expectedResults;
	}
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public String getResearchSubjects() {
		return researchSubjects;
	}
	public void setResearchSubjects(String researchSubjects) {
		this.researchSubjects = researchSubjects;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getOriginality() {
		return originality;
	}
	public void setOriginality(String originality) {
		this.originality = originality;
	}
	public String getAchievements() {
		return achievements;
	}
	public void setAchievements(String achievements) {
		this.achievements = achievements;
	}
	public String getOrgPrincipal() {
		return orgPrincipal;
	}
	public void setOrgPrincipal(String orgPrincipal) {
		this.orgPrincipal = orgPrincipal;
	}
	public String getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	public String getAge0() {
		return age0;
	}
	public void setAge0(String age0) {
		this.age0 = age0;
	}
	public String getRole0() {
		return role0;
	}
	public void setRole0(String role0) {
		this.role0 = role0;
	}
	public String getCoordinator1() {
		return coordinator1;
	}
	public void setCoordinator1(String coordinator1) {
		this.coordinator1 = coordinator1;
	}
	public String getAffiliation1() {
		return affiliation1;
	}
	public void setAffiliation1(String affiliation1) {
		this.affiliation1 = affiliation1;
	}
	public String getAge1() {
		return age1;
	}
	public void setAge1(String age1) {
		this.age1 = age1;
	}
	public String getRole1() {
		return role1;
	}
	public void setRole1(String role1) {
		this.role1 = role1;
	}
	public String getCoordinator2() {
		return coordinator2;
	}
	public void setCoordinator2(String coordinator2) {
		this.coordinator2 = coordinator2;
	}
	public String getAffiliation2() {
		return affiliation2;
	}
	public void setAffiliation2(String affiliation2) {
		this.affiliation2 = affiliation2;
	}
	public String getAge2() {
		return age2;
	}
	public void setAge2(String age2) {
		this.age2 = age2;
	}
	public String getRole2() {
		return role2;
	}
	public void setRole2(String role2) {
		this.role2 = role2;
	}
	public String getCoordinator3() {
		return coordinator3;
	}
	public void setCoordinator3(String coordinator3) {
		this.coordinator3 = coordinator3;
	}
	public String getAffiliation3() {
		return affiliation3;
	}
	public void setAffiliation3(String affiliation3) {
		this.affiliation3 = affiliation3;
	}
	public String getAge3() {
		return age3;
	}
	public void setAge3(String age3) {
		this.age3 = age3;
	}
	public String getRole3() {
		return role3;
	}
	public void setRole3(String role3) {
		this.role3 = role3;
	}
	public String getCoordinator4() {
		return coordinator4;
	}
	public void setCoordinator4(String coordinator4) {
		this.coordinator4 = coordinator4;
	}
	public String getAffiliation4() {
		return affiliation4;
	}
	public void setAffiliation4(String affiliation4) {
		this.affiliation4 = affiliation4;
	}
	public String getAge4() {
		return age4;
	}
	public void setAge4(String age4) {
		this.age4 = age4;
	}
	public String getRole4() {
		return role4;
	}
	public void setRole4(String role4) {
		this.role4 = role4;
	}
	public String getCoordinator5() {
		return coordinator5;
	}
	public void setCoordinator5(String coordinator5) {
		this.coordinator5 = coordinator5;
	}
	public String getAffiliation5() {
		return affiliation5;
	}
	public void setAffiliation5(String affiliation5) {
		this.affiliation5 = affiliation5;
	}
	public String getAge5() {
		return age5;
	}
	public void setAge5(String age5) {
		this.age5 = age5;
	}
	public String getRole5() {
		return role5;
	}
	public void setRole5(String role5) {
		this.role5 = role5;
	}
	public String getResearchContents() {
		return researchContents;
	}
	public void setResearchContents(String researchContents) {
		this.researchContents = researchContents;
	}
	public String getFile2() {
		return file2;
	}
	public void setFile2(String file2) {
		this.file2 = file2;
	}
	
	public String getCountry1() {
		return country1;
	}
	public void setCountry1(String country1) {
		this.country1 = country1;
	}
	public String getCountry2() {
		return country2;
	}
	public void setCountry2(String country2) {
		this.country2 = country2;
	}
	public String getCountry3() {
		return country3;
	}
	public void setCountry3(String country3) {
		this.country3 = country3;
	}
	public String getCountry4() {
		return country4;
	}
	public LocalDate getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setCountry4(String country4) {
		this.country4 = country4;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
