package com.ccw.happy.vo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-19下午9:53:42
 * @auther:  这个是讨论的bean对象
 */
@SuppressWarnings("serial")
public class MsgBean extends BmobObject{
	private BmobFile usericon ; //用户头像
	private String username ;  //用户名
	private String senddate ;	//发送时间
	private String smscontent ;	//发送的内容
	private String gatherId ; // 活动的id
	public MsgBean() {
		super();
	}
	public MsgBean(String tableName) {
		super(tableName);
	}
	public MsgBean(BmobFile usericon, String username, String senddate,
			String smscontent, String gatherId) {
		super();
		this.usericon = usericon;
		this.username = username;
		this.senddate = senddate;
		this.smscontent = smscontent;
		this.gatherId = gatherId;
	}
	public BmobFile getUsericon() {
		return usericon;
	}
	public void setUsericon(BmobFile usericon) {
		this.usericon = usericon;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSenddate() {
		return senddate;
	}
	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}
	public String getSmscontent() {
		return smscontent;
	}
	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}
	public String getGatherId() {
		return gatherId;
	}
	public void setGatherId(String gatherId) {
		this.gatherId = gatherId;
	}
	
	
	
}
