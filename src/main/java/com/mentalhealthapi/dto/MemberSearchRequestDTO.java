package com.mentalhealthapi.dto;

public class MemberSearchRequestDTO {
	 private String name;
	    private String job;
	    private String sex;
	    private String status;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getJob() {
			return job;
		}
		public void setJob(String job) {
			this.job = job;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}

}
