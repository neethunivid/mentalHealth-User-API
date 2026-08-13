package com.mentalhealthapi.dto;

	public class CounsellingReportResponse {
	    private String dt; // Date
	    private int moritaTherapy;
	    private int medicalInstitutions;
	    private int discoveryParty;
	    private int experienceForum;
	    private int booksVideos;
	    private int otherTreatments;
	    private int others;
	    private int subtotal;

	    public CounsellingReportResponse(String dt, int moritaTherapy, int medicalInstitutions, int discoveryParty, 
	                                     int experienceForum, int booksVideos, int otherTreatments, int others, int subtotal) {
	        this.dt = dt;
	        this.moritaTherapy = moritaTherapy;
	        this.medicalInstitutions = medicalInstitutions;
	        this.discoveryParty = discoveryParty;
	        this.experienceForum = experienceForum;
	        this.booksVideos = booksVideos;
	        this.otherTreatments = otherTreatments;
	        this.others = others;
	        this.subtotal = subtotal;
	    }

	    // Getters and setters
	    public String getDt() {
	        return dt;
	    }

	    public void setDt(String dt) {
	        this.dt = dt;
	    }

	    public int getMoritaTherapy() {
	        return moritaTherapy;
	    }

	    public void setMoritaTherapy(int moritaTherapy) {
	        this.moritaTherapy = moritaTherapy;
	    }

	    public int getMedicalInstitutions() {
	        return medicalInstitutions;
	    }

	    public void setMedicalInstitutions(int medicalInstitutions) {
	        this.medicalInstitutions = medicalInstitutions;
	    }

	    public int getDiscoveryParty() {
	        return discoveryParty;
	    }

	    public void setDiscoveryParty(int discoveryParty) {
	        this.discoveryParty = discoveryParty;
	    }

	    public int getExperienceForum() {
	        return experienceForum;
	    }

	    public void setExperienceForum(int experienceForum) {
	        this.experienceForum = experienceForum;
	    }

	    public int getBooksVideos() {
	        return booksVideos;
	    }

	    public void setBooksVideos(int booksVideos) {
	        this.booksVideos = booksVideos;
	    }

	    public int getOtherTreatments() {
	        return otherTreatments;
	    }

	    public void setOtherTreatments(int otherTreatments) {
	        this.otherTreatments = otherTreatments;
	    }

	    public int getOthers() {
	        return others;
	    }

	    public void setOthers(int others) {
	        this.others = others;
	    }

	    public int getSubtotal() {
	        return subtotal;
	    }

	    public void setSubtotal(int subtotal) {
	        this.subtotal = subtotal;
	    }
	}
