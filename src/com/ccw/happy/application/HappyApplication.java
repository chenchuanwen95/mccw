package com.ccw.happy.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Application;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.ccw.happy.vo.UserBean;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-9����6:55:23
 * @auther: �����ǳ����������������һ���ֻ࣬���Ĵ����ȫ�����٣�application�Ż������� �ڱ����н������ݽ�������������
 *          ά��UserBean���󣬵�¼ʱ�жϸö����Ƿ����...
 */
public class HappyApplication extends Application {
	private static HappyApplication mHappyApplication;
	private UserBean ub;
	private HashMap<String, Object> hash;
	// ��λ����
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	private List<LatLng> lists;

	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		Bmob.initialize(this, "37b78134819b1723cd5348b57469783e");
		hash = new HashMap<String, Object>();
		setmHappyApplication(this);
		initdate();
		// ʹ�����ͷ���ʱ�ĳ�ʼ������
	    BmobInstallation.getCurrentInstallation(this).save();
	    // �������ͷ���
	    BmobPush.startWork(this, "37b78134819b1723cd5348b57469783e");
	}

	private void initdate() {
		mLocationClient = new LocationClient(getApplicationContext()); // ����LocationClient��
		mLocationClient.registerLocationListener(myListener);
		initLocation();
		mLocationClient.start();
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		int span = 1000;
		option.setScanSpan(span);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ��ɱ��
		option.SetIgnoreCacheException(false);// ��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);// ��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
		mLocationClient.setLocOption(option);
	}

	public static HappyApplication getmHappyApplication() {
		return mHappyApplication;
	}

	private static void setmHappyApplication(HappyApplication mHappyApplication) {
		HappyApplication.mHappyApplication = mHappyApplication;
	}

	public UserBean getUb() {

		if (ub == null) {
			// ʹ��Bmob�����UserBean ����ֵ
			ub = BmobUser.getCurrentUser(this, UserBean.class);
		}
		return ub;
	}

	public void setUb(UserBean ub) {
		this.ub = ub;
	}

	public Object getHash(String key) {
		Object o = hash.get(key);
		if (key.indexOf("delete") > 0) {
			hash.remove(key);
		}
		return o;
	}

	public void setHash(String str, Object obj) {
		hash.put(str, obj);
	}

	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (lists == null || lists.size() > 3) {
				lists = new ArrayList<LatLng>();
			}
			lists.add(new LatLng(location.getLatitude(), location
					.getLongitude()));
		}
	}

	public List<LatLng> getLists() {
		return lists;
	}
}
