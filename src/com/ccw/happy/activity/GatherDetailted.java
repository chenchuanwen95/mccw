package com.ccw.happy.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;
import com.ccw.happy.R;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.utils.ImageLoaderutils;
import com.ccw.happy.utils.IntenetUtils;
import com.ccw.happy.utils.StringUtilsDemo;
import com.ccw.happy.view.MyImageView;
import com.ccw.happy.vo.DingDan;
import com.ccw.happy.vo.GatherBean;
import com.ccw.happy.vo.UserBean;
import com.ccw.happy.vo.YouHuiBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-18����8:33:56
 * @auther:  �������������ػ������Ϣ����
 */
/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-18����8:33:56
 * @auther: �������������ػ������Ϣ����
 */
public class GatherDetailted extends BaseActivity implements BaseInterface {
	private ImageView act_gather_admin_gobye, act_gather_admingatherimg;
	private MyImageView act_gather_admin_usericon;
	private TextView act_gather_admin_guanli, act_gather_admin_gathername,
			act_gather_admin_tv_time, act_gather_admin_action,
			act_gather_admin_content, act_gather_admin_tv1,
			act_gather_admin_city, act_gather_admin_tv4, act_gather_admin_tv2;
	private Button act_gather_admin_btn;
	private TextView act_gather_admin_tv3;
	private Integer position;
	// ά��һ�������
	private GatherBean mGatherBean;
	// ά��һ����ǰ�û�����
	private UserBean user;
	private ImageLoader mImageLoader;
	private DisplayImageOptions ops1;
	private DisplayImageOptions ops2;
	// ��ȡ��ǰ��ʱ��
	private Calendar c;
	private int myear, mouth, day;
	private int hour, minute, second;
	private String currentDate;
	// �������
	private Integer gatherFlag;
	private AlertDialog.Builder builder;
	private Builder ab;
	private PayListener pay;
	private int number = 0;
	// ���û�������Ϻ� ���֧����ɲ鿴 ����鿴����ת��������
	protected OnClickListener onclic = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// ����ת�������� ��ʱ�� ��Application�����һ�������
			HappyApplication.getmHappyApplication().setHash("gatherComment",
					mGatherBean);
			startActivity(new Intent(getAct(), GatherComment.class));
		}
	};
	private DingDan dd;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setAct(this);
		setContentView(R.layout.act_gather_admin);

		// View�� ��ʾ�߼���
		initViews();
		// Model�� ҵ���߼���
		initDatas();
		// Controller�� �������
		initViewOper();
	}

	/**
	 * ����ĳ�ʼ��
	 */
	@Override
	public void initViews() {
		act_gather_admin_gobye = imgById(R.id.act_gather_admin_gobye);
		act_gather_admingatherimg = imgById(R.id.act_gather_admingatherimg);
		act_gather_admin_usericon = (MyImageView) findViewById(R.id.act_gather_admin_usericon);
		act_gather_admin_guanli = tvById(R.id.act_gather_admin_guanli);
		act_gather_admin_gathername = tvById(R.id.act_gather_admin_gathername);
		act_gather_admin_tv_time = tvById(R.id.act_gather_admin_tv_time);
		act_gather_admin_action = tvById(R.id.act_gather_admin_action);
		act_gather_admin_content = tvById(R.id.act_gather_admin_content);
		act_gather_admin_btn = btnById(R.id.act_gather_admin_btn);
		act_gather_admin_tv4 = tvById(R.id.act_gather_admin_tv4);
		act_gather_admin_tv3 = tvById(R.id.act_gather_admin_tv3);
		act_gather_admin_tv2 = tvById(R.id.act_gather_admin_tv2);
		act_gather_admin_tv1 = tvById(R.id.act_gather_admin_tv1);
		act_gather_admin_city = tvById(R.id.act_gather_admin_city);
	}

	/**
	 * ��������
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initDatas() {
		// ������positionΪ�� �ʹ���������ǴӼ���������ת������
		mGatherBean = (GatherBean) HappyApplication.getmHappyApplication()
				.getHash("gatherpositiondelete");

		// ������ڿ� �ͻ�ȡһ��Item��λ��
		if (mGatherBean == null) {
			position = (Integer) HappyApplication.getmHappyApplication()
					.getHash("gatherPosition");
			if (position == null) {
				position = (Integer) getIntent().getExtras().get(
						"gatherPosition");
			}
			// ����ж�����xlistView������ˢ��ʱ��ֹ�ڴ�����ż����
			if (position < 0) {
				position = 0;
			}
			mGatherBean = ((List<GatherBean>) HappyApplication
					.getmHappyApplication().getHash("initGatherBean"))
					.get(position.intValue());
		}

		// ��ȡʱ�� ��ʱû�õ�
		c = Calendar.getInstance();
		// һ��ʼ��Ĭ����ʾ��ǰ������
		myear = c.get(Calendar.YEAR);
		mouth = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		second = c.get(Calendar.SECOND);
		// ƴ��ʱ���ַ���
		currentDate = myear + "-" + mouth + "-" + day + " " + hour + ":"
				+ minute + ":" + second;
		// �������ȡ���µ����� ���� �����ؽ������

		// ���ݵ�ǰ ������������һ������
		if (IntenetUtils.isNet(getAct())) {
			// ��������粢�ң������Ϊ�յ������ ��ȥ�������� ��֮����ʾ�û����粻����
			if (mGatherBean != null) {
				// ����������ʱ��������ȡ����
				setViewDate();
			} else {
				toastS("��������ʧ��,��������.");
			}
		} else {
			toastS("��������ʧ��,��������.");
		}

		builder = new Builder(getAct());
		builder.setMessage("��ѡ��֧������");
		builder.setNegativeButton("֧����֧��",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Bmob��֧�� ���ػ���Ҫ�Ż�
						zhiFu(1);
					}
				});
		builder.setPositiveButton("΢��֧��",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						zhiFu(2);
					}
				});

	}

	/**
	 * ����֧���ķ���
	 */
	private void zhiFu(final int i) {
		number = i;
		BmobQuery<YouHuiBean> query = new BmobQuery<YouHuiBean>();
		query.addWhereEqualTo("userId", user.getObjectId());
		query.findObjects(getAct(), new FindListener<YouHuiBean>() {
			@Override
			public void onSuccess(List<YouHuiBean> arg0) {
				if (arg0.size() > 1) {
					ab = new Builder(getAct());
					ab.setMessage("��⵽�������Ż�ȯδʹ��.�Ƿ�ʹ��?");
					ab.setPositiveButton("ʹ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(getAct(),
											YouHuiQuan.class);
									intent.putExtra("youhui", 1);
									startActivityForResult(intent, 0);
								}
							});
					ab.setNegativeButton("�´�",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (i == 1) {
										 new BmobPay(getAct()).pay(
										 0.02,
										 "��;İ·->"
										 + mGatherBean
										 .getGatherName(),
										 pay);
//										new BmobPay(getAct()).pay(
//												Integer.parseInt(mGatherBean
//														.getGatherRMB()),
//												"��;İ·->"
//														+ mGatherBean
//																.getGatherName(),
//												pay);
									} else {
										 new BmobPay(getAct()).payByWX(
										 0.02,
										 "��;İ·->"
										 + mGatherBean
										 .getGatherName(),
										 pay);
//										new BmobPay(getAct()).payByWX(
//												Integer.parseInt(mGatherBean
//														.getGatherRMB()),
//												"��;İ·->"
//														+ mGatherBean
//																.getGatherName(),
//												pay);
									}
								}
							});
					ab.show();
				} else {
					// ��������ǰ��i == 1 ��˵���Ǵ�֧����֧��
					if (i == 1) {
						// new BmobPay(getAct()).pay(0.02,
						// "��;İ·->" + mGatherBean.getGatherName(), pay);
						new BmobPay(getAct()).pay(
								Integer.parseInt(mGatherBean.getGatherRMB()),
								"��;İ·->" + mGatherBean.getGatherName(), pay);
					} else { // ������� 1 ��˵���Ǵ�΢��֧��
					// new BmobPay(getAct()).payByWX(0.02, "��;İ·->"
					// + mGatherBean.getGatherName(), pay);
						new BmobPay(getAct()).payByWX(
								Integer.parseInt(mGatherBean.getGatherRMB()),
								"��;İ·->" + mGatherBean.getGatherName(), pay);
					}
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				toastS("�����쳣��������������.");
			}
		});
	}

	/**
	 * ���Ǵ�ѡ���Ż�ȯ������ת�����Ļص�����
	 */
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 0 && RESULT_OK == arg1) {
			String bianhao = arg2.getStringExtra("number");
			String manjian = arg2.getStringExtra("manjian");
			String money = arg2.getStringExtra("money");
			if (Integer.parseInt(mGatherBean.getGatherRMB()) > Integer
					.parseInt(manjian)) {
				toastS("�Ż���   " + money + "  Ԫ");
//				// ɾ���������е��Ż�ȯ
//				user.setYouhui(user.getYouhui() - 1);
				// ���û����е��Ż�������1
				user.increment("youhui", -1);
				YouHuiBean yb = new YouHuiBean();
				yb.setObjectId(bianhao);
				// ɾ���Ż�ȯ�����һ������
				yb.delete(getAct(), new DeleteListener() {

					@Override
					public void onSuccess() {

					}

					@Override
					public void onFailure(int arg0, String arg1) {

					}
				});
				// ��֮ǰ�� i��ֵ����number �������1����֧����֧�� ��֮ ���� ΢��֧��
				if (number == 1) {
					// new BmobPay(getAct()).pay(0.02,
					// "��;İ·->" + mGatherBean.getGatherName(), pay);
					new BmobPay(getAct()).pay(
							Integer.parseInt(mGatherBean.getGatherRMB())
									- Integer.parseInt(money), "��;İ·->"
									+ mGatherBean.getGatherName(), pay);
				} else if (number == 2) {
					// new BmobPay(getAct()).payByWX(0.02,
					// "��;İ·->" + mGatherBean.getGatherName(), pay);
					new BmobPay(getAct()).payByWX(
							Integer.parseInt(mGatherBean.getGatherRMB())
									- Integer.parseInt(money), "��;İ·->"
									+ mGatherBean.getGatherName(), pay);
				} else {
					toastS("��������쳣,��������.");
				}
			} else {
				toastS("��û�дﵽ�Ż�������������ѡ��.");
				ab.show();
			}
		}
	}

	/**
	 * �������������������粢�һ��Ϊ�յ�����»���õķ������÷�������Ҫ���������û�չʾ�����ϸ��Ϣ
	 */

	private void setViewDate() {
		// ���������������ϸ��Ϣ
		act_gather_admin_gathername.setText("�����: "
				+ StringUtilsDemo.getStringUtils2(mGatherBean.getGatherName()));
		act_gather_admin_tv_time
				.setText("�ʱ��: " + mGatherBean.getGatherTime());
		// ���û����ϸ��Ϣ
		act_gather_admin_content.setText(mGatherBean.getGatherIntro());
		// ���û����ϸ��ַ
		act_gather_admin_city.setText("���ַ: " + mGatherBean.getGatherSite());
		// �����û�ͷ��
		mImageLoader = ImageLoaderutils.getInstance(getAct());
		ops1 = ImageLoaderutils.getOpt();
		ops2 = ImageLoaderutils.getOpt2();
		String url1 = null;
		String url2 = null;
		// ���÷������û�ͷ��
		if (mGatherBean.getGatherIcon() != null
				&& mGatherBean.getGatherIcon().getFileUrl(getAct()) != null) {
			url1 = mGatherBean.getGatherIcon().getFileUrl(getAct());
		}
		mImageLoader.displayImage(url1, act_gather_admin_usericon, ops1);
		// ���û��ͼƬ
		if (mGatherBean.getGatherJPG() != null
				&& mGatherBean.getGatherJPG().getFileUrl(getAct()) != null) {
			url2 = mGatherBean.getGatherJPG().getFileUrl(getAct());
		}
		mImageLoader.displayImage(url2, act_gather_admingatherimg, ops2);
		// // ��ȡ��ǰʱ��
		// long fabu = DateTimeUtils.getDate1(currentDate);
		// // ��ȡ��Ŀ�ʼʱ��
		// long huodongstart =
		// DateTimeUtils.getDate2(mGatherBean.getGatherTime()
		// + "");
		// // ��ȡ��Ľ���ʱ��
		// long huodongend = DateTimeUtils.getDate3(mGatherBean.getGatherTime()
		// + "");
		// �����ǰʱ��С�ڻ��ʼʱ�� ����ʾδ����
		if (mGatherBean.getGatherFlag() == 1) {
			// gatherFlag = 1;
			act_gather_admin_action.setText("δ����");
		} else if (mGatherBean.getGatherFlag() == 2) { // �����ǰʱ�䴦�ڻ��ʼʱ��ͻ����ʱ��֮�����ʾ������
			// gatherFlag = 2;
			act_gather_admin_action.setText("������");
			act_gather_admin_action.setTextColor(Color.parseColor("#0000ff"));
		} else if (mGatherBean.getGatherFlag() == 3) { // �����ǰʱ����ڻ����ʱ�� ����ʾ�ѽ���
			// gatherFlag = 3;
			act_gather_admin_action.setText("�ѽ���");
			act_gather_admin_action.setTextColor(Color.parseColor("#b2b2b2"));
			act_gather_admin_action.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG);
		}
		//
		// mGatherBean.setGatherFlag(gatherFlag);
		// GatherBean gb = new GatherBean();
		// gb.addUnique("gatherFlag", gatherFlag);
		// gb.update(getAct(), mGatherBean.getObjectId(), new UpdateListener() {
		//
		// @Override
		// public void onSuccess() {
		//
		// }
		//
		// @Override
		// public void onFailure(int arg0, String arg1) {
		//
		// }
		// });

	}

	/**
	 * ������¼�����
	 */
	@Override
	public void initViewOper() {
		dd = new DingDan();
		user = HappyApplication.getmHappyApplication().getUb();
		/**
		 * �������û�������������������û�id����֤���Ǵ��˷���Ļ������֮���ǲ����߽����˱��
		 */
		// ��˵���ǹ��������������
		if (mGatherBean.getGatherUserId().contains(user.getObjectId())) {
			act_gather_admin_guanli.setText("����");
			// ��˵���ǹ��������������
			act_gather_admin_guanli.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					HappyApplication.getmHappyApplication().setHash(
							"gatherAdmindelete", mGatherBean);
					startActivity(new Intent(getAct(), UserGatherAdmin.class));
				}
			});
		} else {
			// ˵���ǲ����߽���������
			// if (mGatherBean.getGatherFlag() == 1) {
			if (mGatherBean.getPaymentUserId().contains(user.getObjectId())) {
				act_gather_admin_guanli.setText("�鿴");
				act_gather_admin_guanli.setOnClickListener(onclic);
			} else {
				act_gather_admin_guanli.setText("����");
				act_gather_admin_guanli
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								builder.show();
							}
						});
			}
			// } else if (mGatherBean.getGatherFlag() == 2) {
			// act_gather_admin_guanli.setText("������");
			// toastS("������У�������;�μ�.") ;
			// } else if (mGatherBean.getGatherFlag() == 3) {
			// act_gather_admin_guanli.setText("�ѽ���");
			// toastS("��ѽ�����������;�μ�.") ;
			// }
			// Calendar c = Calendar.getInstance();
			// final String year = c.get(Calendar.YEAR) + "";
			// final String mouth = c.get(Calendar.MONTH + 1) + "";
			// final String day = c.get(Calendar.DAY_OF_WEEK) + "";
			// ����֧���Ľӿ�
			pay = new PayListener() {
				private String orderId = null;

				/**
				 * δ֪ԭ�� ���¶�����������֧��
				 */
				@Override
				public void unknow() {
					toastS("δ֪ԭ��֧��ʧ��");
				}

				/**
				 * ֧���ɹ���ʱ��
				 */
				@Override
				public void succeed() {
					toastS("֧���ɹ�");
					act_gather_admin_guanli.setText("�鿴");
					act_gather_admin_guanli.setOnClickListener(onclic);
					// ���û����������Ϣ
					user.getCanjiaGatherId().add(mGatherBean.getObjectId());
					user.getCanjiaGatherName().add(mGatherBean.getGatherName());
					user.getOrderIds().add(orderId);
					// ����һ���µĶ�����
					dd.setGatherId(mGatherBean.getObjectId());
					dd.setGatherName(mGatherBean.getGatherName());
					dd.setUserId(user.getObjectId());
					dd.setDingdanNum(orderId);
					dd.save(getAct(), new SaveListener() {

						@Override
						public void onSuccess() {

						}

						@Override
						public void onFailure(int arg0, String arg1) {

						}
					});
					// ���û������������
					UserBean ub = new UserBean();
					ub.addUnique("canjiaGatherId", mGatherBean.getObjectId());
					ub.addUnique("canjiaGatherName",
							mGatherBean.getGatherName());
					ub.addUnique("orderIds", orderId);
					ub.update(getAct(), user.getObjectId(),
							new UpdateListener() {

								@Override
								public void onSuccess() {

									mGatherBean.getPaymentUserId().add(
											user.getObjectId());
									mGatherBean.getPaymentUserName().add(
											user.getUsername());
									GatherBean bean = new GatherBean();
									// SimpleDateFormat sp = new
									// SimpleDateFormat("yyyy-MM-dd") ;
									// bean.addUnique("paymentTime",sp.format(new
									// Date()));
									bean.addUnique("paymentUserId",
											user.getObjectId());
									bean.addUnique("paymentUserName",
											user.getUsername());

									bean.update(getAct(),
											mGatherBean.getObjectId(),
											new UpdateListener() {

												@Override
												public void onSuccess() {

												}

												@Override
												public void onFailure(int arg0,
														String arg1) {

												}
											});
								}

								@Override
								public void onFailure(int arg0, String arg1) {

								}
							});

				}

				/**
				 * 
				 * @param ֧���ɹ�ʱ
				 *            ������ִ�б����� ����������
				 */
				@Override
				public void orderId(String arg0) {
					this.orderId = arg0;

				}

				@Override
				public void fail(int arg0, String arg1) {
					if (arg0 == -3) {
						// ��������ʾΪ-3ʱ ����Ϊ��װ֧����� �����û���װ֧�����
						toastS("δ��װ΢��֧�����");
						new AlertDialog.Builder(getAct())
								.setMessage(
										"��⵽����δ��װ֧�����,�޷�����΢��֧��,�밲װ���(�Ѵ���ڱ���,����������)")
								.setPositiveButton("��װ",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												installBmobPayPlugin("BmobPayPlugin.apk");
											}

											void installBmobPayPlugin(
													String fileName) {
												try {
													InputStream is = getAssets()
															.open(fileName);
													File file = new File(
															Environment
																	.getExternalStorageDirectory()
																	+ File.separator
																	+ fileName);
													file.createNewFile();
													FileOutputStream fos = new FileOutputStream(
															file);
													byte[] temp = new byte[1024];
													int i = 0;
													while ((i = is.read(temp)) > 0) {
														fos.write(temp, 0, i);
													}
													fos.close();
													is.close();

													Intent intent = new Intent(
															Intent.ACTION_VIEW);
													intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
													intent.setDataAndType(
															Uri.parse("file://"
																	+ file),
															"application/vnd.android.package-archive");
													startActivity(intent);
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
										}).create().show();
					} else if (arg0 == 7777) {
						toastS("δ��װ΢�ſͻ���");
						return;
					}
				}
			};

		}
		// �����ߵ���������Ӧ�¼�
		// act_gather_admin_guanli.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// HappyApplication.getmHappyApplication().setHash(
		// "gatherAdmindelete", mGatherBean);
		// startActivity(new Intent(getAct(), UserGatherAdmin.class));
		// }
		// });

		// �����������ĵ���¼�
		act_gather_admin_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HappyApplication.getmHappyApplication().setHash(
						"gatherComment", mGatherBean);
				startActivity(new Intent(getAct(), GatherComment.class));
			}
		});
		// �˳���ǰ����
		act_gather_admin_gobye.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		// ���û�Ĳμӽ��
		BmobQuery<GatherBean> query = new BmobQuery<GatherBean>();
		query.addWhereEqualTo("objectId", mGatherBean.getObjectId());
		query.findObjects(getAct(), new FindListener<GatherBean>() {

			@Override
			public void onSuccess(List<GatherBean> arg0) {
				mGatherBean = arg0.get(0);
				// ���û�Ĳμӽ��
				if (act_gather_admin_guanli.getText().toString().trim()
						.equals("�鿴")) {
					act_gather_admin_tv1.setText("��֧��");
				} else {
					act_gather_admin_tv1.setText(mGatherBean.getGatherRMB());
				}
				// ����������
				act_gather_admin_tv2.setText(mGatherBean.getPaymentUserId()
						.size() + "");
				// ���õ�ǰ���޵�����
				act_gather_admin_tv3.setText(mGatherBean.getPraiseUserscount()
						+ "");
				// ���۵Ĵ���
				act_gather_admin_tv4.setText(mGatherBean.getPingluns() + "");

				if (mGatherBean.getGatherFlag() == 1) {
					// gatherFlag = 1;
					act_gather_admin_action.setText("δ����");
				} else if (mGatherBean.getGatherFlag() == 2) { // �����ǰʱ�䴦�ڻ��ʼʱ��ͻ����ʱ��֮�����ʾ������
					// gatherFlag = 2;
					act_gather_admin_action.setText("������");
					act_gather_admin_action.setTextColor(Color
							.parseColor("#0000ff"));
					if (mGatherBean.getPaymentUserName().contains(
							user.getUsername())
							&& !mGatherBean.getStartUserName().contains(
									user.getUsername())) {
						AlertDialog.Builder builder = new Builder(getAct());
						builder.setMessage("��ѿ���,���е����ֳ���?");
						builder.setPositiveButton("�����ֳ�",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										GatherBean bean = new GatherBean();
										bean.addUnique("startUserId",
												user.getObjectId());
										bean.addUnique("startUserName",
												user.getUsername());
										bean.update(getAct(),
												mGatherBean.getObjectId(),
												new UpdateListener() {

													@Override
													public void onSuccess() {
														toastS("ȷ�ϳɹ�,ף��������.");
													}

													@Override
													public void onFailure(
															int arg0,
															String arg1) {

													}
												});
									}
								});
						builder.setNegativeButton("�����ֳ�",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										toastS("��Ѿ�����,��Ͻ��ϵ��ֳ�.");
									}
								});
						builder.show();
					}

				} else if (mGatherBean.getGatherFlag() == 3) { // �����ǰʱ����ڻ����ʱ��
																// ����ʾ�ѽ���
					// gatherFlag = 3;
					act_gather_admin_action.setText("�ѽ���");
					act_gather_admin_action.setTextColor(Color
							.parseColor("#b2b2b2"));
					act_gather_admin_action.getPaint().setFlags(
							Paint.STRIKE_THRU_TEXT_FLAG);
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				toastS("��ȡ�����ʧ��,��������.");
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null);
	}
}
