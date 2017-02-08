package com.xinyin.baoming.controller.user;

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
import com.xinyin.baoming.model.vo.TmClass;
import com.xinyin.baoming.model.vo.TtOrderDetail;
import com.xinyin.baoming.service.IClassService;
import com.xinyin.baoming.service.IOrderDetailService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 订单明细表管理
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/user/orderdetail")
public class OrderDetailController extends BaseController {

	private static final Logger logger = Logger.getLogger(OrderDetailController.class);

	@Autowired
	IOrderDetailService iOrderDetailService;
	
	@Autowired
	IClassService iClassService;

	/**
	 * 
	 * 订单明细表管理初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping("/{id}")
	public String init(@PathVariable("id") String id, Model model) {
		model.addAttribute("orderId", id);
		return Views.USER_ORDERDETAIL;
	}

	/**
	 * 
	 * 订单明细表信息(返回Json数据)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "data")
	@ResponseBody
	public Object[] queryOrderDetailJsonData(TmClass record,
			@RequestParam(value="page",required = true, defaultValue = Constant.DEFAULT_PAGE) int page,
			@RequestParam(value="size",required = true, defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value="extra",required = false) String extra,
			HttpServletRequest req,HttpServletResponse resp) throws Exception{
		// 返回数据集合
		Object[] arr = new Object[2];
		try {
			// 封装排序参数
			record.setPage(page);
			record.setPageSize(pageSize);
			PageList<TmClass> list = null;
			if(StringUtils.isNotEmpty(extra)){
				record.setId(extra);
			}
			list = iClassService.selectOrderDetail(record);
			Object[][] objs = new Object[list.size()][8]; // 数组长度需要与前端列数保持一致
			for(int i = 0;i<list.size();i++){
				objs[i][0] = Tools.alignCenter(list.get(i).getId());
				objs[i][1] = Tools.alignCenter(list.get(i).getClassTypeId());
				objs[i][2] = Tools.alignCenter(list.get(i).getCode());
				objs[i][3] = Tools.alignCenter(list.get(i).getClassroomId());
				objs[i][4] = Tools.alignCenter(list.get(i).getClassTimeWeek());
				objs[i][5] = Tools.alignCenter(list.get(i).getClassTimeBegin());
				objs[i][6] = Tools.alignCenter(list.get(i).getClassTimeEnd());
				objs[i][7] = Tools.alignCenter(list.get(i).getTuitionFee());
			}
			arr[0]=(list.getPaginator().getTotalCount());
			arr[1]=objs;
		} catch (Exception e) {
			logger.error(Constant.SYSTEM_EXCEPTION, e);
			throw e;
		}
		return arr;
	}

	/**
	 * 
	 * 添加订单明细表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session, TtOrderDetail record) {
		String resMsg = StringUtils.EMPTY;
		try{
			resMsg = iOrderDetailService.insertSelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 通过订单明细表id取得订单明细表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/data/{id}")
	@ResponseBody
	public TtOrderDetail queryById(@PathVariable("id") String id) {
		TtOrderDetail record = null;
		try{
			record = iOrderDetailService.selectByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		return record;
	}

	/**
	 * 
	 * 修改订单明细表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/modify", method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpSession session, TtOrderDetail record) {
		String resMsg = StringUtils.EMPTY;
		try{
			resMsg = iOrderDetailService.updateByPrimaryKeySelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 根据id删除订单明细表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/delete", method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(value="id") String id) {
		String resMsg = StringUtils.EMPTY;
		try{
			resMsg = iOrderDetailService.deleteByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

}
