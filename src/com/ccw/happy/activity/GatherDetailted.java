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
 * @作者: 陈传稳
 * @时间: 2015-11-18下午8:33:56
 * @auther:  本类是用来加载活动详情信息的类
 */
/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-18下午8:33:56
 * @auther: 本类是用来加载活动详情信息的类
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
	// 维护一个活动对象
	private GatherBean mGatherBean;
	// 维护一个当前用户对象
	private UserBean user;
	private ImageLoader mImageLoader;
	private DisplayImageOptions ops1;
	private DisplayImageOptions ops2;
	// 获取当前的时间
	private Calendar c;
	private int myear, mouth, day;
	private int hour, minute, second;
	private String currentDate;
	// 活动的类型
	private Integer gatherFlag;
	private AlertDialog.Builder builder;
	private Builder ab;
	private PayListener pay;
	private int number = 0;
	// 当用户付款完毕后 会把支付变成查看 点击查看会跳转到评论区
	protected OnClickListener onclic = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// 在跳转到评论区 的时候 向Application中添加一个活动对象
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

		// View层 显示逻辑层
		initViews();
		// Model层 业务逻辑层
		initDatas();
		// Controller层 处理过程
		initViewOper();
	}

	/**
	 * 组件的初始化
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
	 * 加载数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initDatas() {
		// 如果这个position为空 就代表这个不是从检索界面跳转过来的
		mGatherBean = (GatherBean) HappyApplication.getmHappyApplication()
				.getHash("gatherpositiondelete");

		// 如果等于空 就获取一下Item的位置
		if (mGatherBean == null) {
			position = (Integer) HappyApplication.getmHappyApplication()
					.getHash("gatherPosition");
			if (position == null) {
				position = (Integer) getIntent().getExtras().get(
						"gatherPosition");
			}
			// 这个判断是在xlistView中下啦刷新时防止内存溢出才加入的
			if (position < 0) {
				position = 0;
			}
			mGatherBean = ((List<GatherBean>) HappyApplication
					.getmHappyApplication().getHash("initGatherBean"))
					.get(position.intValue());
		}

		// 获取时间 暂时没用到
		c = Calendar.getInstance();
		// 一开始就默认显示当前的日期
		myear = c.get(Calendar.YEAR);
		mouth = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		second = c.get(Calendar.SECOND);
		// 拼接时间字符串
		currentDate = myear + "-" + mouth + "-" + day + " " + hour + ":"
				+ minute + ":" + second;
		// 从网络获取最新的数据 ，， 并加载进活动详情

		// 根据当前 的网络来做下一步操作
		if (IntenetUtils.isNet(getAct())) {
			// 如果有网络并且，活动对象不为空的情况下 就去加载数据 反之就提示用户网络不可用
			if (mGatherBean != null) {
				// 如果有网络的时候在区获取详情
				setViewDate();
			} else {
				toastS("活动详情加载失败,请检查网络.");
			}
		} else {
			toastS("活动详情加载失败,请检查网络.");
		}

		builder = new Builder(getAct());
		builder.setMessage("请选择支付类型");
		builder.setNegativeButton("支付宝支付",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Bmob的支付 本地还需要优化
						zhiFu(1);
					}
				});
		builder.setPositiveButton("微信支付",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						zhiFu(2);
					}
				});

	}

	/**
	 * 调用支付的方法
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
					ab.setMessage("检测到您还有优惠券未使用.是否使用?");
					ab.setPositiveButton("使用",
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
					ab.setNegativeButton("下次",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (i == 1) {
										 new BmobPay(getAct()).pay(
										 0.02,
										 "穷途陌路->"
										 + mGatherBean
										 .getGatherName(),
										 pay);
//										new BmobPay(getAct()).pay(
//												Integer.parseInt(mGatherBean
//														.getGatherRMB()),
//												"穷途陌路->"
//														+ mGatherBean
//																.getGatherName(),
//												pay);
									} else {
										 new BmobPay(getAct()).payByWX(
										 0.02,
										 "穷途陌路->"
										 + mGatherBean
										 .getGatherName(),
										 pay);
//										new BmobPay(getAct()).payByWX(
//												Integer.parseInt(mGatherBean
//														.getGatherRMB()),
//												"穷途陌路->"
//														+ mGatherBean
//																.getGatherName(),
//												pay);
									}
								}
							});
					ab.show();
				} else {
					// 检测如果当前的i == 1 就说明是从支付宝支付
					if (i == 1) {
						// new BmobPay(getAct()).pay(0.02,
						// "穷途陌路->" + mGatherBean.getGatherName(), pay);
						new BmobPay(getAct()).pay(
								Integer.parseInt(mGatherBean.getGatherRMB()),
								"穷途陌路->" + mGatherBean.getGatherName(), pay);
					} else { // 如果不是 1 就说明是从微信支付
					// new BmobPay(getAct()).payByWX(0.02, "穷途陌路->"
					// + mGatherBean.getGatherName(), pay);
						new BmobPay(getAct()).payByWX(
								Integer.parseInt(mGatherBean.getGatherRMB()),
								"穷途陌路->" + mGatherBean.getGatherName(), pay);
					}
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				toastS("网络异常，请检查网络连接.");
			}
		});
	}

	/**
	 * 这是从选择优惠券界面跳转回来的回调方法
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
				toastS("优惠了   " + money + "  元");
//				// 删除服务器中的优惠券
//				user.setYouhui(user.getYouhui() - 1);
				// 对用户表中的优惠数量减1
				user.increment("youhui", -1);
				YouHuiBean yb = new YouHuiBean();
				yb.setObjectId(bianhao);
				// 删除优惠券里面的一条数据
				yb.delete(getAct(), new DeleteListener() {

					@Override
					public void onSuccess() {

					}

					@Override
					public void onFailure(int arg0, String arg1) {

					}
				});
				// 在之前把 i赋值给了number 如果等于1就用支付宝支付 反之 就用 微信支付
				if (number == 1) {
					// new BmobPay(getAct()).pay(0.02,
					// "穷途陌路->" + mGatherBean.getGatherName(), pay);
					new BmobPay(getAct()).pay(
							Integer.parseInt(mGatherBean.getGatherRMB())
									- Integer.parseInt(money), "穷途陌路->"
									+ mGatherBean.getGatherName(), pay);
				} else if (number == 2) {
					// new BmobPay(getAct()).payByWX(0.02,
					// "穷途陌路->" + mGatherBean.getGatherName(), pay);
					new BmobPay(getAct()).payByWX(
							Integer.parseInt(mGatherBean.getGatherRMB())
									- Integer.parseInt(money), "穷途陌路->"
									+ mGatherBean.getGatherName(), pay);
				} else {
					toastS("网络出现异常,请检查网络.");
				}
			} else {
				toastS("你没有达到优惠条件，请重新选择.");
				ab.show();
			}
		}
	}

	/**
	 * 本方法是用来在有网络并且活动不为空的情况下会调用的方法，该方法中主要是用来向用户展示活动的详细信息
	 */

	private void setViewDate() {
		// 向组件上面设置详细信息
		act_gather_admin_gathername.setText("活动名称: "
				+ StringUtilsDemo.getStringUtils2(mGatherBean.getGatherName()));
		act_gather_admin_tv_time
				.setText("活动时间: " + mGatherBean.getGatherTime());
		// 设置活动的详细信息
		act_gather_admin_content.setText(mGatherBean.getGatherIntro());
		// 设置活动的详细地址
		act_gather_admin_city.setText("活动地址: " + mGatherBean.getGatherSite());
		// 设置用户头像
		mImageLoader = ImageLoaderutils.getInstance(getAct());
		ops1 = ImageLoaderutils.getOpt();
		ops2 = ImageLoaderutils.getOpt2();
		String url1 = null;
		String url2 = null;
		// 设置发起活动的用户头像
		if (mGatherBean.getGatherIcon() != null
				&& mGatherBean.getGatherIcon().getFileUrl(getAct()) != null) {
			url1 = mGatherBean.getGatherIcon().getFileUrl(getAct());
		}
		mImageLoader.displayImage(url1, act_gather_admin_usericon, ops1);
		// 设置活动的图片
		if (mGatherBean.getGatherJPG() != null
				&& mGatherBean.getGatherJPG().getFileUrl(getAct()) != null) {
			url2 = mGatherBean.getGatherJPG().getFileUrl(getAct());
		}
		mImageLoader.displayImage(url2, act_gather_admingatherimg, ops2);
		// // 获取当前时间
		// long fabu = DateTimeUtils.getDate1(currentDate);
		// // 获取活动的开始时间
		// long huodongstart =
		// DateTimeUtils.getDate2(mGatherBean.getGatherTime()
		// + "");
		// // 获取活动的结束时间
		// long huodongend = DateTimeUtils.getDate3(mGatherBean.getGatherTime()
		// + "");
		// 如果当前时间小于活动开始时间 就显示未进行
		if (mGatherBean.getGatherFlag() == 1) {
			// gatherFlag = 1;
			act_gather_admin_action.setText("未进行");
		} else if (mGatherBean.getGatherFlag() == 2) { // 如果当前时间处于活动开始时间和活动结束时间之间就显示进行中
			// gatherFlag = 2;
			act_gather_admin_action.setText("进行中");
			act_gather_admin_action.setTextColor(Color.parseColor("#0000ff"));
		} else if (mGatherBean.getGatherFlag() == 3) { // 如果当前时间大于活动结束时间 就显示已结束
			// gatherFlag = 3;
			act_gather_admin_action.setText("已结束");
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
	 * 组件的事件处理
	 */
	@Override
	public void initViewOper() {
		dd = new DingDan();
		user = HappyApplication.getmHappyApplication().getUb();
		/**
		 * 如果活动的用户发起人里面包含，此用户id，就证明是此人发起的活动，，反之就是参与者进入了本活动
		 */
		// 当说明是管理这进入了这个活动
		if (mGatherBean.getGatherUserId().contains(user.getObjectId())) {
			act_gather_admin_guanli.setText("管理");
			// 当说明是管理这进入了这个活动
			act_gather_admin_guanli.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					HappyApplication.getmHappyApplication().setHash(
							"gatherAdmindelete", mGatherBean);
					startActivity(new Intent(getAct(), UserGatherAdmin.class));
				}
			});
		} else {
			// 说明是参与者进入了这个活动
			// if (mGatherBean.getGatherFlag() == 1) {
			if (mGatherBean.getPaymentUserId().contains(user.getObjectId())) {
				act_gather_admin_guanli.setText("查看");
				act_gather_admin_guanli.setOnClickListener(onclic);
			} else {
				act_gather_admin_guanli.setText("参与");
				act_gather_admin_guanli
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								builder.show();
							}
						});
			}
			// } else if (mGatherBean.getGatherFlag() == 2) {
			// act_gather_admin_guanli.setText("进行中");
			// toastS("活动进行中，不可中途参加.") ;
			// } else if (mGatherBean.getGatherFlag() == 3) {
			// act_gather_admin_guanli.setText("已结束");
			// toastS("活动已结束，不可中途参加.") ;
			// }
			// Calendar c = Calendar.getInstance();
			// final String year = c.get(Calendar.YEAR) + "";
			// final String mouth = c.get(Calendar.MONTH + 1) + "";
			// final String day = c.get(Calendar.DAY_OF_WEEK) + "";
			// 调用支付的接口
			pay = new PayListener() {
				private String orderId = null;

				/**
				 * 未知原因 导致订单不能正常支付
				 */
				@Override
				public void unknow() {
					toastS("未知原因支付失败");
				}

				/**
				 * 支付成功的时候
				 */
				@Override
				public void succeed() {
					toastS("支付成功");
					act_gather_admin_guanli.setText("查看");
					act_gather_admin_guanli.setOnClickListener(onclic);
					// 向用户表中添加信息
					user.getCanjiaGatherId().add(mGatherBean.getObjectId());
					user.getCanjiaGatherName().add(mGatherBean.getGatherName());
					user.getOrderIds().add(orderId);
					// 创建一个新的订单表
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
					// 向用户表中添加数据
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
				 * @param 支付成功时
				 *            ，会先执行本方法 产生订单号
				 */
				@Override
				public void orderId(String arg0) {
					this.orderId = arg0;

				}

				@Override
				public void fail(int arg0, String arg1) {
					if (arg0 == -3) {
						// 当错误提示为-3时 代表为安装支付插件 帮组用户安装支付插件
						toastS("未安装微信支付插件");
						new AlertDialog.Builder(getAct())
								.setMessage(
										"监测到你尚未安装支付插件,无法进行微信支付,请安装插件(已打包在本地,无流量消耗)")
								.setPositiveButton("安装",
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
						toastS("未安装微信客户端");
						return;
					}
				}
			};

		}
		// 管理者点击管理的响应事件
		// act_gather_admin_guanli.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// HappyApplication.getmHappyApplication().setHash(
		// "gatherAdmindelete", mGatherBean);
		// startActivity(new Intent(getAct(), UserGatherAdmin.class));
		// }
		// });

		// 进入讨论区的点击事件
		act_gather_admin_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HappyApplication.getmHappyApplication().setHash(
						"gatherComment", mGatherBean);
				startActivity(new Intent(getAct(), GatherComment.class));
			}
		});
		// 退出当前界面
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
		// 设置活动的参加金额
		BmobQuery<GatherBean> query = new BmobQuery<GatherBean>();
		query.addWhereEqualTo("objectId", mGatherBean.getObjectId());
		query.findObjects(getAct(), new FindListener<GatherBean>() {

			@Override
			public void onSuccess(List<GatherBean> arg0) {
				mGatherBean = arg0.get(0);
				// 设置活动的参加金额
				if (act_gather_admin_guanli.getText().toString().trim()
						.equals("查看")) {
					act_gather_admin_tv1.setText("已支付");
				} else {
					act_gather_admin_tv1.setText(mGatherBean.getGatherRMB());
				}
				// 参与活动的人数
				act_gather_admin_tv2.setText(mGatherBean.getPaymentUserId()
						.size() + "");
				// 设置当前点赞的人数
				act_gather_admin_tv3.setText(mGatherBean.getPraiseUserscount()
						+ "");
				// 评论的次数
				act_gather_admin_tv4.setText(mGatherBean.getPingluns() + "");

				if (mGatherBean.getGatherFlag() == 1) {
					// gatherFlag = 1;
					act_gather_admin_action.setText("未进行");
				} else if (mGatherBean.getGatherFlag() == 2) { // 如果当前时间处于活动开始时间和活动结束时间之间就显示进行中
					// gatherFlag = 2;
					act_gather_admin_action.setText("进行中");
					act_gather_admin_action.setTextColor(Color
							.parseColor("#0000ff"));
					if (mGatherBean.getPaymentUserName().contains(
							user.getUsername())
							&& !mGatherBean.getStartUserName().contains(
									user.getUsername())) {
						AlertDialog.Builder builder = new Builder(getAct());
						builder.setMessage("活动已开启,您有到达现场吗?");
						builder.setPositiveButton("我在现场",
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
														toastS("确认成功,祝您玩的愉快.");
													}

													@Override
													public void onFailure(
															int arg0,
															String arg1) {

													}
												});
									}
								});
						builder.setNegativeButton("不在现场",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										toastS("活动已经开启,请赶紧赶到现场.");
									}
								});
						builder.show();
					}

				} else if (mGatherBean.getGatherFlag() == 3) { // 如果当前时间大于活动结束时间
																// 就显示已结束
					// gatherFlag = 3;
					act_gather_admin_action.setText("已结束");
					act_gather_admin_action.setTextColor(Color
							.parseColor("#b2b2b2"));
					act_gather_admin_action.getPaint().setFlags(
							Paint.STRIKE_THRU_TEXT_FLAG);
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				toastS("获取活动详情失败,请检查网络.");
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null);
	}
}
