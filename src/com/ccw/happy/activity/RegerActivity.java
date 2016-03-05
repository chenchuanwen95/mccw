package com.ccw.happy.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

import com.ccw.happy.R;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.utils.RandomYouHuiUtils;
import com.ccw.happy.utils.StringIsNulls;
import com.ccw.happy.vo.UserBean;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-9下午11:23:22
 * @auther:本类是注册界面，，在本界面完成了与服务器的交互并把注册的数据存储到服务器
 */
public class RegerActivity extends BaseActivity implements BaseInterface {
	// EditText 组件
	private EditText mEditText_reger_username, mEditText_reger_password,
			mEditText_reger_password2, mEditText_reger_email,
			mEditText_reger_phone, mEditText_reger_yanzheng;
	private Button mButton_reger_yanzheng, mButton_reger_reger,
			mButton_reger_reset;
	private ImageView mImageView_reger_error1, mImageView_reger_error2,
			mImageView_reger_error3, mImageView_reger_error4,
			mImageView_reger_error5;

	/**
	 * 创建Activity时调用的方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAct(this);
		setContentView(R.layout.act_reger_activity);
		initViews();
		initDatas();
		initViewOper();
	}

	/**
	 * Onstart方法 用来设置注册的按钮刚进入时 不可点击
	 */
	@Override
	protected void onStart() {
		super.onStart();
		mButton_reger_reger.setClickable(false);
		mButton_reger_reger.setTextColor(Color.parseColor("#555555"));
	}

	/**
	 * 组件的初始化
	 */
	@Override
	public void initViews() {
		mEditText_reger_username = etById(R.id.act_reger_username);
		mEditText_reger_password = etById(R.id.act_reger_password);
		mEditText_reger_password2 = etById(R.id.act_reger_password2);
		mEditText_reger_email = etById(R.id.act_reger_email);
		mEditText_reger_phone = etById(R.id.act_reger_phone);
		mEditText_reger_yanzheng = etById(R.id.act_reger_yanzheng);
		mButton_reger_yanzheng = btnById(R.id.act_reger_btn1);
		mButton_reger_reger = btnById(R.id.act_reger_reger);
		mButton_reger_reset = btnById(R.id.act_reger_reset);
		mImageView_reger_error1 = imgById(R.id.act_reger_iv1);
		mImageView_reger_error2 = imgById(R.id.act_reger_iv2);
		mImageView_reger_error3 = imgById(R.id.act_reger_iv3);
		mImageView_reger_error4 = imgById(R.id.act_reger_iv4);
		mImageView_reger_error5 = imgById(R.id.act_reger_iv5);
	}

	/**
	 * 加载数据的方法
	 */
	@Override
	public void initDatas() {

	}

