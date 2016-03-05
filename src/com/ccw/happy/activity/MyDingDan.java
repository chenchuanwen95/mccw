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
 * @����: �´���
 * @ʱ��: 2015-11-20����10:17:43
 * @auther:   �����б��������еĶ�������Ϣ
 */
public class MyDingDan extends BaseActivity implements BaseInterface {
	private ImageView fragment_me_dingdan_gobye ;
	private ListView fragment_me_dingdan_listview ;
	private TextView fragment_me_dingdan_tv ;
	//���������еĶ�����Ϣ
	private List<DingDan> lists ;
	private DingDanAdapter adapter ;
	//ά��һ���û�����
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
		
		//�õ���ǰ��¼���û�
		user = HappyApplication.getmHappyApplication().getUb() ;
		//����һ��LiST����  �򼯺����������
		BmobQuery<DingDan> query = new BmobQuery<DingDan>() ;
		query.addWhereContainsAll("userId", Arrays.asList(user.getObjectId())) ;
		query.findObjects(getAct(), new FindListener<DingDan>() {
			
			@Override
			public void onSuccess(List<DingDan> arg0) {
				lists = arg0 ;
				if(lists == null||lists.size()<1){
					fragment_me_dingdan_tv.setText("���޶�����Ϣ.") ;
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
		//���lists�����е�����С��1��Ҳ����˵û������ʱ������ʾ���޶�����Ϣ
		//  ��֮����ʾ������Ϣ
		
		

	}
	/**
	 * ���������ļ����¼�
	 */
	@Override
	public void initViewOper() {
		//���˼�
		fragment_me_dingdan_gobye.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish() ;
			}
		}) ;
	}
	/**
	 * ������
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null) ;
	}

}
