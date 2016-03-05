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
 * @����: �´���
 * @ʱ��: 2015-11-9����7:14:26
 * @auther: ��������������fragment��ȡ��ͬ�ķ������Ա��ظ�ʹ��
 */
public class BaseFragment extends Fragment {
	public View findViewById(int id) {
		return getView().findViewById(id);
	}

	@SuppressWarnings("unused")
	private static final String TAG = "LOG";

	/**
	 * ����ǰ��������һ��id���ڱ����н���FindViewById ������һ��Button
	 */
	public Button btnById(int id) {
		return (Button) findViewById(id);
	}

	/**
	 * ����ǰ��������һ��id���ڱ����н���FindViewById ������һ��TextView
	 */
	public TextView tvById(int id) {
		return (TextView) findViewById(id);
	}

	/**
	 * ����ǰ��������һ��id���ڱ����н���FindViewById ������һ��EditText
	 */
	public EditText etById(int id) {
		return (EditText) findViewById(id);
	}

	/**
	 * ����ǰ��������һ��id���ڱ����н���FindViewById ������һ��ImageView
	 */
	public ImageView imgById(int id) {
		return (ImageView) findViewById(id);
	}

	/**
	 * ����ǰ��������һ��id���ڱ����н���FindViewById ������һ��CheckBox
	 */
	public CheckBox boxById(int id) {
		return (CheckBox) findViewById(id);
	}
}
