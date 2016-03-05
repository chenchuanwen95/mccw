package com.ccw.happy.activity;


import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.ccw.happy.R;
import com.ccw.happy.adapter.GatherCommentAdapter;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.utils.RandomYouHuiUtils;
import com.ccw.happy.vo.GatherBean;
import com.ccw.happy.vo.MsgBean;
import com.ccw.happy.vo.UserBean;
/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-19����9:06:22
 * @auther:  ��������Ҫ���������û������۽��д������
 */
public class GatherComment extends BaseActivity implements BaseInterface {
	private ImageView act_gather_admin_taolun_gobye ;
	private ListView act_gather_admin_taolun_listview ;
	private EditText act_gather_admin_taolun_sms ;
	private Button act_gather_admin_taolun_send ;
	private UserBean user ;
	private List<MsgBean> msgBeans ;
	private MsgBean msg ;
	private GatherBean mGatherBean ;
	private GatherCommentAdapter adapter ;
	private boolean flag = true; 
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setAct(this) ;
		setContentView(R.layout.act_gather_admin_taolun) ;
		initViews() ;
		initDatas(); 
		initViewOper() ;
	}
	@Override
	public void initViews() {
		act_gather_admin_taolun_gobye = imgById(R.id.act_gather_admin_taolun_gobye) ;
		act_gather_admin_taolun_listview = (ListView) findViewById(R.id.act_gather_admin_taolun_listview) ;
		act_gather_admin_taolun_sms = etById(R.id.act_gather_admin_taolun_sms) ;
		act_gather_admin_taolun_send = btnById(R.id.act_gather_admin_taolun_send) ;
	
	}
	/**
	 * ��������
	 */
	@Override
	public void initDatas() {
		msgBeans = new ArrayList<MsgBean>() ;
		user = HappyApplication.getmHappyApplication().getUb() ;
		mGatherBean = (GatherBean) HappyApplication.getmHappyApplication().getHash("gatherComment") ;
		adapter = new GatherCommentAdapter(msgBeans,this) ;
		
		act_gather_admin_taolun_listview.setAdapter(adapter) ;
		getNewMsgs() ;
	}
	/**
	 * ��ѯ������   2���ӻ�ȡһ���µ�����
	 */
	private void getNewMsgs() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				while(flag){
					publishProgress() ;
					try {
						Thread.sleep(2000) ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				return null;
			}

			@Override
			protected void onProgressUpdate(Void... values) {
				getMsgs() ;
			}

		}.execute();
	}
	/**
	 * ���ݻ��Id�����۵ı�����ȥ��ȡ����
	 * ÿ�ζ�����ԭ��������
	 */
	private void getMsgs() {
		BmobQuery<MsgBean> query = new BmobQuery<MsgBean>() ;
		query.addWhereEqualTo("gatherId", mGatherBean.getObjectId()) ;
		query.setSkip(msgBeans.size()) ;
		query.findObjects(getAct(), new FindListener<MsgBean>() {
			
			@Override
			public void onSuccess(List<MsgBean> arg0) {
				msgBeans.addAll(arg0) ;
				adapter.updateItem(msgBeans) ;
				adapter.notifyDataSetChanged() ;
				if(adapter.getCount()>0){
					act_gather_admin_taolun_listview.setSelection(act_gather_admin_taolun_listview.getAdapter().getCount()-1) ;
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				
			}
		}) ;
	}
	/**
	 * ����ĵ���¼�
	 */
	@Override
	public void initViewOper() {
		//���ص��¼�����
		act_gather_admin_taolun_gobye.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish() ;
			}
		}) ;
		
		//��������
		act_gather_admin_taolun_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String msgStr = getAct().getViewText(act_gather_admin_taolun_sms) ;
				if(msgStr == null||msgStr.length()<1){
					getAct().toastS("���ݲ���Ϊ��.") ;
					return ;
				}
				/**
				 * �����������۵ı�����  �������
				 */
				msg = new MsgBean() ;
				msg.setSmscontent(msgStr) ;
				msg.setUsericon(user.getUserIcon()) ;
				msg.setUsername(user.getUsername()) ;
				msg.setGatherId(mGatherBean.getObjectId()) ;
				msg.save(getAct(), new SaveListener() {
					
					@Override
					public void onSuccess() {
						//��ȡ�Ż�ȯ
						RandomYouHuiUtils.getYouHui(getAct()) ;
						//����������
						act_gather_admin_taolun_sms.setText("") ;
						mGatherBean.setPingluns(msgBeans.size()) ;
						//���۴���
						mGatherBean.increment("pingluns"); 
						mGatherBean.update(getAct(), new UpdateListener() {
							
							@Override
							public void onSuccess() {
								
							}
							
							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub
								
							}
						}) ;
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						getAct().toastS("�����쳣������ʧ��.") ;
					}
				}) ;
				
			}
		}) ;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		flag = false ;
		setAct(null) ;
	}
	@Override
	protected void onStop() {
		super.onStop();
		//�����ǰ���Ż�ȯ�Ľ��滹����ʾ  �������ر�
		RandomYouHuiUtils.isDialogIsShow() ;
	}
}
