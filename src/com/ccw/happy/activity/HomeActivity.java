package com.ccw.happy.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;

import com.ccw.happy.R;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.fragment.FragmentHome;
import com.ccw.happy.fragment.FragmentMe;
import com.ccw.happy.fragment.FragmentMore;
import com.ccw.happy.fragment.FragmentSms;
import com.ccw.happy.vo.MyBmobInstallation;
import com.ccw.happy.vo.UserBean;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-11����10:57:59
 * @auther: ������ �а���4��ѡ���4��fragment���� �����ViewPager����ʵ�����һ���
 *          4��ѡ���ÿ��ѡ�����һ��fragment
 */
public class HomeActivity extends BaseActivity implements BaseInterface {
	private ImageView[] iv = new ImageView[4];
	private TextView[] tv = new TextView[4];
	private TextView add_send;
	private ImageView add_home_iv;
	private int[] imgG = { R.drawable.g1, R.drawable.g2, R.drawable.g3,
			R.drawable.g4 };
	private int[] imgK = { R.drawable.k1, R.drawable.k2, R.drawable.k3,
			R.drawable.k4 };
	private List<Fragment> lists;
	private ViewPager vpager;
	private boolean flag = false;
	private UserBean user;
	private PopupWindow mPopupWindow ;
	private View v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��application������һ�µ�ǰ������
		setAct(this);
		HappyApplication.getmHappyApplication().setHash("activity", HomeActivity.this);
		setContentView(R.layout.home_activity);
		initViews();
		initDatas();
		initViewOper();
		//�����ڰ汾����ʱ������һ�ű��  ֻ��Ҫִ��һ�Σ�������Ϻ�Ϳ���ɾ�����д���
		//		BmobUpdateAgent.initAppVersion(this);
		//�����°汾�ķ���  �˷���������ʲô����¶�������������

