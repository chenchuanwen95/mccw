package com.ccw.happy.adapter;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.listener.UpdateListener;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.utils.DistanceUtil;
import com.ccw.happy.R;
import com.ccw.happy.activity.MapActivity;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.utils.ImageLoaderutils;
import com.ccw.happy.utils.IntenetUtils;
import com.ccw.happy.vo.GatherBean;
import com.ccw.happy.vo.UserBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-13����10:35:41
 * @auther: ������������Ǽ��ص�����ҳ�е����� ÿһ��item������
 */
public class MyXListViewAdapter extends BaseAdapter {
	private List<GatherBean> lists;
	private BaseActivity act;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	private DisplayImageOptions mDisplayImageOptions2;
	private GatherBean beanPosition;
	private UserBean mUserBean;

	// �����������������쳣�Ĵ���
	private int exceptionCount = 0;

	public MyXListViewAdapter(List<GatherBean> lists) {
		super();
		this.lists = lists;
		act = (BaseActivity) HappyApplication.getmHappyApplication().getHash(
				"activity");
		mUserBean = HappyApplication.getmHappyApplication().getUb();
		mImageLoader = ImageLoaderutils.getInstance(act);
		mDisplayImageOptions = ImageLoaderutils.getOpt();
		mDisplayImageOptions2 = ImageLoaderutils.getOpt2();
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh;

		if (convertView == null) {
			convertView = act.getLayoutInflater().inflate(
					R.layout.fragment_home_item, null);
			vh = new ViewHolder();
			vh.item_usericon = (ImageView) convertView
					.findViewById(R.id.fragment_home_item_usericon);
			vh.item_gathername = (TextView) convertView
					.findViewById(R.id.fragment_home_item_gathername);
			vh.item_zan = (ImageView) convertView
					.findViewById(R.id.fragment_home_item_zan);
			vh.item_gatherimg = (ImageView) convertView
					.findViewById(R.id.fragment_home_item_gatherimg);
			vh.item_money = (TextView) convertView
					.findViewById(R.id.fragment_home_item_money);
			vh.item_location = (TextView) convertView
					.findViewById(R.id.fragment_home_item_location);
			vh.item_time = (TextView) convertView
					.findViewById(R.id.fragment_home_item_tv_time);
			vh.item_localtion_iv = (ImageView) convertView
					.findViewById(R.id.fragment_home_item_location_iv);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();

		beanPosition = lists.get(position);
		// ��ȡ�û���ͷ����û��ϴ�����Ƭ

		String userIcon = null;
		if (beanPosition.getGatherIcon() != null) {
			userIcon = beanPosition.getGatherIcon().getFileUrl(act);
		}
		mImageLoader.displayImage(userIcon, vh.item_usericon,
				mDisplayImageOptions);

		String gatherIcon = null;
		if (beanPosition.getGatherJPG() != null) {
			gatherIcon = beanPosition.getGatherJPG().getFileUrl(act);
		}
		mImageLoader.displayImage(gatherIcon, vh.item_gatherimg,
				mDisplayImageOptions2);
		vh.item_gathername.setText(beanPosition.getGatherName());
		vh.item_time.setText("����ʱ��: " + beanPosition.getCreatedAt());
		vh.item_money.setText("�����: " + beanPosition.getGatherRMB() + " Ԫ");
		vh.item_location.setText(getLocationXY(beanPosition.getLocation()));
		// �ж��ĸ���ǵ�ǰ�û�����޵�
		if (IntenetUtils.isNet(act)) {
			if (lists.get(position).getPraiseUsers()
					.contains(mUserBean.getObjectId())) {
				vh.item_zan.setImageResource(R.drawable.zanhou);
			} else {
				vh.item_zan.setImageResource(R.drawable.zanqian);
			}
		}
		// Ϊ����ͼ�������Ӧ�¼�
		vh.item_zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (IntenetUtils.isNet(act)) {
					if (lists.get(position).getPraiseUsers()
							.contains(mUserBean.getObjectId())) {
						// �Ѿ�������� �ٴε����Ҫȡ��
						loadGatherOn(v, position);
					} else {
						// û�е���ޣ��ٴε���ǵ���
						loadGatherOff(v, position);
					}
				} else {
					act.toastS("������������");
				}
			}
		});
		// Ϊ��λͼ�������Ӧ�¼�
		vh.item_localtion_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(act, MapActivity.class);
				intent.putExtra("item_localtion_iv", 1000);
				act.startActivity(intent);
			}
		});
		return convertView;
	}

	// ���õ��޵ķ���
	protected void loadGatherOff(View v, int position) {
		((ImageView) v).setImageResource(R.drawable.zanhou);
		// �Ȱ����ݱ��浽����
		mUserBean.getLoveGatherId().add(lists.get(position).getObjectId());
		lists.get(position).getPraiseUsers().add(mUserBean.getObjectId());
		// �ٰѵ�ǰ�û��ղصĻ�ϴ���������
		UserBean ub = new UserBean();
		ub.addUnique("loveGatherId", lists.get(position).getObjectId());
		ub.update(act, mUserBean.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				if (act == null) {
					return;
				}
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				if (act == null) {
					return;
				}
			}
		});
		// �ѵ���޵ĵ�ǰ�û���ӵ�����ѵ������
		GatherBean gb = new GatherBean();
		gb.addUnique("praiseUsers", mUserBean.getObjectId());
		gb.update(act, lists.get(position).getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				if (act == null) {
					return;
				}
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				if (act == null) {
					return;
				}
			}
		});
		// �û������ղ���������
		lists.get(position).increment("praiseUserscount"); // ��������1
		lists.get(position).update(act, new UpdateListener() {

			@Override
			public void onSuccess() {
				if (act == null) {
					return;
				}
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				if (act == null) {
					return;
				}
			}
		});
		act.toastS("��ϲ�����ղسɹ�");
	}

	// ����ȡ�����޵ķ���
	protected void loadGatherOn(View v, int position) {
		((ImageView) v).setImageResource(R.drawable.zanqian);
		// �Ȱ����ݱ��浽����
		mUserBean.getLoveGatherId().remove(lists.get(position).getObjectId());
		lists.get(position).getPraiseUsers().remove(mUserBean.getObjectId());
		// �ٰѵ�ǰ�û��ղصĻ�ϴ���������
		UserBean ub = new UserBean();
		ub.removeAll("loveGatherId",
				Arrays.asList(lists.get(position).getObjectId()));
		ub.update(act, mUserBean.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				if (act == null) {
					return;
				}
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				if (act == null) {
					return;
				}
			}
		});
		// �ѵ���޵ĵ�ǰ�û���ӵ�����ѵ������
		GatherBean gb = new GatherBean();
		gb.removeAll("praiseUsers", Arrays.asList(mUserBean.getObjectId()));
		gb.update(act, lists.get(position).getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				if (act == null) {
					return;
				}
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				if (act == null) {
					return;
				}
			}
		});
		// �û������ղ���������
		lists.get(position).increment("praiseUserscount", -1); // ��������1
		lists.get(position).update(act, new UpdateListener() {

			@Override
			public void onSuccess() {
				if (act == null) {
					return;
				}
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				if (act == null) {
					return;
				}
			}
		});
		act.toastS("��ȡ�������ղ�");
	}

	private CharSequence getLocationXY(PoiInfo location) {
		LatLng startLocation = null;
		try {
			startLocation = HappyApplication.getmHappyApplication().getLists()
					.get(0);
		} catch (Exception e) {
			CountDownTimer cdt = new CountDownTimer(5000, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					if (millisUntilFinished > 4000) {
						act.toastS("���Ķ�λϵͳ�����쳣,���ڴ���...");
					}
				}

				@Override
				public void onFinish() {
					MyXListViewAdapter.this.notifyDataSetChanged();
					exceptionCount = 0;
				}
			};
			if (exceptionCount == 0) {
				cdt.start();
			}
			exceptionCount++;
		}
		LatLng engLocation = location.location;
		int m = (int) DistanceUtil.getDistance(startLocation, engLocation);
		m += 500;
		if (m < 3000) {
			m -= 500;
			return "������: " + m + " m";
		}
		int km = m / 1000;
		return "������: " + km + " km";
	}

	class ViewHolder {
		ImageView item_usericon, item_zan, item_gatherimg, item_localtion_iv;
		TextView item_gathername, item_money, item_location, item_time;
	}

	public void getListView(List<GatherBean> lists) {
		this.lists = lists;
	}
}
