package com.xinyin.baoming.filter;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.xinyin.baoming.util.Sequence;

/**
 * 
 * 通过servletContext传递给容器下的其他项目
 * 
 * @author HeMingwei
 *
 */
public class SequenceServlet implements Servlet{

	/**
	 * 初始化序号生成器
	 * 
	 * @author HeMingwei
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// 得到生成器对象
		Sequence seq = new Sequence();
		// 设置类的Class
		config.getServletContext().setAttribute("class", Sequence.class);
		// 设置生成器对象
		config.getServletContext().setAttribute("seq", seq);
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
