package com.ccw.happy.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.ccw.happy.R;
import com.ccw.happy.activity.MyLoveGatherActivity;
import com.ccw.happy.adapter.MyGridVeiwAdapter;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseFragment;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.vo.GatherBean;
import com.ccw.happy.vo.MoreBean;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-11上午9:41:42
 * @auther: 本类是更过界面的主类 在类中加载了界面 和维护了GridView
 */
public class FragmentMore extends BaseFragment implements BaseInterface {
	private GridView fragment_more_gv;
	private EditText fragment_more_edittext_sarch;
	private ImageView fragment_more_imageview_sarch;
	private List<MoreBean> lists;
	private MyGridVeiwAdapter adapter;
	private Button fragment_more_mianfei, fragment_more_remen,
			fragment_more_fujin;
	private BaseActivity act;
	private Intent intent;
	String[] moreNames = { "周边户外", "少儿兴趣", "DIY", "健身运动", "节日/集市", "演出",
			"展览/展会", "沙龙讲座", "品茶/美食", "多人聚会" };
	int[] moreImgIds = { R.drawable.sort_zhoubian, R.drawable.sort_shaoer,
			R.drawable.sort_diy, R.drawable.sort_yundong,
			R.drawable.sort_jieri, R.drawable.sort_yanchu,
			R.drawable.sort_zhanlan, R.drawable.sort_jiangzuo,
			R.drawable.sort_meishi, R.drawable.sort_jucan };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_more, null);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.act = (BaseActivity) activity;
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
		fragment_more_gv = (GridView) findViewById(R.id.fragment_more_gv);
		fragment_more_edittext_sarch = etById(R.id.fragment_more_edittext_sarch);
		fragment_more_imageview_sarch = imgById(R.id.fragment_more_imageview_sarch);
		fragment_more_imageview_sarch
				.setOnClickListener(new MyOnClickListener());
		fragment_more_mianfei = btnById(R.id.fragment_more_mianfei);
		fragment_more_mianfei.setOnClickListener(new MyOnClickListener());
		fragment_more_remen = btnById(R.id.fragment_more_remen);
		fragment_more_remen.setOnClickListener(new MyOnClickListener());
		fragment_more_fujin = btnById(R.id.fragment_more_fujin);
		fragment_more_fujin.setOnClickListener(new MyOnClickListener());
	}

	@Override
	public void initDatas() {
		lists = new ArrayList<MoreBean>();
		for (int i = 0; i < 10; i++) {
			lists.add(new MoreBean(moreImgIds[i], moreNames[i]));
		}
		adapter = new MyGridVeiwAdapter(lists);
		fragment_more_gv.setAdapter(adapter);
		// 这是对ScollerList的点击事件
		fragment_more_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent = new Intent(getActivity(), MyLoveGatherActivity.class);
				intent.putExtra("gatherNumber", 2);
				HappyApplication.getmHappyApplication().setHash("typeName",
						moreNames[position]);
				
				startActivity(intent);
			}
		});
	}

	@Override
	public void initViewOper() {

	}

	/**
	 * 
	 * @作者: 陈传稳
	 * @时间: 2015-11-17下午10:32:43
	 * @auther:内部类 类中只是对 免费 附近 热门 等按钮做了点击事件
	 *  case 3 : 免费活动
	 *  case 4 : 热门活动
	 *  case 5 : 周围活动
	 *  case 6 : 模糊搜索
	 */
	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			intent = new Intent(getActivity(), MyLoveGatherActivity.class);
			switch (v.getId()) {
			case R.id.fragment_more_mianfei:
				intent.putExtra("gatherNumber", 3);
				HappyApplication.getmHappyApplication().setHash("typeName",
						"免费活动");
				break;
			case R.id.fragment_more_remen:
				intent.putExtra("gatherNumber", 4);
				HappyApplication.getmHappyApplication().setHash("typeName",
						"热门活动");
				System.out.println("我在执行");
				break;
			case R.id.fragment_more_fujin:
				intent.putExtra("gatherNumber", 5);
				HappyApplication.getmHappyApplication().setHash("typeName",
						"我的周围");
				break;
			case R.id.fragment_more_imageview_sarch:
				String content = fragment_more_edittext_sarch.getText()
						.toString().trim();
				
				fragment_more_edittext_sarch.setText("") ;
				if ("".equals(content) || content == null) {
					act.toastS("请输入要搜索的内容.");
					return;
				}
				intent.putExtra("gatherNumber", 6);
				HappyApplication.getmHappyApplication().setHash("typeName",
						content);
				break;
			}
			startActivity(intent);
		}

	}
}
