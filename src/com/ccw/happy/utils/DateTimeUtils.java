package com.ccw.happy.utils;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeUtils {
	private static SimpleDateFormat format;

	@SuppressLint("SimpleDateFormat")
	public static long getDate1(String date){
		format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			return format.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0 ;
		}
		
	}
	@SuppressLint("SimpleDateFormat")
	public static long getDate2(String date){
		date = date+" 00:00:00" ;
		format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			return format.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0 ;
		}
		
	}
	@SuppressLint("SimpleDateFormat")
	public static long getDate3(String date){
		date = date+" 23:59:59" ;
		format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			return format.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0 ;
		}
		
	}
}
