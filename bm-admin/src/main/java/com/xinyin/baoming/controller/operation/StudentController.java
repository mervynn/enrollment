package com.xinyin.baoming.controller.operation;

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
import com.xinyin.baoming.service.ISchoolService;
import com.xinyin.baoming.service.IStudentService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 学生资料表管理
 * 
 * 创建时间： 2016-08-13
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/operation/student")
public class StudentController extends BaseController {

	private static final Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	IStudentService iStudentService;
	
	@Autowired
	IContextService iContextService;
	
	@Autowired
	ISchoolService iSchoolService;

	/**
	 * 
	 * 学生资料表管理初始化
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping()
	public String init(Model model) {
		// 下拉列表初始化
		// 身份证件类型
		model.addAttribute("idcardList", iContextService.getCodeList("7"));
		// 性别
		model.addAttribute("sexList", iContextService.getCodeList("4"));
		// 是否石狮本地/是否低保户/是否是黑名单
		model.addAttribute("judgeList", iContextService.getCodeList("1"));
		// 所在学校
		model.addAttribute("schoolList", iContextService.getAllSchool());
		// 就读年级
		model.addAttribute("gradeList", iContextService.getCodeList("8"));
		return Views.OPERATION_STUDENT;
	}

	/**
	 * 
	 * 学生资料表信息(返回Json数据)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "data")
	@ResponseBody
	public Object[] queryStudentJsonData(TmStudent record,
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
			@RequestParam(value="col[23]",required = false) String sort23, @RequestParam(value="col[24]",required = false) String sort24,@RequestParam(value="fcol[0]", required = false) String id,
			@RequestParam(value="fcol[1]",required = false) String cardType, @RequestParam(value="fcol[2]",required = false) String idcardNo,
			@RequestParam(value="fcol[3]",required = false) String name, @RequestParam(value="fcol[4]",required = false) String sex,
			@RequestParam(value="fcol[5]",required = false) String isLocalOrNot, @RequestParam(value="fcol[6]",required = false) String parentName,@RequestParam(value="fcol[7]",required = false) String parentPhonenumber,
			@RequestParam(value="fcol[8]",required = false) String schoolId, @RequestParam(value="fcol[9]",required = false) String grade,
			@RequestParam(value="fcol[10]",required = false) String pictureUrl, @RequestParam(value="fcol[11]",required = false) String birthDate,
			@RequestParam(value="fcol[12]",required = false) String address, @RequestParam(value="fcol[13]",required = false) String studentCode,
			@RequestParam(value="fcol[14]",required = false) String isLowincome, @RequestParam(value="fcol[15]",required = false) String homePhone,
			@RequestParam(value="fcol[16]",required = false) String accountId, @RequestParam(value="fcol[17]",required = false) String isBlacklist,
			@RequestParam(value="fcol[18]",required = false) String blacklistReason, @RequestParam(value="fcol[19]",required = false) String blacklistReasonUrl,
			@RequestParam(value="fcol[20]",required = false) String createBy, @RequestParam(value="fcol[21]",required = false) String createDate,
			@RequestParam(value="fcol[22]",required = false) String updateBy, @RequestParam(value="fcol[23]",required = false) String updateDate,
			@RequestParam(value="fcol[24]",required = false) String remarks, HttpServletRequest req,HttpServletResponse resp) throws Exception{
		// 返回数据集合
		Object[] arr = new Object[2];
		try {
			// 封装排序参数
			Map<String,String> map = new LinkedHashMap<String, String>(); // 先进先出
			map.put("CARD_TYPE", sort1);
			map.put("IDCARD_NO", sort2);
			map.put("NAME", sort3);
			map.put("SEX", sort4);
			map.put("IS_LOCAL_OR_NOT", sort5);
			map.put("PARENT_NAME", sort6);
			map.put("PARENT_PHONENUMBER", sort7);
			map.put("SCHOOL_ID", sort8);
			map.put("GRADE", sort9);
			map.put("PICTURE_URL", sort10);
			map.put("BIRTH_DATE", sort11);
			map.put("ADDRESS", sort12);
			map.put("STUDENT_CODE", sort13);
			map.put("IS_LOWINCOME", sort14);
			map.put("HOME_PHONE", sort15);
			map.put("ACCOUNT_ID", sort16);
			map.put("IS_BLACKLIST", sort17);
			map.put("BLACKLIST_REASON", sort18);
			map.put("BLACKLIST_REASON_URL", sort19);
			map.put("CREATE_BY", sort20);
			map.put("CREATE_DATE", sort21);
			map.put("UPDATE_BY", sort22);
			map.put("UPDATE_DATE", sort23);
			map.put("REMARKS", sort24);
			map.put("ID+1", sort0);
			record.setOrderSegment(Tools.toOrderSegment(map));
			PageList<TmStudent> list = null;
			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
			record.setPage(page);
			record.setPageSize(pageSize);
			record.setId(id);
			record.setCardType(cardType);
			record.setIdcardNo(idcardNo);
			record.setName(name);
			record.setSex(sex);
			record.setIsLocalOrNot(isLocalOrNot);
			record.setParentName(parentName);
			record.setParentPhonenumber(parentPhonenumber);
			record.setSchoolId(schoolId);
			record.setGrade(grade);
			record.setPictureUrl(pictureUrl);
			record.setBirthDate(birthDate);
			record.setAddress(address);
			record.setStudentCode(studentCode);
			record.setIsLowincome(isLowincome);
			record.setHomePhone(homePhone);
			record.setAccountId(accountId);
			record.setIsBlacklist(isBlacklist);
			record.setBlacklistReason(blacklistReason);
			record.setBlacklistReasonUrl(blacklistReasonUrl);
			record.setCreateBy(createBy);
			record.setCreateDateStr(createDate);
			record.setUpdateBy(updateBy);
			record.setUpdateDateStr(updateDate);
			record.setRemarks(remarks);
			list = iStudentService.selectBySelective(record);
			Object[][] objs = new Object[list.size()][25]; // 数组长度需要与前端列数保持一致
			for(int i = 0;i<list.size();i++){
				objs[i][0] = Tools.rowSelectionSet(list.get(i).getId());
				objs[i][1] = Tools.rowSelectionSet(list.get(i).getCardType());
				objs[i][2] = Tools.rowSelectionSet(list.get(i).getIdcardNo());
				objs[i][3] = Tools.rowSelectionSet(list.get(i).getName());
				objs[i][4] = Tools.rowSelectionSet(list.get(i).getSex());
				objs[i][5] = Tools.rowSelectionSet(list.get(i).getIsLocalOrNot());
				objs[i][6] = Tools.rowSelectionSet(list.get(i).getParentName());
				objs[i][7] = Tools.rowSelectionSet(list.get(i).getParentPhonenumber());
				objs[i][8] = Tools.rowSelectionSet(list.get(i).getSchoolId());
				objs[i][9] = Tools.rowSelectionSet(list.get(i).getGrade());
				objs[i][10] = Tools.rowSelectionSet(list.get(i).getPictureUrl());
				objs[i][11] = Tools.rowSelectionSet(list.get(i).getBirthDate());
				objs[i][12] = Tools.rowSelectionSet(list.get(i).getAddress());
				objs[i][13] = Tools.rowSelectionSet(list.get(i).getStudentCode());
				objs[i][14] = Tools.rowSelectionSet(list.get(i).getIsLowincome());
				objs[i][15] = Tools.rowSelectionSet(list.get(i).getHomePhone());
				objs[i][16] = Tools.rowSelectionSet(list.get(i).getAccountId());
				objs[i][17] = Tools.rowSelectionSet(list.get(i).getIsBlacklist());
				objs[i][18] = Tools.rowSelectionSet(list.get(i).getBlacklistReason());
				objs[i][19] = Tools.rowSelectionSet(list.get(i).getBlacklistReasonUrl());
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
	 * 添加学生资料表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session, TmStudent record) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setCreateBy(user.getUsername());
			record.setUpdateBy(user.getUsername());
			resMsg = iStudentService.insertSelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 通过学生资料表id取得学生资料表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/data/{id}")
	@ResponseBody
	public TmStudent queryById(@PathVariable("id") String id) {
		TmStudent record = null;
		try{
			record = iStudentService.selectByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		return record;
	}

	/**
	 * 
	 * 修改学生资料表信息
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "/modify", method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpSession session, TmStudent record) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			record.setUpdateBy(user.getUsername());
			resMsg = iStudentService.updateByPrimaryKeySelective(record);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}

	/**
	 * 
	 * 根据id删除学生资料表信息
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
			resMsg = iStudentService.deleteByPrimaryKey(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
}
