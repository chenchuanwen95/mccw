package com.ccw.happy.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.ccw.happy.R;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.utils.IntenetUtils;
import com.ccw.happy.utils.NotificationUtils;
import com.ccw.happy.vo.GatherBean;
import com.ccw.happy.vo.NotificationBean;
import com.google.gson.Gson;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-21下午4:12:20
 * @auther: 本类中是用来发起活动的人来管理活动的
 */
public class UserGatherAdmin extends BaseActivity implements BaseInterface {
	private ImageView act_user_admin_gobye;
	private TextView act_user_admin_guanli, act_user_admin_ren,
			act_user_admin_yuan;
	private ListView act_user_admin_listview;
	private RadioGroup act_user_admin_radio;
	private RadioButton[] radio = new RadioButton[3];
	private EditText act_user_admin_edit;
	private Button act_user_admin_send;
	// 维护一个活动对象
	private GatherBean gather;
	// 维护一个全部的用户
	private List<String> persons;
	// 维护一个到现场的用户
	private List<String> startPerson;
	// 维护一个消息发送的用户集合
	private List<String> smsPerson;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setAct(this);
		setContentView(R.layout.act_user_admin);
		initViews();
		initDatas();
		initViewOper();
	}

	@Override
	public void initViews() {
		act_user_admin_gobye = imgById(R.id.act_user_admin_gobye);
		act_user_admin_guanli = tvById(R.id.act_user_admin_guanli);
		act_user_admin_ren = tvById(R.id.act_user_admin_ren);
		act_user_admin_yuan = tvById(R.id.act_user_admin_yuan);
		act_user_admin_listview = (ListView) findViewById(R.id.act_user_admin_listview);
		act_user_admin_radio = (RadioGroup) findViewById(R.id.act_user_admin_radio);
		act_user_admin_edit = etById(R.id.act_user_admin_edit);
		act_user_admin_send = btnById(R.id.act_user_admin_send);
		radio[0] = (RadioButton) findViewById(R.id.act_user_admin_radio1);
		radio[1] = (RadioButton) findViewById(R.id.act_user_admin_radio2);
		radio[2] = (RadioButton) findViewById(R.id.act_user_admin_radio3);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// BmobQuery<GatherBean> query = new BmobQuery<GatherBean>();
		// query.addWhereEqualTo("objectId", gather.getObjectId()) ;
		// query.findObjects(getAct(), new FindListener<GatherBean>() {
		//
		// @Override
		// public void onSuccess(List<GatherBean> arg0) {
		// gather = arg0.get(0) ;
		// }
		//
		// @Override
		// public void onError(int arg0, String arg1) {
		//
		// }
		// }) ;
	}

	@Override
	public void initDatas() {
		gather = (GatherBean) HappyApplication.getmHappyApplication().getHash(
				"gatherAdmindelete");
		// 这个是参与活动的总人数
		act_user_admin_ren.setText(gather.getPaymentUserName().size() + "");
		act_user_admin_yuan
				.setText((Integer.parseInt(gather.getGatherRMB()) * gather
						.getPaymentUserName().size()) + "");
		// 全部的用户
		persons = gather.getPaymentUserName();
		// 获取到已到的用户信息
		startPerson = gather.getStartUserName();
		// 获取一下未到用户的信息
		smsPerson = new ArrayList<String>();
		smsPerson.addAll(persons);
		smsPerson.removeAll(startPerson);
		// 把数据添加进listView
		act_user_admin_listview.setAdapter(new ArrayAdapter<String>(getAct(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				persons));
		// 设置是否开启活动
		if (gather.getGatherFlag() == 1) {
			act_user_admin_guanli.setText("开启");
			act_user_admin_guanli.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new Builder(getAct());
					builder.setMessage("是否开启活动?");
					builder.setPositiveButton("现在开启",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									gather.setGatherFlag(2);
									gather.increment("gatherFlag");
									gather.update(getAct(),
											new UpdateListener() {

												@Override
												public void onSuccess() {
													act_user_admin_guanli
															.setText("结束");

												}

												@Override
												public void onFailure(int arg0,
														String arg1) {

												}
											});
								}
							});
					builder.setNegativeButton("稍等一会",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
					builder.show();
				}
			});

		} else if (gather.getGatherFlag() == 2) {
			act_user_admin_guanli.setText("结束");
			act_user_admin_guanli.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new Builder(getAct());
					builder.setMessage("是否结束活动?");
					builder.setPositiveButton("现在结束",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									gather.setGatherFlag(3);
									gather.increment("gatherFlag");
									gather.update(getAct(),
											new UpdateListener() {

												@Override
												public void onSuccess() {
													act_user_admin_guanli
															.setText("已结束");
												}

												@Override
												public void onFailure(int arg0,
														String arg1) {

												}
											});
								}
							});
					builder.setNegativeButton("稍等一会",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
					builder.show();
				}
			});

		} else if (gather.getGatherFlag() == 3) {
			act_user_admin_guanli.setText("已结束");
			act_user_admin_guanli.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					toastS("活动已经结束。");
				}
			});
		}

	}

	@Override
	public void initViewOper() {
		// 点击发送消息时的响应事件
		act_user_admin_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String text = getViewText(act_user_admin_edit);
				if (IntenetUtils.isNet(getAct())) {
					if (!"".equals(text)) {
						// 开始推送消息
						act_user_admin_edit.setText("");
						NotificationBean bean = new NotificationBean();
						bean.setSendSms(text);
						bean.setGatherId(gather.getObjectId());
						bean.setGatherName(gather.getGatherName());
						NotificationUtils.push(bean, getAct(), smsPerson);
					} else {
						toastS("请输入推送内容.");
					}

				} else {
					toastS("发送失败，请检查网络.");
				}
			}
		});
		// 后退键的响应事件
		act_user_admin_gobye.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 这里是RadioGroup的响应事件
		act_user_admin_radio
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.act_user_admin_radio1:
							setRadioButtonColor(0);
							smsPerson = persons;
							act_user_admin_edit.setHint("推送给全部人员");
							break;
						case R.id.act_user_admin_radio2:
							setRadioButtonColor(1);
							act_user_admin_edit.setHint("推送给已到人员");
							smsPerson = startPerson;
							break;
						case R.id.act_user_admin_radio3:
							setRadioButtonColor(2);
							act_user_admin_edit.setHint("推送给未到人员");
							smsPerson = new ArrayList<String>();
							smsPerson.addAll(persons);
							smsPerson.removeAll(startPerson);
							break;
						}
						if (IntenetUtils.isNet(getAct())) {
							act_user_admin_listview
									.setAdapter(new ArrayAdapter<String>(
											getAct(),
											android.R.layout.simple_list_item_1,
											android.R.id.text1, smsPerson));

						} else {
							toastS("查询失败，请检查网络.");
						}
					}
				});
	}

	protected void setRadioButtonColor(int i) {
		for (int j = 0; j < 3; j++) {
			if (i == j) {
				radio[i].setTextColor(Color.parseColor("#F6D357"));
			} else {
				radio[j].setTextColor(Color.parseColor("#000000"));
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null);
	}
}
