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
 * @作者: 陈传稳
 * @时间: 2015-11-11上午10:57:59
 * @auther: 主界面 中包含4个选项卡，4个fragment界面 结合了ViewPager可以实现左右滑动
 *          4个选项卡中每个选项卡都有一个fragment
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
		// 向application中设置一下当前上下文
		setAct(this);
		HappyApplication.getmHappyApplication().setHash("activity", HomeActivity.this);
		setContentView(R.layout.home_activity);
		initViews();
		initDatas();
		initViewOper();
		//这是在版本更新时创建的一张表格  只需要执行一次，创建完毕后就可以删除此行代码
		//		BmobUpdateAgent.initAppVersion(this);
		//更新新版本的方法  此方法代表在什么情况下都可以来检查更新

		BmobUpdateAgent.setUpdateOnlyWifi(false) ;
	}

	@Override
	protected void onStart() {
		super.onStart();
		HappyApplication.getmHappyApplication().setHash("activity", HomeActivity.this);
	}
	/**
	 * 数据的初始化
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
	 * 添加数据
	 */
	@Override
	public void initDatas() {
		lists = new ArrayList<Fragment>();
		// 使用事物来加载fragment
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
	 * 组件的监听事件
	 */
	@Override
	public void initViewOper() {
		//监听自动更新
		BmobUpdateAgent.update(this);

		/*	// 当点击红色叉号的时候的响应事件
		act_home_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				act_home_add.setVisibility(View.INVISIBLE);
			}
		});*/

		// 这个是发布活动的activity
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
		//调用查询方法  查询出设备表中的信息   并且把当前用户和id保存进设备表中
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
							Log.i("bmob","设备信息更新成功");
						}

						@Override
						public void onFailure(int code, String msg) {
							Log.i("bmob","设备信息更新失败:"+msg);
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
	 * 点击事件
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
	 * @param 用来切换图标的方法
	 *            ，，通过点击事件传入的参数来判断是哪一个选项卡
	 */
	private void initonclick(int index) {
		if (index == 4) {
			add_home_iv.setImageResource(R.drawable.k5);
			// 当选中了添加按钮的时候 弹出的界面
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
	 * @作者: 陈传稳
	 * @时间: 2015-11-11上午11:24:16
	 * @auther: 这个是内部类 自定义adapter来添加选项卡
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
			toastS("再次点击确认退出.");
			flag = true;
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						toastS("退出异常.");
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

	//发起活动，我的收藏，优惠券，我的订单等等的点击事件
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
