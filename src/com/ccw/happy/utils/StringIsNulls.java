package com.ccw.happy.utils;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-10上午9:10:18
 * @auther: 判断传入进来的字符串是否为空，如果为空就返回true,反之就为false
 */
public class StringIsNulls {
	public static boolean isNull(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
}
