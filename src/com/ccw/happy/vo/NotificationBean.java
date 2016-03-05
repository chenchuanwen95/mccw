package com.ccw.happy.vo;

import cn.bmob.v3.BmobObject;

public class NotificationBean extends BmobObject{
	private String sendSms ;
	private String gatherName ;
	private String gatherId ;
	private String userId ;
	public NotificationBean() {
		super();
	}
	public NotificationBean(String sendSms, String gatherName, String gatherId) {
		super();
		this.sendSms = sendSms;
		this.gatherName = gatherName;
		this.gatherId = gatherId;
	}
	public String getSendSms() {
		return sendSms;
	}
	public void setSendSms(String sendSms) {
		this.sendSms = sendSms;
	}
	public String getGatherName() {
		return gatherName;
	}
	public void setGatherName(String gatherName) {
		this.gatherName = gatherName;
	}
	public String getGatherId() {
		return gatherId;
	}
	public void setGatherId(String gatherId) {
		this.gatherId = gatherId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}
