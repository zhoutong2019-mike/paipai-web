package com.paipai.utils;


public class MikeUtil {
	//封装方法1  通过类型分页查找

	//2.验证字符串非空
	public static boolean notNullOrEmpty(String str) {
		if(str==null||"".equals(str))
			return false;
		return true;
	}

}
