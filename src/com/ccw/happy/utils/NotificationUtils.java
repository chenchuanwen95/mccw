package com.ccw.happy.utils;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;

import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.vo.NotificationBean;
import com.google.gson.Gson;

public class NotificationUtils {

	public static void push(NotificationBean bean, BaseActivity act,
			List<String> paymentUserName) {
		BmobPushManager bmobPush = new BmobPushManager(act);
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		query.addWhereContainedIn("userName", paymentUserName);
		bmobPush.setQuery(query);
		Gson g = new Gson() ;
		String jsonstr = g.toJson(bean) ;
		try {
			bmobPush.pushMessage(new JSONObject(jsonstr));
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}

}
