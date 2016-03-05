package com.ccw.happy.vo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

import com.baidu.mapapi.search.core.PoiInfo;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-13上午11:04:25
 * @auther: 用户发起活动时的封装类
 */
@SuppressWarnings("serial")
public class GatherBean extends BmobObject {
	private String _id;// id 不需要用户手动输入
	private String gatherClass;// 活动类型 用户选择 ````
	private String gatherTitle;// 活动简介 用户输入 ````
	private String gatherTime;// 活动开始时间 用户选择 ````
	private String gatherName;// 活动名称 用户输入 ````
	private String gatherCity;// 活动所在城市 用户选择/输入
	private String gatherSite;// 活动地点 用户选择/输入
	private String gatherRMB; // 活动价格 用户输入 ````
	private Integer pingluns;// 评论次数

	private String gatherIntro;// 活动详细介绍 用户输入 ````
	private PoiInfo location;// 活动经纬度 用户选择/输入
	private BmobFile gatherJPG;// 活动图片 //用户上传````
	private String gatherUserId;// 活动发起用户Id 用来查询用户表 系统自动获取填入
	private BmobFile gatherIcon;// 发起活动者头像 系统自动获取填入

	private List<String> praiseUsers;// 记录点赞的用户名 //系统录入
	private Integer praiseUserscount;// 记录点赞的次数 //系统录入

	private List<String> paymentUserId;// 参与付款的用户Id 系统录入
	private List<String> paymentUserName;// 参与付款的用户Name 系统录入
	private String paymentTime ;//用户参与活动的时间

	private List<String> startUserId;// 活动开始 ，到活动现场的用户Id 系统录入
	private List<String> startUserName;// 活动开始 ，到活动现场的用户Name 系统录入

	private BmobGeoPoint gpsAdd;// Bmob服务器需要通过 这个位置对象, 进行距离的判断

//	private Boolean flag;// 当前活动状态(是否开始) 系统录入
	private Integer gatherFlag ;  //当前的活动三种状态（未进行  进行中  已结束）  系统录入

	public GatherBean() {
		super();
	}

	public GatherBean(String tableName) {
		super(tableName);
	}

	public GatherBean(String gatherClass, String gatherTitle,
			String gatherTime, String gatherName, String gatherCity,
			String gatherSite, String gatherRMB, Integer pingluns,
			String gatherIntro, PoiInfo location, BmobFile gatherJPG,
			String gatherUserId, BmobFile gatherIcon, List<String> praiseUsers,
			Integer praiseUserscount, List<String> paymentUserId,
			List<String> paymentUserName, List<String> startUserId,
			List<String> startUserName, BmobGeoPoint gpsAdd,Integer gatherFlag) {
		super();
		this.gatherClass = gatherClass;
		this.gatherTitle = gatherTitle;
		this.gatherTime = gatherTime;
		this.gatherName = gatherName;
		this.gatherCity = gatherCity;
		this.gatherSite = gatherSite;
		this.gatherRMB = gatherRMB;
		this.pingluns = pingluns;
		this.gatherIntro = gatherIntro;
		this.location = location;
		this.gatherJPG = gatherJPG;
		this.gatherUserId = gatherUserId;
		this.gatherIcon = gatherIcon;
		this.praiseUsers = praiseUsers;
		this.praiseUserscount = praiseUserscount;
		this.paymentUserId = paymentUserId;
		this.paymentUserName = paymentUserName;
		this.startUserId = startUserId;
		this.startUserName = startUserName;
		this.gpsAdd = gpsAdd;
		this.setGatherFlag(gatherFlag) ;
	}

