package com.xinyin.baoming.controller.history;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.vo.TcCode;
import com.xinyin.baoming.model.vo.ThLastClass;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.service.ILastClassService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 历史班级表管理
 * 
 * 创建时间： 2016-08-22
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/history/lastclass")
public class LastClassController extends BaseController {

	private static final Logger logger = Logger.getLogger(LastClassController.class);

	@Autowired
	ILastClassService iLastClassService;
	
	@Autowired
	IContextService iContextService;

	/**
	 * 
	 * 历史班级表管理初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping()
	public String init() {
		return Views.HISTORY_LASTCLASS;
	}

	/**
	 * 
	 * 历史班级表信息(返回Json数据)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "data")
	@ResponseBody
	public Object[] queryLastClassJsonData(ThLastClass record, HttpSession session,
			@RequestParam(value="page",required = true, defaultValue = Constant.DEFAULT_PAGE) int page,
			@RequestParam(value="size",required = true, defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value="col[0]",required = false, defaultValue = Constant.DESC_CODE) String sort0,
			@RequestParam(value="col[1]",required = false) String sort1, @RequestParam(value="col[2]",required = false) String sort2,
			@RequestParam(value="col[3]",required = false) String sort3, @RequestParam(value="col[4]",required = false) String sort4,
			@RequestParam(value="col[5]",required = false) String sort5, @RequestParam(value="col[6]",required = false) String sort6,
			@RequestParam(value="col[7]",required = false) String sort7, @RequestParam(value="col[8]",required = false) String sort8,
			@RequestParam(value="col[9]",required = false) String sort9, @RequestParam(value="col[10]",required = false) String sort10,
			@RequestParam(value="col[11]",required = false) String sort11, @RequestParam(value="col[12]",required = false) String sort12,
			@RequestParam(value="col[13]",required = false) String sort13, @RequestParam(value="col[14]",required = false) String sort14,
			@RequestParam(value="col[15]",required = false) String sort15, @RequestParam(value="col[16]",required = false) String sort16,
			@RequestParam(value="col[17]",required = false) String sort17, @RequestParam(value="col[18]",required = false) String sort18,
			@RequestParam(value="col[19]",required = false) String sort19, @RequestParam(value="col[20]",required = false) String sort20,
			@RequestParam(value="col[21]",required = false) String sort21, @RequestParam(value="col[22]",required = false) String sort22,
			@RequestParam(value="col[23]",required = false) String sort23, @RequestParam(value="col[24]",required = false) String sort24,
			@RequestParam(value="fcol[0]",required = false) String id, @RequestParam(value="fcol[1]",required = false) String code,
			@RequestParam(value="fcol[2]",required = false) String minAge, @RequestParam(value="fcol[3]",required = false) String isOnlyOldStudent,
			@RequestParam(value="fcol[4]",required = false) String targetGroup, @RequestParam(value="fcol[5]",required = false) String limitedAmount,
			@RequestParam(value="fcol[6]",required = false) String defaultAmount, @RequestParam(value="fcol[7]",required = false) String remainAmount,
			@RequestParam(value="fcol[8]",required = false) String paidAmount, @RequestParam(value="fcol[9]",required = false) String status,
			@RequestParam(value="fcol[10]",required = false) String tuitionFee, @RequestParam(value="fcol[11]",required = false) String classTimeWeek,
			@RequestParam(value="fcol[12]",required = false) String classTimeBegin, @RequestParam(value="fcol[13]",required = false) String classTimeEnd,
			@RequestParam(value="fcol[14]",required = false) String picture, @RequestParam(value="fcol[15]",required = false) String classTypeId,
			@RequestParam(value="fcol[16]",required = false) String teacherId, @RequestParam(value="fcol[17]",required = false) String classroomId,
			@RequestParam(value="fcol[18]",required = false) String termId, @RequestParam(value="fcol[19]",required = false) String lock,
			@RequestParam(value="fcol[20]",required = false) String createBy, @RequestParam(value="fcol[21]",required = false) String createDate,
			@RequestParam(value="fcol[22]",required = false) String updateBy, @RequestParam(value="fcol[23]",required = false) String updateDate,
			@RequestParam(value="fcol[24]",required = false) String remarks, HttpServletRequest req,HttpServletResponse resp) throws Exception{
		// 返回数据集合
		Object[] arr = new Object[2];
		try {
			// 封装排序参数
			Map<String,String> map = new LinkedHashMap<String, String>(); // 先进先出
			map.put("CODE", sort1);
			map.put("MIN_AGE", sort2);
			map.put("IS_ONLY_OLD_STUDENT", sort3);
			map.put("TARGET_GROUP", sort4);
			map.put("LIMITED_AMOUNT", sort5);
			map.put("DEFAULT_AMOUNT", sort6);
			map.put("REMAIN_AMOUNT", sort7);
			map.put("PAID_AMOUNT", sort8);
			map.put("STATUS", sort9);
			map.put("TUITION_FEE", sort10);
			map.put("CLASS_TIME_WEEK", sort11);
			map.put("CLASS_TIME_BEGIN", sort12);
			map.put("CLASS_TIME_END", sort13);
			map.put("PICTURE", sort14);
			map.put("CLASS_TYPE_ID", sort15);
			map.put("TEACHER_ID", sort16);
			map.put("CLASSROOM_ID", sort17);
			map.put("TERM_ID", sort18);
			map.put("LOCK", sort19);
			map.put("CREATE_BY", sort20);
			map.put("CREATE_DATE", sort21);
			map.put("UPDATE_BY", sort22);
			map.put("UPDATE_DATE", sort23);
			map.put("REMARKS", sort24);
			map.put("ID+1", sort0);
			record.setOrderSegment(Tools.toOrderSegment(map));
			PageList<ThLastClass> list = null;
			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
			record.setPage(page);
			record.setPageSize(pageSize);
			record.setId(id);
			record.setCode(code);
			record.setMinAge(minAge);
			record.setIsOnlyOldStudent(isOnlyOldStudent);
			record.setTargetGroup(targetGroup);
			record.setLimitedAmount(limitedAmount);
			record.setDefaultAmount(defaultAmount);
			record.setRemainAmount(remainAmount);
			record.setPaidAmount(paidAmount);
			record.setStatus(status);
			record.setTuitionFee(tuitionFee);
			record.setClassTimeWeek(classTimeWeek);
			record.setClassTimeBegin(classTimeBegin);
			record.setClassTimeEnd(classTimeEnd);
			record.setPicture(picture);
			record.setClassTypeId(classTypeId);
			record.setTeacherId(teacherId);
			record.setClassroomId(classroomId);
			record.setTermId(termId);
			record.setLock(lock);
			record.setCreateBy(createBy);
			record.setCreateDateStr(createDate);
			record.setUpdateBy(updateBy);
			record.setUpdateDateStr(updateDate);
			record.setRemarks(remarks);
			// 判断是返回全部数据还是登陆老师相关数据
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			List<TcCode> cList = iContextService.getCodeList("10"); 
			String teacherRoleId = null;
			for(TcCode obj : cList){
				if("1".equals(obj.getSort())){
					teacherRoleId = obj.getName();
				}
				break;
			}
			if(teacherRoleId.equals(user.getRoleId())){
				// 借助该辅助字段实现查询限制
				record.setIdListStr(user.getPhone());
			}
			list = iLastClassService.selectBySelective(record);
			Object[][] objs = new Object[list.size()][25]; // 数组长度需要与前端列数保持一致
			for(int i = 0;i<list.size();i++){
				objs[i][0] = Tools.rowSelectionSet(list.get(i).getId());
				objs[i][1] = Tools.rowSelectionSet(list.get(i).getCode());
				objs[i][2] = Tools.rowSelectionSet(list.get(i).getMinAge());
				objs[i][3] = Tools.rowSelectionSet(list.get(i).getIsOnlyOldStudent());
				objs[i][4] = Tools.rowSelectionSet(list.get(i).getTargetGroup());
				objs[i][5] = Tools.rowSelectionSet(list.get(i).getLimitedAmount());
				objs[i][6] = Tools.rowSelectionSet(list.get(i).getDefaultAmount());
				objs[i][7] = Tools.rowSelectionSet(list.get(i).getRemainAmount());
				objs[i][8] = Tools.rowSelectionSet(list.get(i).getPaidAmount());
				objs[i][9] = Tools.rowSelectionSet(list.get(i).getStatus());
				objs[i][10] = Tools.rowSelectionSet(list.get(i).getTuitionFee());
				objs[i][11] = Tools.rowSelectionSet(list.get(i).getClassTimeWeek());
				objs[i][12] = Tools.rowSelectionSet(list.get(i).getClassTimeBegin());
				objs[i][13] = Tools.rowSelectionSet(list.get(i).getClassTimeEnd());
				objs[i][14] = Tools.rowSelectionSet(list.get(i).getPicture());
				objs[i][15] = Tools.rowSelectionSet(list.get(i).getClassTypeId());
				objs[i][16] = Tools.rowSelectionSet(list.get(i).getTeacherId());
				objs[i][17] = Tools.rowSelectionSet(list.get(i).getClassroomId());
				objs[i][18] = Tools.rowSelectionSet(list.get(i).getTermId());
				objs[i][19] = Tools.rowSelectionSet(list.get(i).getLock());
				objs[i][20] = Tools.rowSelectionSet(list.get(i).getCreateBy());
				objs[i][21] = Tools.rowSelectionSet(list.get(i).getCreateDate() !=null ?
					sdf.format(list.get(i).getCreateDate()):StringUtils.EMPTY);
				objs[i][22] = Tools.rowSelectionSet(list.get(i).getUpdateBy());
				objs[i][23] = Tools.rowSelectionSet(list.get(i).getUpdateDate() !=null ?
					sdf.format(list.get(i).getUpdateDate()):StringUtils.EMPTY);
				objs[i][24] = Tools.rowSelectionSet(list.get(i).getRemarks());
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
	 * 添加历史班级表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session, ThLastClass record) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setCreateBy(user.getUsername());
			record.setUpdateBy(user.getUsername());
			resMsg = iLastClassService.insertSelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 通过历史班级表id取得历史班级表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/data/{id}")
	@ResponseBody
	public ThLastClass queryById(@PathVariable("id") String id) {
		ThLastClass record = null;
		try{
			record = iLastClassService.selectByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		return record;
	}

	/**
	 * 
	 * 修改历史班级表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/modify", method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpSession session, ThLastClass record) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setUpdateBy(user.getUsername());
			resMsg = iLastClassService.updateByPrimaryKeySelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 根据id删除历史班级表信息
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
			resMsg = iLastClassService.deleteByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
}
