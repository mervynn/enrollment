package com.xinyin.baoming.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class StringUtils {
	public static final Logger LOGGER = Logger.getLogger(StringUtils.class);
	/**
	 * 判断多个参数中是否有空值
	 * @param args
	 * @return
	 */
	public static boolean isHasEmpty(String... args) {
		for (String str : args) {
			if(str == null || str.trim().length() == 0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断两个字符串是否相等
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEquals(String a, String b) {
		if(a == b)
			return true;
		if(a != null && b != null)
			return a.trim().equals(b.trim());
		if(a != null)
			return a.equals(b);
		return false;
	}
	
	/**
	 * 提示成功信息
	 * @param resp
	 * @param msg
	 */
	public static void alertSuccess(HttpServletResponse resp, String msg) {
		resp.reset();
		resp.setContentType("text/html;charset=utf-8");
		try {
			resp.getWriter().write("<script>alert('" + msg + "');location.href=document.referrer;</script>");
		} catch (IOException e) {
			LOGGER.error("IO异常,无法写出", e);
		}
	}
}
