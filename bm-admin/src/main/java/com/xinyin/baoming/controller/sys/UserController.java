package com.xinyin.baoming.controller.sys;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
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
import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.service.IRoleService;
import com.xinyin.baoming.service.IUserService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 系统用户表管理
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController {

private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	IUserService iSysAdminUserService;
	
	@Autowired
	IRoleService iSysAuthRoleService;
	
	/**
	 * 系统用户信息管理初始化
	 * @param youSysAdminUser
	 * @return
	 */
	@RequestMapping(value="")
	public String init(Model model){
		model.addAttribute("roleInfo", iSysAuthRoleService.selectAllRoles()); // 角色信息
		model.addAttribute("userInfo", iSysAdminUserService.selectAllAdminUserList()); // 用户信息
		return Views.SYS_USER;
	}
	
	/**
	 * 系统用户信息(返回Json)
	 * @param youSysAdminUser
	 * @throws Exception 
	 * */
	@RequestMapping(value="/data")
	@ResponseBody
	public Object[] querySysAdminUserJson(TsUser youSysAdminUser,
			@RequestParam(value="page",required = true, defaultValue = Constant.DEFAULT_PAGE) int page,  
			@RequestParam(value="size",required = true, defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value="col[0]",required = false, defaultValue = Constant.DESC_CODE) String sort0,
			@RequestParam(value="col[1]",required = false) String sort1, @RequestParam(value="col[2]",required = false) String sort2,
			@RequestParam(value="col[3]",required = false) String sort3, @RequestParam(value="col[4]",required = false) String sort4,
			@RequestParam(value="col[5]",required = false) String sort5, @RequestParam(value="col[6]",required = false) String sort6,
			@RequestParam(value="col[7]",required = false) String sort7, @RequestParam(value="col[8]",required = false) String sort8,
			@RequestParam(value="fcol[0]",required = false) String username, @RequestParam(value="fcol[1]",required = false) String realName, 
			@RequestParam(value="fcol[2]",required = false) String phone, @RequestParam(value="fcol[3]",required = false) String upUser, 
			@RequestParam(value="fcol[4]",required = false) String roleId, @RequestParam(value="fcol[5]",required = false) String createBy,
			@RequestParam(value="fcol[6]",required = false) String createDate, @RequestParam(value="fcol[7]",required = false) String updateBy,
			@RequestParam(value="fcol[8]",required = false) String updateDate, HttpServletRequest req,HttpServletResponse resp) throws Exception{
		// 返回数据集合
		Object[] arr = new Object[2];
		try {
			// 封装排序参数
			Map<String,String> map = new LinkedHashMap<String, String>(); // 先进先出
			map.put("REAL_NAME", sort1);
			map.put("PHONE", sort2);
			map.put("UP_USER", sort3);
			map.put("ROLE_ID", sort4);
			map.put("CREATE_BY", sort5);
			map.put("CREATE_DATE", sort6);
			map.put("UPDATE_BY", sort7);
			map.put("UPDATE_DATE", sort8);
			map.put("USERNAME", sort0);
			youSysAdminUser.setOrderSegment(Tools.toOrderSegment(map));
			PageList<TsUser> list = null;
			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
			youSysAdminUser.setPage(page);
			youSysAdminUser.setPageSize(pageSize);
			youSysAdminUser.setUsername(username);
			youSysAdminUser.setRealName(realName);
			youSysAdminUser.setPhone(phone);
			youSysAdminUser.setUpUser(upUser);
			youSysAdminUser.setRoleId(roleId);
			youSysAdminUser.setCreateBy(createBy);
			youSysAdminUser.setCreateDateStr(createDate);
			youSysAdminUser.setUpdateBy(updateBy);
			youSysAdminUser.setUpdateDateStr(updateDate);
			list = iSysAdminUserService.selectAdminUserList(youSysAdminUser);
			Object[][] objs = new Object[list.size()][9];
			for(int i = 0;i<list.size();i++){
				objs[i][0] = Tools.rowSelectionSet(list.get(i).getUsername());
				objs[i][1] = Tools.rowSelectionSet(list.get(i).getRealName());
				objs[i][2] = Tools.rowSelectionSet(list.get(i).getPhone());
				objs[i][3] = Tools.rowSelectionSet(list.get(i).getUpUser());
				objs[i][4] = Tools.rowSelectionSet(list.get(i).getRoleId());
				objs[i][5] = Tools.rowSelectionSet(list.get(i).getCreateBy());
				objs[i][6] = Tools.rowSelectionSet(list.get(i).getCreateDate() !=null ?
					sdf.format(list.get(i).getCreateDate()):StringUtils.EMPTY);
				objs[i][7] = Tools.rowSelectionSet(list.get(i).getUpdateBy());
				objs[i][8] = Tools.rowSelectionSet(list.get(i).getUpdateDate() !=null ?
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
	 * 添加系统用户信息
	 * @param TsUser
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session, TsUser youSysAdminUser) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			youSysAdminUser.setCreateBy(user.getUsername());
			youSysAdminUser.setUpdateBy(user.getUsername());
			resMsg = iSysAdminUserService.save(youSysAdminUser);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			if(Message.USER_EXISTS.equals(e.getMessage())){
				return e.getMessage();
			}else{
				return Constant.SYSTEM_EXCEPTION;
			}
		}
		return resMsg;
	}
	
	/**
	 * 通过系统用户id取得系统用户信息
	 * @param id
	 */
	@RequestMapping(value="/data/{id}")
	@ResponseBody
	public TsUser queryById(@PathVariable("id") String id) {
		TsUser youSysAdminUser = null;
		try{
			youSysAdminUser = iSysAdminUserService.queryAdminUserInfoById(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		return youSysAdminUser;
	}
	
	/**
	 * 修改系统用户信息
	 * @param TsUser
	 */
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpSession session, TsUser youSysAdminUser) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			youSysAdminUser.setUpdateBy(user.getUsername());
			resMsg = iSysAdminUserService.modify(youSysAdminUser);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 删除
	 * @param req
	 * @param resp
	 * @param youSysAdminUser
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(value="id") String id) {
		String resMsg = StringUtils.EMPTY;
		try{
			resMsg = iSysAdminUserService.deleteAdminUser(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 用户存在校验
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/username/validation")
	@ResponseBody
	public String valid(@RequestParam(value="username") String username) {
		// 系统用户存在查询
		TsUser dbUser = iSysAdminUserService.queryUser(username,StringUtils.EMPTY);
		if(dbUser != null){
			return Message.USER_EXISTS;
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * 用户密码正确验证
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/password/validation")
	@ResponseBody
	public String passwordValid(HttpSession session, @RequestParam(value="password") String password) {
		TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
		String username = user.getUsername();
		// 系统用户存在查询
		TsUser dbUser = iSysAdminUserService.queryUser(username, password);
		if(dbUser != null){
			return Constant.USER_ADMIN;
		}else{
			return Message.PASSWORD_ERROR;
		}
	}
	
	/**
	 * 用户密码修改
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/password/modify")
	@ResponseBody
	public String passwordModify(HttpSession session ,@RequestParam(value="newPassword") String password, 
			@RequestParam(value="type") String type) {
		TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
		String username = user.getUsername();
		String resMsg = StringUtils.EMPTY;
		// 系统用户存在查询
		try{
			TsUser youSysAdminUser = new TsUser();
			youSysAdminUser.setUsername(username);
			youSysAdminUser.setUpdateBy(username);
			youSysAdminUser.setPassword(password);
			resMsg = iSysAdminUserService.modify(youSysAdminUser);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return e.getMessage();
		}
		return resMsg;
		
	}
}
