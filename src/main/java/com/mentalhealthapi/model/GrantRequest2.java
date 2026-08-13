package com.mentalhealthapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="grant_request2")
public class GrantRequest2 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="grant_id")
	private Long grantId;
	@Lob
	private String details;
	@Lob
	private String purpose;
	@Lob
	private String target;
	@Lob
	private String contents;
	@Lob
	@Column(name="expectedResults")
	private String expectedResults;
	@Lob
	private String approval;
	@Lob
	@Column(name="research_subjects")
	private String researchSubjects;
	@Lob
	private String keywords;
	@Lob
	private String originality;
	@Lob
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
	@Column(columnDefinition="VARCHAR(200)",nullable = true)
	private String role2;
	@Column(columnDefinition="VARCHAR(200)")
	private String coordinator3;
	@Column(columnDefinition="VARCHAR(200)")
	private String affiliation3;
	@Column(columnDefinition="VARCHAR(200)")
	private String age3;
	@Column(columnDefinition="VARCHAR(200)",nullable = true)
	private String role3;
	@Column(columnDefinition="VARCHAR(200)")
	private String coordinator4;
	@Column(columnDefinition="VARCHAR(200)")
	private String affiliation4;
	@Column(columnDefinition="VARCHAR(200)")
	private String age4;
	@Column(columnDefinition="VARCHAR(200)",nullable = true)
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getGrantId() {
		return grantId;
	}
	public void setGrantId(Long grantId) {
		this.grantId = grantId;
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
	
}
