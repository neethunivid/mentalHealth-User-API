package com.mentalhealthapi.dto;

public class GenderReportDTO {
    private String dt; 
    private int himself; 
    private int others; 
    private int male;       
    private int female;    
    private int unknown;   
    private int subtotal;    

    public GenderReportDTO(String dt, int himself, int others, int male, int female, int unknown, int subtotal) {
        this.dt = dt; 
        this.himself = himself;
        this.others = others;
        this.male = male;
        this.female = female;
        this.unknown = unknown;
        this.subtotal = subtotal;
    }

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public int getHimself() {
		return himself;
	}

	public void setHimself(int himself) {
		this.himself = himself;
	}

	public int getOthers() {
		return others;
	}

	public void setOthers(int others) {
		this.others = others;
	}

	public int getMale() {
		return male;
	}

	public void setMale(int male) {
		this.male = male;
	}

	public int getFemale() {
		return female;
	}

	public void setFemale(int female) {
		this.female = female;
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
