package com.ccw.happy.vo;

import android.content.Context;
import cn.bmob.v3.BmobInstallation;
/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-21����3:30:50
 * @auther:�������������û�����Ϣ���ֻ��豸����Ϣ�󶨵�һ���
 */
public class MyBmobInstallation extends BmobInstallation {

    /**  
     * �û�id-�������Խ��豸���û�֮����а�
     */  
    private String uid;
    private String userId ;
    private String userName ;

    public MyBmobInstallation(Context context) {
        super(context);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}