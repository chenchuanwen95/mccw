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
 * @����: �´���
 * @ʱ��: 2015-11-9����7:02:25
 * @auther: �������ڳ�ȡһЩ���еķ���
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

	/**
	 * ����һ��EditText��� ��ȡ��������ݲ�����
	 */
	public String getViewText(EditText et) {
		return et.getText().toString().trim();
	}

	/**
	 * ����ǰ��������һ���ַ�����3����ʾ
	 */
	public void toastS(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ����ǰ��������һ���ַ�����5����ʾ
	 */
	public void toastL(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	/**
	 * ����ǰ��������һ���ַ�������Log��ӡ��ʾ
	 */
	public void log(String text) {
		Log.i(TAG, text + "");
	}

	/**
	 * ����ǰ��������һ�����֣���Log��ӡ��ʾ
	 */
	public void log(int text) {
		Log.i(TAG, text + "");
	}

}
