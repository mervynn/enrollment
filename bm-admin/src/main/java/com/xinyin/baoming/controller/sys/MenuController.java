package com.xinyin.baoming.controller.sys;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.vo.TsMenu;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.service.IMenuService;
import com.xinyin.baoming.util.SessionUtil;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 菜单表管理
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/sys/menu")
public class MenuController extends BaseController {

	@Autowired
	IMenuService iMenuService;
	
	/**
	 * 菜单管理初始化
	 * @param authRole
	 * @return
	 */
	@RequestMapping(value="")
	public String init(Model model){
		return Views.SYS_MENU;
	}
	
	/**
	 * 获得菜单json
	 */
	@RequestMapping("/init")
	@ResponseBody
	public String init(HttpServletResponse resp) {
		List<TsMenu> menus = iMenuService.getRootMenus();
		JsonArray array = new JsonArray();
		makeTree(menus, array);
		JsonObject data = new JsonObject();
		data.add("data", array);
		return data.toString();
	}
	
	/**
	 * 新增菜单
	 * @param menu
	 * @param parentId
	 */
	@RequestMapping("/add")
	@ResponseBody
	public void add(TsMenu menu,String type, HttpSession session) {
		String parentId = menu.getMenuId();
		if("1".equals(type)) {
			menu.setMenuId(null);
			iMenuService.addMenu(menu, parentId);
		} else {
			iMenuService.addMenu(menu, parentId);
		}
		// 更新当前用户session，省去二次登录，直接刷新界面即可
		TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
		List<TsMenu> roots = iMenuService.getRootMenusByUserName(user.getUsername());
		session.setAttribute(Constant.MENU_ROOTS, roots);
	}
	/**
	 * 移除菜单
	 * @param menu
	 * @param resp
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public void remove(TsMenu menu, HttpSession session){
		iMenuService.removeMenu(menu);
		// 更新当前用户session，省去二次登录，直接刷新界面即可
		TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
		List<TsMenu> roots = iMenuService.getRootMenusByUserName(user.getUsername());
		session.setAttribute(Constant.MENU_ROOTS, roots);
	}
	/**
	 * 修改菜单
	 * @param menu
	 * @param resp
	 * @throws SQLException 
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public void modify(TsMenu menu, HttpSession session){
		TsMenu opt = iMenuService.getMenuById(menu.getMenuId(), false);
		opt.setMenuHref(menu.getMenuHref());
		opt.setMenuName(menu.getMenuName());
		opt.setSort(menu.getSort());
		opt.setIsMenu(menu.getIsMenu());
		opt.setJspName(menu.getJspName());
		opt.setJsSel(menu.getJsSel());
		iMenuService.modifyMenu(opt);
		// 更新当前用户session，省去二次登录，直接刷新界面即可
		TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
		List<TsMenu> roots = iMenuService.getRootMenusByUserName(user.getUsername());
		session.setAttribute(Constant.MENU_ROOTS, roots);
	}

	/**
	 * 菜单Top
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/topMenu")
	public String topMenu(HttpServletRequest req, HttpServletResponse resp) {
		List<TsMenu> roots = (List<TsMenu>) req.getSession().getAttribute(Constant.MENU_ROOTS);
		if(roots == null) {
			roots = iMenuService.getRootMenusByUserName(SessionUtil.getLoginUser(req).getUsername());
			req.getSession().setAttribute(Constant.MENU_ROOTS, roots);
		}
		req.setAttribute("menus", roots);
		return "topMenu";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/leftMenu")
	public String leftMenu(HttpServletRequest req,TsMenu target, HttpServletResponse resp) {
		List<TsMenu> roots = (List<TsMenu>) req.getSession().getAttribute(Constant.MENU_ROOTS);
		if(roots == null) {
			roots = iMenuService.getRootMenusByUserName(SessionUtil.getLoginUser(req).getUsername());
			req.getSession().setAttribute(Constant.MENU_ROOTS, roots);
		}
		for (TsMenu menu : roots) {
			if(menu.getMenuId().equals(target.getMenuId())) {
				req.setAttribute("menus", menu.getMenus());
			}
		}
		return "leftMenu";
	}
	/**
	 * 形成树结构
	 * @param menus
	 * @param array
	 */
	private void makeTree(List<TsMenu> menus,JsonArray array) {
		for (TsMenu menu : menus) {
			JsonObject temp = new JsonObject();
			temp.addProperty("title", menu.getMenuName());
			JsonObject attr = new JsonObject();

			attr.addProperty("menuName", menu.getMenuName());
			attr.addProperty("menuId", menu.getMenuId());
			attr.addProperty("menuHref", menu.getMenuHref());
			attr.addProperty("sort", menu.getSort());
			attr.addProperty("isMenu", menu.getIsMenu());
			attr.addProperty("jspName", menu.getJspName());
			attr.addProperty("jsSel", menu.getJsSel());
			temp.add("attr", attr);
			
			List<TsMenu> childs = menu.getMenus();
			JsonObject data = new JsonObject();
			data.add("data", temp);
			if(childs != null && !childs.isEmpty()) {
				JsonArray datas = new JsonArray();
				makeTree(new ArrayList<TsMenu>(menu.getMenus()), datas);
				data.add("children", datas);
			}
			array.add(data);
		}
	}
}
