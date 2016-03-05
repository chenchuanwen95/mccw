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
 * @����: �´���
 * @ʱ��: 2015-11-21����7:44:14
 * @auther:   ������Ϣ�Ĺ㲥
 */

public class MyPushMessageReceiver extends BroadcastReceiver {
	private NotificationBean nb ;
	@Override
	public void onReceive(final Context context, Intent intent) {
		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
			Log.i("bmob",
					"�ͻ����յ��������ݣ�"
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
							Toast.makeText(context, "��������ʧ��.", 0).show() ;
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
	 * @param Notification  ֪ͨ
	 *            ʹ��Notification��������������Ϣ nf.defaults �Ƿ�������ʱ�ֻ���֪ͨ
	 *            ���𶯻���������������Ƶȵ� nf.flags �ǵ�����Ϣ����ʱ �û��ɲ�����������Ϣ ������ʹ����δ����ͼ
	 *            PendingIntent ��������ָ��ʱ����������Ϣ ���ʹ��֪ͨ�Ĺ����� ��������Ϣ nm.notify ��һ������Ϊ0
	 *            ԭ�� ���������Ϊ0 ���кܶ�������Ϣ���͹��� ���Ϊ0�����ǰ����Ϣû�в鿴����ô�Ͳ������µ���Ϣ���ͽ���
	 */
	@SuppressLint("ServiceCast")
	private void showNotification(Context context) {

		Notification nf = new Notification(R.drawable.a1, "��;İ·:�����»֪ͨ.",
				System.currentTimeMillis());
		nf.defaults = Notification.DEFAULT_ALL;
		nf.flags = Notification.FLAG_AUTO_CANCEL;
		Intent intent = new Intent(context, GatherDetailted.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
		nf.setLatestEventInfo(context, nb.getGatherName(), nb.getSendSms(), pi);
		NotificationManager nm = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		// ��һ����������Ϊ0 ԭ�� ���������Ϊ0 ���кܶ�������Ϣ���͹��� ���Ϊ0�����ǰ����Ϣû�в鿴����ô�Ͳ������µ���Ϣ���ͽ���
		nm.notify(0, nf);

	}

}