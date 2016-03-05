package com.ccw.happy.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;

import com.baidu.mapapi.model.LatLng;
import com.ccw.happy.R;
import com.ccw.happy.adapter.MyXListViewAdapter;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.utils.StringUtilsDemo;
import com.ccw.happy.vo.GatherBean;
import com.ccw.happy.vo.UserBean;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-17����7:14:41
 * @auther: ���������������û��ĸ����ղ��뷢��Ļ��
 */

public class MyLoveGatherActivity extends BaseActivity implements BaseInterface {
	// ���е��øý��洩������ֵ
	private int gatherNumber;
	// �������
	private TextView mylove_gather_title,mylove_gather_text;
	private ListView mylove_gather_listview;
	private LinearLayout mylove_gather_liearlayout;
	private ImageView mylove_gather_loading,mylave_gather_gobye,mylave_gather_add;
	private UserBean user ;
	private List<GatherBean> lists ;

	private MyXListViewAdapter adapter ;
	protected String stringStr;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setAct(this);
		//��adapter���õ�ǰ�������Ļ���
		HappyApplication.getmHappyApplication().setHash("activity", MyLoveGatherActivity.this) ;
		setContentView(R.layout.mylove_gather);
		initViews();
		initDatas();
		initViewOper();
	}
	/**
	 * ����ĳ�ʼ��
	 */
	@Override
	public void initViews() {
		mylove_gather_title = tvById(R.id.mylove_gather_title) ;
		mylove_gather_listview = (ListView) findViewById(R.id.mylove_gather_listview) ;
		mylove_gather_liearlayout = (LinearLayout) findViewById(R.id.mylove_gather_liearlayout) ;
		mylove_gather_loading = imgById(R.id.mylove_gather_loading) ;
		mylove_gather_text = tvById(R.id.mylove_gather_text) ;
		mylave_gather_gobye = imgById(R.id.mylave_gather_gobye) ;
		mylave_gather_gobye.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyLoveGatherActivity.this.finish() ;
			}
		}) ;
		mylave_gather_add = imgById(R.id.mylave_gather_add) ;
		mylave_gather_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyLoveGatherActivity.this,HomeActivity.class) ;
				startActivity(intent) ;
				MyLoveGatherActivity.this.finish() ;
			}
		}) ;
	}
	/**
	 * ��������
	 */
	@Override
	public void initDatas() {
		stringStr = "���ڼ�����." ;
		// ��ȡ����������
		user = HappyApplication.getmHappyApplication().getUb() ;
		gatherNumber = getIntent().getIntExtra("gatherNumber", 0);
		lists = new ArrayList<GatherBean> () ;
		getGatherNumber();
	}
	/**
	 * �ڱ������н����ж��û�������ʲô�������Ա������ֱ�ӵ���Ӧ
	 * 0  �ղصĻ
	 * 1  �����Ļ
	 * 2 �Ӹ������ѡ��Ļ
	 * 3 ��ѻ
	 * 4 ���Ż
	 * 5 �����
	 * 6 ģ����ѯ
	 */
	private void getGatherNumber() {
		BmobQuery<GatherBean> query = new BmobQuery<GatherBean>();
		String typeName = (String) HappyApplication.getmHappyApplication().getHash("typeName") ;
		query.order("-createdAt") ;
		imageRotate() ;
		switch (gatherNumber) {
		case 0:
			mylove_gather_title.setText("�ҵ��ղ�") ;
			query.addWhereEqualTo("praiseUsers", user.getObjectId());
			stringStr = "�������ղصĻ." ;
			break;
		case 1:
			mylove_gather_title.setText("�ҵĻ") ;
			query.addWhereContainedIn("objectId", user.getmGatherId()) ;
			stringStr = "�����������Ļ." ;
			break;
		case 2:
			//��ѯ��ͬ���͵Ļ
			mylove_gather_title.setText(typeName) ;
			query.addWhereEqualTo("gatherClass", typeName) ;
			stringStr = "���ޡ�"+typeName+"�����͵Ļ." ;
			break ;
		case 3:
			//�����ѵĻ
			mylove_gather_title.setText(typeName) ;
			query.addWhereEqualTo("gatherRMB", "0") ;
			stringStr = "Sorry,���ޡ�"+typeName+"��." ;
			break ;
		case 4:
			//��ѯ�����ղس���10���˵Ļ
			mylove_gather_title.setText(typeName) ;
			query.order("-praiseUserscount") ;
			query.setLimit(10) ;
			stringStr = "��ѯʧ��,�����²�ѯ." ;
			break ;
		case 5:
			//���ҵ�λ�������8���
			LatLng latlng= HappyApplication.getmHappyApplication().getLists().get(0) ;
			mylove_gather_title.setText(typeName) ;
			query.addWhereNear("gpsAdd", new BmobGeoPoint(latlng.longitude, latlng.latitude));
			query.setLimit(8); //ֻ��ѯ����İ���
			stringStr = "��"+typeName+"�����޻." ;
			break ;
		case 6:
			//ģ����ѯ
			mylove_gather_title.setText(StringUtilsDemo.getStringUtils(typeName)) ;
			String typeName1 = (String) HappyApplication.getmHappyApplication().getHash("typeName") ;
			query.addWhereContains("gatherName", typeName1) ;
			stringStr = "���޹��ڡ�"+typeName+"���Ļ." ;
		}
		//��ѯ�ķ���
		query.findObjects(getAct(), new FindListener<GatherBean>() {
			
			@Override
			public void onSuccess(List<GatherBean> arg0) {
				//��ѯ�ɹ����ü��صĶ�����ʧ
				mylove_gather_liearlayout.setVisibility(View.INVISIBLE) ;
				//�Ѳ�ѯ�������ݸ�ֵ����ǰ��List����
				lists = arg0 ;
				
				//�����ѯ������С��1��ʱ�򣬾ʹ���û������   �õ�ǰ�Ľ�����ʾû������
				//��֮�Ͱ�������ʾ���� ���ö�����ʧ��
				if(lists.size()<1){
					mylove_gather_text.setText(stringStr) ;
				}else{
					mylove_gather_text.setVisibility(View.INVISIBLE) ;
					mylove_gather_listview.setVisibility(View.VISIBLE) ;
					adapter = new MyXListViewAdapter(lists) ;
					mylove_gather_listview.setAdapter(adapter) ;
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				//������ز���������  ���п��������������
				mylove_gather_liearlayout.setVisibility(View.INVISIBLE) ;
				mylove_gather_text.setText("����ʧ��,��������.") ;
			}
		}) ;
		//������listView�ĵ���¼�
	
		mylove_gather_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				
				HappyApplication.getmHappyApplication().setHash(
						"gatherpositiondelete", lists.get(position));
				startActivity(new Intent(getAct(), GatherDetailted.class));
			}
		});
	  	
		
	}
	//���䶯��   �������������Ҫ��������ת����  �ö����ڲ�ͣ����ת �������ٵط�ʽ�ڲ�ͣ��ת
	private void imageRotate() {
		RotateAnimation an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		an.setDuration(1000) ;
		an.setInterpolator(new  LinearInterpolator()) ;
		an.setRepeatCount(-1) ;
		mylove_gather_loading.startAnimation(an) ;
	}
	//�¼��ķ���
	@Override  
	public void initViewOper() {

	}
	//�����ǰѵ�ǰ��activity����Ϊkong 
	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null);
	}
}
