package com.mentalhealthapi.dto;

public class AgeReportDTO {
    private String dt; 
    private int group10;
    private int group20; 
    private int group30; 
    private int group40; 
    private int group50; 
    private int group60; 
    private int group70; 
    private int unknown; 
    private int subtotal; // Total case

    public AgeReportDTO(String dt, int group1, int group2, int group3, int group4, int group5, int group6, int group7, int group8, int subtotal) {
        this.dt = dt;
        this.group10 = group1;
        this.group20 = group2;
        this.group30 = group3;
        this.group40 = group4;
        this.group50 = group5;
        this.group60 = group6;
        this.group70 = group7;
        this.unknown = group8;
        this.subtotal = subtotal;
    }

    public String getDt() { // Changed to getDt()
        return dt;
    }

    public void setDt(String dt) { // Changed to setDt()
        this.dt = dt;
    }

    public int getGroup10() {
        return group10;
    }

    public void setGroup10(int group10) {
        this.group10 = group10;
    }

    public int getGroup20() {
        return group20;
    }

    public void setGroup20(int group20) {
        this.group20 = group20;
    }

    public int getGroup30() {
        return group30;
    }

    public void setGroup30(int group30) {
        this.group30 = group30;
    }

    public int getGroup40() {
        return group40;
    }

    public void setGroup40(int group40) {
        this.group40 = group40;
    }

    public int getGroup50() {
        return group50;
    }

    public void setGroup50(int group50) {
        this.group50 = group50;
    }

    public int getGroup60() {
        return group60;
    }

    public void setGroup60(int group60) {
        this.group60 = group60;
    }

    public int getGroup70() {
        return group70;
    }

    public void setGroup70(int group70) {
        this.group70 = group70;
    }


    public int getUnknown() {
		return unknown;
	}

	public void setUnknown(int unknown) {
		this.unknown = unknown;
	}

	public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}
