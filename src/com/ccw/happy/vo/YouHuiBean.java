package com.ccw.happy.vo;

import cn.bmob.v3.BmobObject;

public class YouHuiBean extends BmobObject{
	private String userId ;// ���ID
	private String money ; //�Żݽ��
	private String manMoney ; //�����ٿ���
	private String youXiaoQi ;//��Ч��
	public YouHuiBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public YouHuiBean(String gatherId, String money, String manMoney) {
		super();
		this.setUserId(gatherId);
		this.money = money;
		this.manMoney = manMoney;
	}

	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getManMoney() {
		return manMoney;
	}
	public void setManMoney(String manMoney) {
		this.manMoney = manMoney;
	}

	public String getYouXiaoQi() {
		return youXiaoQi;
	}
	public void setYouXiaoQi(String youXiaoQi) {
		this.youXiaoQi = youXiaoQi;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
