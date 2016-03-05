package com.ccw.happy.base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-9下午7:14:26
 * @auther: 本类中是用来给fragment抽取相同的方法，以便重复使用
 */
public class BaseFragment extends Fragment {
	public View findViewById(int id) {
		return getView().findViewById(id);
	}

	@SuppressWarnings("unused")
	private static final String TAG = "LOG";

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
}
