package com.ccw.happy.adapter;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.OrderQueryListener;
import com.ccw.happy.R;
import com.ccw.happy.base.BaseActivity;
import com.ccw.happy.vo.DingDan;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-22上午9:48:26
 * @auther: 这个适配器是用来向订单中添加 您购买的活动 和购买的订单好
 */
public class DingDanAdapter extends BaseAdapter {
	private List<DingDan> lists;
	private BaseActivity act;
	private DingDan dd;
	public DingDanAdapter(List<DingDan> lists, BaseActivity act) {
		super();
		this.lists = lists;
		this.act = act;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder vh;
		dd = lists.get(arg0);
//		new BmobPay(act).query(dd.getDingdanNum(), new OrderQueryListener() {
//
//			@Override
//			public void succeed(String arg0) {
//				
//				HttpClient httpClient = new DefaultHttpClient();
//				String url = "https://api.bmob.cn/1/pay/Bmob"
//						+ dd.getDingdanNum()
//						+ "?X-Bmob-Application-Id=37b78134819b1723cd5348b57469783e&X-Bmob-REST-API-Key=c0aa7fdeab1cc46885b686fb09ae9dd6";
//				HttpGet get = new HttpGet(url);
//				try {
//					HttpResponse response = httpClient.execute(get);
//					String strObj = EntityUtils.toString(response.getEntity(), "UTF-8") ;
//
//					System.out.println(strObj) ;
//				} catch (ClientProtocolException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//			@Override
//			public void fail(int arg0, String arg1) {
//
//			}
//		});
		if (arg1 == null) {
			arg1 = act.getLayoutInflater().inflate(R.layout.fragment_dingdan,
					null);
			vh = new ViewHolder();
			vh.title = (TextView) arg1.findViewById(R.id.textView1);
			vh.content = (TextView) arg1.findViewById(R.id.textView2);
			arg1.setTag(vh);
		}
		vh = (ViewHolder) arg1.getTag();
		vh.title.setText("订单名称:" + dd.getGatherName());
		vh.content.setText("订单号码:" + dd.getDingdanNum());
		return arg1;
	}

	class ViewHolder {
		TextView title, content;
	}
}
