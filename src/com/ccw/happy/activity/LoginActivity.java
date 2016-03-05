package com.ccw.happy.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

import com.ccw.happy.R;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.vo.UserBean;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-9����9:57:04
 * @auther: �����ǵ�¼���� �ڽ������������û���������Ĺ��� ��ס���� �Լ�ȥ���ȹ���
 */
public class LoginActivity extends BaseActivity implements BaseInterface {
	protected static final int REQUEST_CODE = 1;
	private TextView mTextView_username, mTextView_password, mTextView_reger;
	private CheckBox mCheckBox;
	private Button mButton;
	// ���ݵķ�װ����
	private UserBean user;
	private String username, password;

	// �����û�����������ļ�
	private SharedPreferences sp;
	private boolean flag = false;

	/**
	 * activity����ʱ���õķ���
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAct(this);
		setContentView(R.layout.act_login_activity);
		sp = getSharedPreferences("user", MODE_PRIVATE);
		initViews();
		String name = sp.getString("username", "");
		String pass = sp.getString("password", "");
		flag = sp.getBoolean("flag", false);
		if (flag) {
			mCheckBox.setChecked(true);
			if (mCheckBox.isChecked()) {
				mTextView_username.setText(name);
				mTextView_password.setText(pass);
			}
		} else {
			mCheckBox.setChecked(false);
			mTextView_username.setText("");
			mTextView_password.setText("");
		}
		initDatas();
		initViewOper();
	}

	/**
	 * ���ݵĳ�ʼ��
	 */
	@Override
	public void initViews() {
		mTextView_username = tvById(R.id.act_login_username);
		mTextView_password = tvById(R.id.act_login_password);
		mTextView_reger = tvById(R.id.act_login_reger);
		mCheckBox = boxById(R.id.act_login_cb);
		mButton = btnById(R.id.act_login_login);
	}

	/**
	 * ������� �ķ���
	 */
	@Override
	public void initDatas() {
		
	}

	/**
	 * ����ĵ���¼�
	 */
	@Override
	public void initViewOper() {

		// �����¼���¼�

		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String username = mTextView_username.getText().toString()
						.trim();
				final String password = mTextView_password.getText().toString()
						.trim();
				final ProgressDialog pd = new ProgressDialog(getAct());
				pd.setCancelable(false);
				pd.setMessage("��¼��...");
				pd.show();
				BmobUser.loginByAccount(getAct(), username, password,
						new LogInListener<UserBean>() {

							@Override
							public void done(UserBean user, BmobException e) {
								if (pd.isShowing()) {
									pd.dismiss();
								}
								if (user == null) {
									toastS("��½ʧ��!:" + e.getLocalizedMessage());
								} else {
									if (getAct() == null) {
										return;
									}
									// ��ס����ĵ����¼�
									Editor e1 = sp.edit();
									if (mCheckBox.isChecked()) {
										flag = true;
										e1.putString("username", mTextView_username.getText().toString()
												.trim());
										e1.putString("password", mTextView_password.getText().toString()
												.trim());
										e1.putBoolean("flag", flag);
									} else {
										flag = false;
										e1.putString("username","");
										e1.putString("password", "");
										e1.putBoolean("flag", flag);
									}
									e1.commit();
									
									
									
									LoginActivity.this.user = user;
									HappyApplication.getmHappyApplication()
											.setUb(user);
									toastS("��¼�ɹ�!");
									startActivity(new Intent(getAct(),
											HomeActivity.class));
									finish();
								}
							}
						});
			}
		});
		/**
		 * ע�ᰴť�ĵ���¼�
		 */
		// ע��ĵ����¼�
		mTextView_reger.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getAct(), RegerActivity.class));
			}
		});

	}

	/**
	 * onStart()���� �����ڽ���ս���ʱ���û�����������������˺ź�����
	 */
	@Override
	protected void onStart() {
		// ����������������û�ע����û���
		super.onStart();
		username = (String) HappyApplication.getmHappyApplication().getHash(
				"usernamedelete");
		password = (String) HappyApplication.getmHappyApplication().getHash(
				"passworddelete");
		if (username != null) {
			mTextView_username.setText(username);
		}
		if (password != null) {
			mTextView_password.setText(password);
		}
		user = BmobUser.getCurrentUser(this, UserBean.class);
		if (user != null) {
			mTextView_username.setText(user.getUsername());
		}
	}

	/**
	 * ����������ʱ���õķ���
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null);
	}
}
