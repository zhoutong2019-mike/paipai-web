package com.paipai.utils;

import java.util.regex.Pattern;

public class UserUtil {
	/**
	 * 1. 用户名格式校验 {4-16位字母、数字 、下划线｝
	 * @param username
	 * @return
	 */
	public static boolean checkUser(String username) {
		if(username == null) 
			return false;
		return Pattern.matches("^[a-z][a-zA-Z\\d_]{3,15}$", username);
	}
	/**
	 * 2.用户密码非空校验
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String password) {
		return password!=null && !"".equals(password);
	}
	/**
	 * 3.用户手机号格式校验｛11位手机号｝
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		if(mobile == null) 
			return false;
		return Pattern.matches("^1[3-9]\\d{9}$", mobile);
	}
	
	/**
	 * 4.用户邮箱号格式校验
	 * @param mobile
	 * @return
	 */
	public static boolean checkEmail(String email) {
		if(email == null) 
			return false;
		return Pattern.matches("^[a-zA-Z1-9][a-zA-Z0-9_]*@[a-zA-Z0-9]+\\.[a-zA-Z]+$", email);
	}
	/**
	 * 5. 用户身份证号格式校验
	 * @param idcard
	 * @return
	 */
	public static boolean checkIdcard(String idcard) {
		if(idcard == null) 
			return false;
		return Pattern.matches("(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$)", idcard);
	}
	
	/**
	 * 6. 用户类型校验
	 * @param utype
	 * @return
	 */
	public static boolean checkUserType(int utype) {
		return utype==0 || utype==1;
	}
	/**
	 * 7.用户公司的非空校验
	 * @param password
	 * @return
	 */
	public static boolean checkCompany(String company) {
		return company!=null && !"".equals(company);
	}
	
	/**
	 * 8.用户地址的非空校验
	 * @param password
	 * @return
	 */
	public static boolean checkAddress(String address) {
		return address!=null && !"".equals(address);
	}
	
	
}
