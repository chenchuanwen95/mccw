package com.ccw.happy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-9下午7:37:35
 * @auther: 本类是用来判断用户是否有联网
 */
public class IntenetUtils {
	public static boolean isNet(Context context) {
		// 获取网络管理器
		if(context == null){
			return false;
		}
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取网络状态对象
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		// 判断是否联网
		if (mNetworkInfo == null) {
			return false;
		}
		return mNetworkInfo.isConnected();
	}
}
