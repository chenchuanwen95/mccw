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
 * @作者: 陈传稳
 * @时间: 2015-11-11上午11:36:03
 * @auther: 这个是个人中心的界面
 */
public class FragmentMe extends BaseFragment implements BaseInterface {
	private static final int REQUESTCODE1 = 1;
	private static final int REQUESTCODE2 = 2;
	// 维护一个UserBean对象
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
	// 用来下载或者拿到本地的用户头像
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;
	// 设置时间的类
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
		// 显示用户发起活动的次数
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
	 * 对数据的操作
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
				// 显示用户发起活动的次数
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
		 * 这是在下载头像以及缓存到本地和内存的操作
		 */
		String url = null;
		if (user.getUserIcon() != null) {
			// 先拿到用户头像在服务器上面的url
			url = user.getUserIcon().getFileUrl(act);
			// 如果返回的url不为空的时候就根据ImageLoaderUtils工具类来下载数据了
			if (url != null) {
				mImageLoader = ImageLoaderutils.getInstance(act);
				options = ImageLoaderutils.getOpt();
			}
			mImageLoader.displayImage(url, fragment_img_head, options);
		}
		// 当用户登录进来时就把用户名显示出来
		fragment_me_username.setText(user.getUsername());
		// 更换头像的对话框
		mAlertDialog_head = new Builder(act);
		mAlertDialog_head.setMessage("选择图片来源：");
		mAlertDialog_head.setPositiveButton("去拍照",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivityForResult(new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE), REQUESTCODE2);
					}
				});
		/**
		 * 更换头像的对话框
		 */
		mAlertDialog_head.setNegativeButton("去相册",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

						intent.putExtra("return-data", true);
						intent.setType("image/*");
						// 剪裁
						intent.putExtra("crop", "circleCrop");
						// 裁剪比例
						intent.putExtra("aspectX", 1);
						intent.putExtra("aspectY", 1);
						// 裁剪大小
						intent.putExtra("outputX", 240);
						intent.putExtra("outputY", 240);
						startActivityForResult(intent, REQUESTCODE1);
					}
				});
		// 退出的确认对话框
		mAlertDialog_gobye = new Builder(act);
		mAlertDialog_gobye.setMessage("下次见！");
		mAlertDialog_gobye.setCancelable(false);
		mAlertDialog_gobye.setPositiveButton("好的",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(act==null){
							return ;
						}
						act.finish();
					}
				});
		mAlertDialog_gobye.setNegativeButton("不行",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		// 注销的确认对话框
		mAlertDialog_lognout = new Builder(act);
		mAlertDialog_lognout.setMessage("换个账号？");
		mAlertDialog_lognout.setCancelable(false);
		mAlertDialog_lognout.setPositiveButton("现在换",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(act==null){
							return ;
						}
						// 清楚本地缓存
						BmobUser.logOut(act);
						HappyApplication.getmHappyApplication().setUb(null);
						startActivity(new Intent(act, LoginActivity.class));
						act.finish();
					}
				});
		mAlertDialog_lognout.setNegativeButton("下次再说",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		// 设置初始时间
		c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH) + 1;
		mDay = c.get(Calendar.DAY_OF_MONTH);
		fragment_me_textview_time.setText(mYear + "-" + mMonth + "-" + mDay);
		// 选择时间 的对话框
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
	 * 主要是退出和注销的textview的组件事件
	 */
	@Override
	public void initViewOper() {
		//点击发起活动的事件
		fragment_me_sendgather.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(act, HomeActivity.class) ;
				startActivity(intent) ;
				act.finish() ;
			}
		}) ;
		// 选择时间 的点击事件
		fragment_me_imageview_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dpd.show();
			}
		});
		// 更换头像
		fragment_img_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		// 这个是退出的事件
		fragment_gobye.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 让退出的确认提示框弹出来
				mAlertDialog_gobye.show();
			}
		});
		// 这个是注销的事件
		fragment_lognout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 让注销的确认提示框弹出来
				mAlertDialog_lognout.show();
			}
		});
		// 当用户点击头像时弹出的对话框
		fragment_img_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mAlertDialog_head.show();
			}
		});
	}

	/**
	 * 打开相册后的回调方法
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE1 || requestCode == REQUESTCODE2) {
			if (null != data) {
				Bitmap headBitmap = (Bitmap) data.getExtras().get("data");
				// 1、压缩图片，最好把图片控制在50k以内
				headBitmap = ImageYaSuoUtils.getBitmap50k(headBitmap, 50);
				// 2、把图片保存在本地
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
				// 3、把图片上传到Bmob服务器
				final BmobFile bf = new BmobFile(newfile);
				bf.uploadblock(act, new UploadFileListener() {

					@Override
					public void onSuccess() {
						// UserBean ub =
						// HappyApplication.getmHappyApplication().getUb() ;
						UserBean ub = new UserBean();
						// 上传用户头像的时候，一定要创建一个新的UserBean对象，
						// 用新的对象去修改旧的UserBean对象
						// 如果服务器中不存在该字段 会自动创建一个新的字段
						// 如果新的对象中存在不是原数据中的字段 就会去把新的字段追加到旧的服务器数据中
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
				// 4、把图片显示到ImageView组件上面
				fragment_img_head.setImageBitmap(headBitmap);
				user.setUserIcon(bf);
			}
		}
	}
}
