package com.ccw.happy.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.baidu.mapapi.search.core.PoiInfo;
import com.ccw.happy.R;
import com.ccw.happy.adapter.SendGetherAdapter;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.utils.ImageYaSuoUtils;
import com.ccw.happy.vo.GatherBean;
import com.ccw.happy.vo.MoreBean;
import com.ccw.happy.vo.UserBean;
/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-12����9:12:39
 * @auther: �������������������   �ڴ����п����ϴ�ͼƬ�ʹ򿪵�ͼ�Ĺ���
 */
public class SendGetherActivity extends BaseActivity implements BaseInterface {
	private ImageView act_send_gether_img,act_send_gethr_map ;
	private TextView mTextView_city;
	private TextView act_send_gether_ok ,act_send_gather_time ;
	private EditText act_send_gather_name,act_send_gather_content ,act_send_gather_money;
	private Spinner act_send_gether_spinner ;
	private PoiInfo poi;
	//ά��һ����Ķ���
	private GatherBean mGatherBean;
	//ά��һ��back��dialog
	private AlertDialog.Builder mBuilder ;
	//ά��һ��ʱ����� 
	private Calendar c ;
	private int myear , mouth ,day ;
	//��Spinner���������
	private List<MoreBean> lists ;
	//������
	private SendGetherAdapter adapter ;
	//ȷ�Ϸ���֮ǰ�ĺ�ʱ����
	private ProgressDialog mProgressDialog ;
	//ά��һ����ǰ�û�����
	private UserBean user ;

	String[] moreNames = { "�ܱ߻���", "�ٶ���Ȥ", "DIY", "�����˶�", "����/����", "�ݳ�",
			"չ��/չ��", "ɳ������", "Ʒ��/��ʳ", "���˾ۻ�" };
	int[] moreImgIds = { R.drawable.sort_zhoubian, R.drawable.sort_shaoer,
			R.drawable.sort_diy, R.drawable.sort_yundong,
			R.drawable.sort_jieri, R.drawable.sort_yanchu ,
			R.drawable.sort_zhanlan,R.drawable.sort_jiangzuo,R.drawable.sort_meishi,R.drawable.sort_jucan};

