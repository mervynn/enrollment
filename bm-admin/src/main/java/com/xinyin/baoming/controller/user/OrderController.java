package com.xinyin.baoming.controller.user;

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
import com.xinyin.baoming.model.vo.TtOrder;
import com.xinyin.baoming.service.IOrderService;
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
 * 创建时间： 2016-08-14
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/user/order")
public class OrderController extends BaseController {

	private static final Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	IOrderService iOrderService;

	@Autowired
	IContextService iContextService;
	
	@Autowired
	IStudentService iStudentService;
	

	/**
	 * 
	 * 订单表管理初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping()
	public String init(Model model) {
		return Views.USER_ORDER;
	}

	/**
	 * 
	 * 订单表信息(返回Json数据)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "data")
	@ResponseBody
	public Object[] queryOrderJsonData(TtOrder record,
			@RequestParam(value="page",required = true, defaultValue = Constant.DEFAULT_PAGE) int page,
			@RequestParam(value="size",required = true, defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value="col[0]",required = false, defaultValue = Constant.DESC_CODE) String sort0,
			@RequestParam(value="col[1]",required = false) String sort1, @RequestParam(value="col[2]",required = false) String sort2,
			@RequestParam(value="col[3]",required = false) String sort3, @RequestParam(value="col[4]",required = false) String sort4,
			@RequestParam(value="col[5]",required = false) String sort5, @RequestParam(value="col[6]",required = false) String sort6,
			@RequestParam(value="col[7]",required = false) String sort7, @RequestParam(value="col[8]",required = false) String sort8,
			@RequestParam(value="col[9]",required = false) String sort9, @RequestParam(value="col[10]",required = false) String sort10,
			@RequestParam(value="fcol[0]",required = false) String id, @RequestParam(value="fcol[1]",required = false) String phonenumber,
			@RequestParam(value="fcol[2]",required = false) String idcardNo, @RequestParam(value="fcol[3]",required = false) String studentId,
			@RequestParam(value="fcol[4]",required = false) String totalMoney, @RequestParam(value="fcol[5]",required = false) String status,
			@RequestParam(value="fcol[6]",required = false) String createBy, @RequestParam(value="fcol[7]",required = false) String createDate,
			@RequestParam(value="fcol[8]",required = false) String updateBy, @RequestParam(value="fcol[9]",required = false) String updateDate,
			@RequestParam(value="fcol[10]",required = false) String remarks, HttpServletRequest req,HttpServletResponse resp) throws Exception{
		// 返回数据集合
		Object[] arr = new Object[2];
		try {
			// 封装排序参数
			Map<String,String> map = new LinkedHashMap<String, String>(); // 先进先出
			map.put("PHONENUMBER", sort1);
			map.put("IDCARD_NO", sort2);
			map.put("STUDENT_ID", sort3);
			map.put("TOTAL_MONEY", sort4);
			map.put("STATUS", sort5);
			map.put("CREATE_BY", sort6);
			map.put("CREATE_DATE", sort7);
			map.put("UPDATE_BY", sort8);
			map.put("UPDATE_DATE", sort9);
			map.put("REMARKS", sort10);
			map.put("ID", sort0);
			record.setOrderSegment(Tools.toOrderSegment(map));
			PageList<TtOrder> list = null;
			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
			record.setPage(page);
			record.setPageSize(pageSize);
			record.setId(id);
			record.setPhonenumber(phonenumber);
			record.setIdcardNo(idcardNo);
			record.setStudentId(studentId);
			record.setTotalMoney(totalMoney);
			record.setStatus(status);
			record.setCreateBy(createBy);
			record.setCreateDateStr(createDate);
			record.setUpdateBy(updateBy);
			record.setUpdateDateStr(updateDate);
			record.setRemarks(remarks);
			list = iOrderService.selectBySelective(record);
			Object[][] objs = new Object[list.size()][11]; // 数组长度需要与前端列数保持一致
			for(int i = 0;i<list.size();i++){
				objs[i][0] = Tools.rowSelectionSet(list.get(i).getId());
				objs[i][1] = Tools.rowSelectionSet(list.get(i).getPhonenumber());
				objs[i][2] = Tools.rowSelectionSet(list.get(i).getIdcardNo());
				objs[i][3] = Tools.rowSelectionSet(list.get(i).getStudentId());
				objs[i][4] = Tools.rowSelectionSet(list.get(i).getTotalMoney());
				objs[i][5] = Tools.rowSelectionSet(list.get(i).getStatus());
				objs[i][6] = Tools.rowSelectionSet(list.get(i).getCreateBy());
				objs[i][7] = Tools.rowSelectionSet(list.get(i).getCreateDate() !=null ?
					sdf.format(list.get(i).getCreateDate()):StringUtils.EMPTY);
				objs[i][8] = Tools.rowSelectionSet(list.get(i).getUpdateBy());
				objs[i][9] = Tools.rowSelectionSet(list.get(i).getUpdateDate() !=null ?
					sdf.format(list.get(i).getUpdateDate()):StringUtils.EMPTY);
				objs[i][10] = Tools.rowSelectionSet(list.get(i).getRemarks());
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
	 * 添加订单表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session, TtOrder record) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setCreateBy(user.getUsername());
			record.setUpdateBy(user.getUsername());
			resMsg = iOrderService.insertSelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 通过订单表id取得订单表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/data/{id}")
	@ResponseBody
	public TtOrder queryById(@PathVariable("id") String id) {
		TtOrder record = null;
		try{
			record = iOrderService.selectByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		return record;
	}

	/**
	 * 
	 * 修改订单表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/modify", method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpSession session, TtOrder record) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setUpdateBy(user.getUsername());
			resMsg = iOrderService.updateByPrimaryKeySelective(record);
		}catch(Exception e){
			logger.error(StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION, e);
			return StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 根据id删除订单表信息
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
			resMsg = iOrderService.deleteByPrimaryKey(id);
		}catch(Exception e){
			logger.error(StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION, e);
			return StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 
	 * 订单结算
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/balance/{id}", method=RequestMethod.POST)
	@ResponseBody
	public String balance(HttpSession session, @PathVariable("id") String id) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			TtOrder to = new TtOrder();
			to.setUpdateBy(user.getUsername());
			to.setStatus(Constant.ORDER_STATUS_PAID_SUCCESS);
			to.setId(id);
			resMsg = iOrderService.updateByPrimaryKeySelective(to);
			if(Message.UPDATE_SUCCESS.equals(resMsg)){
				return Message.PAID_SUCCESS;
			}
		}catch(Exception e){
			logger.error(StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION, e);
			return StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 
	 * 反订单结算
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/cancelbalance/{id}", method=RequestMethod.POST)
	@ResponseBody
	public String cancelbalance(HttpSession session, @PathVariable("id") String id) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			TtOrder to = new TtOrder();
			to.setUpdateBy(user.getUsername());
			to.setStatus(Constant.ORDER_STATUS_COMMITED);
			to.setId(id);
			resMsg = iOrderService.updateByPrimaryKeySelective(to);
			if(Message.UPDATE_SUCCESS.equals(resMsg)){
				return Message.CANCEL_PAID_SUCCESS;
			}
		}catch(Exception e){
			logger.error(StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION, e);
			return StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
}
