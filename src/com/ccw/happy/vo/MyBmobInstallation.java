package com.ccw.happy.vo;

import android.content.Context;
import cn.bmob.v3.BmobInstallation;
/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-21下午3:30:50
 * @auther:本类是用来把用户的信息和手机设备的信息绑定到一起的
 */
public class MyBmobInstallation extends BmobInstallation {

    /**  
     * 用户id-这样可以将设备与用户之间进行绑定
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