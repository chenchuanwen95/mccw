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
 * @����: �´���
 * @ʱ��: 2015-11-9����7:18:16
 * @auther:��ӭ���������������ݣ��ж��Ƿ����� ��ת����ȵȵ� ������ֻ��һ�Ŷ���
 */
public class InitMainActivity extends BaseActivity implements BaseInterface {
	// ���list����
	private List<GatherBean> lists;
	// ����ͼƬ���������
	private ImageView iv;
	// ���ض���������
	private AlphaAnimation mAlphaAnimation;
	// �����ǣ������ж��Ƿ�����
	private boolean isNet = false;
	// ��ʾ�û��ĶԻ���
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
	 * ��ʼ�����
	 */
	@Override
	public void initViews() {
		iv = imgById(R.id.act_init_iv);
		mAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this,
				R.anim.act_init_main_alpha);
	}

	/**
	 * ��ʼ������
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
				// �������ļ��е��˻������ڿ� ����������������� ֱ�ӽ��뵽������
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
				builder.setTitle("����");
				builder.setMessage("��⵽û������");
				builder.setCancelable(false);
				builder.setPositiveButton("����", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS));
					}
				});
				builder.setNegativeButton("�˳�", new OnClickListener() {

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
	 * �������������¼�
	 */
	@Override
	public void initViewOper() {
		iv.startAnimation(mAlphaAnimation);
		/**
		 * ����������¼�
		 */
		mAlphaAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO ������ʼʱ
				/**
				 * �ڶ�����ʼʱ�ж�һ���Ƿ����������
				 */
				if (getAct() == null) {
					return;
				}
				isNet = IntenetUtils.isNet(getAct());

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO �����ط�ʱ

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO ��������ʱ
				// �ڶ�������ʱ�ж������Ƿ���� if���þ���ת����һ��activtiy ���������
				// ����ʾһ���û�����ǰ���粻���ã����û���������
				if (isNet) {
				} else {
					Builder builder = new Builder(getAct());
					builder.setTitle("����");
					builder.setMessage("��⵽û������");
					builder.setCancelable(false);
					builder.setPositiveButton("����", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(
									Settings.ACTION_WIRELESS_SETTINGS));
							maAlertDialog.dismiss();
						}
					});
					builder.setNegativeButton("�˳�", new OnClickListener() {

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
	 * �����������������֮����ȥ�жϵ�����û����������
	 */
	@Override
	protected void onStart() {  
		super.onStart();
		// ���builder�����ڸ�null˵���Ǵ�����������֮�󷵻ص��ĵ�ǰ����
		if (maAlertDialog != null) {
			// if maAlertDialog����ʾ��ʱ�� �Ͱ�������
			if (maAlertDialog.isShowing()) {
				maAlertDialog.dismiss();
			}
			mProgressDialog = new ProgressDialog(getAct());
			mProgressDialog.setCancelable(false);
			if (!mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}
			new AsyncTask<Void, Integer, Void>() {
				// ����ƴ���ַ���
				private StringBuilder sb = null;
				
				// ���߳��еĺ�ʱ����
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

				// UI�̵߳ĸ���
				@Override
				protected  void onProgressUpdate(Integer[] values) {

					switch (values[0] % 6) {
					case 0:
						sb = new StringBuilder();
						break;
					}
					sb.append(".");
					mProgressDialog.setMessage("������" + sb.toString());
				}

				// �߳̽�����ʱ��
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
	 * ���Է��ؼ�
	 */
	@Override
	public void onBackPressed() {
	}

	/**
	 * �ڵ�ǰ���洦����ͣ״̬ʱ
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	/**
	 * ��activity����ʱ���õķ���
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null);
	}
}
