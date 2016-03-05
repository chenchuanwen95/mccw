package com.ccw.happy.vo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

import com.baidu.mapapi.search.core.PoiInfo;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-13����11:04:25
 * @auther: �û�����ʱ�ķ�װ��
 */
@SuppressWarnings("serial")
public class GatherBean extends BmobObject {
	private String _id;// id ����Ҫ�û��ֶ�����
	private String gatherClass;// ����� �û�ѡ�� ````
	private String gatherTitle;// ���� �û����� ````
	private String gatherTime;// ���ʼʱ�� �û�ѡ�� ````
	private String gatherName;// ����� �û����� ````
	private String gatherCity;// ����ڳ��� �û�ѡ��/����
	private String gatherSite;// ��ص� �û�ѡ��/����
	private String gatherRMB; // ��۸� �û����� ````
	private Integer pingluns;// ���۴���

	private String gatherIntro;// ���ϸ���� �û����� ````
	private PoiInfo location;// ���γ�� �û�ѡ��/����
	private BmobFile gatherJPG;// �ͼƬ //�û��ϴ�````
	private String gatherUserId;// ������û�Id ������ѯ�û��� ϵͳ�Զ���ȡ����
	private BmobFile gatherIcon;// ������ͷ�� ϵͳ�Զ���ȡ����

	private List<String> praiseUsers;// ��¼���޵��û��� //ϵͳ¼��
	private Integer praiseUserscount;// ��¼���޵Ĵ��� //ϵͳ¼��

	private List<String> paymentUserId;// ���븶����û�Id ϵͳ¼��
	private List<String> paymentUserName;// ���븶����û�Name ϵͳ¼��
	private String paymentTime ;//�û�������ʱ��

	private List<String> startUserId;// ���ʼ ������ֳ����û�Id ϵͳ¼��
	private List<String> startUserName;// ���ʼ ������ֳ����û�Name ϵͳ¼��

	private BmobGeoPoint gpsAdd;// Bmob��������Ҫͨ�� ���λ�ö���, ���о�����ж�

//	private Boolean flag;// ��ǰ�״̬(�Ƿ�ʼ) ϵͳ¼��
	private Integer gatherFlag ;  //��ǰ�Ļ����״̬��δ����  ������  �ѽ�����  ϵͳ¼��

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
