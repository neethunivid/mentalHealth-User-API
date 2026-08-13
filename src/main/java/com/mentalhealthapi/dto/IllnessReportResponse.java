package com.mentalhealthapi.dto;

public class IllnessReportResponse {
    private String dt; // Date
    private int normal;
    private int anxiety;
    private int panic;
    private int obsessiveCompulsive;
    private int socialInterpersonal;
    private int moodDisorder;
    private int otherNeuroses;
    private int others;
    private int subtotal;

    public IllnessReportResponse(String dt, int normal, int anxiety, int panic, int obsessiveCompulsive,
                                  int socialInterpersonal, int moodDisorder, int otherNeuroses, int others, int subtotal) {
        this.dt = dt;
        this.normal = normal;
        this.anxiety = anxiety;
        this.panic = panic;
        this.obsessiveCompulsive = obsessiveCompulsive;
        this.socialInterpersonal = socialInterpersonal;
        this.moodDisorder = moodDisorder;
        this.otherNeuroses = otherNeuroses;
        this.others = others;
        this.subtotal = subtotal;
    }

	public int getNormal() {
		return normal;
	}

	public void setNormal(int normal) {
		this.normal = normal;
	}

	public int getAnxiety() {
		return anxiety;
	}

	public void setAnxiety(int anxiety) {
		this.anxiety = anxiety;
	}

	public int getPanic() {
		return panic;
	}

	public void setPanic(int panic) {
		this.panic = panic;
	}

	public int getObsessiveCompulsive() {
		return obsessiveCompulsive;
	}

	public void setObsessiveCompulsive(int obsessiveCompulsive) {
		this.obsessiveCompulsive = obsessiveCompulsive;
	}

	public int getSocialInterpersonal() {
		return socialInterpersonal;
	}

	public void setSocialInterpersonal(int socialInterpersonal) {
		this.socialInterpersonal = socialInterpersonal;
	}

	public int getMoodDisorder() {
		return moodDisorder;
	}

	public void setMoodDisorder(int moodDisorder) {
		this.moodDisorder = moodDisorder;
	}

	public int getOtherNeuroses() {
		return otherNeuroses;
	}

	public void setOtherNeuroses(int otherNeuroses) {
		this.otherNeuroses = otherNeuroses;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
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
