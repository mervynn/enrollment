package com.xinyin.baoming.controller.wap;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.vo.TmAccount;
import com.xinyin.baoming.model.vo.TmClassType;
import com.xinyin.baoming.service.IClassTypeService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 课程类型信息管理
 * 
 * 创建时间： 2016-08-12
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/wap/classtype")
public class WapClassTypeController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(WapClassTypeController.class);

	@Autowired
	IClassTypeService iClassTypeService;
	
	@Autowired
	IContextService iContextService;

	/**
	 * 
	 * 课程类型信息管理初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value="")
	public String init(Model model, HttpSession session){
		try{
			TmAccount account = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(account == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			// 左侧根目录
			List<TmClassType> leftTree = iContextService.getLeftTree();
			model.addAttribute("leftTree", leftTree);
			// 根节点id
			String rootId = leftTree.get(0).getId();
			// 获取第一个根节点的右子树
			model.addAttribute("rightTree", iContextService.getClassTypeList(rootId));
			// 当前九宫图父级菜单
			model.addAttribute("lastParent", iContextService.getClassTypeById(rootId));
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		
		return Views.WAP_CLASSTYPE;
	}
	
	/**
	 * 类型详情(有子菜单时候显示菜单，没有则session设定课程类型id，同时跳转到课程列表界面)
	 *
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/{id}")
	public String detail(@PathVariable("id") String id, Model model, HttpSession session) {
		try{
			TmAccount ta = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(ta == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			List<TmClassType> list = iContextService.getClassTypeList(id);
			if(list.size() != 0){
				model.addAttribute("leftTree", iContextService.getLeftTree());
				model.addAttribute("rightTree", list);
				// 当前九宫图父级菜单
				model.addAttribute("lastParent", iContextService.getClassTypeById(id));
			}else{
				ta.setClassTypeId(id);
				session.setAttribute(Constant.WAP_LOGIN_KEY, ta);
				return "redirect:" + Views.WAP_CLASS_LIST + Constant.SUFFIX;
			}
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_CLASSTYPE;
	}
}
