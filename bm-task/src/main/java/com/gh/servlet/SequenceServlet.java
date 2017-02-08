package com.gh.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.gh.util.CommonUtils;

/**
 * 
 * 通过ServletContext拿到主后台项目的序号生成器
 * 
 * @author HeMingwei
 *
 */
public class SequenceServlet implements Servlet{
	private static final Logger logger = Logger.getLogger(SequenceServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context= config.getServletContext().getContext("/admin");
		try {
			if(context != null){
				Class<?> clazz = (Class<?>) context.getAttribute("class");
				Object obj = context.getAttribute("seq");
				if(clazz != null && obj != null){
					Method method = clazz.getMethod("nextId");
					CommonUtils.obj = obj;
					CommonUtils.method = method;
				}else{
					logger.warn("请确认站点admin设定class和seq参数!");
				}
			}else{
				logger.warn("请确认server.xml做了本站点的ServletContext同步配置!");
			}
		} catch (Exception e) {
			logger.error("同步ServletContext异常",e);
		}	
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
