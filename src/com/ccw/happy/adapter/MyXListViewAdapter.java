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
 * @作者: 陈传稳
 * @时间: 2015-11-13下午10:35:41
 * @auther: 这个适配器中是加载的在主页中的数据 每一条item的适配
 */
public class MyXListViewAdapter extends BaseAdapter {
	private List<GatherBean> lists;
	private BaseActivity act;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	private DisplayImageOptions mDisplayImageOptions2;
	private GatherBean beanPosition;
	private UserBean mUserBean;

	// 这个是用来计算出现异常的次数
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
		// 获取用户的头像和用户上传的照片

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
		vh.item_time.setText("发布时间: " + beanPosition.getCreatedAt());
		vh.item_money.setText("活动经费: " + beanPosition.getGatherRMB() + " 元");
		vh.item_location.setText(getLocationXY(beanPosition.getLocation()));
		// 判断哪个活动是当前用户点过赞的
		if (IntenetUtils.isNet(act)) {
			if (lists.get(position).getPraiseUsers()
					.contains(mUserBean.getObjectId())) {
				vh.item_zan.setImageResource(R.drawable.zanhou);
			} else {
				vh.item_zan.setImageResource(R.drawable.zanqian);
			}
		}
		// 为点赞图标添加相应事件
		vh.item_zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (IntenetUtils.isNet(act)) {
					if (lists.get(position).getPraiseUsers()
							.contains(mUserBean.getObjectId())) {
						// 已经点过赞了 再次点击需要取消
						loadGatherOn(v, position);
					} else {
						// 没有点过赞，再次点击是点赞
						loadGatherOff(v, position);
					}
				} else {
					act.toastS("请检查网络连接");
				}
			}
		});
		// 为定位图标添加响应事件
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

	// 调用点赞的方法
	protected void loadGatherOff(View v, int position) {
		((ImageView) v).setImageResource(R.drawable.zanhou);
		// 先把数据保存到本地
		mUserBean.getLoveGatherId().add(lists.get(position).getObjectId());
		lists.get(position).getPraiseUsers().add(mUserBean.getObjectId());
		// 再把当前用户收藏的活动上传到服务器
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
		// 把点过赞的当前用户添加到活动的已点击栏里
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
		// 让活动表里的收藏数量增加
		lists.get(position).increment("praiseUserscount"); // 分数递增1
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
		act.toastS("恭喜宝贝收藏成功");
	}

	// 调用取消点赞的方法
	protected void loadGatherOn(View v, int position) {
		((ImageView) v).setImageResource(R.drawable.zanqian);
		// 先把数据保存到本地
		mUserBean.getLoveGatherId().remove(lists.get(position).getObjectId());
		lists.get(position).getPraiseUsers().remove(mUserBean.getObjectId());
		// 再把当前用户收藏的活动上传到服务器
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
		// 把点过赞的当前用户添加到活动的已点击栏里
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
		// 让活动表里的收藏数量增加
		lists.get(position).increment("praiseUserscount", -1); // 分数递增1
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
		act.toastS("已取消宝贝收藏");
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
						act.toastS("您的定位系统出现异常,正在处理...");
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
			return "距离您: " + m + " m";
		}
		int km = m / 1000;
		return "距离您: " + km + " km";
	}

	class ViewHolder {
		ImageView item_usericon, item_zan, item_gatherimg, item_localtion_iv;
		TextView item_gathername, item_money, item_location, item_time;
	}

	public void getListView(List<GatherBean> lists) {
		this.lists = lists;
	}
}