		BmobUpdateAgent.setUpdateOnlyWifi(false) ;
	}

	@Override
	protected void onStart() {
		super.onStart();
		HappyApplication.getmHappyApplication().setHash("activity", HomeActivity.this);
	}
	/**
	 * ���ݵĳ�ʼ��
	 */
	@Override
	public void initViews() {
		v = LayoutInflater.from(this).inflate(R.layout.act_home_add, null) ;
		mPopupWindow = new PopupWindow(v, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		vpager = (ViewPager) findViewById(R.id.act_home_fl);

		tv[0] = (TextView) findViewById(R.id.act_home_tv1);
		tv[1] = (TextView) findViewById(R.id.act_home_tv2);
		tv[2] = (TextView) findViewById(R.id.act_home_tv3);
		tv[3] = (TextView) findViewById(R.id.act_home_tv4);

		iv[0] = (ImageView) findViewById(R.id.act_home_iv1);
		iv[1] = (ImageView) findViewById(R.id.act_home_iv2);
		iv[2] = (ImageView) findViewById(R.id.act_home_iv3);
		iv[3] = (ImageView) findViewById(R.id.act_home_iv4);
		add_send = (TextView) v.findViewById(R.id.act_home_add_tv1);
		add_home_iv = imgById(R.id.act_home_iv0);
		//		act_home_add = (RelativeLayout) findViewById(R.id.act_home_layout_add);
		//		act_home_delete = (ImageView) findViewById(R.id.act_home_delete);

	}

	/**
	 * �������
	 */
	@Override
	public void initDatas() {
		lists = new ArrayList<Fragment>();
		// ʹ������������fragment
		lists.add(new FragmentHome());
		lists.add(new FragmentSms());
		lists.add(new FragmentMe());
		lists.add(new FragmentMore());
		vpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		vpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				initonclick(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	/**
	 * ����ļ����¼�
	 */
	@Override
	public void initViewOper() {
		//�����Զ�����
		BmobUpdateAgent.update(this);

		/*	// �������ɫ��ŵ�ʱ�����Ӧ�¼�
		act_home_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				act_home_add.setVisibility(View.INVISIBLE);
			}
		});*/

		// ����Ƿ������activity
		add_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(getAct()==null){
					return ;
				}
				startActivity(new Intent(getAct(), SendGetherActivity.class));
				//				act_home_add.setVisibility(View.INVISIBLE);

			}
		});
		//���ò�ѯ����  ��ѯ���豸���е���Ϣ   ���Ұѵ�ǰ�û���id������豸����
		BmobQuery<MyBmobInstallation> query = new BmobQuery<MyBmobInstallation>();
		user = HappyApplication.getmHappyApplication().getUb() ;
		query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(this));
		query.findObjects(this, new FindListener<MyBmobInstallation>() {

			@Override
			public void onSuccess(List<MyBmobInstallation> object) {
				// TODO Auto-generated method stub
				if(object.size() > 0){
					MyBmobInstallation mbi = object.get(0);
					mbi.setUserId(user.getObjectId());
					mbi.setUserName(user.getUsername()) ;
					mbi.update(getAct(),new UpdateListener() {

						@Override
						public void onSuccess() {
							Log.i("bmob","�豸��Ϣ���³ɹ�");
						}

						@Override
						public void onFailure(int code, String msg) {
							Log.i("bmob","�豸��Ϣ����ʧ��:"+msg);
						}
					});
				}else{
				}
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});

	}

	/**
	 * ����¼�
	 * 
	 * @param v
	 */
	public void onclick(View v) {
		switch (v.getId()) {
		case R.id.act_home_ll:
			initonclick(0);
			vpager.setCurrentItem(0);
			break;
		case R.id.act_home_l2:
			initonclick(1);
			vpager.setCurrentItem(1);
			break;
		case R.id.act_home_l3:
			initonclick(2);
			vpager.setCurrentItem(2);
			break;
		case R.id.act_home_l4:
			initonclick(3);
			vpager.setCurrentItem(3);
			break;
		case R.id.act_home_l0:
			initonclick(4);
			break;
		}
	}

	/**
	 * 
	 * @param �����л�ͼ��ķ���
	 *            ����ͨ������¼�����Ĳ������ж�����һ��ѡ�
	 */
	private void initonclick(int index) {
		if (index == 4) {
			add_home_iv.setImageResource(R.drawable.k5);
			// ��ѡ������Ӱ�ť��ʱ�� �����Ľ���
			mPopupWindow.setTouchable(true) ;
			mPopupWindow.setTouchInterceptor(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return false;
				}
			});
			mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.a001)) ;
			View vv = LayoutInflater.from(this).inflate(R.layout.home_activity, null) ;
			mPopupWindow.showAtLocation(vv,Gravity.BOTTOM,-75,120) ;
		} else {
			add_home_iv.setImageResource(R.drawable.g5);
		}
		for (int i = 0; i < 4; i++) {
			if (index == i) {
				tv[i].setTextColor(Color.parseColor("#EE8414"));
				iv[i].setBackgroundResource(imgK[i]);

			} else {
				tv[i].setTextColor(Color.parseColor("#000000"));
				iv[i].setBackgroundResource(imgG[i]);
			}
		}
	}

	/**
	 * 
	 * @����: �´���
	 * @ʱ��: 2015-11-11����11:24:16
	 * @auther: ������ڲ��� �Զ���adapter�����ѡ�
	 */
	class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return lists.get(arg0);
		}

		@Override
		public int getCount() {
			return lists.size();
		}

	}
	@Override
	public void onBackPressed() {
		if (flag) {
			super.onBackPressed();
		} else {
			toastS("�ٴε��ȷ���˳�.");
			flag = true;
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						toastS("�˳��쳣.");
					}
					flag = false;
				};
			}.start();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null);
	}

	//�������ҵ��ղأ��Ż�ȯ���ҵĶ����ȵȵĵ���¼�
	public void clickMyLove(View v){
		Intent intent = null ;
		switch (v.getId()) {
		case R.id.fragment_me_liearlayout_shoucang:
			intent = new Intent(this, MyLoveGatherActivity.class) ;
			intent.putExtra("gatherNumber", 0) ;
			break;
		case R.id.fragment_me_liearlayout_gather:
			intent = new Intent(this, MyLoveGatherActivity.class) ;
			intent.putExtra("gatherNumber", 1) ;
			break ;
		case R.id.fragment_me_liearlayout_dingdan:
			intent = new Intent(this, MyDingDan.class) ;
			break ;
		case R.id.fragment_me_liearlayout_youhui:
			intent = new Intent(this, YouHuiQuan.class) ;
			break ;
		}
		startActivity(intent) ;
	}

}
