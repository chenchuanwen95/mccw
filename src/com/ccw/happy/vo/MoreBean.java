package com.ccw.happy.vo;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-11上午11:37:01
 * @auther: 封装类 用来对更多界面 的GridView里面的Item组件 添加数据
 */
public class MoreBean {
	private int img;
	private String text;

	public MoreBean(int img, String text) {
		super();
		this.img = img;
		this.text = text;
	}

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
