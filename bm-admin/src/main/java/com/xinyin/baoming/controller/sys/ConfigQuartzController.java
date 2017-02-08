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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.vo.TsConfigQuartz;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.service.IConfigQuartzService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 计划任务表管理
 * 
 * 创建时间： 2016-08-20
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/sys/configquartz")
public class ConfigQuartzController extends BaseController {

	private static final Logger logger = Logger.getLogger(ConfigQuartzController.class);

	@Autowired
	IConfigQuartzService iConfigQuartzService;

	/**
	 * 
	 * 计划任务表管理初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping()
	public String init() {
		return Views.SYS_CONFIGQUARTZ;
	}

	/**
	 * 
	 * 计划任务表信息(返回Json数据)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "data")
	@ResponseBody
	public Object[] queryConfigQuartzJsonData(TsConfigQuartz record,
			@RequestParam(value="page",required = true, defaultValue = Constant.DEFAULT_PAGE) int page,
			@RequestParam(value="size",required = true, defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value="col[0]",required = false, defaultValue = Constant.DESC_CODE) String sort0,
			@RequestParam(value="col[1]",required = false) String sort1, @RequestParam(value="col[2]",required = false) String sort2,
			@RequestParam(value="col[3]",required = false) String sort3, @RequestParam(value="col[4]",required = false) String sort4,
			@RequestParam(value="col[5]",required = false) String sort5, @RequestParam(value="col[6]",required = false) String sort6,
			@RequestParam(value="col[7]",required = false) String sort7, @RequestParam(value="col[8]",required = false) String sort8,
			@RequestParam(value="col[9]",required = false) String sort9, @RequestParam(value="col[10]",required = false) String sort10,
			@RequestParam(value="col[11]",required = false) String sort11, @RequestParam(value="col[12]",required = false) String sort12,
			@RequestParam(value="col[13]",required = false) String sort13, @RequestParam(value="fcol[0]", required = false) String id,
			@RequestParam(value="fcol[1]",required = false) String triggerName, @RequestParam(value="fcol[2]",required = false) String cronExpression,
			@RequestParam(value="fcol[3]",required = false) String jobDetailName, @RequestParam(value="fcol[4]",required = false) String targetObject,
			@RequestParam(value="fcol[5]",required = false) String methodName, @RequestParam(value="fcol[6]",required = false) String concurrent,
			@RequestParam(value="fcol[7]",required = false) String state, @RequestParam(value="fcol[8]",required = false) String isSpringBean,
			@RequestParam(value="fcol[9]",required = false) String createBy, @RequestParam(value="fcol[10]",required = false) String createDate,
			@RequestParam(value="fcol[11]",required = false) String updateBy, @RequestParam(value="fcol[12]",required = false) String updateDate,
			@RequestParam(value="fcol[13]",required = false) String remarks, HttpServletRequest req,HttpServletResponse resp) throws Exception{
		// 返回数据集合
		Object[] arr = new Object[2];
		try {
			// 封装排序参数
			Map<String,String> map = new LinkedHashMap<String, String>(); // 先进先出
			map.put("TRIGGER_NAME", sort1);
			map.put("CRON_EXPRESSION", sort2);
			map.put("JOB_DETAIL_NAME", sort3);
			map.put("TARGET_OBJECT", sort4);
			map.put("METHOD_NAME", sort5);
			map.put("CONCURRENT", sort6);
			map.put("STATE", sort7);
			map.put("IS_SPRING_BEAN", sort8);
			map.put("CREATE_BY", sort9);
			map.put("CREATE_DATE", sort10);
			map.put("UPDATE_BY", sort11);
			map.put("UPDATE_DATE", sort12);
			map.put("REMARKS", sort13);
			map.put("ID+1", sort0);
			record.setOrderSegment(Tools.toOrderSegment(map));
			PageList<TsConfigQuartz> list = null;
			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
			record.setPage(page);
			record.setPageSize(pageSize);
			record.setId(id);
			record.setTriggerName(triggerName);
			record.setCronExpression(cronExpression);
			record.setJobDetailName(jobDetailName);
			record.setTargetObject(targetObject);
			record.setMethodName(methodName);
			record.setConcurrent(concurrent);
			record.setState(state);
			record.setIsSpringBean(isSpringBean);
			record.setCreateBy(createBy);
			record.setCreateDateStr(createDate);
			record.setUpdateBy(updateBy);
			record.setUpdateDateStr(updateDate);
			record.setRemarks(remarks);
			list = iConfigQuartzService.selectBySelective(record);
			Object[][] objs = new Object[list.size()][14]; // 数组长度需要与前端列数保持一致
			for(int i = 0;i<list.size();i++){
				objs[i][0] = Tools.rowSelectionSet(list.get(i).getId());
				objs[i][1] = Tools.rowSelectionSet(list.get(i).getTriggerName());
				objs[i][2] = Tools.rowSelectionSet(list.get(i).getCronExpression());
				objs[i][3] = Tools.rowSelectionSet(list.get(i).getJobDetailName());
				objs[i][4] = Tools.rowSelectionSet(list.get(i).getTargetObject());
				objs[i][5] = Tools.rowSelectionSet(list.get(i).getMethodName());
				objs[i][6] = Tools.rowSelectionSet(list.get(i).getConcurrent());
				objs[i][7] = Tools.rowSelectionSet(list.get(i).getState());
				objs[i][8] = Tools.rowSelectionSet(list.get(i).getIsSpringBean());
				objs[i][9] = Tools.rowSelectionSet(list.get(i).getCreateBy());
				objs[i][10] = Tools.rowSelectionSet(list.get(i).getCreateDate() !=null ?
					sdf.format(list.get(i).getCreateDate()):StringUtils.EMPTY);
				objs[i][11] = Tools.rowSelectionSet(list.get(i).getUpdateBy());
				objs[i][12] = Tools.rowSelectionSet(list.get(i).getUpdateDate() !=null ?
					sdf.format(list.get(i).getUpdateDate()):StringUtils.EMPTY);
				objs[i][13] = Tools.rowSelectionSet(list.get(i).getRemarks());
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
	 * 添加计划任务表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session, TsConfigQuartz record) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setCreateBy(user.getUsername());
			record.setUpdateBy(user.getUsername());
			resMsg = iConfigQuartzService.insertSelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 通过计划任务表id取得计划任务表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/data/{id}")
	@ResponseBody
	public TsConfigQuartz queryById(@PathVariable("id") String id) {
		TsConfigQuartz record = null;
		try{
			record = iConfigQuartzService.selectByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		return record;
	}

	/**
	 * 
	 * 修改计划任务表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/modify", method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpSession session, TsConfigQuartz record) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setUpdateBy(user.getUsername());
			resMsg = iConfigQuartzService.updateByPrimaryKeySelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 根据id删除计划任务表信息
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
			resMsg = iConfigQuartzService.deleteByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
}
