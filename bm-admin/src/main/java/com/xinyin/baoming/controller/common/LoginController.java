package com.xinyin.baoming.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TsMenu;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.service.IMenuService;
import com.xinyin.baoming.service.IUserService;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

@Controller
@RequestMapping
public class LoginController {
	
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	IUserService iSysAdminUserService;
	
	@Autowired
	IMenuService iSysMenuService;
	/**
	 * 登录
	 *
	 * @param request
	 * @param session
	 * @param model
	 * @param userName
	 *          用户名
	 * @param password
	 *          密码
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest req,HttpServletResponse resp,HttpSession session, Model model,
			@RequestParam(required = false, value = "username") String username,
			@RequestParam(required = false, value = "password") String password) {
		TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
		// 判断session中是否有用户信息，如果已经登录直接跳到home页面
		if (user!= null) {
			// 添加菜单，图标等前台资源
			session.setAttribute(Constant.LOGIN_KEY, user);
			return Views.HOME;
		}
		try {
			if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
				return Views.LOGIN_PAGE;
			}
			List<TsMenu> roots = null;
			// 验证用户
			TsUser userDb = iSysAdminUserService.queryUser(username, password);
			if(userDb == null){
				model.addAttribute("msg", Message.WRONG_USERNAME_OR_PASSWORD);
				return Views.LOGIN_PAGE;
			}
			roots = iSysMenuService.getRootMenusByUserName(username);
			session.setAttribute(Constant.LOGIN_KEY, userDb);
			session.setAttribute(Constant.MENU_ROOTS, roots);
			// 设定pjax，以便session失效后的下次登陆可以正常加载layout
			session.setAttribute("HTTP_X_PJAX", false);
		} catch (ServiceException e) {
			logger.error(Constant.LOGIN_EXCEPTION, e);
			return Views.LOGIN_PAGE;
		}
		return Views.HOME;
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
		TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
		if (user == null) {
			return Views.LOGIN_PAGE;
		}
		try {
			session.removeAttribute(Constant.LOGIN_KEY);
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
		return Views.LOGIN_PAGE;
	}
	
}
