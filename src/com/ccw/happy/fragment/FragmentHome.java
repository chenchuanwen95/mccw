package com.ccw.happy.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.ccw.happy.R;
import com.ccw.happy.activity.GatherDetailted;
import com.ccw.happy.adapter.MyXListViewAdapter;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.base.BaseFragment;
import com.ccw.happy.base.BaseInterface;
import com.ccw.happy.view.XListView;
import com.ccw.happy.view.XListView.IXListViewListener;
import com.ccw.happy.vo.GatherBean;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-11����11:35:31
 * @auther: ������Ǹҽ���������ʱ������������,,�����а��������û����������л
 */
public class FragmentHome extends BaseFragment implements BaseInterface {
	private XListView fragment_home_xlistview;
	private MyXListViewAdapter adapter;
	private List<GatherBean> lists;
	private BaseActivity act;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, null);
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
		fragment_home_xlistview = (XListView) findViewById(R.id.fragment_home_xlistview);
		fragment_home_xlistview.setPullLoadEnable(true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initDatas() {
		act = (BaseActivity) HappyApplication.getmHappyApplication().getHash(
				"activity");
		// ��ȡ���е����� ��ǰ������InitMainActivity ���Ѿ���ȡ�����ڿ���ͨ��applicationֱ��ʹ��
		lists = (List<GatherBean>) HappyApplication.getmHappyApplication()
				.getHash("initGatherBean");
		if (lists == null) {
			lists = new ArrayList<GatherBean>();
		}
		adapter = new MyXListViewAdapter(lists);
		fragment_home_xlistview.setAdapter(adapter);

	}  

	@Override
	public void initViewOper() {
		// XlistView�ĵ���¼�
		fragment_home_xlistview
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						HappyApplication.getmHappyApplication().setHash(
								"gatherPosition", position - 1);
						startActivity(new Intent(act, GatherDetailted.class));
					}
				});

		// xlistView���¼�
		fragment_home_xlistview.setXListViewListener(new IXListViewListener() {
			// ����ˢ�µĻص�����
			@Override
			public void onRefresh() {

				BmobQuery<GatherBean> query = new BmobQuery<GatherBean>();
				query.order("-createdAt");
				query.findObjects(act, new FindListener<GatherBean>() {

					@Override
					public void onSuccess(List<GatherBean> arg0) {
						if (act == null) {
							return;
						}
						lists = arg0;
						HappyApplication.getmHappyApplication().setHash(
								"initGatherBean", arg0);
						adapter.getListView(lists);
						adapter.notifyDataSetChanged();
						fragment_home_xlistview.stopRefresh();
						act.toastS("ˢ�³ɹ�.");
					}

					@Override
					public void onError(int arg0, String arg1) {
						if (act == null) {
							return;
						}
						fragment_home_xlistview.stopRefresh();
						act.toastS("ˢ��ʧ��,������������.");
					}
				});
			}

			// �������ظ���Ļص�����
			@Override
			public void onLoadMore() {
				BmobQuery<GatherBean> query = new BmobQuery<GatherBean>();
				// ������ط���һ��Ҫ�Ⱥ���������
				query.setSkip(lists.size());
				query.order("-createdAt");

				query.findObjects(act, new FindListener<GatherBean>() {

					@Override
					public void onSuccess(List<GatherBean> arg0) {
						if (act == null) {
							return;
						}
						lists.addAll(arg0);
						adapter.getListView(lists);
						adapter.notifyDataSetChanged();
						fragment_home_xlistview.stopLoadMore();
						act.toastS("���سɹ�.");
					}

					@Override
					public void onError(int arg0, String arg1) {
						if (act == null) {
							return;
						}
						fragment_home_xlistview.stopLoadMore();
						act.toastS("����ʧ��,������������.");
					}
				});
			}
		});
	}

}
