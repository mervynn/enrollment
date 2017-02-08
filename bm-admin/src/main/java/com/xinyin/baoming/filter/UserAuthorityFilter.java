package com.xinyin.baoming.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.xinyin.baoming.model.vo.TsMenu;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.util.constant.Constant;

/**
 * 用户权限拦截器
 * 
 * 1.白名单不做任何拦截
 * 2.未在白名单的做SESSION失效拦截
 * 3.未在白名单的且没有权限的请求(XmlHttpRequest和Form请求方式)
 * 
 * @author HeMingwei
 *
 */
public class UserAuthorityFilter implements Filter {
	private static final Logger LOGGER = Logger.getLogger(UserAuthorityFilter.class);
	private static List<String> dislogin = new ArrayList<String>();
	static {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(UserAuthorityFilter.class.getClassLoader().getResourceAsStream("dislogin.xml"));
			NodeList urls = doc.getElementsByTagName("url");
			if (urls != null && urls.getLength() != 0) {
				for (int i = 0; i < urls.getLength(); i++) {
					dislogin.add(urls.item(i).getFirstChild().getNodeValue());
				}
			}
		} catch (Exception e) {
			LOGGER.error("init dislogin error", e);
		}
	}

	@Override
	public void destroy() {

	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		long st = System.currentTimeMillis();
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		TsUser user = (TsUser) request.getSession().getAttribute(Constant.LOGIN_KEY);
		String reqURI = request.getRequestURI().substring(request.getContextPath().length());
		if (dislogin.contains(reqURI) || (StringUtils.isNotEmpty(reqURI) && reqURI.startsWith(Constant.API_START_URI))) {
			chain.doFilter(req, resp);
		} else if(user != null){
			List<TsMenu> rules = user.getHasMenu();
			boolean isPass = false;
			for (int i = 0,length = rules.size(); i < length; i++) {
				String curRule = rules.get(i).getMenuHref();
				// 权限一致或从属于用户已有权限(权限不细化到按钮和CRUD)则通过
				if(reqURI.equals(curRule) || 
						(curRule.split("/").length == 3 && reqURI.indexOf(curRule.substring(0, curRule.length()-6)) != -1)) {
					isPass = true;
					break;
				}
			}
			// 判断用户是否有该url的执行权限
			if (isPass) {
				chain.doFilter(req, resp);
			} else {
				LOGGER.warn("用户：" + user.getUsername() + "越权访问 :\t" + reqURI);
				// 越权访问处理
				if (request.getHeader("x-requested-with") != null 
						&& request.getHeader("x-requested-with").equals("XMLHttpRequest")) { // AJAX请求 
					response.setHeader("sessionstatus", "unauthorized"); 
				} else { 
					response.sendRedirect(((HttpServletRequest) req).getContextPath()+ "/403.jsp");
				}
			}
		} else {
			// Session 失效处理
			if (request.getHeader("x-requested-with") != null 
					&& request.getHeader("x-requested-with").equals("XMLHttpRequest")) { // AJAX请求 
				response.setHeader("sessionstatus", "timeout"); 
			} else { 
				response.sendRedirect(((HttpServletRequest) req).getContextPath()+ "/");
			}
		}
		LOGGER.debug(reqURI + "\tTime \t" + (System.currentTimeMillis() - st));
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}
}
