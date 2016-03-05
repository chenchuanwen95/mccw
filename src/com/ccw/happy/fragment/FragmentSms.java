package com.ccw.happy.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.ccw.happy.R;
import com.ccw.happy.adapter.NotifitycationAdapter;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseFragment;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.vo.NotificationBean;
import com.ccw.happy.vo.UserBean;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-11上午11:36:25
 * @auther: 这个是信息的界面
 */
public class FragmentSms extends BaseFragment implements BaseInterface {
	private ListView fragment_sms_listview ;
	private TextView fragment_sms_context ;
	private ImageView fragment_sms_push ;
	private NotifitycationAdapter adapter ;
	private List<NotificationBean> lists ;
	private BaseActivity act ;
	private UserBean user ;
	private ProgressDialog pd;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_sms, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		initDatas();
		initViewOper();
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.act = (BaseActivity) activity ;
	}
	@Override
	public void initViews() {
		fragment_sms_listview = (ListView) findViewById(R.id.fragment_sms_listview);
		fragment_sms_context = tvById(R.id.fragment_sms_context) ;
		fragment_sms_push = imgById(R.id.fragment_sms_push) ;
		pd = new ProgressDialog(act) ;
	}	

	@Override
	public void initDatas() {
		user = HappyApplication.getmHappyApplication().getUb() ;
		lists = new ArrayList<NotificationBean>() ;
		adapter = new NotifitycationAdapter(lists, act) ;
		fragment_sms_listview.setVisibility(View.VISIBLE) ;
		fragment_sms_context.setVisibility(View.INVISIBLE) ;
		fragment_sms_listview.setAdapter(adapter) ;
		//获取刷新消息的方法
		getSms() ;
	}

	private void getSms() {
		BmobQuery<NotificationBean> query = new BmobQuery<NotificationBean>() ;
		query.addWhereEqualTo("userId", user.getObjectId()) ; 
		query.setSkip(lists.size()) ;
		query.order("-createdAt") ;
		query.findObjects(act, new FindListener<NotificationBean>() {

			@Override
			public void onSuccess(List<NotificationBean> arg0) {
				lists.addAll(arg0);
				if(lists.size()<1){
					fragment_sms_listview.setVisibility(View.INVISIBLE) ;
					fragment_sms_context.setVisibility(View.VISIBLE) ;
					fragment_sms_context.setText("暂时没有您的消息.");
				}
				adapter.updateItem(lists) ;
				adapter.notifyDataSetChanged() ;
				if(pd!=null&&pd.isShowing()){
					pd.dismiss() ;
					Toast.makeText(act, "刷新成功.", Toast.LENGTH_SHORT).show() ;
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				fragment_sms_listview.setVisibility(View.INVISIBLE) ;
				fragment_sms_context.setVisibility(View.VISIBLE) ;
				fragment_sms_context.setText("暂时没有您的消息.");
				if(pd!=null||pd.isShowing()){
					pd.dismiss() ;
				}
				Toast.makeText(act, "网络连接出现错误.", Toast.LENGTH_SHORT).show() ;
			}
		}) ;
	}


	@Override
	public void initViewOper() {
		fragment_sms_push.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pd.show() ;
				getSms(); 
			}
		}) ;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
