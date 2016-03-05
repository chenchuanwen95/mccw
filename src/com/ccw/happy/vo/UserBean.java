package com.ccw.happy.vo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

@SuppressWarnings("serial")
/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-10����9:12:14
 * @auther: ����ƴ��UserBean ����
 */
public class UserBean extends BmobUser {
	// ͼƬ����Դ
	private BmobFile userIcon;
	private List<String> mGatherId;// ��ǰ�û��������ID
	private List<String> loveGatherId;// ��ǰ�û��ղص�ID
	private List<String> canjiaGatherName ; //������Ļ����
	private List<String> canjiaGatherId;// ��ǰ�û�������ĻID
	private List<String> orderIds;// ����ɹ���
	private Integer youhui;// �Ż�ȯ
	
	
	public List<String> getmGatherId() {
		if (mGatherId == null) {
			mGatherId = new ArrayList<String>();
		}
		return mGatherId;
	}

	public void setmGatherId(List<String> mGatherId) {
		this.mGatherId = mGatherId;
	}

	public List<String> getLoveGatherId() {
		if(loveGatherId == null){
			loveGatherId = new ArrayList<String>();
		}
		return loveGatherId;
	}

	public void setLoveGatherId(List<String> loveGatherId) {
		this.loveGatherId = loveGatherId;
	}

	public List<String> getCanjiaGatherId() {
		if(canjiaGatherId == null){
			canjiaGatherId = new ArrayList<String>() ;
		}
		return canjiaGatherId;
	}

	public void setCanjiaGatherId(List<String> canjiaGatherId) {
		this.canjiaGatherId = canjiaGatherId;
	}

	public List<String> getOrderIds() {
		if(orderIds == null){
			orderIds = new ArrayList<String>() ;
		}
		return orderIds;
	}

	public void setOrderIds(List<String> orderIds) {
		this.orderIds = orderIds;
	}

	public Integer getYouhui() {
		if(youhui == null){
			youhui = 0 ;
		}
		return youhui;
	}

	public void setYouhui(Integer youhui) {
		this.youhui = youhui;
	}

	public BmobFile getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(BmobFile userIcon) {
		this.userIcon = userIcon;
	}

	public List<String> getCanjiaGatherName() {
		if(canjiaGatherName == null){
			canjiaGatherName = new ArrayList<String>() ;
		}
		return canjiaGatherName;
	}

	public void setCanjiaGatherName(List<String> canjiaGatherName) {
		this.canjiaGatherName = canjiaGatherName;
	}


}
