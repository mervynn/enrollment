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
import com.xinyin.baoming.model.vo.TmContent;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.service.IContentService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;
import com.xinyin.baoming.util.constant.Views;

/**  
 * 内容管理
 * 
 * 创建时间：2016-08-19
 * @author HeMingwei
 * @version 1.0
 */
@Controller
@RequestMapping("/operation/content")
public class ContentController {
	private static final Logger logger = Logger.getLogger(ContentController.class);
	
	@Autowired
	IContextService iContextService;
	
	@Autowired
	IContentService iContentService;
	
	/**
	 * 内容管理初始化
	 * @param operationContent
	 * @return
	 */
	@RequestMapping(value="")
	public String init(Model model){
		model.addAttribute("contentType", iContextService.getCodeList("9"));
		return Views.OPERATION_CONTENT;
	}
	
	/**
	 * 内容列表查询(返回Json数据)
	 * @param content
	 * @param page
	 * @param pageSize
	 * @param sort0
	 * @param sort1
	 * @param sort2
	 * @param sort3
	 * @param sort4
	 * @param sort5
	 * @param sort6
	 * @param sort7
	 * @param id
	 * @param name
	 * @param uri
	 * @param createBy
	 * @param createDate
	 * @param updateBy
	 * @param updateDate
	 * @param remarks
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/data")
	@ResponseBody
	public Object[] queryContentResultReportJson(TmContent content,
			@RequestParam(value="page",required = true, defaultValue = Constant.DEFAULT_PAGE) int page,  
			@RequestParam(value="size",required = true, defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value="col[0]",required = false, defaultValue = Constant.DESC_CODE) String sort0,
			@RequestParam(value="col[1]",required = false) String sort1, @RequestParam(value="col[2]",required = false) String sort2,
			@RequestParam(value="col[3]",required = false) String sort3, @RequestParam(value="col[4]",required = false) String sort4,
			@RequestParam(value="col[5]",required = false) String sort5, @RequestParam(value="col[6]",required = false) String sort6,
			@RequestParam(value="col[7]",required = false) String sort7, @RequestParam(value="fcol[0]",required = false) String id,
			@RequestParam(value="fcol[1]",required = false) String type, @RequestParam(value="fcol[2]",required = false) String uri, 
			@RequestParam(value="fcol[3]",required = false) String createBy, @RequestParam(value="fcol[4]",required = false) String createDate, 
			@RequestParam(value="fcol[5]",required = false) String updateBy, @RequestParam(value="fcol[6]",required = false) String updateDate, 
			@RequestParam(value="fcol[7]",required = false) String remarks, HttpServletRequest req,HttpServletResponse resp) throws Exception{
		// 返回数据集合
		Object[] arr = new Object[2];
		try {
			// 封装排序参数
			Map<String,String> map = new LinkedHashMap<String, String>(); // 先进先出
			map.put("NAME", sort1);
			map.put("URI", sort2);
			map.put("CREATE_BY", sort3);
			map.put("CREATE_DATE", sort4);
			map.put("UPDATE_BY", sort5);
			map.put("UPDATE_DATE", sort6);
			map.put("REMARKS", sort7);
			map.put("ID+1", sort0);
			content.setOrderSegment(Tools.toOrderSegment(map));
			PageList<TmContent> list = null;
			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
			content.setPage(page);
			content.setPageSize(pageSize);
			content.setId(id);
			content.setType(type);
			content.setUri(uri);
			content.setCreateBy(createBy);
			content.setCreateDateStr(createDate);
			content.setUpdateBy(updateBy);
			content.setUpdateDateStr(updateDate);
			content.setRemarks(remarks);
			list = iContentService.selectContentList(content);
			Object[][] objs = new Object[list.size()][8]; // 数组长度需要与前端列数保持一致
			for(int i = 0;i<list.size();i++){
				objs[i][0] = Tools.rowSelectionSet(list.get(i).getId());
				objs[i][1] = Tools.rowSelectionSet(list.get(i).getType());
				objs[i][2] = Tools.rowSelectionSet(list.get(i).getUri());
				objs[i][3] = Tools.rowSelectionSet(list.get(i).getCreateBy());
				objs[i][4] = Tools.rowSelectionSet(list.get(i).getCreateDate() !=null ?
					sdf.format(list.get(i).getCreateDate()):StringUtils.EMPTY);
				objs[i][5] = Tools.rowSelectionSet(list.get(i).getUpdateBy());
				objs[i][6] = Tools.rowSelectionSet(list.get(i).getUpdateDate() !=null ?
					sdf.format(list.get(i).getUpdateDate()):StringUtils.EMPTY);
				objs[i][7] = Tools.rowSelectionSet(list.get(i).getRemarks());
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
	 * 添加内容
	 * @param TmContent
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session, TmContent operationContent) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			operationContent.setCreateBy(user.getUsername());
			operationContent.setUpdateBy(user.getUsername());
			resMsg = iContentService.save(operationContent);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 通过内容id取得内容
	 * @param id
	 */
	@RequestMapping(value="/data/{id}")
	@ResponseBody
	public TmContent queryById(@PathVariable("id") String id) {
		TmContent content = null;
		try{
			content = iContentService.queryContentInfoById(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
		}
		return content;
	}
	
	/**
	 * 修改内容
	 * @param TmContent
	 */
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	@ResponseBody
	public String modify(HttpSession session, TmContent operationContent) {
		String resMsg = StringUtils.EMPTY;
		try{
			TsUser user = (TsUser) session.getAttribute(Constant.LOGIN_KEY);
			operationContent.setUpdateBy(user.getUsername());
			resMsg = iContentService.modify(operationContent);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 删除
	 * @param req
	 * @param resp
	 * @param operationContent
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(value="id") String id) {
		String resMsg = StringUtils.EMPTY;
		try{
			resMsg = iContentService.deleteContent(id);
		}catch(Exception e){
			logger.error(Constant.SYSTEM_EXCEPTION,e);
			return Constant.SYSTEM_EXCEPTION;
		}
		return resMsg;
	}
	
	/**
	 * 类型存在校验
	 * 
	 * @param code
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/valid/{type}")
	@ResponseBody
	public String valid(@PathVariable("type") String type) {
		TmContent oc = iContentService.selectByType(type);
		if(oc != null){
			return Message.CONTENT_EXISTS;
		}else{
			return StringUtils.EMPTY;
		}
	}
	
}