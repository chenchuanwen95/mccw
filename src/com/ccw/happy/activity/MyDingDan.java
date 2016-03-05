package com.ccw.happy.activity;


import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.ccw.happy.R;
import com.ccw.happy.adapter.DingDanAdapter;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.vo.DingDan;
import com.ccw.happy.vo.UserBean;
/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-20下午10:17:43
 * @auther:   本类中保存了所有的订单号信息
 */
public class MyDingDan extends BaseActivity implements BaseInterface {
	private ImageView fragment_me_dingdan_gobye ;
	private ListView fragment_me_dingdan_listview ;
	private TextView fragment_me_dingdan_tv ;
	//保存了所有的订单信息
	private List<DingDan> lists ;
	private DingDanAdapter adapter ;
	//维护一个用户对象
	private UserBean user ;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setAct(this) ;
		setContentView(R.layout.fragment_me_dingdan) ;
		initViews() ;
		initDatas() ;
		initViewOper() ;
	}
	@Override
	public void initViews() {
		fragment_me_dingdan_tv = tvById(R.id.fragment_me_dingdan_tv) ;
		fragment_me_dingdan_gobye = imgById(R.id.fragment_me_dingdan_gobye) ;
		fragment_me_dingdan_listview = (ListView) findViewById(R.id.fragment_me_dingdan_listview) ;
	}

	@Override
	public void initDatas() {
		
		//得到当前登录的用户
		user = HappyApplication.getmHappyApplication().getUb() ;
		//创建一个LiST集合  向集合中添加数据
		BmobQuery<DingDan> query = new BmobQuery<DingDan>() ;
		query.addWhereContainsAll("userId", Arrays.asList(user.getObjectId())) ;
		query.findObjects(getAct(), new FindListener<DingDan>() {
			
			@Override
			public void onSuccess(List<DingDan> arg0) {
				lists = arg0 ;
				if(lists == null||lists.size()<1){
					fragment_me_dingdan_tv.setText("暂无订单信息.") ;
				}else{
					fragment_me_dingdan_tv.setVisibility(View.INVISIBLE) ;
					fragment_me_dingdan_listview.setVisibility(View.VISIBLE) ;
					adapter = new DingDanAdapter(lists, getAct()) ;
					fragment_me_dingdan_listview.setAdapter(adapter) ;
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				
			}
		}) ;
		//如果lists集合中的数据小于1，也就是说没有数据时，就显示暂无订单信息
		//  反之则显示订单信息
		
		

	}
	/**
	 * 这个是组件的监听事件
	 */
	@Override
	public void initViewOper() {
		//后退键
		fragment_me_dingdan_gobye.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish() ;
			}
		}) ;
	}
	/**
	 * 销毁是
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null) ;
	}

}
