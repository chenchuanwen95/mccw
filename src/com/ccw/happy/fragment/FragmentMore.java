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
 * @����: �´���
 * @ʱ��: 2015-11-11����9:41:42
 * @auther: �����Ǹ������������ �����м����˽��� ��ά����GridView
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
	String[] moreNames = { "�ܱ߻���", "�ٶ���Ȥ", "DIY", "�����˶�", "����/����", "�ݳ�",
			"չ��/չ��", "ɳ������", "Ʒ��/��ʳ", "���˾ۻ�" };
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
		// ���Ƕ�ScollerList�ĵ���¼�
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
	 * @����: �´���
	 * @ʱ��: 2015-11-17����10:32:43
	 * @auther:�ڲ��� ����ֻ�Ƕ� ��� ���� ���� �Ȱ�ť���˵���¼�
	 *  case 3 : ��ѻ
	 *  case 4 : ���Ż
	 *  case 5 : ��Χ�
	 *  case 6 : ģ������
	 */
	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			intent = new Intent(getActivity(), MyLoveGatherActivity.class);
			switch (v.getId()) {
			case R.id.fragment_more_mianfei:
				intent.putExtra("gatherNumber", 3);
				HappyApplication.getmHappyApplication().setHash("typeName",
						"��ѻ");
				break;
			case R.id.fragment_more_remen:
				intent.putExtra("gatherNumber", 4);
				HappyApplication.getmHappyApplication().setHash("typeName",
						"���Ż");
				System.out.println("����ִ��");
				break;
			case R.id.fragment_more_fujin:
				intent.putExtra("gatherNumber", 5);
				HappyApplication.getmHappyApplication().setHash("typeName",
						"�ҵ���Χ");
				break;
			case R.id.fragment_more_imageview_sarch:
				String content = fragment_more_edittext_sarch.getText()
						.toString().trim();
				
				fragment_more_edittext_sarch.setText("") ;
				if ("".equals(content) || content == null) {
					act.toastS("������Ҫ����������.");
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
