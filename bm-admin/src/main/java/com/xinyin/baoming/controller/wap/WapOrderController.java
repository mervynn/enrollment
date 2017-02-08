package com.xinyin.baoming.controller.wap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.vo.TmAccount;
import com.xinyin.baoming.model.vo.TmClass;
import com.xinyin.baoming.model.vo.TmStudent;
import com.xinyin.baoming.model.vo.TtOrder;
import com.xinyin.baoming.model.vo.TtOrderDetail;
import com.xinyin.baoming.service.IClassService;
import com.xinyin.baoming.service.IOrderDetailService;
import com.xinyin.baoming.service.IOrderService;
import com.xinyin.baoming.service.IShoppingCartService;
import com.xinyin.baoming.service.IStudentService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 订单表管理
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/wap/user/order")
public class WapOrderController extends BaseController {

	private static final Logger logger = Logger.getLogger(WapOrderController.class);

	@Autowired
	IOrderService iOrderService;
	
	@Autowired
	IOrderDetailService iOrderDetailService;
	
	@Autowired
	IClassService iClassService;
	
	@Autowired
	IStudentService iStudentService;
	
	@Autowired
	IShoppingCartService iShoppingCartService;
	
	@Autowired
	IContextService iContextService;
	
	/**
	 * 
	 * 用户订单列表初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping("/list")
	public String init(HttpSession session, Model model, TtOrder record) {
		try{
			TmAccount account = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(account == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			TmAccount ta = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			// 判断是否有默认选择的学生,有则返回其订单数据,否则直接返回空数据
			if(StringUtils.isNotEmpty(ta.getDefultStudentId())){
				record.setStudentId(ta.getDefultStudentId());
			}else{
				return Views.WAP_USER_ORDER_LIST;
			}
			// 订单降序排序
			Map<String,String> map = new LinkedHashMap<String, String>();
			map.put("ID+1", Constant.SORT_DESC);
			record.setOrderSegment(Tools.toOrderSegment(map));
			record.setPageSize(Constant.DEFAULT_MAX_PAGE_SIZE);
			// 获取当前招生阶段
			String curStage = iContextService.getCodeList("0").get(0).getName();
			// 返回是否是旧生阶段
			model.addAttribute("isOldStage", Constant.ENROL_STAGE_FOR_OLD.equals(curStage));
			model.addAttribute("list", iOrderService.selectExactlyBySelective(record));
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION, e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_USER_ORDER_LIST;
	}
	
	/**
	 * 
	 * 根据订单id删除订单表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") String id, Model model, HttpSession session) {
		try{
			TmAccount account = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(account == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			TtOrder param = new TtOrder();
			param.setId(id);
			iOrderService.deleteBySelective(param);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return "redirect:" + Views.WAP_USER_ORDER_LIST + Constant.SUFFIX;
	}

	/**
	 * 
	 * 填写订单
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/edit")
	public String edit(HttpSession session, Model model) {
		try{
			TmAccount account = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(account == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			model.addAttribute("account", account);
			model.addAttribute("student", iStudentService.selectByPrimaryKey(account.getDefultStudentId()));
			model.addAttribute("classList", iShoppingCartService.selectByStudentId(account.getDefultStudentId()));
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_USER_ORDER_EDIT;
	}
	
	/**
	 * 
	 * 提交订单
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/submit")
	public String submit(HttpSession session, Model model) {
		try{
			TmAccount account = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(account == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			TmAccount user = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			// 保存订单信息批处理
			Map<String, String> res = iOrderService.saveOrderFromcartBatch(user.getDefultStudentId());
			// 购课车中无班级提醒
			if(res.size() == 0){
				model.addAttribute("errMsg", Message.COMMIT_ERROR);
				model.addAttribute("account", account);
				model.addAttribute("student", iStudentService.selectByPrimaryKey(account.getDefultStudentId()));
				return Views.WAP_USER_ORDER_EDIT;
			}
			model.addAttribute("orderId", res.get("orderId"));
			model.addAttribute("totalFee", res.get("totalFee"));
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_USER_ORDER_RESULT;
	}
	
	/**
	 * 
	 * 订单明细
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/detail/{id}")
	public String detail(@PathVariable("id") String id, Model model, HttpSession session) {
		List<TmClass> classList = new ArrayList<TmClass>();
		try{
			TmAccount account = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(account == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			TtOrderDetail param = new TtOrderDetail();
			param.setId(id);
			PageList<TtOrderDetail> list = iOrderDetailService.selectBySelective(param);
			TtOrder order = iOrderService.selectByPrimaryKey(id);
			TmStudent studentInfo =  iStudentService.selectByPrimaryKey(order.getStudentId());
			for(TtOrderDetail detail : list){
				classList.add(iClassService.selectByPrimaryKey(detail.getClassId()));
			}
			// 订单信息
			model.addAttribute("orderInfo", order);
			// 订购的学生信息
			model.addAttribute("studentInfo", studentInfo);
			// 订单详情
			model.addAttribute("classList", classList);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_USER_ORDER_DETAIL;
	}
	
}
