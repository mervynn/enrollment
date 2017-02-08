package com.xinyin.baoming.controller.wap;

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
@RequestMapping(value = "/wap/user/center")
public class WapUserCenterController extends BaseController {

	private static final Logger logger = Logger.getLogger(WapUserCenterController.class);

	@Autowired
	IAccountService iAccountService;
	
	@Autowired
	IStudentService iStudentService;

	/**
	 * 
	 * 个人中心初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping()
	public String init(Model model, HttpSession session) {
		try {
			TmAccount ta = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(ta == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			// 通过默认学生id，取得学生姓名
			if(StringUtils.isNotEmpty(ta.getDefultStudentId())){
				TmStudent ts = iStudentService.selectByPrimaryKey(ta.getDefultStudentId());
				model.addAttribute("curStudent", ts);
			}
			model.addAttribute("user", ta);
		} catch (Exception e) {
			logger.error(Message.API_CALL_EXCEPTION, e);
		}
		return Views.WAP_USER_CENTER;
	}
	
	
	/**
	 * 
	 * 设置默认学生
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping("/setdefaultstudent")
	@ResponseBody
	public RestResp setDefaultStudent(HttpSession session, String studentId) {
		RestResp resp = new RestResp();
		try {
			TmAccount ta = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			ta.setDefultStudentId(studentId);
			iAccountService.updateByPrimaryKeySelective(ta);
			resp.setCode(Constant.API_SUCCESS_CODE);
			resp.setMsg(Message.API_CALL_SUCCESS);
		} catch (Exception e) {
			resp.setCode(Constant.API_EXCEPTION_CODE);
			resp.setMsg(Message.API_CALL_EXCEPTION);
			logger.error(Message.API_CALL_EXCEPTION, e);
		}
		return resp;
	}
	
}
