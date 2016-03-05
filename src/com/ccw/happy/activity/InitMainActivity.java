package com.ccw.happy.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.ccw.happy.R;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.utils.IntenetUtils;
import com.ccw.happy.vo.GatherBean;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-9下午7:18:16
 * @auther:欢迎界面用来加载数据，判断是否联网 跳转界面等等等 这里面只有一张动画
 */
public class InitMainActivity extends BaseActivity implements BaseInterface {
	// 活动的list集合
	private List<GatherBean> lists;
	// 加载图片对象的引用
	private ImageView iv;
	// 加载动画的引用
	private AlphaAnimation mAlphaAnimation;
	// 打个标记，用来判断是否联网
	private boolean isNet = false;
	// 提示用户的对话框，
	private AlertDialog maAlertDialog;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAct(this);
		setContentView(R.layout.act_init_main);
		initViews();
		initDatas();
		initViewOper();
	}

	/**
	 * 初始化组件
	 */
	@Override
	public void initViews() {
		iv = imgById(R.id.act_init_iv);
		mAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this,
				R.anim.act_init_main_alpha);
	}

	/**
	 * 初始化数据
	 */
	@Override
	public void initDatas() {
		lists = new ArrayList<GatherBean>();
		BmobQuery<GatherBean> query = new BmobQuery<GatherBean>();
		query.order("-createdAt");
		query.findObjects(getAct(), new FindListener<GatherBean>() {

			@Override
			public void onSuccess(List<GatherBean> arg0) {
				lists = arg0;

				HappyApplication.getmHappyApplication().setHash(
						"initGatherBean", lists);
				// 当本地文件中的账户不等于空 并且在有网的情况下 直接进入到主界面
				Intent intent = new Intent(getAct(), LoginActivity.class);
				if (HappyApplication.getmHappyApplication().getUb() != null) {
					intent = new Intent(getAct(), LoginActivity.class);
				}
				startActivity(intent);
				getAct().finish();
			}

			@Override
			public void onError(int arg0, String arg1) {
				Builder builder = new Builder(getAct());
				builder.setTitle("警告");
				builder.setMessage("检测到没有网络");
				builder.setCancelable(false);
				builder.setPositiveButton("联网", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS));
					}
				});
				builder.setNegativeButton("退出", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (getAct() == null) {
							return;
						}
						getAct().finish();
					}
				});
				if (maAlertDialog == null || !maAlertDialog.isShowing()) {
					maAlertDialog = builder.create();
					maAlertDialog.show();
				}
			}
		});

	}

	/**
	 * 用来给组件添加事件
	 */
	@Override
	public void initViewOper() {
		iv.startAnimation(mAlphaAnimation);
		/**
		 * 给动画添加事件
		 */
		mAlphaAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO 动画开始时
				/**
				 * 在动画开始时判断一下是否有网络可用
				 */
				if (getAct() == null) {
					return;
				}
				isNet = IntenetUtils.isNet(getAct());

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO 动画重放时

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO 动画结束时
				// 在动画结束时判断网络是否可用 if可用就跳转带另一个activtiy 如果不可用
				// 就提示一下用户，当前网络不可用，请用户设置网络
				if (isNet) {
				} else {
					Builder builder = new Builder(getAct());
					builder.setTitle("警告");
					builder.setMessage("检测到没有网络");
					builder.setCancelable(false);
					builder.setPositiveButton("联网", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(
									Settings.ACTION_WIRELESS_SETTINGS));
							maAlertDialog.dismiss();
						}
					});
					builder.setNegativeButton("退出", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (getAct() == null) {
								return;
							}
							getAct().finish();
						}
					});
					if (maAlertDialog == null || !maAlertDialog.isShowing()) {
						maAlertDialog = builder.create();
						maAlertDialog.show();
					}

				}
			}
		});
	}

	/**
	 * 用来在设置完成网络之后再去判断到底有没有网络连接
	 */
	@Override
	protected void onStart() {  
		super.onStart();
		// 如果builder不等于个null说明是从设置完网络之后返回到的当前界面
		if (maAlertDialog != null) {
			// if maAlertDialog在显示的时候 就把他关了
			if (maAlertDialog.isShowing()) {
				maAlertDialog.dismiss();
			}
			mProgressDialog = new ProgressDialog(getAct());
			mProgressDialog.setCancelable(false);
			if (!mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}
			new AsyncTask<Void, Integer, Void>() {
				// 用来拼接字符串
				private StringBuilder sb = null;
				
				// 子线程中的耗时操作
				@Override
				protected Void doInBackground(Void[] params) {

					p: for (int i = 0; i < 60; i++) {
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						publishProgress(i);
						isNet = IntenetUtils.isNet(getAct());
						if (isNet) {
							break p;
						}
					}
				return null;
				};

				// UI线程的更新
				@Override
				protected  void onProgressUpdate(Integer[] values) {

					switch (values[0] % 6) {
					case 0:
						sb = new StringBuilder();
						break;
					}
					sb.append(".");
					mProgressDialog.setMessage("加载中" + sb.toString());
				}

				// 线程结束的时候
				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					if (getAct() != null) {
						if (isNet) {
							initDatas();
						} else {
							if (!maAlertDialog.isShowing()) {
								maAlertDialog.show();
							}
						}
					}
				}
			}.execute();
		}
	}

	/**
	 * 忽略返回键
	 */
	@Override
	public void onBackPressed() {
	}

	/**
	 * 在当前界面处于暂停状态时
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	/**
	 * 当activity销毁时调用的方法
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null);
	}
}
