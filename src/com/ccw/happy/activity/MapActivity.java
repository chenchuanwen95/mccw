package com.ccw.happy.activity;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.ccw.happy.R;
import com.ccw.happy.adapter.MyAdapter;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.sql.DBManager;
import com.ccw.happy.vo.MyListItem;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-12下午11:32:12
 * @auther: 本类中使用来加载百度地图的类，，在类中得到了地图上面的内容之后 点击按钮之后把数据带回上一层界面
 */
public class MapActivity extends BaseActivity implements BaseInterface {
	private ImageView mImageView_sousuo;
	private EditText mEditText_city;
	private MapView mapView;
	private BaiduMap mBaiduMap;
	private PoiSearch mPoiSearch;
	private List<PoiInfo> mAllPoi;

	private DBManager dbm;
	private SQLiteDatabase db;
	private Spinner spinner1 = null;
	private Spinner spinner2 = null;
	private String province = null;
	private String city = null;
	private String district = null;

	// 定义一个标记 用来判断是从哪个界面调用的地图
	// private int locationNum = 0 ;
	// private String mSDCardPath =
	// Environment.getExternalStorageDirectory()+"";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setAct(this);
		setContentView(R.layout.send_gether_map);
		// Intent intent = getIntent() ;
		// locationNum = intent.getIntExtra("item_localtion_iv",0) ;
		initViews();
		initDatas();
		initViewOper();
		// if(locationNum!=1000){
		// }else{
		// getAct().toastS("开始导航") ;
		// }

	}

	/**
	 * 组建的初始化
	 */
	@Override
	public void initViews() {
		mImageView_sousuo = imgById(R.id.send_gether_map_img);
		mEditText_city = etById(R.id.send_gether_map_et);
		// 初始化城市组建
		spinner1 = (Spinner) findViewById(R.id.send_map_spinner1);
		spinner2 = (Spinner) findViewById(R.id.send_map_spinner2);
		mapView = (MapView) findViewById(R.id.send_gether_mapview);

	}

	/**
	 * 添加数据
	 */
	@Override
	public void initDatas() {
		spinner1.setPrompt("请选择省：");
		spinner2.setPrompt("请选择市：");
		initSpinner1();
		// 得到检索的对象
		mPoiSearch = PoiSearch.newInstance();
		// 实例化百度地图的对象试图
		mBaiduMap = mapView.getMap();
		// 设置为普通地图模式
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

	}

	/**
	 * 这个是组建的响应方法
	 */
	@Override
	public void initViewOper() {

		// 创建检索的监听者
		OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

			public void onGetPoiResult(PoiResult result) {
				// 检索地点失败时
				if (result.error != SearchResult.ERRORNO.NO_ERROR) {
					// 详情检索失败
					// result.error请参考SearchResult.ERRORNO
					toastS("暂无搜索信息!");
				} else {
					if (getAct() == null) {
						return;
					}
					// 检索成功
					// toastS(city+":"+result.getAllPoi().get(0).name) ;
					mAllPoi = result.getAllPoi();
					// 获取POI检索结果
					mBaiduMap.clear();
					// 创建PoiOverlay
					PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
					// 设置overlay可以处理标注点击事件
					mBaiduMap.setOnMarkerClickListener(overlay);
					// 设置PoiOverlay数据
					overlay.setData(result);
					// 添加PoiOverlay到地图中
					overlay.addToMap();
					overlay.zoomToSpan();
					return;
				}
			}

			public void onGetPoiDetailResult(PoiDetailResult result) {
				// 获取Place详情页检索结果
			}
		};
		// 设置检索监听者
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
		// 设置一下图片的点击搜索事件
		mImageView_sousuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toastS("搜索中...");
				// 1 获取搜索的地点
				String cityName = getViewText(mEditText_city);
				// 2 开始检索
				mPoiSearch.searchInCity((new PoiCitySearchOption()).city(city)
						.keyword(cityName).pageNum(0)); // 检索总页数中的第一页
			}
		});
	}

	// 把地图的生命周期与activity的生命周期绑定在一起
	@Override
	protected void onResume() {
		mapView.setVisibility(View.VISIBLE);
		mapView.onResume();
		super.onResume();
	}

	/**
	 * 当前activity如果暂停 也要让地图暂停
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 当前activity销毁也要让地图销毁
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 
	 * @作者: 陈传稳
	 * @时间: 2015-11-18上午9:21:38
	 * @auther:对检索结果覆盖物类的自定义
	 */
	private class MyPoiOverlay extends PoiOverlay {
		private Button button;

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			button = new Button(getApplicationContext());
			button.setBackgroundResource(R.drawable.btn_selector);
			// 定义用于显示该InfoWindow的坐标点
			LatLng pt = mAllPoi.get(index).location;
			String city = mAllPoi.get(index).name;
			button.setText(city);
			// 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
			InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);
			// 显示InfoWindow
			mBaiduMap.showInfoWindow(mInfoWindow);
			// 调用Button的响应方法
			initEvent(index);
			return true;
		}

		/**
		 * 点击Button后的响应事件
		 * 
		 * @param index
		 */
		private void initEvent(final int index) {
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/*
					 * Intent intent = getIntent() ; intent.putExtra("city",
					 * city) ; setResult(RESULT_OK, intent) ;
					 */
					HappyApplication.getmHappyApplication().setHash(
							"mapdelete", mAllPoi.get(index));
					finish();
				}
			});
		}
	}

	/**
	 * 对spinner1进行添加数据
	 */
	public void initSpinner1() {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		List<MyListItem> list = new ArrayList<MyListItem>();
		try {
			String sql = "select * from province";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				MyListItem myListItem = new MyListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			MyListItem myListItem = new MyListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			list.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();

		MyAdapter myAdapter = new MyAdapter(this, list);
		spinner1.setAdapter(myAdapter);
		spinner1.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
	}

	/**
	 * 对spinner2添加数据
	 * 
	 * @param pcode
	 */
	public void initSpinner2(String pcode) {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		List<MyListItem> list = new ArrayList<MyListItem>();

		try {
			String sql = "select * from city where pcode='" + pcode + "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				MyListItem myListItem = new MyListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			MyListItem myListItem = new MyListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			list.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();

		MyAdapter myAdapter = new MyAdapter(this, list);
		spinner2.setAdapter(myAdapter);
		spinner2.setOnItemSelectedListener(new SpinnerOnSelectedListener2());
	}

	/**
	 * 
	 * @作者: 陈传稳
	 * @时间: 2015-11-18上午9:22:18
	 * @auther:对Spinner1 的点击事件
	 */
	class SpinnerOnSelectedListener1 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			province = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();
			String pcode = ((MyListItem) adapterView
					.getItemAtPosition(position)).getPcode();

			initSpinner2(pcode);
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
		}
	}

	/**
	 * 
	 * @作者: 陈传稳
	 * @时间: 2015-11-18上午9:22:41
	 * @auther: 对Spinner2的点击事件
	 */
	class SpinnerOnSelectedListener2 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			city = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();
			String pcode = ((MyListItem) adapterView
					.getItemAtPosition(position)).getPcode();

		}

		public void onNothingSelected(AdapterView<?> adapterView) {
		}
	}

}
