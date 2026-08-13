package com.mentalhealthapi.dto;

import com.mentalhealthapi.model.Remarks;

public class RemarkWithMemberDetailsDTO {
    private Remarks remark;
    private String name;
    private String sex;

    public RemarkWithMemberDetailsDTO(Remarks remark, String name, String sex) {
        this.remark = remark;
        this.name = name;
        this.sex = sex;
    }

    // Getters and setters
    public Remarks getRemark() {
        return remark;
    }

    public void setRemark(Remarks remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

