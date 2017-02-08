package com.xinyin.baoming.controller.wap;

import java.io.IOException;
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

import com.xinyin.baoming.controller.common.BaseController;
import com.xinyin.baoming.model.vo.TmAccount;
import com.xinyin.baoming.model.vo.TmClass;
import com.xinyin.baoming.model.vo.TmStudent;
import com.xinyin.baoming.service.IClassService;
import com.xinyin.baoming.service.IShoppingCartService;
import com.xinyin.baoming.service.IStudentService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 班级表Api
 * 
 * 创建时间： 2016-08-09
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/wap/class")
public class WapClassController extends BaseController {

	private static final Logger logger = Logger.getLogger(WapClassController.class);

	@Autowired
	IClassService iClassService;
	
	@Autowired
	IStudentService iStudentService;
	
	@Autowired
	IShoppingCartService iShoppingCartService;
	
	@Autowired
	IContextService iContextService;
	
	/**
	 * 班级列表
	 * 
	 * @param model
	 * @param session
	 * @param keyword
	 * @param filter
	 * @param searchBtnEvent
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, HttpSession session, String keyword, String filter, String searchBtnEvent)  {
		
		try{
			TmAccount ta = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(ta == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			// 获取当前招生阶段
			String curStage = iContextService.getCodeList("0").get(0).getName();
			// 招生阶段关闭时，无任何班级返回
			if(Constant.ENROL_STAGE_CLOSE.equals(curStage) || Constant.ENROL_STAGE_FOR_OLD.equals(curStage)){
				model.addAttribute("enrolStageClose", curStage);
				return Views.WAP_CLASS_LIST;
			}
			TmClass param = new TmClass();
			// 取得当前登陆学生的生日，没设置默认学生、默认学生不存在或默认学生生日数据为空都无任何班级返回
			if(StringUtils.isEmpty(ta.getDefultStudentId())){
				return Views.WAP_CLASS_LIST;
			}else{
				TmStudent ts = iStudentService.selectByPrimaryKey(ta.getDefultStudentId());
				if(ts == null || StringUtils.isEmpty(ts.getBirthDate())){
					return Views.WAP_CLASS_LIST;
				}else{
					param.setMinAge(ts.getBirthDate());
				}
			}
			// session中有班级类型并且非查询按钮触发的查询时，过滤条件要增加班级类型
			if(StringUtils.isBlank(searchBtnEvent)){
				param.setClassTypeId(ta.getClassTypeId());
			}
			// 条件模糊匹配
			if(StringUtils.isNotEmpty(keyword)){
				param.setRemarks(keyword);
				model.addAttribute("keyword", keyword);
			}
			// 默认过滤条件-已上架
			param.setStatus(Constant.COMMODITY_STATUS_UP);
			// 售罄与未售罄互斥筛选
			if(Constant.FILTER_SOLDOUT.equals(filter)){
				param.setRemainAmount("0");
			}else{
				// 没有任何过滤按钮柴筛选情况下,排除调售罄的班级
				// 需要在xml中根据辅助参数idListStr判定是否要排除售罄的班级
				param.setIdListStr("1");
			}
			// 其他互斥按钮筛选
			Map<String,String> map = new LinkedHashMap<String, String>();
			if(Constant.FILTER_GENERAL.equals(filter)){
				map.put("LIMITED_AMOUNT+1", Constant.SORT_DESC);
			}else if(Constant.FILTER_VOLUMN.equals(filter)){
				map.put("REMAIN_AMOUNT+1", Constant.SORT_DESC);
			}else if(Constant.FILTER_PRICE_UP.equals(filter)){
				map.put("TUITION_FEE+1", Constant.SORT_ASC);
			}else if(Constant.FILTER_PRICE_DOWN.equals(filter)){
				map.put("TUITION_FEE+1", Constant.SORT_DESC);
			}
			param.setOrderSegment(Tools.toOrderSegment(map));
			model.addAttribute("filter", filter);
			param.setRemarks(keyword);
			param.setPageSize(Constant.DEFAULT_MAX_PAGE_SIZE);
			List<TmClass> classList = iClassService.selectBySelectiveForWap(param);
			model.addAttribute("list", classList);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		return Views.WAP_CLASS_LIST;
	}
	
	/**
	 * 班级详情
	 *
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/detail/{id}")
	public String detail(@PathVariable("id") String id, Model model, HttpSession session) {
		TmClass record = null;
		int cartClassAmount = 0;
		try{
			TmAccount ta = (TmAccount) session.getAttribute(Constant.WAP_LOGIN_KEY);
			if(ta == null){
				return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
			}
			TmClass tc = new TmClass();
			tc.setId(id);
			List<TmClass> list = iClassService.selectExactlyBySelective(tc);
			record = list.size() != 0 ? list.get(0) : null;
			model.addAttribute("detail", record);
			cartClassAmount = iShoppingCartService.selectByStudentId(ta.getDefultStudentId()).size();
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return "redirect:" + Views.WAP_INDEX_PAGE + Constant.SUFFIX;
		}
		// 购物车中课程数量
		model.addAttribute("classAmount", cartClassAmount);
		return Views.WAP_CLASS_DETAIL;
	}

}
