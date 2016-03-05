package com.ccw.happy.utils;

public class StringUtilsDemo {
	public static String getStringUtils(String typeName){
		if(typeName.length()>3){
			typeName = typeName.substring(0, 3);
			return "关于"+typeName +"...";
		}else{
			return typeName+"的活动" ;
		}
	}
	
	public static String getStringUtils2(String gatherName){
		if(gatherName.length()>7){
			gatherName = gatherName.substring(0, 5);
			return gatherName +"...";
		}
		return gatherName ;
	}
}
