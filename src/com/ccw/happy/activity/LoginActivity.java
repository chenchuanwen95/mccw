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
 * @作者: 陈传稳
 * @时间: 2015-11-9下午9:57:04
 * @auther: 本类是登录界面 在界面中有输入用户名和密码的过程 记住密码 以及去祖册等功能
 */
public class LoginActivity extends BaseActivity implements BaseInterface {
	protected static final int REQUEST_CODE = 1;
	private TextView mTextView_username, mTextView_password, mTextView_reger;
	private CheckBox mCheckBox;
	private Button mButton;
	// 数据的封装对象
	private UserBean user;
	private String username, password;

	// 保存用户名和密码的文件
	private SharedPreferences sp;
	private boolean flag = false;

	/**
	 * activity创建时调用的方法
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
	 * 数据的初始化
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
	 * 添加数据 的方法
	 */
	@Override
	public void initDatas() {
		
	}

	/**
	 * 组件的点击事件
	 */
	@Override
	public void initViewOper() {

		// 点击登录的事件

		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String username = mTextView_username.getText().toString()
						.trim();
				final String password = mTextView_password.getText().toString()
						.trim();
				final ProgressDialog pd = new ProgressDialog(getAct());
				pd.setCancelable(false);
				pd.setMessage("登录中...");
				pd.show();
				BmobUser.loginByAccount(getAct(), username, password,
						new LogInListener<UserBean>() {

							@Override
							public void done(UserBean user, BmobException e) {
								if (pd.isShowing()) {
									pd.dismiss();
								}
								if (user == null) {
									toastS("登陆失败!:" + e.getLocalizedMessage());
								} else {
									if (getAct() == null) {
										return;
									}
									// 记住密码的单击事件
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
									toastS("登录成功!");
									startActivity(new Intent(getAct(),
											HomeActivity.class));
									finish();
								}
							}
						});
			}
		});
		/**
		 * 注册按钮的点击事件
		 */
		// 注册的单击事件
		mTextView_reger.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getAct(), RegerActivity.class));
			}
		});

	}

	/**
	 * onStart()方法 用来在界面刚进入时向用户名和密码上面添加账号和密码
	 */
	@Override
	protected void onStart() {
		// 在输入栏上面加入用户注册的用户名
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
	 * 当界面销毁时调用的方法
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null);
	}
}
