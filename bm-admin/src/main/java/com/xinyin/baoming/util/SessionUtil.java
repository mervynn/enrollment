package com.xinyin.baoming.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.util.constant.Constant;

public class SessionUtil {
	public static TsUser getLoginUser(ServletRequest servletRequest) {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		return (TsUser) req.getSession().getAttribute(Constant.LOGIN_KEY);
	}
}
