package com.ccw.happy.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.ccw.happy.R;
import com.ccw.happy.adapter.YouHuiAdapter;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.vo.UserBean;
import com.ccw.happy.vo.YouHuiBean;
/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-21����11:18:15
 * @auther:   �������Ż�ȯ��չʾ��
 */
public class YouHuiQuan extends BaseActivity implements BaseInterface {
	
	private ImageView youhuiquan_listview_gobye ;
	private TextView youhuiquan_listview_tv ;
	private ListView youhuiquan_listview ;
	private YouHuiAdapter adapter ;
	private UserBean user ;
	private List<YouHuiBean> lists ;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setAct(this) ;
		setContentView(R.layout.youhuiquan_listview) ;
		initViews() ;
		initDatas() ;
		initViewOper() ;
	}
	@Override
	public void initViews() {
		youhuiquan_listview_gobye = imgById(R.id.youhuiquan_listview_gobye) ;
		youhuiquan_listview_tv = tvById(R.id.youhuiquan_listview_tv) ;
		youhuiquan_listview = (ListView) findViewById(R.id.youhuiquan_listview) ;
	}

	@Override
	public void initDatas() {
		user = HappyApplication.getmHappyApplication().getUb() ;
		//�Ϸ�������ȥ��ѯ����
		BmobQuery<YouHuiBean> query = new BmobQuery<YouHuiBean>() ;
		query.addWhereEqualTo("userId", user.getObjectId()) ;
		query.order("-createdAt") ;
		query.findObjects(getAct(), new FindListener<YouHuiBean>() {
			
			@Override
			public void onSuccess(List<YouHuiBean> arg0) {
				lists = arg0 ;
				youhuiquan_listview.setVisibility(View.VISIBLE) ;
				youhuiquan_listview_tv.setVisibility(View.INVISIBLE) ;
				adapter = new YouHuiAdapter(arg0, getAct()) ;
				youhuiquan_listview.setAdapter(adapter) ;
			}
			@Override
			public void onError(int arg0, String arg1) {
				youhuiquan_listview_tv.setText("������û���Ż�ȯ.") ;
				
			}
		}) ;
	}
	/**
	 * ����������ĵ���¼�
	 */
	@Override
	public void initViewOper() {
		Intent intent = getIntent() ;
		int num = intent.getIntExtra("youhui", 0) ;
		if(num==1){
			youhuiquan_listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if(lists!=null&&lists.size()>0){
						//�������ǻص� ������  ��֧��֮ǰ�� ���淵������
						Intent intent1 = new Intent() ;
						intent1.putExtra("number", lists.get(arg2).getObjectId()) ;
						intent1.putExtra("manjian", lists.get(arg2).getManMoney()) ;
						intent1.putExtra("money", lists.get(arg2).getMoney()) ;
						setResult(RESULT_OK, intent1) ;
						finish() ;
					}
				}
			});
		}
		
		//���˵��¼�
		youhuiquan_listview_gobye.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		}) ;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null) ;
	}
}
