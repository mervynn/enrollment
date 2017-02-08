package com.xinyin.baoming.controller.basedata;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.xinyin.baoming.model.vo.TcCode;
import com.xinyin.baoming.service.ICodeService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

/**
 * 
 * 码表管理
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/basedata/code")
public class CodeController extends BaseController {

private static final Logger logger = Logger.getLogger(CodeController.class);
	
	@Autowired
	ICodeService iSysCodeService;
	
	@Autowired
	IContextService iContextService;
	
	/**
	 * 系统常量管理初始化
	 * @param sysCode
	 * @return
	 */
	@RequestMapping(value="")
	public String init(){
		return Views.BASEDATA_CODE;
	}
	
	/**
	 * 系统常量(返回Json数据)
	 * 
	 * @param sysCode
	 * @param page
	 * @param pageSize
	 * @param sort0
	 * @param sort1
	 * @param sort2
	 * @param sort3
	 * @param sort4
	 * @param code
	 * @param sort
	 * @param name
	 * @param englishName
	 * @param type
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/data")
	@ResponseBody
	public Object[] querySysCodeResultReportJson(TcCode sysCode,
			@RequestParam(value="page",required = true, defaultValue = Constant.DEFAULT_PAGE) int page,  
			@RequestParam(value="size",required = true, defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value="col[0]",required = false, defaultValue = Constant.ASC_CODE) String sort0, // 1:默认主键升序排序
			@RequestParam(value="col[1]",required = false, defaultValue = Constant.ASC_CODE) String sort1, 
			@RequestParam(value="col[2]",required = false) String sort2, @RequestParam(value="col[3]",required = false) String sort3, 
			@RequestParam(value="col[4]",required = false) String sort4, @RequestParam(value="fcol[0]",required = false) String code, 
			@RequestParam(value="fcol[1]",required = false) String sort, @RequestParam(value="fcol[2]",required = false) String name, 
			@RequestParam(value="fcol[3]",required = false) String englishName, @RequestParam(value="fcol[4]",required = false) String type, 
			HttpServletRequest req,HttpServletResponse resp) throws Exception{
		// 返回数据集合
		Object[] arr = new Object[2];
		try {
			// 封装排序参数
			Map<String,String> map = new LinkedHashMap<String, String>(); // 先进先出
			map.put("NAME", sort2);
			map.put("ENGLISH_NAME", sort3);
			map.put("TYPE", sort4);
			map.put("CODE+1", sort0); // 初始化升序
			map.put("SORT+1", sort1); // 初始化升序
			sysCode.setOrderSegment(Tools.toOrderSegment(map));
			PageList<TcCode> list = null;
			sysCode.setPage(page);
			sysCode.setPageSize(pageSize);
			sysCode.setCode(code);
			sysCode.setSort(sort);
			sysCode.setName(name);
			sysCode.setEnglishName(englishName);
			sysCode.setType(type);
			list = iSysCodeService.selectSysCodeList(sysCode);
			Object[][] objs = new Object[list.size()][5]; // 数组长度需要与前端列数保持一致
			for(int i = 0;i<list.size();i++){
				objs[i][0] = Tools.rowSelectionSet(list.get(i).getCode());
				objs[i][1] = Tools.rowSelectionSet(list.get(i).getSort());
				objs[i][2] = Tools.rowSelectionSet(list.get(i).getName());
				objs[i][3] = Tools.rowSelectionSet(list.get(i).getEnglishName());
				objs[i][4] = Tools.rowSelectionSet(list.get(i).getType());
			}
			arr[0]=(list.getPaginator().getTotalCount());
			arr[1]=objs;
		} catch (Exception e) {
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			throw e;
		}
		return arr;
	}

	/**
	 * 添加系统常量
	 * @param TcCode
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public String save(Model model,TcCode sysCode) {
		String resMsg = StringUtils.EMPTY;
		try{
			resMsg = iSysCodeService.save(sysCode);
			iContextService.initDic(); // 更新内存字典数据
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 通过系统常量id取得系统常量
	 * @param id
	 */
	@RequestMapping(value="/data/code/{code}/sort/{sort}")
	@ResponseBody
	public TcCode queryById(@PathVariable("code") String code, @PathVariable("sort") String sort) {
		TcCode sysCode = null;
		try{
			sysCode = iSysCodeService.querySysCodeInfoById(code, sort);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		return sysCode;
	}
	
	/**
	 * 修改系统常量
	 * @param TcCode
	 */
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	@ResponseBody
	public String modify(Model model, TcCode sysCode) {
		String resMsg = StringUtils.EMPTY;
		try{
			resMsg = iSysCodeService.modify(sysCode);
			iContextService.initDic(); // 更新内存字典数据
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 常量删除
	 * @param req
	 * @param resp
	 * @param sysCode
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(value="id") String id) {
		String resMsg = StringUtils.EMPTY;
		try{
			resMsg = iSysCodeService.deleteSysCode(id);
			iContextService.initDic(); // 更新内存字典数据
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 常量存在校验
	 * 
	 * @param code
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/valid/{code}/{sort}")
	@ResponseBody
	public String valid(@PathVariable("code") String code, @PathVariable("sort") String sort) {
		// 常量存在查询
		TcCode sysCode = iSysCodeService.querySysCodeInfoById(code, sort);
		if(sysCode != null){
			return Message.SYS_CODE_EXISTS;
		}else{
			return StringUtils.EMPTY;
		}
	}
}
