package com.xinyin.baoming.controller.wap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.pojo.RestResp;
import com.xinyin.baoming.model.vo.TmAccount;
import com.xinyin.baoming.service.IAccountService;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 账号注册
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/wap/register")
public class WapRegisterController extends BaseController {

	private static final Logger logger = Logger.getLogger(WapRegisterController.class);

	@Autowired
	IAccountService iAccountService;

	/**
	 * 
	 * 账号注册初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping()
	public String init() {
		return Views.WAP_REGISTER_PAGE;
	}
	
	/**
	 * 验证图片验证码和手机号是否已注册
	 * 
	 * @param request
	 * @param response
	 * @param code
	 * @param code_name
	 */
	@RequestMapping({ "/verify/codeandphonenumber" })
	@ResponseBody
	public RestResp validate_code(String authcode, String mobile, HttpSession session) {
		RestResp resp = new RestResp();
		try {
			// 图片验证码超时验证
			Object obj = session.getAttribute(Constant.WAP_VERIFY_CODE_KEY);
			if(obj == null){
				resp.setCode(Constant.API_SESSION_EXPIRED);
				resp.setMsg(Message.PICTURE_CODE_EXPIRED);
				return resp;
			}
			// 图片验证码正确性验证
			if(!String.valueOf(obj).equals(authcode)){
				resp.setCode(Constant.API_VERIFY_CODE_MISTAKE);
				resp.setMsg(Message.PICTURE_VERIFY_CODE_MISTAKE);
				return resp;
			}
			// 手机号码验证
			if (StringUtils.isNotEmpty(mobile)) {
				TmAccount ta = new TmAccount();
				ta.setPhonenumber(mobile);
				PageList<TmAccount> rs = iAccountService.selectBySelective(ta);
				if (rs.getPaginator().getTotalCount() != 0) {
					resp.setCode(Constant.API_PHOEN_OWNER_CHANGE);
					resp.setMsg(Message.PHONENUMBER_EXISTS);
					return resp;
				}
			}else{
				// 返回手机号码空值提醒
				resp.setCode(Constant.API_ILLEGAL_PARAMETER_CODE);
				resp.setMsg(Message.PHONENUMBER_CANTBE_EMPTY);
				return resp;
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
	 * 
	 * 账号注册初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping("/do")
	@ResponseBody
	public RestResp register(String mobile, String pwd, String checkcode, HttpSession session) {
		RestResp resp = new RestResp();
		try{
			// 短信验证码超时校验
			Object smsCode = session.getAttribute(Constant.WAP_SMSCODE_KEY);
			if(smsCode == null){
				resp.setCode(Constant.API_SESSION_EXPIRED);
				resp.setMsg(Message.SMS_CODE_EXPIRED);
				return resp;
			}
			// 短信验证码正确性校验
			if(!String.valueOf(smsCode).equals(checkcode)){
				resp.setCode(Constant.API_SMS_CODE_MISTAKE);
				resp.setMsg(Message.SMS_VERIFY_CODE_MISTAKE);
				return resp;
			}
			TmAccount param = new TmAccount();
			param.setPhonenumber(mobile);
			// 若手机号已经注册，则删除原有账号
			iAccountService.deleteBySelective(param);
			param.setPassword(pwd);
			resp.setMsg(iAccountService.insertSelective(param));
			// 设定session登陆并到完善资料界面
			session.setAttribute(Constant.WAP_LOGIN_KEY, iAccountService.select(param).get(0));
			resp.setCode(Constant.API_SUCCESS_CODE);
			resp.setSucccb(Constant.DOMAIN_NAME + Views.WAP_USER_STUDENT_ADD + Constant.SUFFIX);
		}catch(Exception e){
			resp.setCode(Constant.API_EXCEPTION_CODE);
			resp.setMsg(Message.API_CALL_EXCEPTION);
			logger.error(Message.API_CALL_EXCEPTION, e);
		}
		return resp;
	}
}
