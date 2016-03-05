package com.ccw.happy.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ccw.happy.R;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.vo.GatherBean;

public class GatherAdapter extends BaseAdapter {
	private List<GatherBean> lists ;
	private BaseActivity act ;
	
	public GatherAdapter(List<GatherBean> lists, BaseActivity act) {
		super();
		this.lists = lists;
		this.act = act;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
		if(convertView==null){
			convertView = act.getLayoutInflater().inflate(R.layout.gather_item,null) ;
		}
		TextView tv = (TextView) convertView.findViewById(R.id.textView1) ;
		tv.setText(lists.get(position).getGatherName()) ;
		return convertView;
	}

}
