package com.ccw.happy.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;

import com.baidu.mapapi.model.LatLng;
import com.ccw.happy.R;
import com.ccw.happy.adapter.MyXListViewAdapter;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.utils.StringUtilsDemo;
import com.ccw.happy.vo.GatherBean;
import com.ccw.happy.vo.UserBean;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-17下午7:14:41
 * @auther: 本类中是用来做用户的各种收藏与发起的活动的
 */

public class MyLoveGatherActivity extends BaseActivity implements BaseInterface {
	// 所有调用该界面穿过来的值
	private int gatherNumber;
	// 所有组件
	private TextView mylove_gather_title,mylove_gather_text;
	private ListView mylove_gather_listview;
	private LinearLayout mylove_gather_liearlayout;
	private ImageView mylove_gather_loading,mylave_gather_gobye,mylave_gather_add;
	private UserBean user ;
	private List<GatherBean> lists ;

	private MyXListViewAdapter adapter ;
	protected String stringStr;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setAct(this);
		//给adapter设置当前的上下文环境
		HappyApplication.getmHappyApplication().setHash("activity", MyLoveGatherActivity.this) ;
		setContentView(R.layout.mylove_gather);
		initViews();
		initDatas();
		initViewOper();
	}
	/**
	 * 组件的初始化
	 */
	@Override
	public void initViews() {
		mylove_gather_title = tvById(R.id.mylove_gather_title) ;
		mylove_gather_listview = (ListView) findViewById(R.id.mylove_gather_listview) ;
		mylove_gather_liearlayout = (LinearLayout) findViewById(R.id.mylove_gather_liearlayout) ;
		mylove_gather_loading = imgById(R.id.mylove_gather_loading) ;
		mylove_gather_text = tvById(R.id.mylove_gather_text) ;
		mylave_gather_gobye = imgById(R.id.mylave_gather_gobye) ;
		mylave_gather_gobye.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyLoveGatherActivity.this.finish() ;
			}
		}) ;
		mylave_gather_add = imgById(R.id.mylave_gather_add) ;
		mylave_gather_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyLoveGatherActivity.this,HomeActivity.class) ;
				startActivity(intent) ;
				MyLoveGatherActivity.this.finish() ;
			}
		}) ;
	}
	/**
	 * 加载数据
	 */
	@Override
	public void initDatas() {
		stringStr = "正在加载中." ;
		// 获取到各个参数
		user = HappyApplication.getmHappyApplication().getUb() ;
		gatherNumber = getIntent().getIntExtra("gatherNumber", 0);
		lists = new ArrayList<GatherBean> () ;
		getGatherNumber();
	}
	/**
	 * 在本方法中进行判断用户做的是什么操作，以便给予最直接的响应
	 * 0  收藏的活动
	 * 1  发布的活动
	 * 2 从更多界面选择的活动
	 * 3 免费活动
	 * 4 热门活动
	 * 5 附近活动
	 * 6 模糊查询
	 */
	private void getGatherNumber() {
		BmobQuery<GatherBean> query = new BmobQuery<GatherBean>();
		String typeName = (String) HappyApplication.getmHappyApplication().getHash("typeName") ;
		query.order("-createdAt") ;
		imageRotate() ;
		switch (gatherNumber) {
		case 0:
			mylove_gather_title.setText("我的收藏") ;
			query.addWhereEqualTo("praiseUsers", user.getObjectId());
			stringStr = "暂无您收藏的活动." ;
			break;
		case 1:
			mylove_gather_title.setText("我的活动") ;
			query.addWhereContainedIn("objectId", user.getmGatherId()) ;
			stringStr = "暂无您发布的活动." ;
			break;
		case 2:
			//查询不同类型的活动
			mylove_gather_title.setText(typeName) ;
			query.addWhereEqualTo("gatherClass", typeName) ;
			stringStr = "暂无【"+typeName+"】类型的活动." ;
			break ;
		case 3:
			//查出免费的活动
			mylove_gather_title.setText(typeName) ;
			query.addWhereEqualTo("gatherRMB", "0") ;
			stringStr = "Sorry,暂无【"+typeName+"】." ;
			break ;
		case 4:
			//查询出被收藏超过10个人的活动
			mylove_gather_title.setText(typeName) ;
			query.order("-praiseUserscount") ;
			query.setLimit(10) ;
			stringStr = "查询失败,请重新查询." ;
			break ;
		case 5:
			//离我的位置最近的8个活动
			LatLng latlng= HappyApplication.getmHappyApplication().getLists().get(0) ;
			mylove_gather_title.setText(typeName) ;
			query.addWhereNear("gpsAdd", new BmobGeoPoint(latlng.longitude, latlng.latitude));
			query.setLimit(8); //只查询最近的八条
			stringStr = "【"+typeName+"】暂无活动." ;
			break ;
		case 6:
			//模糊查询
			mylove_gather_title.setText(StringUtilsDemo.getStringUtils(typeName)) ;
			String typeName1 = (String) HappyApplication.getmHappyApplication().getHash("typeName") ;
			query.addWhereContains("gatherName", typeName1) ;
			stringStr = "暂无关于【"+typeName+"】的活动." ;
		}
		//查询的方法
		query.findObjects(getAct(), new FindListener<GatherBean>() {
			
			@Override
			public void onSuccess(List<GatherBean> arg0) {
				//查询成功后让加载的动画消失
				mylove_gather_liearlayout.setVisibility(View.INVISIBLE) ;
				//把查询到的数据赋值给当前的List集合
				lists = arg0 ;
				
				//如果查询的数据小于1的时候，就代表没有数据   让当前的界面显示没有数据
				//反之就把数据显示出来 并让动画消失等
				if(lists.size()<1){
					mylove_gather_text.setText(stringStr) ;
				}else{
					mylove_gather_text.setVisibility(View.INVISIBLE) ;
					mylove_gather_listview.setVisibility(View.VISIBLE) ;
					adapter = new MyXListViewAdapter(lists) ;
					mylove_gather_listview.setAdapter(adapter) ;
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				//如果加载不出来数据  很有可能是网络的问题
				mylove_gather_liearlayout.setVisibility(View.INVISIBLE) ;
				mylove_gather_text.setText("加载失败,请检查网络.") ;
			}
		}) ;
		//这里是listView的点击事件
	
		mylove_gather_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				
				HappyApplication.getmHappyApplication().setHash(
						"gatherpositiondelete", lists.get(position));
				startActivity(new Intent(getAct(), GatherDetailted.class));
			}
		});
	  	
		
	}
	//补间动画   在这个方法中主要是做了旋转动画  让动画在不停的旋转 并以匀速地方式在不停旋转
	private void imageRotate() {
		RotateAnimation an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		an.setDuration(1000) ;
		an.setInterpolator(new  LinearInterpolator()) ;
		an.setRepeatCount(-1) ;
		mylove_gather_loading.startAnimation(an) ;
	}
	//事件的方法
	@Override  
	public void initViewOper() {

	}
	//销毁是把当前的activity设置为kong 
	@Override
	protected void onDestroy() {
		super.onDestroy();
		setAct(null);
	}
}
