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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.vo.TtClassStudent;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.service.IClassStudentService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 班级学生关联表管理
 * 
 * 创建时间： 2016-08-20
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/user/classstudent")
public class ClassStudentController extends BaseController {

	private static final Logger logger = Logger.getLogger(ClassStudentController.class);

	@Autowired
	IClassStudentService iClassStudentService;

	/**
	 * 
	 * 班级学生关联表管理初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping()
	public String init() {
		return Views.USER_CLASSSTUDENT;
	}

	/**
	 * 
	 * 班级学生关联表信息(返回Json数据)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "data")
	@ResponseBody
	public Object[] queryClassStudentJsonData(TtClassStudent record,
			@RequestParam(value="page",required = true, defaultValue = Constant.DEFAULT_PAGE) int page,
			@RequestParam(value="size",required = true, defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value="col[0]",required = false, defaultValue = Constant.DESC_CODE) String sort0,
			@RequestParam(value="col[1]",required = false) String sort1, @RequestParam(value="col[2]",required = false) String sort2,
			@RequestParam(value="col[3]",required = false) String sort3, @RequestParam(value="col[4]",required = false) String sort4,
			@RequestParam(value="col[5]",required = false) String sort5, @RequestParam(value="col[6]",required = false) String sort6,
			@RequestParam(value="col[7]",required = false) String sort7, @RequestParam(value="fcol[0]", required = false) String id,
			@RequestParam(value="fcol[1]",required = false) String classId, @RequestParam(value="fcol[2]",required = false) String studentId,
			@RequestParam(value="fcol[3]",required = false) String remarks,
			@RequestParam(value="fcol[4]",required = false) String createBy, @RequestParam(value="fcol[5]",required = false) String createDate,
			@RequestParam(value="fcol[6]",required = false) String updateBy, @RequestParam(value="fcol[7]",required = false) String updateDate,
			HttpServletRequest req,HttpServletResponse resp) throws Exception{
		// 返回数据集合
		Object[] arr = new Object[2];
		try {
			// 封装排序参数
			Map<String,String> map = new LinkedHashMap<String, String>(); // 先进先出
			map.put("CLASS_ID", sort1);
			map.put("STUDENT_ID", sort2);
			map.put("REMARKS", sort3);
			map.put("CREATE_BY", sort4);
			map.put("CREATE_DATE", sort5);
			map.put("UPDATE_BY", sort6);
			map.put("UPDATE_DATE", sort7);
			map.put("ID+1", sort0);
			record.setOrderSegment(Tools.toOrderSegment(map));
			PageList<TtClassStudent> list = null;
			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
			record.setPage(page);
			record.setPageSize(pageSize);
			record.setId(id);
			record.setClassId(classId);
			record.setStudentId(studentId);
			record.setCreateBy(createBy);
			record.setCreateDateStr(createDate);
			record.setUpdateBy(updateBy);
			record.setUpdateDateStr(updateDate);
			record.setRemarks(remarks);
			list = iClassStudentService.selectBySelective(record);
			Object[][] objs = new Object[list.size()][8]; // 数组长度需要与前端列数保持一致
			for(int i = 0;i<list.size();i++){
				objs[i][0] = Tools.rowSelectionSet(list.get(i).getId());
				objs[i][1] = Tools.rowSelectionSet(list.get(i).getClassId());
				objs[i][2] = Tools.rowSelectionSet(list.get(i).getStudentId());
				objs[i][3] = Tools.rowSelectionSet(list.get(i).getRemarks());
				objs[i][4] = Tools.rowSelectionSet(list.get(i).getCreateBy());
				objs[i][5] = Tools.rowSelectionSet(list.get(i).getCreateDate() !=null ?
					sdf.format(list.get(i).getCreateDate()):StringUtils.EMPTY);
				objs[i][6] = Tools.rowSelectionSet(list.get(i).getUpdateBy());
				objs[i][7] = Tools.rowSelectionSet(list.get(i).getUpdateDate() !=null ?
					sdf.format(list.get(i).getUpdateDate()):StringUtils.EMPTY);
				
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
	 * 添加班级学生关联表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session, TtClassStudent record) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setCreateBy(user.getUsername());
			record.setUpdateBy(user.getUsername());
			resMsg = iClassStudentService.insertSelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 通过班级学生关联表id取得班级学生关联表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/data/{id}")
	@ResponseBody
	public TtClassStudent queryById(@PathVariable("id") String id) {
		TtClassStudent record = null;
		try{
			record = iClassStudentService.selectByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		return record;
	}

	/**
	 * 
	 * 修改班级学生关联表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/modify", method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpSession session, TtClassStudent record) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setUpdateBy(user.getUsername());
			resMsg = iClassStudentService.updateByPrimaryKeySelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 根据id删除班级学生关联表信息
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
			resMsg = iClassStudentService.deleteByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
}