	public GatherBean(String _id, String gatherClass, String gatherTitle,
			String gatherTime, String gatherName, String gatherCity,
			String gatherSite, String gatherRMB, Integer pingluns,
			String gatherIntro, PoiInfo location, BmobFile gatherJPG,
			String gatherUserId, BmobFile gatherIcon, List<String> praiseUsers,
			Integer praiseUserscount, List<String> paymentUserId,
			List<String> paymentUserName, List<String> startUserId,
			List<String> startUserName, BmobGeoPoint gpsAdd , Integer gatherFlag) {
		super();
		this._id = _id;
		this.gatherClass = gatherClass;
		this.gatherTitle = gatherTitle;
		this.gatherTime = gatherTime;
		this.gatherName = gatherName;
		this.gatherCity = gatherCity;
		this.gatherSite = gatherSite;
		this.gatherRMB = gatherRMB;
		this.pingluns = pingluns;
		this.gatherIntro = gatherIntro;
		this.location = location;
		this.gatherJPG = gatherJPG;
		this.gatherUserId = gatherUserId;
		this.gatherIcon = gatherIcon;
		this.praiseUsers = praiseUsers;
		this.praiseUserscount = praiseUserscount;
		this.paymentUserId = paymentUserId;
		this.paymentUserName = paymentUserName;
		this.startUserId = startUserId;
		this.startUserName = startUserName;
		this.gpsAdd = gpsAdd;
		this.setGatherFlag(gatherFlag) ;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getGatherClass() {
		return gatherClass;
	}

	public void setGatherClass(String gatherClass) {
		this.gatherClass = gatherClass;
	}

	public String getGatherTitle() {
		return gatherTitle;
	}

	public void setGatherTitle(String gatherTitle) {
		this.gatherTitle = gatherTitle;
	}

	public String getGatherTime() {
		return gatherTime;
	}

	public void setGatherTime(String gatherTime) {
		this.gatherTime = gatherTime;
	}

	public String getGatherName() {
		return gatherName;
	}

	public void setGatherName(String gatherName) {
		this.gatherName = gatherName;
	}

	public String getGatherCity() {
		return gatherCity;
	}

	public void setGatherCity(String gatherCity) {
		this.gatherCity = gatherCity;
	}

	public String getGatherSite() {
		return gatherSite;
	}

	public void setGatherSite(String gatherSite) {
		this.gatherSite = gatherSite;
	}

	public String getGatherRMB() {
		return gatherRMB;
	}

	public void setGatherRMB(String gatherRMB) {
		this.gatherRMB = gatherRMB;
	}

	public Integer getPingluns() {
		if(pingluns ==null){
			pingluns = 0;
		}
		return pingluns;
	}

	public void setPingluns(Integer pingluns) {
		this.pingluns = pingluns;
	}

	public String getGatherIntro() {
		return gatherIntro;
	}

	public void setGatherIntro(String gatherIntro) {
		this.gatherIntro = gatherIntro;
	}

	public PoiInfo getLocation() {
		return location;
	}

	public void setLocation(PoiInfo location) {
		this.location = location;
	}

	public BmobFile getGatherJPG() {
		return gatherJPG;
	}

	public void setGatherJPG(BmobFile gatherJPG) {
		this.gatherJPG = gatherJPG;
	}

	public String getGatherUserId() {
		return gatherUserId;
	}

	public void setGatherUserId(String gatherUserId) {
		this.gatherUserId = gatherUserId;
	}

	public BmobFile getGatherIcon() {
		return gatherIcon;
	}

	public void setGatherIcon(BmobFile gatherIcon) {
		this.gatherIcon = gatherIcon;
	}

	public List<String> getPraiseUsers() {
		if(praiseUsers == null){
			praiseUsers = new ArrayList<String>() ;
		}
		return praiseUsers;
	}

	public void setPraiseUsers(List<String> praiseUsers) {
		this.praiseUsers = praiseUsers;
	}

	public Integer getPraiseUserscount() {
		if(praiseUserscount == null){
			praiseUserscount = 0;
		}
		return praiseUserscount;
	}

	public void setPraiseUserscount(Integer praiseUserscount) {
		this.praiseUserscount = praiseUserscount;
	}

	public List<String> getPaymentUserId() {
		if(paymentUserId == null){
			paymentUserId = new ArrayList<String>() ;
		}
		return paymentUserId;
	}

	public void setPaymentUserId(List<String> paymentUserId) {
		this.paymentUserId = paymentUserId;
	}

	public List<String> getPaymentUserName() {
		if(paymentUserName == null){
			paymentUserName = new ArrayList<String>() ;
		}
		return paymentUserName;
	}

	public void setPaymentUserName(List<String> paymentUserName) {
		this.paymentUserName = paymentUserName;
	}

	public List<String> getStartUserId() {
		if(startUserId == null){
			startUserId = new ArrayList<String>() ;
		}
		return startUserId;
	}

	public void setStartUserId(List<String> startUserId) {
		this.startUserId = startUserId;
	}

	public List<String> getStartUserName() {
		if(startUserName == null){
			startUserName = new ArrayList<String>() ;
		}
		return startUserName;
	}

	public void setStartUserName(List<String> startUserName) {
		this.startUserName = startUserName;
	}

	public BmobGeoPoint getGpsAdd() {
		return gpsAdd;
	}

	public void setGpsAdd(BmobGeoPoint gpsAdd) {
		this.gpsAdd = gpsAdd;
	}

	public Integer getGatherFlag() {
		if(gatherFlag == null){
			gatherFlag = 0 ;
		}
		return gatherFlag;
	}

	public void setGatherFlag(Integer gatherFlag) {
		
		this.gatherFlag = gatherFlag;
	}

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}



}
