package com.xinyin.baoming.controller.wap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.pojo.RestResp;
import com.xinyin.baoming.model.vo.TmAccount;
import com.xinyin.baoming.model.vo.TmStudent;
import com.xinyin.baoming.model.vo.TtShoppingCart;
import com.xinyin.baoming.service.IClassService;
import com.xinyin.baoming.service.IShoppingCartService;
import com.xinyin.baoming.service.IStudentService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 购课车表管理
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/wap/user/shoppingcart")
public class WapShoppingCartController extends BaseController {

	private static final Logger logger = Logger.getLogger(WapShoppingCartController.class);

	@Autowired
	IShoppingCartService iShoppingCartService;
	
	@Autowired
	IStudentService iStudentService;
	
	@Autowired
	IClassService iClassService;
	
	@Autowired
	IContextService iContextService;

	/**
	 * 
	 * 购课车表管理初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping("")
	public String init(HttpSession session, Model model) {
		try{
			TmAccount ta = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(ta == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			// 获取当前招生阶段
			String curStage = iContextService.getCodeList("0").get(0).getName();
			// 返回是否是旧生阶段
			model.addAttribute("isOldStage", Constant.ENROL_STAGE_FOR_OLD.equals(curStage));
			model.addAttribute("list", iShoppingCartService.selectByStudentId(ta.getDefultStudentId()));
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION, e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_USER_SHOPPINGCART;
	}

	/**
	 * 
	 * 添加购课车表信息(Ajax)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "save", method=RequestMethod.POST)
	@ResponseBody
	public RestResp save(HttpSession session, TtShoppingCart record) {
		RestResp resp = new RestResp();
		try{
			TmAccount user = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			record.setStudentId(user.getDefultStudentId());
			// 未添加学生校验
			if(StringUtils.isBlank(user.getDefultStudentId())){
				resp.setCode(Constant.API_ILLEGAL_PARAMETER_CODE);
				resp.setMsg(Message.GOTO_COMPLETE);
				return resp;
			}
			
			// 没有设置默认学生校验
			TmStudent stu = iStudentService.selectByPrimaryKey(user.getDefultStudentId());
			if(stu == null){
				resp.setCode(Constant.API_DATA_NOT_EXISTS_CODE);
				resp.setMsg(Message.GOTO_SET_DEFAULT_STUDENT);
				return resp;
			}
			
			// 黑名单选课限制
			if(Constant.BLACK_LIST_YES.equals(stu.getIsBlacklist())){
				resp.setCode(Constant.API_ADD_FAILED);
				resp.setMsg(Message.BLACK_LIST_WARN);
				return resp;
			}
			
			// 执行添加操作(业务层校验，失败则数据回滚)
			String res = iShoppingCartService.insertSelective(record);
			if(!Message.SAVE_SUCCESS.equals(res)){
				resp.setCode(Constant.API_ADD_FAILED);
				resp.setMsg(res);
				return resp;
			}
			String classAmount = String.valueOf(iShoppingCartService.selectByStudentId(user.getDefultStudentId()).size());
			String remainAmount = iClassService.selectByPrimaryKey(record.getClassId()).getRemainAmount();
			resp.setCode(Constant.API_SUCCESS_CODE);
			resp.setTotalCount(classAmount);
			resp.setPage(remainAmount);
		}catch(Exception e){
			resp.setCode(Constant.API_EXCEPTION_CODE);
			resp.setMsg(StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION);
			logger.error(StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION, e);
		}
		return resp;
	}

	/**
	 * 
	 * 根据商品id和学生id删除购课车表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") String id, HttpSession session) {
		try{
			TmAccount ta = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(ta == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			TtShoppingCart tsc = new TtShoppingCart();
			tsc.setStudentId(ta.getDefultStudentId());
			tsc.setClassId(id);
			iShoppingCartService.deleteBySelective(tsc);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return "redirect:" + Views.WAP_USER_SHOPPINGCART + Constant.SUFFIX;
	}

}
