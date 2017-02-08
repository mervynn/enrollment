package com.xinyin.baoming.controller.wap;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.pojo.RestResp;
import com.xinyin.baoming.util.SMSUtil;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 发送短信验证码
 * 
 * 创建时间： 2016-08-09
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/wap/sms")
public class WapSmsController extends BaseController {

	private static final Logger logger = Logger.getLogger(WapSmsController.class);
	
	/**
	 * 发送短信验证码、同时保存到session中
	 *
	 * @param model
	 * @param mobile || session.getAttribute(Constant.WAP_PHONENUMBER_KEY) 从session或请求参数头上拿到手机号码
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "sendcode")
	@ResponseBody
	public RestResp checkaccount(HttpSession session, Model model, String mobile) {
		RestResp resp = new RestResp();
		try {
			Object phonenumber = session.getAttribute(Constant.WAP_PHONENUMBER_KEY);
			String s = phonenumber != null ? (String) phonenumber : StringUtils.isBlank(mobile)?StringUtils.EMPTY : mobile;
			if(StringUtils.isNotEmpty(s)){
				String smscode = SMSUtil.sendTemplateSMS(s);
				//String smscode = "123456";
				session.setAttribute(Constant.WAP_SMSCODE_KEY, smscode);
			}
			resp.setCode(Constant.API_SUCCESS_CODE);
		} catch (Exception e) {
			resp.setCode(Constant.API_EXCEPTION_CODE);
			resp.setMsg(Message.API_CALL_EXCEPTION);
			logger.error(Message.API_CALL_EXCEPTION, e);
		}
		return resp;
	}
	
	/**
	 * 验证短信验证码
	 *
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "verify")
	@ResponseBody
	public RestResp verify(Model model, String smscodee, HttpSession session) {
		RestResp resp = new RestResp();
		try {
			String smscode = (String) session.getAttribute(Constant.WAP_SMSCODE_KEY);
			if(smscodee != null && smscodee.equals(smscode)){
				resp.setCode(Constant.API_SUCCESS_CODE);
				resp.setSucccb(Constant.DOMAIN_NAME + Views.WAP_USER_GETPASSWORD_THREE + Constant.SUFFIX);
			}else{
				resp.setCode(Constant.API_VERIFY_CODE_MISTAKE);
				resp.setMsg(Message.SMS_VERIFY_CODE_MISTAKE);
			}
		} catch (Exception e) {
			resp.setCode(Constant.API_EXCEPTION_CODE);
			resp.setMsg(Message.API_CALL_EXCEPTION);
			logger.error(Message.API_CALL_EXCEPTION, e);
		}
		return resp;
	}

}
