package com.xinyin.baoming.controller.wap;

import java.util.List;

import javax.servlet.http.HttpSession;

import mobi.dreambox.frameowrk.core.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.vo.TmAccount;
import com.xinyin.baoming.model.vo.TmStudent;
import com.xinyin.baoming.model.vo.TtClassStudent;
import com.xinyin.baoming.model.vo.TtOrder;
import com.xinyin.baoming.model.vo.TtShoppingCart;
import com.xinyin.baoming.service.IAccountService;
import com.xinyin.baoming.service.IClassStudentService;
import com.xinyin.baoming.service.IOrderService;
import com.xinyin.baoming.service.ISchoolService;
import com.xinyin.baoming.service.IShoppingCartService;
import com.xinyin.baoming.service.IStudentService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 学生资料表管理
 * 
 * 创建时间： 2016-08-09
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/wap/user/student")
public class WapStudentController extends BaseController {

	private static final Logger logger = Logger.getLogger(WapStudentController.class);

	@Autowired
	IStudentService iStudentService;
	
	@Autowired
	IAccountService iAccountService;
	
	@Autowired
	IContextService iContextService;
	
	@Autowired
	ISchoolService iSchoolService;
	
	@Autowired
	IShoppingCartService iShoppingCartService;
	
	@Autowired
	IOrderService iOrderService;
	
	@Autowired
	IClassStudentService iClassStudentService;
	
	/**
	 * 
	 * 学生资料列表初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping("/list")
	public String list(HttpSession session, Model model) {
		try{
			TmAccount user = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(user == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			TmStudent ts = new TmStudent();
			ts.setAccountId(user.getId());
			List<TmStudent> list = iStudentService.selectExactlyBySelective(ts);
			model.addAttribute("list", list);
			model.addAttribute("defaultStudent", user.getDefultStudentId());
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_USER_STUDENT_LIST;
	}

	/**
	 * 
	 * 添加学生资料初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping("/add")
	public String add(Model model, HttpSession session) {
		try{
			TmAccount user = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(user == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			// 下拉列表初始化
			// 身份证件类型
			model.addAttribute("idcardList", iContextService.getCodeList("7"));
			// 性别
			model.addAttribute("sexList", iContextService.getCodeList("4"));
			// 是否石狮本地/是否低保户
			model.addAttribute("judgeList", iContextService.getCodeList("1"));
			// 所在学校
			model.addAttribute("schoolList", iContextService.getAllSchool());
			// 就读年级
			model.addAttribute("gradeList", iContextService.getCodeList("8"));
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_USER_STUDENT_ADD;
	}
	
	/**
	 * 
	 * 修改 初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping("/modify/{id}")
	public String modify(@PathVariable("id") String id, Model model, HttpSession session) {
		try{
			TmAccount user = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(user == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			model.addAttribute("student", iStudentService.selectByPrimaryKey(id));
			// 下拉列表初始化
			// 身份证件类型
			model.addAttribute("idcardList", iContextService.getCodeList("7"));
			// 性别
			model.addAttribute("sexList", iContextService.getCodeList("4"));
			// 是否石狮本地/是否低保户
			model.addAttribute("judgeList", iContextService.getCodeList("1"));
			// 所在学校
			model.addAttribute("schoolList", iContextService.getAllSchool());
			// 就读年级
			model.addAttribute("gradeList", iContextService.getCodeList("8"));
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_USER_STUDENT_MODIFY;
	}
	
	/**
	 * 
	 * 保存前校验
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "validbeforesave")
	@ResponseBody
	public String validbeforesave(HttpSession session, TmStudent record) {
		try{
			TmAccount user = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			record.setAccountId(user.getId());
			TmStudent tsp = new TmStudent();
			tsp.setIdcardNo(record.getIdcardNo());
			List<TmStudent> stuList1 = iStudentService.selectExactlyBySelective(tsp);
			// 编辑保存
			if(StringUtil.isNotNull(record.getId())){
				// 编辑时，证件号码重复校验
				for(TmStudent obj : stuList1){
					if(record.getIdcardNo().equals(obj.getIdcardNo()) && !record.getId().equals(obj.getId())){
						return Message.IDCARDNO_EXIST;
					}
				}
				iStudentService.updateByPrimaryKeySelective(record);
			// 新增保存
			}else{
				// 新增时，证件号码重复校验
				for(TmStudent obj : stuList1){
					if(record.getIdcardNo().equals(obj.getIdcardNo())){
						return Message.IDCARDNO_EXIST;
					}
				}
				// 学生数量上限校验(最多两个)
				TmStudent ts = new TmStudent();
				ts.setAccountId(user.getId());
				List<TmStudent> stuList = iStudentService.selectExactlyBySelective(ts);
				if(stuList != null && stuList.size() >= 2){
					return Message.LIMIT_TWO;
				}
			}
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Message.API_CALL_EXCEPTION;
		}
		return "";
	}

	/**
	 * 
	 * 添加学生资料表信息1
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "save")
	public String save(HttpSession session, TmStudent record) {
		try{
			TmAccount user = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(user == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			record.setAccountId(user.getId());
			// 编辑保存
			if(StringUtil.isNotNull(record.getId())){
				iStudentService.updateByPrimaryKeySelective(record);
			// 新增保存
			}else{
				iStudentService.insertSelective(record);
				// 如果首次添加，设定该学生为默认学生，同时更新sessino中的用户默认学生信息
				if(StringUtils.isBlank(user.getDefultStudentId())){
					TmStudent stuParam = new TmStudent();
					stuParam.setAccountId(user.getId());
					List<TmStudent> list = iStudentService.selectExactlyBySelective(stuParam);
					user.setDefultStudentId(list.get(0).getId());
					iAccountService.updateByPrimaryKeySelective(user);
					session.setAttribute(Constant.WAP_LOGIN_KEY, user);
				}
			}
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return "redirect:" + Views.WAP_USER_STUDENT_LIST + Constant.SUFFIX;
	}
	
	/**
	 * 
	 * 删除前校验(购物车，订单表，报班表是否有学生数据)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/validbeforedelete/{id}")
	@ResponseBody
	public String validbeforedelete(@PathVariable("id") String id, Model model, HttpSession session) {
		try{
			TtShoppingCart tsc = new TtShoppingCart();
			tsc.setStudentId(id);
			List<TtShoppingCart> tscList = iShoppingCartService.selectExactlyBySelective(tsc);
			if(tscList.size() != 0){
				return "1";
			}
			TtOrder to = new TtOrder();
			to.setStudentId(id);
			List<TtOrder> toList = iOrderService.selectExactlyBySelective(to);
			if(toList.size() != 0){
				return "2";
			}
			TtClassStudent tcs = new TtClassStudent();
			tcs.setStudentId(id);
			List<TtClassStudent> tcsList = iClassStudentService.selectExactlyBySelective(tcs);
			if(tcsList.size() != 0){
				return "3";
			}
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Message.API_CALL_EXCEPTION;
		}
		return "";
	}

	/**
	 * 
	 * 根据id删除学生资料表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") String id, Model model, HttpSession session) {
		try{
			TmAccount user = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(user == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			TmStudent ts = new TmStudent();
			ts.setId(id);
			iStudentService.deleteBySelective(ts);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return "redirect:" + Views.WAP_USER_STUDENT_LIST + Constant.SUFFIX;
	}
}
