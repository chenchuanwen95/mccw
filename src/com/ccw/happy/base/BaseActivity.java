package com.ccw.happy.base;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-9下午7:02:25
 * @auther: 本类用于抽取一些共有的方法
 */
public class BaseActivity extends FragmentActivity {

	private static final String TAG = "LOG";
	private BaseActivity act;

	public BaseActivity getAct() {
		return act;
	}

	public void setAct(BaseActivity act) {
		this.act = act;
	}

	/**
	 * 给当前方法传入一个id，在本类中进行FindViewById 并返回一个Button
	 */
	public Button btnById(int id) {
		return (Button) findViewById(id);
	}

	/**
	 * 给当前方法传入一个id，在本类中进行FindViewById 并返回一个TextView
	 */
	public TextView tvById(int id) {
		return (TextView) findViewById(id);
	}

	/**
	 * 给当前方法传入一个id，在本类中进行FindViewById 并返回一个EditText
	 */
	public EditText etById(int id) {
		return (EditText) findViewById(id);
	}

	/**
	 * 给当前方法传入一个id，在本类中进行FindViewById 并返回一个ImageView
	 */
	public ImageView imgById(int id) {
		return (ImageView) findViewById(id);
	}

	/**
	 * 给当前方法传入一个id，在本类中进行FindViewById 并返回一个CheckBox
	 */
	public CheckBox boxById(int id) {
		return (CheckBox) findViewById(id);
	}

	/**
	 * 传入一个EditText组件 获取里面的数据并返回
	 */
	public String getViewText(EditText et) {
		return et.getText().toString().trim();
	}

	/**
	 * 给当前方法传入一个字符串，3秒显示
	 */
	public void toastS(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 给当前方法传入一个字符串，5秒显示
	 */
	public void toastL(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	/**
	 * 给当前方法传入一个字符串，用Log打印显示
	 */
	public void log(String text) {
		Log.i(TAG, text + "");
	}

	/**
	 * 给当前方法传入一个数字，用Log打印显示
	 */
	public void log(int text) {
		Log.i(TAG, text + "");
	}

}
