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
 * @����: �´���
 * @ʱ��: 2015-11-9����11:23:22
 * @auther:������ע����棬���ڱ������������������Ľ�������ע������ݴ洢��������
 */
public class RegerActivity extends BaseActivity implements BaseInterface {
	// EditText ���
	private EditText mEditText_reger_username, mEditText_reger_password,
			mEditText_reger_password2, mEditText_reger_email,
			mEditText_reger_phone, mEditText_reger_yanzheng;
	private Button mButton_reger_yanzheng, mButton_reger_reger,
			mButton_reger_reset;
	private ImageView mImageView_reger_error1, mImageView_reger_error2,
			mImageView_reger_error3, mImageView_reger_error4,
			mImageView_reger_error5;

	/**
	 * ����Activityʱ���õķ���
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
	 * Onstart���� ��������ע��İ�ť�ս���ʱ ���ɵ��
	 */
	@Override
	protected void onStart() {
		super.onStart();
		mButton_reger_reger.setClickable(false);
		mButton_reger_reger.setTextColor(Color.parseColor("#555555"));
	}

	/**
	 * ����ĳ�ʼ��
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
	 * �������ݵķ���
	 */
	@Override
	public void initDatas() {

	}

	/**
	 * ��������ļ����¼�
	 */
	@Override
	public void initViewOper() {
		// TODO �ж���֤��

		// TODO ��������û��������ж�
		mEditText_reger_username.addTextChangedListener(new TextWatcher() {
			// ��������û����ı�ʱ
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String username = s.toString();
				if (username.length() == 0) {
					mImageView_reger_error1.setVisibility(View.VISIBLE);
				} else if (username.length() >= 6 && username.length() <= 12) {// ���û������ڵ���6λ����С�ڵ���12λ��ʱ�����ͼƬ����ʾ
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
		// TODO ��������ж�
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

				// ���ظ�������������ж��ظ������뵱ǰ�����벻��ͬ ����ͬ�򱨴�
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
		// TODO ���ظ�������ж�
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

		// TODO��Email���ж�
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
		// TODO ���ֻ�������ж�
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

		// �Դ�����ʾ�ĵ���¼�
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

		// TODO ��������ð�ťʱ
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

		// TODO �������ȡ��֤���ʱ��
		mButton_reger_yanzheng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String phone = getViewText(mEditText_reger_phone);
				if (phone == null || phone.length() != 11) {
					toastS("�ֻ����벻��ȷ.");
				} else {
					// ����ǶԵ����֤��ť�ĸı�
					new CountDownTimer(60000, 1000) {

						@Override
						public void onTick(long millisUntilFinished) {
							mButton_reger_yanzheng
									.setText((millisUntilFinished / 1000)
											+ "����ط�");
							mButton_reger_yanzheng.setClickable(false);
							mButton_reger_yanzheng.setTextColor(Color
									.parseColor("#000000"));

						}

						@Override
						public void onFinish() {
							mButton_reger_yanzheng.setText("��ȡ��֤��");
							mButton_reger_yanzheng.setClickable(true);
							mButton_reger_yanzheng.setTextColor(Color
									.parseColor("#0000ff"));
						}
					}.start();

					BmobSMS.requestSMSCode(getAct(), phone, "��;İ·",
							new RequestSMSCodeListener() {

								@Override
								public void done(Integer arg0,
										BmobException arg1) {
									if (arg1 == null) {// ��֤�뷢�ͳɹ�
										toastS("���ͳɹ�.");// ���ڲ�ѯ���ζ��ŷ�������
										// ������֤����֤�ɹ�֮��,��ע��İ�ť ���Ե��
										mButton_reger_reger.setClickable(true);
										mButton_reger_reger.setTextColor(Color
												.parseColor("#000000"));
									}

								}
							});

				}
			}
		});

		// TODO �����ע�ᰴť������֤����֤ͨ����ʱ��
		mButton_reger_reger.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO ������е����붼�Ϸ������Ҷ����Ϲ����ʱ����ע��ɹ�
				// ע��ʱ������ʹ��save��ע��
				final ProgressDialog pd = new ProgressDialog(getAct());
				pd.setCancelable(false);
				pd.setMessage("ע����.");
				pd.show();
				// TODO ����Ƕ���֤�����֤�Ƿ�ͨ��
				BmobSMS.verifySmsCode(getAct(),
						getViewText(mEditText_reger_phone),
						getViewText(mEditText_reger_yanzheng),
						new VerifySMSCodeListener() {
							@Override
							public void done(BmobException ex) {
								pd.dismiss();
								if (ex == null) {// ������֤������֤�ɹ�
									// ���������ݶ���װ��Userbean����
									final String username = getViewText(mEditText_reger_username);
									if (StringIsNulls.isNull(username)) {
										toastS("�����û�������.");
										return;
									}
									final String password = getViewText(mEditText_reger_password);
									if (StringIsNulls.isNull(password)) {
										toastS("�����������.");
										return;
									}
									final String password2 = getViewText(mEditText_reger_password2);
									if (!password2.equals(password)) {
										toastS("�������������벻һ��.");
										return;
									}
									String emails = getViewText(mEditText_reger_email);
									if (StringIsNulls.isNull(emails)
											|| emails.indexOf("@") == -1) {
										toastS("�������䲻��ȷ.");
										return;
									}
									final String phone = getViewText(mEditText_reger_phone);
									if (StringIsNulls.isNull(phone)
											|| phone.length() != 11) {
										toastS("����ֻ��Ų���ȷ");
										return;
									}
									UserBean ub = new UserBean();
									ub.setUsername(username);
									ub.setPassword(password);
									ub.setEmail(emails);
									ub.setMobilePhoneNumber(phone);
									// ����ǰ�ֻ��Ѿ�����֤����
									ub.setMobilePhoneNumberVerified(true);

									ub.signUp(getAct(), new SaveListener() {

										@Override
										public void onSuccess() {
											if (getAct() == null) {
												return;
											}
											// ��Appliaction�б����û���������

											HappyApplication
													.getmHappyApplication()
													.setHash("usernamedelete",
															phone);
											HappyApplication
													.getmHappyApplication()
													.setHash("passworddelete",
															password);
											//ͨ���Ż�ȯ�Ĺ���������һ������
											RandomYouHuiUtils.getYouHui(getAct()) ;
											toastS("ע��ɹ�.");
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
