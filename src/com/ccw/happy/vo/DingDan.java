package com.ccw.happy.vo;

import java.util.List;

import cn.bmob.v3.BmobObject;
/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-22����9:49:17
 * @auther:����Ƕ�����bean����
 */
public class DingDan extends BmobObject{

	private String gatherName ;
	private String dingdanNum ;
	private String gatherId ;
	private String userId ;
	public String getGatherName() {
		return gatherName;
	}
	public void setGatherName(String gatherName) {
		this.gatherName = gatherName;
	}
	public String getDingdanNum() {
		return dingdanNum;
	}
	public void setDingdanNum(String dingdanNum) {
		this.dingdanNum = dingdanNum;
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
