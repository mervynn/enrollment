package com.xinyin.baoming.controller.wap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyin.baoming.model.pojo.RestResp;
import com.xinyin.baoming.model.vo.TmContent;
import com.xinyin.baoming.service.IContentService;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

@Controller
@RequestMapping(value = "/wap/content")
public class WapContentController {
	private static final Logger logger = Logger.getLogger(WapContentController.class);
	
	@Autowired
	IContentService iContentService;

	/**
	 * 内容列表
	 * 
	 * @param reqHeader
	 * @return
	 */
	@RequestMapping(value="/info",method=RequestMethod.GET)
	@ResponseBody
	public RestResp getContent(@RequestHeader Map<String,String> reqHeader,HttpServletRequest request,
			HttpServletResponse servletRespone){
		RestResp resp=new RestResp();
		try {
			String type = reqHeader.get("type");
			// 普通参数请求方式
			if(StringUtils.isEmpty(type)){
				type = request.getParameter("type");
			}
			if(StringUtils.isNotEmpty(type)){
				TmContent oc = iContentService.selectByType(type);
				if(oc != null){
					resp.setCode(Constant.API_SUCCESS_CODE);
					resp.setMsg(Message.API_CALL_SUCCESS);
					resp.setData(oc);
				}else{
					resp.setCode(Constant.API_DATA_NOT_EXISTS_CODE);
					resp.setMsg(Message.CONTENT_NOT_EXISTS);
				}
			}else{
				resp.setCode(Constant.API_ILLEGAL_PARAMETER_CODE);
				resp.setMsg(Message.TYPE_CANTBE_EMPTY);
			}
		} catch (Exception e) {
			resp.setCode(Constant.API_EXCEPTION_CODE);
			resp.setMsg(Message.API_CALL_EXCEPTION);
		}
		return resp;
	}
	
	/**
	 * 返回内容(html内容元素head body等)
	 * 
	 * @param reqHeader
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	public String getContent(@PathVariable("id") String id){
		String html= StringUtils.EMPTY;
		if(StringUtils.isEmpty(id)){
			return Message.MISS_PARAMETER;
		}
		try {
			TmContent content = iContentService.queryContentInfoById(id);
			if(content != null){
				html = content.getContent();
			}
		} catch (Exception e) {
			logger.error(Message.API_CALL_EXCEPTION, e);
		}
		return html;
	}
}
