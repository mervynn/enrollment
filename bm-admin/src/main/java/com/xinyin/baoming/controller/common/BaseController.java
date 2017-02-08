package com.xinyin.baoming.controller.common;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyin.baoming.util.CommonUtils;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * Controller 基础类
 * 
 * @author
 * 
 */
@Controller
@RequestMapping(value="/base")
public class BaseController {

	private static final Logger logger = Logger.getLogger(BaseController.class);
	
	/**
	 * 设定 PJAX 请求标识到Session
	 * @param req
	 */
	@RequestMapping(value="/pjax")
	@ResponseBody
	public String setSession(HttpServletRequest req){
		req.getSession().setAttribute("HTTP_X_PJAX", "true");
		return StringUtils.EMPTY;
	}
	
	/**
	 * 上传H5图文
	 * 
	 * @param req
	 * @throws Exception 
	 */
	@RequestMapping(value="/upload")
	@ResponseBody
	public String upload(HttpServletRequest req, String content) throws Exception{
		// 防止乱码做前端url加密，后端解密
		String webFilePath = CommonUtils.writeFileByContent(req, Constant.GUIDE_PATH, 
				URLDecoder.decode(content,"utf-8"));
		return webFilePath;
	}
	
	/**
	 * 
	 * @param url
	 * @param domType
	 * @param targetName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/jsoup")
	@ResponseBody
	public String jsoup(String url, String domType, String targetName){
		String returnDom = StringUtils.EMPTY;
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			if("id".equals(domType)){
				returnDom = doc.getElementById(targetName).html();
			}else if("tag".equals(domType)){
				returnDom = doc.getElementsByTag(targetName).html();
			}else if("class".equals(domType)){
				returnDom = doc.getElementsByClass(targetName).html();
			}else if("name".equals(domType)){
				returnDom = doc.getElementsByAttribute(targetName).html();
			}
		} catch (Exception e) {
			logger.error(Message.LOG_URI_EXCEPTION + url);
			returnDom = Message.URI_EXCEPTION;
		}
		return returnDom;
	}
	
	/**
	 * 得到主机地址
	 * 
	 * @param request
	 * @return
	 */
	public String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
}
