package com.ccw.happy.adapter;

import java.util.List;

import com.ccw.happy.R;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.vo.MoreBean;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-11上午9:41:05
 * @auther: 本类中是FragmentMore更多界面的GridView的adapter
 */
public class SendGetherAdapter extends BaseAdapter {
	private List<MoreBean> lists;
	private BaseActivity act;

	public SendGetherAdapter(List<MoreBean> lists) {
		super();
		this.lists = lists;
		// 使用application中的setHash方法获取BaseActivity的对象
		act = (BaseActivity) HappyApplication.getmHappyApplication().getHash(
				"activity");
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
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder vh;
		if (v == null) {
			v = act.getLayoutInflater().inflate(
					R.layout.sendgather_spinner_item, null);
			vh = new ViewHolder();
			vh.more_iv = (ImageView) v.findViewById(R.id.sendgather_item_iv);
			vh.more_tv = (TextView) v.findViewById(R.id.sendgather_item_tv);
			v.setTag(vh);
		}
		vh = (ViewHolder) v.getTag();
		vh.more_iv.setImageResource(lists.get(position).getImg());
		vh.more_tv.setText(lists.get(position).getText());
		return v;
	}

	static class ViewHolder {
		ImageView more_iv;
		TextView more_tv;
	}

}
