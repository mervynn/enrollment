package com.xinyin.baoming.controller.wap;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.pojo.RestResp;
import com.xinyin.baoming.model.vo.TmAccount;
import com.xinyin.baoming.model.vo.TmStudent;
import com.xinyin.baoming.service.IAccountService;
import com.xinyin.baoming.service.IStudentService;
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
@RequestMapping(value = "/wap/user/getpassword")
public class WapGetPasswordController extends BaseController {

	private static final Logger logger = Logger.getLogger(WapGetPasswordController.class);
	
	@Autowired
	IAccountService iAccountService;
	
	@Autowired
	IStudentService iStudentService;
	
	/**
	 * 第一步
	 *
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/one")
	public String one(Model model) {
		return Views.WAP_USER_GETPASSWORD_ONE;
	}
	
	/**
	 * 第一步校验
	 *
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/checkaccount")
	@ResponseBody
	public RestResp checkaccount(String name, String code, HttpSession session, Model model) {
		RestResp resp = new RestResp();
		try {
			// 验证码
			if(StringUtils.isNotEmpty(code) && !code.equals(session.getAttribute(Constant.WAP_VERIFY_CODE_KEY))){
				resp.setMsg(Message.PICTURE_VERIFY_CODE_MISTAKE);
				resp.setCode(Constant.API_VERIFY_CODE_MISTAKE);
				return resp;
			}
			// 手机号码校验
			TmAccount param = new TmAccount();
			param.setPhonenumber(name);
			List<TmAccount> list = iAccountService.select(param);
			if(0 == list.size()){
				resp.setMsg(Message.PHONENUMBER_NOT_EXISTS);
				resp.setCode(Constant.API_DATA_NOT_EXISTS_CODE);
				return resp;
			}
			session.setAttribute(Constant.WAP_PHONENUMBER_KEY, name);
			resp.setCode(Constant.API_SUCCESS_CODE);
			resp.setSucccb(Constant.DOMAIN_NAME + Views.WAP_USER_GETPASSWORD_TWO + Constant.SUFFIX);
		} catch (Exception e) {
			resp.setCode(Constant.API_EXCEPTION_CODE);
			resp.setMsg(Message.API_CALL_EXCEPTION);
			logger.error(Message.API_CALL_EXCEPTION, e);
		}
		return resp;
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
		String phonenumber = CommonUtils.null2String(session.getAttribute(Constant.WAP_PHONENUMBER_KEY));
		String number = CommonUtils.encodePhonenumber(phonenumber);
		model.addAttribute("noticeMsg", "请输入"+ number +"收到的短信验证码");
		return Views.WAP_USER_GETPASSWORD_TWO;
	}
	
	/**
	 * 第三步
	 *
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/three")
	public String three(Model model) {
		return Views.WAP_USER_GETPASSWORD_THREE;
	}
	
	/**
	 * 第三步验证(通过学生名字，验证通过则修改密码)
	 *
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/studentresetpwd")
	@ResponseBody
	public RestResp studentresetpwd(HttpSession session, String receiver, String password, Model model) {
		RestResp resp = new RestResp();
		try{
			Object phonenumber = session.getAttribute(Constant.WAP_PHONENUMBER_KEY);
			// 离开太久判断
			if(phonenumber == null){
				resp.setCode(Constant.API_SESSION_EXPIRED);
				resp.setMsg(Message.SESSION_EXPIRED);
				return resp;
			}
			TmAccount param = new TmAccount();
			param.setPhonenumber(String.valueOf(phonenumber));
			List<TmAccount> alist = iAccountService.select(param);
			String accountId = alist.get(0).getId();
			TmStudent param1 = new TmStudent();
			param1.setAccountId(accountId);
			List<TmStudent> sList = iStudentService.selectExactlyBySelective(param1);
			for(TmStudent obj : sList){
				if(receiver.equals(obj.getName())){
					// 修改密码
					TmAccount paramToUpdate = new TmAccount();
					paramToUpdate.setId(accountId);
					paramToUpdate.setPassword(password);
					iAccountService.updateByPrimaryKeySelective(paramToUpdate);
					resp.setCode(Constant.API_SUCCESS_CODE);
					resp.setSucccb(Constant.DOMAIN_NAME + Views.WAP_INDEX_PAGE + Constant.SUFFIX);
					return resp;
				}
			}
			// 验证失败时返回提示
			resp.setCode(Constant.API_DATA_NOT_EXISTS_CODE);
			resp.setMsg(Message.STUDENT_NAME_VERIFY_MISTAKE);
		}catch(Exception e){
			resp.setCode(Constant.API_EXCEPTION_CODE);
			resp.setMsg(Message.API_CALL_EXCEPTION);
			logger.error(Message.API_CALL_EXCEPTION, e);
		}
		return resp;
	}
	
}
