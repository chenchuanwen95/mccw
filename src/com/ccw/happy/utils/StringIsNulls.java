package com.ccw.happy.utils;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-10����9:10:18
 * @auther: �жϴ���������ַ����Ƿ�Ϊ�գ����Ϊ�վͷ���true,��֮��Ϊfalse
 */
public class StringIsNulls {
	public static boolean isNull(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
}
