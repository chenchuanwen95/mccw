package com.ccw.happy.receiver;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import cn.bmob.push.PushConstants;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.ccw.happy.R;
import com.ccw.happy.activity.GatherDetailted;
import com.ccw.happy.activity.InitMainActivity;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.vo.GatherBean;
import com.ccw.happy.vo.NotificationBean;
import com.google.gson.Gson;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-21下午7:44:14
 * @auther:   推送消息的广播
 */

public class MyPushMessageReceiver extends BroadcastReceiver {
	private NotificationBean nb ;
	@Override
	public void onReceive(final Context context, Intent intent) {
		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
			Log.i("bmob",
					"客户端收到推送内容："
							+ intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
			String str = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING) ;
			Gson g = new Gson() ;
			nb = g.fromJson(str, NotificationBean.class) ;
			if(nb!=null){
				nb.setUserId(BmobUser.getCurrentUser(context).getObjectId()) ;
				nb.save(context) ;
				BmobQuery<GatherBean> query = new BmobQuery<GatherBean>() ;
				query.addWhereEqualTo("objectId", nb.getGatherId()) ;
				query.findObjects(context, new FindListener<GatherBean>() {
					
					@Override
					public void onSuccess(List<GatherBean> arg0) {
						if(arg0==null||arg0.size()<1){
							Toast.makeText(context, "网络连接失败.", 0).show() ;
							return ;
						}
						HappyApplication.getmHappyApplication().setHash("gatherpositiondelete",arg0.get(0)) ;
 						showNotification(context);						
					}
					
					@Override
					public void onError(int arg0, String arg1) {
						
					}
				}) ;
				
			}
		}
	}

	/**
	 * 
	 * @param Notification  通知
	 *            使用Notification类来发起推送消息 nf.defaults 是发起推送时手机的通知
	 *            是震动还是铃声还是闪光灯等等 nf.flags 是当有消息进来时 用户可不可以销毁消息 在这里使用了未来意图
	 *            PendingIntent 可以用来指定时间来推送消息 最后使用通知的管理者 来推送消息 nm.notify 第一个参数为0
	 *            原因 如果不设置为0 会有很多推送消息发送过来 如果为0如果当前的消息没有查看，那么就不会有新的消息推送进来
	 */
	@SuppressLint("ServiceCast")
	private void showNotification(Context context) {

		Notification nf = new Notification(R.drawable.a1, "穷途陌路:您有新活动通知.",
				System.currentTimeMillis());
		nf.defaults = Notification.DEFAULT_ALL;
		nf.flags = Notification.FLAG_AUTO_CANCEL;
		Intent intent = new Intent(context, GatherDetailted.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
		nf.setLatestEventInfo(context, nb.getGatherName(), nb.getSendSms(), pi);
		NotificationManager nm = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		// 第一个参数设置为0 原因 如果不设置为0 会有很多推送消息发送过来 如果为0如果当前的消息没有查看，那么就不会有新的消息推送进来
		nm.notify(0, nf);

	}

}