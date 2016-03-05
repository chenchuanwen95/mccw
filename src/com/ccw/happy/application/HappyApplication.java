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
 * @作者: 陈传稳
 * @时间: 2015-11-9下午6:55:23
 * @auther: 本类是程序中生命周期最长的一个类，只有四大组件全部销毁，application才会死亡。 在本类中进行数据交互，缓存数据
 *          维护UserBean对象，登录时判断该对象是否存在...
 */
public class HappyApplication extends Application {
	private static HappyApplication mHappyApplication;
	private UserBean ub;
	private HashMap<String, Object> hash;
	// 定位功能
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
		// 使用推送服务时的初始化操作
	    BmobInstallation.getCurrentInstallation(this).save();
	    // 启动推送服务
	    BmobPush.startWork(this, "37b78134819b1723cd5348b57469783e");
	}

	private void initdate() {
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener);
		initLocation();
		mLocationClient.start();
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
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
			// 使用Bmob缓存给UserBean 对象赋值
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
