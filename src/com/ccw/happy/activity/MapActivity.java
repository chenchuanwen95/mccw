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
 * @����: �´���
 * @ʱ��: 2015-11-12����11:32:12
 * @auther: ������ʹ�������ذٶȵ�ͼ���࣬�������еõ��˵�ͼ���������֮�� �����ť֮������ݴ�����һ�����
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

	// ����һ����� �����ж��Ǵ��ĸ�������õĵ�ͼ
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
		// getAct().toastS("��ʼ����") ;
		// }

	}

	/**
	 * �齨�ĳ�ʼ��
	 */
	@Override
	public void initViews() {
		mImageView_sousuo = imgById(R.id.send_gether_map_img);
		mEditText_city = etById(R.id.send_gether_map_et);
		// ��ʼ�������齨
		spinner1 = (Spinner) findViewById(R.id.send_map_spinner1);
		spinner2 = (Spinner) findViewById(R.id.send_map_spinner2);
		mapView = (MapView) findViewById(R.id.send_gether_mapview);

	}

	/**
	 * �������
	 */
	@Override
	public void initDatas() {
		spinner1.setPrompt("��ѡ��ʡ��");
		spinner2.setPrompt("��ѡ���У�");
		initSpinner1();
		// �õ������Ķ���
		mPoiSearch = PoiSearch.newInstance();
		// ʵ�����ٶȵ�ͼ�Ķ�����ͼ
		mBaiduMap = mapView.getMap();
		// ����Ϊ��ͨ��ͼģʽ
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

	}

	/**
	 * ������齨����Ӧ����
	 */
	@Override
	public void initViewOper() {

		// ���������ļ�����
		OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

			public void onGetPoiResult(PoiResult result) {
				// �����ص�ʧ��ʱ
				if (result.error != SearchResult.ERRORNO.NO_ERROR) {
					// �������ʧ��
					// result.error��ο�SearchResult.ERRORNO
					toastS("����������Ϣ!");
				} else {
					if (getAct() == null) {
						return;
					}
					// �����ɹ�
					// toastS(city+":"+result.getAllPoi().get(0).name) ;
					mAllPoi = result.getAllPoi();
					// ��ȡPOI�������
					mBaiduMap.clear();
					// ����PoiOverlay
					PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
					// ����overlay���Դ����ע����¼�
					mBaiduMap.setOnMarkerClickListener(overlay);
					// ����PoiOverlay����
					overlay.setData(result);
					// ���PoiOverlay����ͼ��
					overlay.addToMap();
					overlay.zoomToSpan();
					return;
				}
			}

			public void onGetPoiDetailResult(PoiDetailResult result) {
				// ��ȡPlace����ҳ�������
			}
		};
		// ���ü���������
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
		// ����һ��ͼƬ�ĵ�������¼�
		mImageView_sousuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toastS("������...");
				// 1 ��ȡ�����ĵص�
				String cityName = getViewText(mEditText_city);
				// 2 ��ʼ����
				mPoiSearch.searchInCity((new PoiCitySearchOption()).city(city)
						.keyword(cityName).pageNum(0)); // ������ҳ���еĵ�һҳ
			}
		});
	}

	// �ѵ�ͼ������������activity���������ڰ���һ��
	@Override
	protected void onResume() {
		mapView.setVisibility(View.VISIBLE);
		mapView.onResume();
		super.onResume();
	}

	/**
	 * ��ǰactivity�����ͣ ҲҪ�õ�ͼ��ͣ
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * ��ǰactivity����ҲҪ�õ�ͼ����
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 
	 * @����: �´���
	 * @ʱ��: 2015-11-18����9:21:38
	 * @auther:�Լ����������������Զ���
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
			// ����������ʾ��InfoWindow�������
			LatLng pt = mAllPoi.get(index).location;
			String city = mAllPoi.get(index).name;
			button.setText(city);
			// ����InfoWindow , ���� view�� �������꣬ y ��ƫ����
			InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);
			// ��ʾInfoWindow
			mBaiduMap.showInfoWindow(mInfoWindow);
			// ����Button����Ӧ����
			initEvent(index);
			return true;
		}

		/**
		 * ���Button�����Ӧ�¼�
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
	 * ��spinner1�����������
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
	 * ��spinner2�������
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
	 * @����: �´���
	 * @ʱ��: 2015-11-18����9:22:18
	 * @auther:��Spinner1 �ĵ���¼�
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
	 * @����: �´���
	 * @ʱ��: 2015-11-18����9:22:41
	 * @auther: ��Spinner2�ĵ���¼�
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
