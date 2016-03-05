package com.ccw.happy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-9����7:37:35
 * @auther: �����������ж��û��Ƿ�������
 */
public class IntenetUtils {
	public static boolean isNet(Context context) {
		// ��ȡ���������
		if(context == null){
			return false;
		}
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// ��ȡ����״̬����
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		// �ж��Ƿ�����
		if (mNetworkInfo == null) {
			return false;
		}
		return mNetworkInfo.isConnected();
	}
}
