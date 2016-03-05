package com.ccw.happy.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccw.happy.R;
import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.utils.ImageLoaderutils;
import com.ccw.happy.vo.MsgBean;
import com.ccw.happy.vo.NotificationBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-19下午10:02:25
 * @auther:这是讨论区的item的适配器
 */
public class NotifitycationAdapter extends BaseAdapter{
	private List<NotificationBean> lists ;
	private BaseActivity act ;

	public NotifitycationAdapter(List<NotificationBean> lists,BaseActivity act) {
		this.lists = lists;
		this.act = act ;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh ;
		NotificationBean bean = lists.get(position) ;
		if(convertView == null){
			convertView = act.getLayoutInflater().inflate(R.layout.admin_taolun_item, null) ;
			vh = new ViewHolder() ;
			vh.username = (TextView) convertView.findViewById(R.id.admin_tolun_item_username) ;
			vh.senddate = (TextView) convertView.findViewById(R.id.admin_tolun_item_time) ;
			vh.smscontent = (TextView) convertView.findViewById(R.id.admin_tolun_item_content) ;
			convertView.setTag(vh) ;
		}
		vh = (ViewHolder) convertView.getTag() ;
		vh.username.setText(bean.getGatherName()) ;
		vh.senddate.setText(bean.getCreatedAt()) ;
		vh.smscontent.setText(bean.getSendSms()) ;
		return convertView;
	}
	class ViewHolder{
		TextView username ,senddate ,smscontent ;
	}
	public void updateItem(List<NotificationBean> lists){
		this.lists = lists ;
	}
}
