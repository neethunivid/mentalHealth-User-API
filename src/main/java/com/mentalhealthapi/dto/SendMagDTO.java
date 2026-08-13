package com.mentalhealthapi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SendMagDTO {
	private Integer magId;
    private List<Integer> userIds;
    private boolean sendNow;
    private LocalDateTime sendTime;
	public Integer getMagId() {
		return magId;
	}
	public void setMagId(Integer magId) {
		this.magId = magId;
	}
	public List<Integer> getUserIds() {
		return userIds;
	}
	public LocalDateTime getSendTime() {
		return sendTime;
	}
	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
	}
	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}
	public boolean isSendNow() {
		return sendNow;
	}
	public void setSendNow(boolean sendNow) {
		this.sendNow = sendNow;
	}
	

}
