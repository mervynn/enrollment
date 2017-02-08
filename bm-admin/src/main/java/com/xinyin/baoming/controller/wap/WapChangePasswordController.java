package com.xinyin.baoming.controller.wap;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.vo.TmAccount;
import com.xinyin.baoming.service.IAccountService;
import com.xinyin.baoming.service.IClassService;
import com.xinyin.baoming.util.CommonUtils;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 找回密码
 * 
 * 创建时间： 2016-08-09
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/wap/user/changepassword")
public class WapChangePasswordController extends BaseController {

	private static final Logger logger = Logger.getLogger(WapChangePasswordController.class);

	@Autowired
	IClassService iClassService;
	
	@Autowired
	IAccountService iAccountService;
	
	/**
	 * 第一步
	 *
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/one")
	public String one(Model model, HttpSession session) {
		try{
			TmAccount ta = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(ta == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			model.addAttribute("phonenumber", CommonUtils.encodePhonenumber(ta.getPhonenumber()));
			// session中设定手机号，方便下一步发送验证码，调用公共方法
			session.setAttribute(Constant.WAP_PHONENUMBER_KEY, ta.getPhonenumber());
		} catch (Exception e) {
			logger.error(Message.API_CALL_EXCEPTION, e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_USER_CHANGEPASSWORD_ONE;
	}
	
	/**
	 * 第二步
	 *
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/two")
	public String two(HttpSession session, Model model) {
		TmAccount ta = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
		if(ta == null){
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_USER_CHANGEPASSWORD_TWO;
	}
	
	/**
	 * 第三步
	 *
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/dochangepassword")
	public String dochangepassword(HttpSession session, Model model, String password) {
		try{
			TmAccount ta = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(ta == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			TmAccount paramToUpdate = new TmAccount();
			paramToUpdate.setId(ta.getId());
			paramToUpdate.setPassword(password);
			iAccountService.updateByPrimaryKeySelective(paramToUpdate);
			session.removeAttribute(Constant.WAP_LOGIN_KEY);
			if (session != null) {
				try {
					session.invalidate(); // 将session置为无效
				} catch (IllegalStateException e) {
					logger.debug("logout: session already invalidated");
				}
			}
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}catch(Exception e){
			logger.error(Message.API_CALL_EXCEPTION, e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
	}

}
