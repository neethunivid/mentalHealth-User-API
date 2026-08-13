package com.mentalhealthapi.dto;

import java.util.List;

public class SeminarRequestDTO {
    private String seminarName;
    private String name1;
    private String name2;
    private String namek1;
    private String namek2;
    private String sex;
    private String job;
    private String jobTitle;
    private String school;
    private int age;
    private String email;
    private String reason;
    private String topic;
    private List<String> participants;

    // Default constructor
    public SeminarRequestDTO() {
        // Default constructor
    }

    // Getters and Setters
    public String getSeminarName() {
        return seminarName;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getNamek1() {
        return namek1;
    }

    public void setNamek1(String namek1) {
        this.namek1 = namek1;
    }

    public String getNamek2() {
        return namek2;
    }

    public void setNamek2(String namek2) {
        this.namek2 = namek2;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