	/**
	 * 各个组件的监听事件
	 */
	@Override
	public void initViewOper() {
		// TODO 判断验证码

		// TODO 对输入的用户名进行判断
		mEditText_reger_username.addTextChangedListener(new TextWatcher() {
			// 当输入的用户名改变时
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String username = s.toString();
				if (username.length() == 0) {
					mImageView_reger_error1.setVisibility(View.VISIBLE);
				} else if (username.length() >= 6 && username.length() <= 12) {// 当用户名大于等于6位并且小于等于12位的时候才让图片不显示
					mImageView_reger_error1.setVisibility(View.INVISIBLE);
				} else {
					mImageView_reger_error1.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		// TODO 对密码的判断
		mEditText_reger_password.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String pass = s.toString();
				if (pass.length() == 0) {
					mImageView_reger_error2.setVisibility(View.VISIBLE);
				} else if (pass.length() >= 6 && pass.length() <= 18) {
					mImageView_reger_error2.setVisibility(View.INVISIBLE);
				} else {
					mImageView_reger_error2.setVisibility(View.VISIBLE);
				}

				// 当重复密码输入完毕判断重复密码与当前密码想不相同 不相同则报错
				if (getViewText(mEditText_reger_password2).length() > 0) {
					if (!getViewText(mEditText_reger_password2).equals(pass)) {
						mImageView_reger_error3.setVisibility(View.VISIBLE);
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		// TODO 对重复密码的判断
		mEditText_reger_password2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String pass2 = s.toString().trim();
				if (pass2.length() == 0) {
					mImageView_reger_error3.setVisibility(View.VISIBLE);
				} else if (!pass2.equals(getViewText(mEditText_reger_password))) {
					mImageView_reger_error3.setVisibility(View.VISIBLE);
				} else {
					mImageView_reger_error3.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		// TODO对Email的判断
		mEditText_reger_email.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String email = s.toString();
				if (email.length() == 0) {
					mImageView_reger_error4.setVisibility(View.VISIBLE);
				} else if (getViewText(mEditText_reger_email).indexOf("@") == -1) {
					mImageView_reger_error4.setVisibility(View.VISIBLE);
				} else {
					mImageView_reger_error4.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		// TODO 对手机号码的判断
		mEditText_reger_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String phone = s.toString();
				if (phone.length() == 0) {
					mImageView_reger_error5.setVisibility(View.VISIBLE);
				} else if (phone.length() != 11) {
					mImageView_reger_error5.setVisibility(View.VISIBLE);
				} else {
					mImageView_reger_error5.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		// 对错误提示的点击事件
		mImageView_reger_error1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditText_reger_username.setText("");
				mImageView_reger_error1.setVisibility(View.INVISIBLE);
			}
		});
		mImageView_reger_error2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditText_reger_password.setText("");
				mEditText_reger_password2.setText("");
				mImageView_reger_error2.setVisibility(View.INVISIBLE);
				mImageView_reger_error3.setVisibility(View.INVISIBLE);
			}
		});
		mImageView_reger_error3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditText_reger_password2.setText("");
				mImageView_reger_error3.setVisibility(View.INVISIBLE);
			}
		});
		mImageView_reger_error4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditText_reger_email.setText("");
				mImageView_reger_error4.setVisibility(View.INVISIBLE);
			}
		});
		mImageView_reger_error5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditText_reger_phone.setText("");
				mImageView_reger_error5.setVisibility(View.INVISIBLE);
			}
		});

		// TODO 当点击重置按钮时
		mButton_reger_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditText_reger_username.setText("");
				mEditText_reger_password.setText("");
				mEditText_reger_password2.setText("");
				mEditText_reger_email.setText("");
				mEditText_reger_phone.setText("");
				mEditText_reger_yanzheng.setText("");
				mImageView_reger_error1.setVisibility(View.INVISIBLE);
				mImageView_reger_error2.setVisibility(View.INVISIBLE);
				mImageView_reger_error3.setVisibility(View.INVISIBLE);
				mImageView_reger_error4.setVisibility(View.INVISIBLE);
				mImageView_reger_error5.setVisibility(View.INVISIBLE);
			}
		});

		// TODO 当点击获取验证码的时候
		mButton_reger_yanzheng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String phone = getViewText(mEditText_reger_phone);
				if (phone == null || phone.length() != 11) {
					toastS("手机号码不正确.");
				} else {
					// 这个是对点击验证按钮的改变
					new CountDownTimer(60000, 1000) {

						@Override
						public void onTick(long millisUntilFinished) {
							mButton_reger_yanzheng
									.setText((millisUntilFinished / 1000)
											+ "秒后重发");
							mButton_reger_yanzheng.setClickable(false);
							mButton_reger_yanzheng.setTextColor(Color
									.parseColor("#000000"));

						}

						@Override
						public void onFinish() {
							mButton_reger_yanzheng.setText("获取验证码");
							mButton_reger_yanzheng.setClickable(true);
							mButton_reger_yanzheng.setTextColor(Color
									.parseColor("#0000ff"));
						}
					}.start();

					BmobSMS.requestSMSCode(getAct(), phone, "穷途陌路",
							new RequestSMSCodeListener() {

								@Override
								public void done(Integer arg0,
										BmobException arg1) {
									if (arg1 == null) {// 验证码发送成功
										toastS("发送成功.");// 用于查询本次短信发送详情
										// 短信验证码验证成功之后,让注册的按钮 可以点击
										mButton_reger_reger.setClickable(true);
										mButton_reger_reger.setTextColor(Color
												.parseColor("#000000"));
									}

								}
							});

				}
			}
		});

		// TODO 当点击注册按钮并且验证码验证通过的时候
		mButton_reger_reger.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 如果所有的输入都合法，并且都符合规则的时候，则注册成功
				// 注册时，不能使用save来注册
				final ProgressDialog pd = new ProgressDialog(getAct());
				pd.setCancelable(false);
				pd.setMessage("注册中.");
				pd.show();
				// TODO 这个是对验证码的验证是否通过
				BmobSMS.verifySmsCode(getAct(),
						getViewText(mEditText_reger_phone),
						getViewText(mEditText_reger_yanzheng),
						new VerifySMSCodeListener() {
							@Override
							public void done(BmobException ex) {
								pd.dismiss();
								if (ex == null) {// 短信验证码已验证成功
									// 把所有数据都封装成Userbean对象
									final String username = getViewText(mEditText_reger_username);
									if (StringIsNulls.isNull(username)) {
										toastS("您的用户名过短.");
										return;
									}
									final String password = getViewText(mEditText_reger_password);
									if (StringIsNulls.isNull(password)) {
										toastS("您的密码过短.");
										return;
									}
									final String password2 = getViewText(mEditText_reger_password2);
									if (!password2.equals(password)) {
										toastS("您两次输入密码不一致.");
										return;
									}
									String emails = getViewText(mEditText_reger_email);
									if (StringIsNulls.isNull(emails)
											|| emails.indexOf("@") == -1) {
										toastS("您的邮箱不正确.");
										return;
									}
									final String phone = getViewText(mEditText_reger_phone);
									if (StringIsNulls.isNull(phone)
											|| phone.length() != 11) {
										toastS("你的手机号不正确");
										return;
									}
									UserBean ub = new UserBean();
									ub.setUsername(username);
									ub.setPassword(password);
									ub.setEmail(emails);
									ub.setMobilePhoneNumber(phone);
									// 代表当前手机已经被验证过了
									ub.setMobilePhoneNumberVerified(true);

									ub.signUp(getAct(), new SaveListener() {

										@Override
										public void onSuccess() {
											if (getAct() == null) {
												return;
											}
											// 在Appliaction中保存用户名和密码

											HappyApplication
													.getmHappyApplication()
													.setHash("usernamedelete",
															phone);
											HappyApplication
													.getmHappyApplication()
													.setHash("passworddelete",
															password);
											//通过优惠券的工具类来试一试手气
											RandomYouHuiUtils.getYouHui(getAct()) ;
											toastS("注册成功.");
											getAct().finish();
										}

										@Override
										public void onFailure(int arg0,
												String arg1) {
											toastS(arg1);
											pd.dismiss();
										}
									});
								}
							}
						});

			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null);
	}
	@Override
	protected void onStop() {
		super.onStop();
		RandomYouHuiUtils.isDialogIsShow() ;
	}

}
