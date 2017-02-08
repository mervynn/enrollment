package com.xinyin.baoming.controller.sys;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.vo.TsMenu;
import com.xinyin.baoming.model.vo.TsRole;
import com.xinyin.baoming.model.vo.TsRoleAuth;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.service.IMenuService;
import com.xinyin.baoming.service.IRoleService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 角色表管理
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {

private static final Logger logger = Logger.getLogger(RoleController.class);
	
	@Autowired
	IRoleService iSysAuthRoleService;
	
	@Autowired
	IMenuService iSysMenuService;
	
	/**
	 * 角色管理初始化
	 * @param authRole
	 * @return
	 */
	@RequestMapping(value="")
	public String init(Model model){
		return Views.SYS_ROLE;
	}
	
	/**
	 * 角色(返回Json)
	 * @param authRole
	 * @throws Exception 
	 * */
	@RequestMapping(value="/data")
	@ResponseBody
	public Object[] querySysAuthRoleJson(TsRole authRole,
			@RequestParam(value="page",required = true, defaultValue = Constant.DEFAULT_PAGE) int page,  
			@RequestParam(value="size",required = true, defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value="col[0]",required = false, defaultValue = Constant.DESC_CODE) String sort0,
			@RequestParam(value="col[1]",required = false) String sort1, @RequestParam(value="col[2]",required = false) String sort2,
			@RequestParam(value="col[3]",required = false) String sort3, @RequestParam(value="col[4]",required = false) String sort4,
			@RequestParam(value="col[5]",required = false) String sort5, @RequestParam(value="fcol[0]",required = false) String roleId, 
			@RequestParam(value="fcol[1]",required = false) String roleName, @RequestParam(value="fcol[2]",required = false) String createBy, 
			@RequestParam(value="fcol[3]",required = false) String createDate, @RequestParam(value="fcol[4]",required = false) String updateBy, 
			@RequestParam(value="fcol[5]",required = false) String updateDate,HttpServletRequest req,HttpServletResponse resp) throws Exception{
		// 返回数据集合
		Object[] arr = new Object[2];
		try {
			// 封装排序参数
			Map<String,String> map = new LinkedHashMap<String, String>(); // 先进先出
			map.put("ROLE_NAME", sort1);
			map.put("CREATE_BY", sort2);
			map.put("CREATE_DATE", sort3);
			map.put("UPDATE_BY", sort4);
			map.put("UPDATE_DATE", sort5);
			map.put("ROLE_ID+1", sort0);
			authRole.setOrderSegment(Tools.toOrderSegment(map));
			PageList<TsRole> list = null;
			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
			authRole.setPage(page);
			authRole.setPageSize(pageSize);
			authRole.setRoleId(roleId);
			authRole.setRoleName(roleName);
			authRole.setCreateBy(createBy);
			authRole.setCreateDateStr(createDate);
			authRole.setUpdateBy(updateBy);
			authRole.setUpdateDateStr(updateDate);
			list = iSysAuthRoleService.selectAuthRoleList(authRole);
			Object[][] objs = new Object[list.size()][6];
			for(int i = 0;i<list.size();i++){
				objs[i][0] = Tools.rowSelectionSet(list.get(i).getRoleId());
				objs[i][1] = Tools.rowSelectionSet(list.get(i).getRoleName());
				objs[i][2] = Tools.rowSelectionSet(list.get(i).getCreateBy());
				objs[i][3] = Tools.rowSelectionSet(list.get(i).getCreateDate() !=null ?
					sdf.format(list.get(i).getCreateDate()):StringUtils.EMPTY);
				objs[i][4] = Tools.rowSelectionSet(list.get(i).getUpdateBy());
				objs[i][5] = Tools.rowSelectionSet(list.get(i).getUpdateDate() !=null ?
					sdf.format(list.get(i).getUpdateDate()):StringUtils.EMPTY);
			}
			arr[0]=(list.getPaginator().getTotalCount());
			arr[1]=objs;
		} catch (Exception e) {
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			throw e;
		}
		return arr;
	}

	/**
	 * 添加角色
	 * @param TsRole
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session, TsRole authRole) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			authRole.setCreateBy(user.getUsername());
			authRole.setUpdateBy(user.getUsername());
			resMsg = iSysAuthRoleService.save(authRole);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 通过版本id取得角色
	 * @param id
	 */
	@RequestMapping(value="/data/{id}")
	@ResponseBody
	public TsRole queryById(@PathVariable("id") String id) {
		TsRole authRole = null;
		try{
			authRole = iSysAuthRoleService.queryAuthRoleInfoById(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		return authRole;
	}
	
	/**
	 * 修改角色
	 * @param TsRole
	 */
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpSession session, TsRole authRole) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			authRole.setUpdateBy(user.getUsername());
			resMsg = iSysAuthRoleService.modify(authRole);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 通过角色id取得菜单树
	 * @param id
	 */
	@RequestMapping(value="/auth/data/{id}")
	@ResponseBody
	public String authJson(@PathVariable("id") String id) {
		List<TsRoleAuth> rules = iSysAuthRoleService.selectAuthRoleRules(id);
		List<TsMenu> roots = iSysMenuService.getRootMenus();
		JsonArray jsonArr = new JsonArray();
		for (TsMenu root : roots) {
			JsonObject jsonObj = mkRootJson(root, rules);
			jsonArr.add(jsonObj);
		}
		return jsonArr.toString();
	}
	
	/**
	 * 权限更新
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/auth/update")
	@ResponseBody
	public String authUpdate(@RequestParam(value="menuIds") String menuIds,@RequestParam(value="roleId") String roleId, HttpSession session) throws Exception {
		String resMsg = StringUtils.EMPTY;
		try{
			resMsg = iSysAuthRoleService.modifyRoleAuth(roleId, menuIds);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		// 更新当前用户session，省去二次登录，直接刷新界面即可
		TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
		List<TsMenu> roots = iSysMenuService.getRootMenusByUserName(user.getUsername());
		session.setAttribute(Constant.MENU_ROOTS, roots);
		return resMsg;
	}
	
	/**
	 * 删除
	 * @param req
	 * @param resp
	 * @param authRole
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(value="id") String id) {
		String resMsg = StringUtils.EMPTY;
		try{
			resMsg = iSysAuthRoleService.deleteAuthRole(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 生成JSON字符串
	 * @param root
	 * @param rules
	 * @return
	 */
	private JsonObject mkRootJson(TsMenu root, List<TsRoleAuth> rules) {
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("text", root.getMenuName());
		jsonObj.addProperty("id", root.getMenuId());
		
		if(root.getMenus().isEmpty()) {
			//如果该菜单没有下级菜单 ，该选中的就选中
			for (TsRoleAuth authRoleRules : rules) {
				if(authRoleRules.getMenuId().equals(root.getMenuId())){
					JsonObject state = new JsonObject();
					state.addProperty("selected", "true");
					jsonObj.add("state"	, state);
					break;
				}
			}
		} else {
			//检查菜单下级菜单是否全部选中
			//如果这个菜单下还有其他的菜单。当下级所有的菜单都被选中时，此菜单才被选中
			boolean isAllSel = checkMenuIsAllSel(root, rules);
			if(isAllSel) {
				JsonObject state = new JsonObject();
				state.addProperty("selected", isAllSel);
				jsonObj.add("state"	, state);
			}
		}
		JsonArray jsonArr = new JsonArray();
		if(root.getMenuHref() != null) {
			List<TsMenu> roots = root.getMenus();
			for (TsMenu menu : roots) {
				JsonObject exObj = mkRootJson(menu, rules);
				jsonArr.add(exObj);
			}
		}
		jsonObj.add("children", jsonArr);
		return jsonObj;
	}
	
	/**
	 * 检查菜单下级菜单是否全部选中
	 * @param root
	 * @param rules
	 * @return
	 */
	private boolean checkMenuIsAllSel(TsMenu root, List<TsRoleAuth> rules) {
		List<TsMenu> menus = root.getMenus();
		if(menus.isEmpty()) {
			for (TsRoleAuth rule : rules) {
				if(root.getMenuId().equals(rule.getMenuId())) {
					return true;
				}
			}
			return false;
		} else {
			boolean isAllSel = true;
			for (TsMenu menu : menus) {
				boolean isSel = checkMenuIsAllSel(menu, rules);
				if(isSel == false) {
					isAllSel = false;
					break;
				}
			}
			return isAllSel;
		}
	}
}
