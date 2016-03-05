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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-19下午10:02:25
 * @auther:这是讨论区的item的适配器
 */
public class GatherCommentAdapter extends BaseAdapter{
	private List<MsgBean> lists ;
	private BaseActivity act ;
	private ImageLoader loader;
	private DisplayImageOptions opt;

	public GatherCommentAdapter(List<MsgBean> lists,BaseActivity act) {
		this.lists = lists;
		this.act = act ;
		loader = ImageLoaderutils.getInstance(act) ;
		opt = ImageLoaderutils.getOpt() ;
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
		if(convertView == null){
			convertView = act.getLayoutInflater().inflate(R.layout.admin_taolun_item, null) ;
			vh = new ViewHolder() ;
			vh.usericon = (ImageView) convertView.findViewById(R.id.admin_tolun_item_usericon) ;
			vh.username = (TextView) convertView.findViewById(R.id.admin_tolun_item_username) ;
			vh.senddate = (TextView) convertView.findViewById(R.id.admin_tolun_item_time) ;
			vh.smscontent = (TextView) convertView.findViewById(R.id.admin_tolun_item_content) ;
			convertView.setTag(vh) ;
		}
		vh = (ViewHolder) convertView.getTag() ;
		MsgBean bean = lists.get(position) ;
		String url = null ;
		if(bean.getUsericon()!=null&&bean.getUsericon().getFileUrl(act)!=null){
			url = bean.getUsericon().getFileUrl(act) ;
		}
		loader.displayImage(url, vh.usericon, opt) ;
		vh.username.setText(bean.getUsername()) ;
		vh.senddate.setText(bean.getCreatedAt()) ;
		vh.smscontent.setText(bean.getSmscontent()) ;
		return convertView;
	}
	class ViewHolder{
		ImageView usericon ;
		TextView username ,senddate ,smscontent ;
	}
	public void updateItem(List<MsgBean> lists){
		this.lists = lists ;
	}
}
