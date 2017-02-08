package com.xinyin.baoming.controller.wap;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyin.baoming.model.pojo.RestResp;
import com.xinyin.baoming.model.vo.TmAccount;
import com.xinyin.baoming.model.vo.TmStudent;
import com.xinyin.baoming.model.vo.TtShoppingCart;
import com.xinyin.baoming.service.IAccountService;
import com.xinyin.baoming.service.IShoppingCartService;
import com.xinyin.baoming.service.IStudentService;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

@Controller
@RequestMapping(value = "/wap")
public class WapLoginController {
	
	private static final Logger logger = Logger.getLogger(WapLoginController.class);
	
	@Autowired
	IAccountService iAccountService;
	
	@Autowired
	IStudentService iStudentService;
	
	@Autowired
	IShoppingCartService iShoppingCartService;

	/**
	 * 登陆界面初始化(登陆时跳转到首页)
	 *
	 * @param request
	 * @param session
	 * @param model
	 * @param phonenumber
	 *          用户名
	 * @param password
	 *          密码
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/index")
	public String index(HttpSession session, String phonenumber, String password) {
		TmAccount user = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
		// 判断session中是否有用户信息，如果已经登录直接跳到课程选择
		if (user!= null) {
			// 清空session中的分类信息
			user.setClassTypeId(null);
			session.setAttribute(Constant.WAP_LOGIN_KEY, user);
			return "redirect:" + Views.WAP_CLASS_LIST + Constant.SUFFIX;
		}else{
			// 否则跳到登陆界面
			return Views.WAP_INDEX_PAGE;
		}
	}
	
	/**
	 * 会员登录
	 *
	 * @param request
	 * @param session
	 * @param model
	 * @param phonenumber
	 *          用户名
	 * @param password
	 *          密码
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public RestResp login(TmAccount record, String authcode, HttpSession session) {
		RestResp resp = new RestResp();
		try {
			List<TmAccount> accountList = iAccountService.select(record);
			// 验证码判断
			if(StringUtils.isNotEmpty(authcode) && !authcode.equals(session.getAttribute(Constant.WAP_VERIFY_CODE_KEY))){
				resp.setMsg(Message.PICTURE_VERIFY_CODE_MISTAKE);
				resp.setCode(Constant.API_VERIFY_CODE_MISTAKE);
				return resp;
			}
			// 用户名密码判断
			if(accountList != null && accountList.size() != 0){
				
				TmStudent param = new TmStudent();
				param.setAccountId(accountList.get(0).getId());
				List<TmStudent> list = iStudentService.selectExactlyBySelective(param);
				// 补全资料
				if(list == null || list.size() == 0){
					resp.setSucccb(Constant.DOMAIN_NAME + Views.WAP_USER_STUDENT_ADD + Constant.SUFFIX);
				// 否则，判断购课车有无商品
				}else{
					TtShoppingCart ptsc = new TtShoppingCart();
					ptsc.setStudentId(accountList.get(0).getDefultStudentId());
					List<TtShoppingCart> shoppingCart = iShoppingCartService.selectExactlyBySelective(ptsc);
					// 购课车无数据跳转到课程列表界面
					if(shoppingCart == null || shoppingCart.size() == 0){
						resp.setSucccb(Constant.DOMAIN_NAME + Views.WAP_CLASS_LIST + Constant.SUFFIX);
					// 购课车有数据跳转到购课车列表界面
					}else{
						resp.setSucccb(Constant.DOMAIN_NAME + Views.WAP_USER_SHOPPINGCART + Constant.SUFFIX);
					}
				}
				// 设置接口调用成功，业务可继续流转
				resp.setCode(Constant.API_SUCCESS_CODE);
				// 设置session信息
				session.setAttribute(Constant.WAP_LOGIN_KEY, accountList.get(0));
			}else{
				resp.setCode(Constant.API_DATA_NOT_EXISTS_CODE);
				resp.setMsg(Message.WRONG_USERNAME_OR_PASSWORD);
			}
			
		} catch (Exception e) {
			resp.setCode(Constant.API_EXCEPTION_CODE);
			resp.setMsg(Message.API_CALL_EXCEPTION);
			logger.error(Message.API_CALL_EXCEPTION, e);
		}
		return resp;
	}

	/**
	 * 用户登出
	 *
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpSession session, Model model) {
		TmAccount user = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
		if (user == null) {
			return Views.WAP_INDEX_PAGE;
		}
		try {
			session.removeAttribute(Constant.WAP_LOGIN_KEY);
			if (session != null) {
				try {
					session.invalidate(); // 将session置为无效
				} catch (IllegalStateException e) {
					logger.debug("logout: session already invalidated");
				}
			}
		} catch (Exception e) {
			logger.error(Constant.LOG_OUT_EXCEPTION, e);
		}
		return Views.WAP_INDEX_PAGE;
	}
	
}