	private BmobFile gatherbf;
	private  int index;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setAct(this) ;
		setContentView(R.layout.act_send_gether) ;
		initViews() ;
		initDatas() ;
		initViewOper() ;
	}
	/**
	 * ��ʼ�����
	 */
	@Override
	public void initViews() {
		act_send_gether_img = imgById(R.id.act_send_gether_img) ;
		act_send_gethr_map = imgById(R.id.act_send_gether_map) ;
		mTextView_city = tvById(R.id.act_send_gether_tv_city) ;
		act_send_gether_ok = tvById(R.id.act_send_gether_ok) ;
		act_send_gather_time = tvById(R.id.act_send_gather_time);
		act_send_gather_name = etById(R.id.act_send_gather_name) ;
		act_send_gather_content = etById(R.id.act_send_gather_content) ;
		act_send_gather_money = etById(R.id.act_send_gather_money) ;
		act_send_gether_spinner = (Spinner) findViewById(R.id.act_send_gether_spinner) ;
	}

	@Override
	public void initDatas() {

		user = HappyApplication.getmHappyApplication().getUb() ;
		if(user == null){
			toastS("����δ��¼,���¼.") ;
			startActivity(new Intent(getAct(),LoginActivity.class)) ;
			finish() ;
			BaseActivity act = (BaseActivity) HappyApplication.getmHappyApplication().getHash("activity") ;
			act.finish() ;
		}

		c = Calendar.getInstance() ;
		//һ��ʼ��Ĭ����ʾ��ǰ������
		myear = c.get(Calendar.YEAR) ;
		mouth = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DAY_OF_MONTH) ; 
		if((mouth+"").length()==1){
			act_send_gather_time.setText(myear+"-0"+mouth+"-"+day) ;
		}else if((day+"").length()==1){
			act_send_gather_time.setText(myear+"-"+mouth+"-0"+day) ;
		}else if((mouth+"").length()==1&&(day+"").length()==1){
			act_send_gather_time.setText(myear+"-0"+mouth+"-0"+day) ;
		}else{
			//���õ����ʱ��
			act_send_gather_time.setText(myear+"-"+mouth+"-"+day) ;
		}
		//���÷����֮ǰ�ĺ�ʱ����
		mProgressDialog = new ProgressDialog(this) ;
		mProgressDialog.setCancelable(false) ;
		//��spinner���������
		lists = new ArrayList<MoreBean>() ;
		for (int i = 0; i < 10; i++) {
			lists.add(new MoreBean(moreImgIds[i], moreNames[i])) ;
		}
		adapter = new SendGetherAdapter(lists);
		act_send_gether_spinner.setAdapter(adapter) ;
		//�˳�֮ǰ���û�ȷ�Ͽ�
		mBuilder = new Builder(getAct()) ;
		mBuilder.setTitle("����") ;
		mBuilder.setMessage("��Ҫ�˳���ǰ�༭��?") ;
		mBuilder.setCancelable(false) ;
		mBuilder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish() ;
			}
		}) ;
		mBuilder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		}) ;
	}
	/**
	 * ������������Ӧ�¼�
	 */
	@Override
	public void initViewOper() {
		//�����������ť��ʱ��
		act_send_gether_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String gatherName = act_send_gather_name.getText().toString().trim() ;
				String gatherTime = act_send_gather_time.getText().toString().trim() ;
				String gatherContent = act_send_gather_content.getText().toString().trim() ;
				String gatherCity = mTextView_city.getText().toString().trim() ;
				String gatherMoney = act_send_gather_money.getText().toString().trim() ;
				String gatherType = moreNames[index] ;
				
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss() ;
				}
				if(null==gatherName||"".equals(gatherName)){
					toastS("����Ʋ���Ϊ��.") ;
					return ;
				}else if(null==gatherTime||"".equals(gatherTime)){
					toastS("�ʱ�䲻��Ϊ��.") ;
					return ;
				}else if(null == gatherContent||"".equals(gatherContent)){
					toastS("����ݲ���Ϊ��.") ;
					return ;
				}else if(null == gatherCity||"".equals(gatherCity)){
					toastS("����в���Ϊ��.") ;
					return ;
				}else if(null == gatherMoney||"".equals(gatherMoney)){
					toastS("�����С�� 1") ;
					return ;
				}else if(null == gatherType||"".equals(gatherType)){
					toastS("����Ͳ���Ϊ��.") ;
					return ;
				}else if(null == gatherbf){
					toastS("�ͼƬ��δѡ.") ;
					return ;
				}else{
					mProgressDialog.show() ;
					mGatherBean.setGatherUserId(user.getObjectId()) ;
					mGatherBean.setGatherName(gatherName) ;
					mGatherBean.setGatherTime(gatherTime) ;
					mGatherBean.setGatherIntro(gatherContent) ;
					mGatherBean.setGatherCity(gatherCity) ;
					mGatherBean.setGatherSite(poi.address) ;
					mGatherBean.setGatherClass(gatherType) ;
					mGatherBean.setGatherRMB(gatherMoney) ;
					mGatherBean.setGatherIcon(user.getUserIcon()) ;
					mGatherBean.setGatherJPG(gatherbf) ;
					mGatherBean.setGatherFlag(1) ;
					BmobGeoPoint point = new BmobGeoPoint(poi.location.longitude, poi.location.latitude);
					mGatherBean.setGpsAdd(point);
					mGatherBean.save(getAct(), new SaveListener() {

						@Override
						public void onSuccess() {

							toastS("������ɹ�.") ;
							user.getmGatherId().add(mGatherBean.getObjectId()) ;
							UserBean ub = new UserBean() ;
							ub.addUnique("mGatherId", mGatherBean.getObjectId()); 
							ub.update(getAct(), user.getObjectId(),new UpdateListener() {

								@Override
								public void onSuccess() {
									if(mProgressDialog.isShowing()){
										mProgressDialog.dismiss() ;
									}
									finish() ;
								}

								@Override
								public void onFailure(int arg0, String arg1) {
									if(mProgressDialog.isShowing()){
										mProgressDialog.dismiss() ;
									}
									if(getAct()==null){
										return ;
									}
									toastS("�����ʧ��.") ;
								}
							}) ;
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							if(mProgressDialog.isShowing()){
								mProgressDialog.dismiss() ;
							}
							if(getAct()==null){
								return ;
							}
							toastS("�����ʧ��.") ;
						}
					}) ;
				}
			}
		}) ;
		//ѡȡ����͵�ʱ��
		act_send_gether_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				index = position ;
			}  

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		}) ;
		//�������ʱ���ʱ��
		act_send_gather_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DatePickerDialog(getAct(), new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						myear = year ;
						mouth  = monthOfYear  + 1;
						day = dayOfMonth ;
						if((mouth+"").length()==1){
							act_send_gather_time.setText(myear+"-0"+mouth+"-"+day) ;
						}else if((day+"").length()==1){
							act_send_gather_time.setText(myear+"-"+mouth+"-0"+day) ;
						}else if((mouth+"").length()==1&&(day+"").length()==1){
							act_send_gather_time.setText(myear+"-0"+mouth+"-0"+day) ;
						}else{
							//���õ����ʱ��
							act_send_gather_time.setText(myear+"-"+mouth+"-"+day) ;
						}
					}
				}, myear, mouth - 1, day).show() ;
			}
		}) ;
		//�����ѡ��ص�ʱ
		act_send_gethr_map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getAct(),MapActivity.class)) ;
			}
		}) ;

		//������ϴ�ͼƬʱ
		act_send_gether_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

				intent.putExtra("return-data", true);
				intent.setType("image/*");
				// ����
				intent.putExtra("crop", "circleCrop");
				// �ü�����
				intent.putExtra("aspectX", 3);
				intent.putExtra("aspectY", 2);
				// �ü���С
				intent.putExtra("outputX", 360);
				intent.putExtra("outputY", 240);
				startActivityForResult(intent, 1) ;
			}
		}) ;
	}
	/**
	 * �����ѡ��ͼƬ�Ļص�����
	 */
	@Override
	protected void onActivityResult(int request, int respones, Intent data) {
		super.onActivityResult(request, respones, data);
		if(request==1){
			if(null!=data&&data.getExtras()!=null&&data.getExtras().get("data")!=null){
				Bitmap gatherBitmap = (Bitmap) data.getExtras().get("data") ;
				//ѹ��ͼƬ
				gatherBitmap = ImageYaSuoUtils.getBitmap50k(gatherBitmap, 100) ;
				//2����ͼƬ�����ڱ���
				File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),"Android/data/gather") ;
				if(!file.exists()){
					file.mkdirs() ;
				}
				File newfile = new File(file.getAbsolutePath()+"/gether.JPEG") ; 
				if(newfile.exists()){
					newfile.delete() ;
				}
				OutputStream out = null;
				try {
					out = new FileOutputStream(newfile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				gatherBitmap.compress(CompressFormat.JPEG, 100, out) ;
				gatherbf = new BmobFile(newfile) ;
				//				�ϴ�������
				gatherbf.uploadblock(getAct(), new UploadFileListener() {

					@Override
					public void onSuccess() {
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						if(getAct()==null){
							return ;
						}
						toastS("ͼƬ�ϴ�ʧ����.") ;
						act_send_gether_img.setImageResource(R.drawable.highlighted) ;
					}
				}) ;
				act_send_gether_img.setImageBitmap(gatherBitmap) ;
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		mGatherBean = new GatherBean() ; 
		poi = (PoiInfo) HappyApplication.getmHappyApplication().getHash("mapdelete") ;
		if(poi!=null){
			mGatherBean.setLocation(poi) ;
			mTextView_city.setText(poi.name) ;
		}
	}
	@Override
	public void onBackPressed() {
		mBuilder.show();
	}
}
