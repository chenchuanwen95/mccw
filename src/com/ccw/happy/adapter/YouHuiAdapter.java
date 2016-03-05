package com.ccw.happy.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ccw.happy.R;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.vo.YouHuiBean;
/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-21上午11:25:47
 * @auther:  这个是adapter的适配器
 */
public class YouHuiAdapter extends BaseAdapter {
	private List<YouHuiBean> lists ;
	private BaseActivity act ;
	public YouHuiAdapter(List<YouHuiBean> lists, BaseActivity act) {
		super();
		this.lists = lists;
		this.act = act;
	}

	@Override
	public int getCount() {
		return lists.size() ;
	}

	@Override
	public Object getItem(int arg0) {
		return lists.get(arg0) ;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder vh ;
		if(arg1 == null){
			arg1 = act.getLayoutInflater().inflate(R.layout.youhuiquan, null) ;
			vh = new ViewHolder() ;
			vh.youhuiquan_time = (TextView) arg1.findViewById(R.id.youhuiquan_time) ;
			vh.youhuiquan_money = (TextView) arg1.findViewById(R.id.youhuiquan_money) ;
			vh.youhuiquan_manjian = (TextView) arg1.findViewById(R.id.youhuiquan_manjian) ;
			arg1.setTag(vh) ;
		}
		vh = (ViewHolder) arg1.getTag() ;
		vh.youhuiquan_time.setText(lists.get(arg0).getYouXiaoQi()) ;
		vh.youhuiquan_money.setText(lists.get(arg0).getMoney()) ;
		vh.youhuiquan_manjian.setText(lists.get(arg0).getManMoney()) ;
		return arg1;
	}
	class ViewHolder{
		TextView youhuiquan_time,youhuiquan_money,youhuiquan_manjian ;
	}
}
