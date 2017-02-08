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
import com.xinyin.baoming.model.vo.TmStudent;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.model.vo.TtRecommend;
import com.xinyin.baoming.model.vo.TtShoppingCart;
import com.xinyin.baoming.service.IClassService;
import com.xinyin.baoming.service.IRecommendService;
import com.xinyin.baoming.service.IShoppingCartService;
import com.xinyin.baoming.service.IStudentService;
import com.xinyin.baoming.service.ITeacherService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 旧生课程推荐表管理
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/user/recommend")
public class RecommendController extends BaseController {

	private static final Logger logger = Logger.getLogger(RecommendController.class);

	@Autowired
	IRecommendService iRecommendService;
	
	@Autowired
	ITeacherService iTeacherService;
	
	@Autowired
	IStudentService iStudentService;
	
	@Autowired
	IClassService iClassService;
	
	@Autowired
	IContextService iContextService;
	
	@Autowired
	IShoppingCartService iShoppingCartService;

	/**
	 * 
	 * 旧生课程推荐表管理初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping()
	public String init(Model model) {
		model.addAttribute("status", iContextService.getCodeList("11"));
		return Views.USER_RECOMMEND;
	}

	/**
	 * 
	 * 旧生课程推荐表信息(返回Json数据)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "data")
	@ResponseBody
	public Object[] queryRecommendJsonData(TtRecommend record,
			@RequestParam(value="page",required = true, defaultValue = Constant.DEFAULT_PAGE) int page,
			@RequestParam(value="size",required = true, defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value="col[0]",required = false, defaultValue = Constant.DESC_CODE) String sort0,
			@RequestParam(value="col[1]",required = false) String sort1, @RequestParam(value="col[2]",required = false) String sort2,
			@RequestParam(value="col[3]",required = false) String sort3, @RequestParam(value="col[4]",required = false) String sort4,
			@RequestParam(value="col[5]",required = false) String sort5, @RequestParam(value="col[6]",required = false) String sort6,
			@RequestParam(value="col[7]",required = false) String sort7, @RequestParam(value="col[8]",required = false) String sort8,
			@RequestParam(value="col[9]",required = false) String sort9, @RequestParam(value="fcol[0]", required = false) String id,
			@RequestParam(value="fcol[1]",required = false) String studentId, @RequestParam(value="fcol[2]",required = false) String classId,
			@RequestParam(value="fcol[3]",required = false) String teacherId, @RequestParam(value="fcol[4]",required = false) String isThroughApproval,
			@RequestParam(value="fcol[5]",required = false) String createBy, @RequestParam(value="fcol[6]",required = false) String createDate,
			@RequestParam(value="fcol[7]",required = false) String updateBy, @RequestParam(value="fcol[8]",required = false) String updateDate,
			@RequestParam(value="fcol[9]",required = false) String remarks, HttpServletRequest req,HttpServletResponse resp) throws Exception{
		// 返回数据集合
		Object[] arr = new Object[2];
		try {
			// 封装排序参数
			Map<String,String> map = new LinkedHashMap<String, String>(); // 先进先出
			map.put("STUDENT_ID", sort1);
			map.put("CLASS_ID", sort2);
			map.put("TEACHER_ID", sort3);
			map.put("IS_THROUGH_APPROVAL", sort4);
			map.put("CREATE_BY", sort5);
			map.put("CREATE_DATE", sort6);
			map.put("UPDATE_BY", sort7);
			map.put("UPDATE_DATE", sort8);
			map.put("REMARKS", sort9);
			map.put("ID+1", sort0);
			record.setOrderSegment(Tools.toOrderSegment(map));
			PageList<TtRecommend> list = null;
			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
			record.setPage(page);
			record.setPageSize(pageSize);
			record.setId(id);
			record.setStudentId(studentId);
			record.setClassId(classId);
			record.setTeacherId(teacherId);
			record.setIsThroughApproval(isThroughApproval);
			record.setCreateBy(createBy);
			record.setCreateDateStr(createDate);
			record.setUpdateBy(updateBy);
			record.setUpdateDateStr(updateDate);
			record.setRemarks(remarks);
			list = iRecommendService.selectBySelective(record);
			Object[][] objs = new Object[list.size()][10]; // 数组长度需要与前端列数保持一致
			for(int i = 0;i<list.size();i++){
				objs[i][0] = Tools.rowSelectionSet(list.get(i).getId());
				objs[i][1] = Tools.rowSelectionSet(list.get(i).getStudentId());
				objs[i][2] = Tools.rowSelectionSet(list.get(i).getClassId());
				objs[i][3] = Tools.rowSelectionSet(list.get(i).getTeacherId());
				objs[i][4] = Tools.rowSelectionSet(list.get(i).getIsThroughApproval());
				objs[i][5] = Tools.rowSelectionSet(list.get(i).getCreateBy());
				objs[i][6] = Tools.rowSelectionSet(list.get(i).getCreateDate() !=null ?
					sdf.format(list.get(i).getCreateDate()):StringUtils.EMPTY);
				objs[i][7] = Tools.rowSelectionSet(list.get(i).getUpdateBy());
				objs[i][8] = Tools.rowSelectionSet(list.get(i).getUpdateDate() !=null ?
					sdf.format(list.get(i).getUpdateDate()):StringUtils.EMPTY);
				objs[i][9] = Tools.rowSelectionSet(list.get(i).getRemarks());
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
	 * 添加旧生课程推荐表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session, TtRecommend record) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setCreateBy(user.getUsername());
			record.setUpdateBy(user.getUsername());
			resMsg = iRecommendService.insertSelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 通过旧生课程推荐表id取得旧生课程推荐表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/data/{id}")
	@ResponseBody
	public TtRecommend queryById(@PathVariable("id") String id) {
		TtRecommend record = null;
		try{
			TtRecommend tr = new TtRecommend();
			tr.setId(id);
			record = iRecommendService.selectBySelective(tr).get(0);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		return record;
	}

	/**
	 * 
	 * 修改旧生课程推荐表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/modify", method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpSession session, TtRecommend record) {
		String resMsg = StringUtils.EMPTY;
		try{
			String status = record.getIsThroughApproval();
			// 获取相关数据
			record = iRecommendService.selectByPrimaryKey(record.getId());
			record.setIsThroughApproval(status);
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setUpdateBy(user.getUsername());
			// 审核通过 添加到学生选课车
			if(Constant.REVIEW_STATUS_SUCCESS.equals(status)){
				// 学生信息不存在校验
				TmStudent stu = iStudentService.selectByPrimaryKey(record.getStudentId());
				if(stu == null){
					return Message.STUDENT_INFO_NOT_EXIST;
				}
				TtShoppingCart tsc = new TtShoppingCart();
				tsc.setStudentId(record.getStudentId());
				tsc.setClassId(record.getClassId());
				tsc.setOperationTeacherId(record.getTeacherId());
				// 系统审批人
				tsc.setCreateBy(user.getUsername());
				tsc.setUpdateBy(user.getUsername());
				String res = iShoppingCartService.insertSelective(tsc);
				if(!Message.SAVE_SUCCESS.equals(res)){
					return Message.REVIEWFAILED_REASON + res;
				}
			}
			resMsg = iRecommendService.updateByPrimaryKeySelective(record);
		}catch(Exception e){
			logger.error(StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION, e);
			return StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 根据id删除旧生课程推荐表信息
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
			resMsg = iRecommendService.deleteByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
}
