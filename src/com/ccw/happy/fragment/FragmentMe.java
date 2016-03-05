package com.ccw.happy.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.ccw.happy.R;
import com.ccw.happy.activity.GatherDetailted;
import com.ccw.happy.activity.HomeActivity;
import com.ccw.happy.activity.LoginActivity;
import com.ccw.happy.adapter.GatherAdapter;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseFragment;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.utils.ImageLoaderutils;
import com.ccw.happy.utils.ImageYaSuoUtils;
import com.ccw.happy.view.MyImageView;
import com.ccw.happy.vo.GatherBean;
import com.ccw.happy.vo.UserBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-11����11:36:03
 * @auther: ����Ǹ������ĵĽ���
 */
public class FragmentMe extends BaseFragment implements BaseInterface {
	private static final int REQUESTCODE1 = 1;
	private static final int REQUESTCODE2 = 2;
	// ά��һ��UserBean����
	private UserBean user;
	private TextView fragment_gobye, fragment_lognout, fragment_me_username,
			fragment_me_textview_time ,fragment_me_sendgather;
	private TextView fragment_me_tv_faqihuodong,fragment_me_textview_youhui, fragment_me_textview_shoucang,fragment_me_tv_dingdan;
	private ImageView fragment_me_imageview_time;
	private MyImageView fragment_img_head;
	private ListView fragment_me_lv ;
	private LinearLayout fragment_me_ll ;
	private BaseActivity act;
	private AlertDialog.Builder mAlertDialog_gobye;
	private AlertDialog.Builder mAlertDialog_lognout;
	private AlertDialog.Builder mAlertDialog_head;
	// �������ػ����õ����ص��û�ͷ��
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;
	// ����ʱ�����
	private Calendar c;
	private int mYear, mMonth, mDay;
	private DatePickerDialog dpd;
	private GatherAdapter adapter ;
	private List<GatherBean> lists ;
	private OnItemClickListener onclick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			HappyApplication.getmHappyApplication().setHash("gatherpositiondelete", lists.get(position)) ;
			startActivity(new Intent(act, GatherDetailted.class)) ;
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_me, null);
	}

	@Override
	public void onAttach(Activity activity) {
		act = (BaseActivity) activity;
		super.onAttach(activity);
	}

	@Override
	public void onStart() {
		super.onStart();
		// ��ʾ�û������Ĵ���
		fragment_me_tv_faqihuodong.setText(user.getmGatherId().size() + "");
		fragment_me_textview_shoucang.setText(user.getLoveGatherId().size()
				+ "");
		fragment_me_tv_dingdan.setText(user.getCanjiaGatherId().size()+"") ;
		fragment_me_textview_youhui.setText(user.getYouhui()+""); 
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initViews();
		initDatas();
		initViewOper();
	}

	@Override
	public void initViews() {
		fragment_gobye = tvById(R.id.fragment_me_gobye);
		fragment_lognout = tvById(R.id.fragment_me_lognout);
		fragment_me_username = tvById(R.id.fragment_me_username);
		fragment_img_head = (MyImageView) findViewById(R.id.fragment_me_head);
		fragment_me_tv_faqihuodong = tvById(R.id.fragment_me_tv_faqihuodong);
		fragment_me_imageview_time = imgById(R.id.fragment_me_imageview_time);
		fragment_me_textview_time = tvById(R.id.fragment_me_textview_time);
		fragment_me_textview_shoucang = tvById(R.id.fragment_me_textview_shoucang);
		fragment_me_sendgather = tvById(R.id.fragment_me_sendgather) ;
		fragment_me_tv_dingdan = tvById(R.id.fragment_me_tv_dingdan) ;
		fragment_me_textview_youhui = tvById(R.id.fragment_me_textview_youhui) ;
		fragment_me_lv = (ListView) findViewById(R.id.fragment_me_lv) ;
		fragment_me_ll = (LinearLayout) findViewById(R.id.fragment_me_ll) ;
	}

	/**
	 * �����ݵĲ���
	 */
	@Override
	public void initDatas() {
		user = HappyApplication.getmHappyApplication().getUb();

		BmobQuery<UserBean> query = new BmobQuery<UserBean>();
		query.addWhereEqualTo("objectId", user.getObjectId()) ;
		query.findObjects(act, new FindListener<UserBean>() {
			
			@Override
			public void onSuccess(List<UserBean> arg0) {
				user = arg0.get(0) ;
				HappyApplication.getmHappyApplication().setUb(user) ;
				// ��ʾ�û������Ĵ���
				fragment_me_tv_faqihuodong.setText(user.getmGatherId().size() + "");
				fragment_me_textview_shoucang.setText(user.getLoveGatherId().size()
						+ "");
				fragment_me_tv_dingdan.setText(user.getCanjiaGatherId().size()+"") ;
				fragment_me_textview_youhui.setText(user.getYouhui()+"");
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				
			}
		}) ;
		/**
		 * ����������ͷ���Լ����浽���غ��ڴ�Ĳ���
		 */
		String url = null;
		if (user.getUserIcon() != null) {
			// ���õ��û�ͷ���ڷ����������url
			url = user.getUserIcon().getFileUrl(act);
			// ������ص�url��Ϊ�յ�ʱ��͸���ImageLoaderUtils������������������
			if (url != null) {
				mImageLoader = ImageLoaderutils.getInstance(act);
				options = ImageLoaderutils.getOpt();
			}
			mImageLoader.displayImage(url, fragment_img_head, options);
		}
		// ���û���¼����ʱ�Ͱ��û�����ʾ����
		fragment_me_username.setText(user.getUsername());
		// ����ͷ��ĶԻ���
		mAlertDialog_head = new Builder(act);
		mAlertDialog_head.setMessage("ѡ��ͼƬ��Դ��");
		mAlertDialog_head.setPositiveButton("ȥ����",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivityForResult(new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE), REQUESTCODE2);
					}
				});
		/**
		 * ����ͷ��ĶԻ���
		 */
		mAlertDialog_head.setNegativeButton("ȥ���",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

						intent.putExtra("return-data", true);
						intent.setType("image/*");
						// ����
						intent.putExtra("crop", "circleCrop");
						// �ü�����
						intent.putExtra("aspectX", 1);
						intent.putExtra("aspectY", 1);
						// �ü���С
						intent.putExtra("outputX", 240);
						intent.putExtra("outputY", 240);
						startActivityForResult(intent, REQUESTCODE1);
					}
				});
		// �˳���ȷ�϶Ի���
		mAlertDialog_gobye = new Builder(act);
		mAlertDialog_gobye.setMessage("�´μ���");
		mAlertDialog_gobye.setCancelable(false);
		mAlertDialog_gobye.setPositiveButton("�õ�",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(act==null){
							return ;
						}
						act.finish();
					}
				});
		mAlertDialog_gobye.setNegativeButton("����",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		// ע����ȷ�϶Ի���
		mAlertDialog_lognout = new Builder(act);
		mAlertDialog_lognout.setMessage("�����˺ţ�");
		mAlertDialog_lognout.setCancelable(false);
		mAlertDialog_lognout.setPositiveButton("���ڻ�",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(act==null){
							return ;
						}
						// ������ػ���
						BmobUser.logOut(act);
						HappyApplication.getmHappyApplication().setUb(null);
						startActivity(new Intent(act, LoginActivity.class));
						act.finish();
					}
				});
		mAlertDialog_lognout.setNegativeButton("�´���˵",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		// ���ó�ʼʱ��
		c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH) + 1;
		mDay = c.get(Calendar.DAY_OF_MONTH);
		fragment_me_textview_time.setText(mYear + "-" + mMonth + "-" + mDay);
		// ѡ��ʱ�� �ĶԻ���
		dpd = new DatePickerDialog(act, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				fragment_me_textview_time.setText(year + "-"
						+ (monthOfYear + 1) + "-" + dayOfMonth);
				BmobQuery<GatherBean> query = new BmobQuery<GatherBean>() ;
				query.addWhereEqualTo("gatherTime", fragment_me_textview_time.getText().toString().trim()) ;
				query.findObjects(act, new FindListener<GatherBean>() {
					
					@Override
					public void onSuccess(List<GatherBean> arg0) {
						lists = arg0 ;
						if(arg0!=null&&arg0.size()>0){
							fragment_me_ll.setVisibility(View.INVISIBLE) ;
							fragment_me_lv.setVisibility(View.VISIBLE) ;
							adapter = new GatherAdapter(arg0, act) ;
							fragment_me_lv.setAdapter(adapter) ;
						}else{
							fragment_me_ll.setVisibility(View.VISIBLE) ;
							fragment_me_lv.setVisibility(View.INVISIBLE) ;
						}
						
					}
					
					@Override
					public void onError(int arg0, String arg1) {
						fragment_me_ll.setVisibility(View.VISIBLE) ;
						fragment_me_lv.setVisibility(View.INVISIBLE) ;
					}
				}) ;
				
				fragment_me_lv.setOnItemClickListener(onclick) ;
			}
		}, mYear, mMonth - 1, mDay);
	}

	/**
	 * ��Ҫ���˳���ע����textview������¼�
	 */
	@Override
	public void initViewOper() {
		//����������¼�
		fragment_me_sendgather.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(act, HomeActivity.class) ;
				startActivity(intent) ;
				act.finish() ;
			}
		}) ;
		// ѡ��ʱ�� �ĵ���¼�
		fragment_me_imageview_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dpd.show();
			}
		});
		// ����ͷ��
		fragment_img_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		// ������˳����¼�
		fragment_gobye.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ���˳���ȷ����ʾ�򵯳���
				mAlertDialog_gobye.show();
			}
		});
		// �����ע�����¼�
		fragment_lognout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ע����ȷ����ʾ�򵯳���
				mAlertDialog_lognout.show();
			}
		});
		// ���û����ͷ��ʱ�����ĶԻ���
		fragment_img_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mAlertDialog_head.show();
			}
		});
	}

	/**
	 * ������Ļص�����
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE1 || requestCode == REQUESTCODE2) {
			if (null != data) {
				Bitmap headBitmap = (Bitmap) data.getExtras().get("data");
				// 1��ѹ��ͼƬ����ð�ͼƬ������50k����
				headBitmap = ImageYaSuoUtils.getBitmap50k(headBitmap, 50);
				// 2����ͼƬ�����ڱ���
				File file = new File(Environment.getExternalStorageDirectory()
						.getAbsoluteFile(), "Android/data/gather");
				if (!file.exists()) {
					file.mkdirs();
				}
				File newfile = new File(file.getAbsolutePath()
						+ "/userIcon.JPEG");
				if (newfile.exists()) {
					newfile.delete();
				}
				OutputStream out = null;
				try {
					out = new FileOutputStream(newfile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				headBitmap.compress(CompressFormat.JPEG, 100, out);
				// 3����ͼƬ�ϴ���Bmob������
				final BmobFile bf = new BmobFile(newfile);
				bf.uploadblock(act, new UploadFileListener() {

					@Override
					public void onSuccess() {
						// UserBean ub =
						// HappyApplication.getmHappyApplication().getUb() ;
						UserBean ub = new UserBean();
						// �ϴ��û�ͷ���ʱ��һ��Ҫ����һ���µ�UserBean����
						// ���µĶ���ȥ�޸ľɵ�UserBean����
						// ����������в����ڸ��ֶ� ���Զ�����һ���µ��ֶ�
						// ����µĶ����д��ڲ���ԭ�����е��ֶ� �ͻ�ȥ���µ��ֶ�׷�ӵ��ɵķ�����������
						ub.setUserIcon(bf);
						ub.update(act, user.getObjectId(),
								new UpdateListener() {

									@Override
									public void onSuccess() {
										if(act==null){
											return ;
										}
									}

									@Override
									public void onFailure(int arg0, String arg1) {
										if(act==null){
											return ;
										}
									}
								});
						user.setUserIcon(bf);
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						if(act==null){
							return ;
						}
					}
				});
				// 4����ͼƬ��ʾ��ImageView�������
				fragment_img_head.setImageBitmap(headBitmap);
				user.setUserIcon(bf);
			}
		}
	}
}
