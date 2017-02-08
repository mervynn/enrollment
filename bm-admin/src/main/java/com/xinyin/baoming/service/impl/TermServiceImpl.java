package com.xinyin.baoming.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.mapper.ThLastClassMapper;
import com.xinyin.baoming.mapper.ThLastClassStudentMapper;
import com.xinyin.baoming.mapper.TmClassMapper;
import com.xinyin.baoming.mapper.TmTermMapper;
import com.xinyin.baoming.mapper.TtClassStudentMapper;
import com.xinyin.baoming.mapper.TtOrderDetailMapper;
import com.xinyin.baoming.mapper.TtOrderMapper;
import com.xinyin.baoming.mapper.TtShoppingCartMapper;
import com.xinyin.baoming.mapper.TzAllClassMapper;
import com.xinyin.baoming.mapper.TzAllClassStudentMapper;
import com.xinyin.baoming.mapper.TzAllOrderDetailMapper;
import com.xinyin.baoming.mapper.TzAllOrderMapper;
import com.xinyin.baoming.model.vo.TmTerm;
import com.xinyin.baoming.service.ITermService;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 学期信息信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
@SuppressWarnings("serial")
public class TermServiceImpl extends BaseService implements ITermService {

	@Autowired
	private TmTermMapper tmTermMapper;
	@Autowired
	TzAllClassMapper tzAllClassMapper;
	@Autowired
	ThLastClassMapper thLastClassMapper;
	@Autowired
	TzAllClassStudentMapper tzAllClassStudentMapper;
	@Autowired
	ThLastClassStudentMapper thLastClassStudentMapper;
	@Autowired
	TmClassMapper tmClassMapper;
	@Autowired
	TtClassStudentMapper ttClassStudentMapper;
	@Autowired
	TzAllOrderMapper tzAllOrderMapper;
	@Autowired
	TzAllOrderDetailMapper tzAllOrderDetailMapper;
	@Autowired
	TtOrderMapper ttOrderMapper;
	@Autowired
	TtOrderDetailMapper ttOrderDetailMapper;
	@Autowired
	TtShoppingCartMapper ttShoppingCartMapper;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<TmTerm>  selectBySelective( TmTerm record ) throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("id",record.getId());
			params.put("name",record.getName());
			params.put("beginDate",record.getBeginDate());
			params.put("endDate",record.getEndDate());
			params.put("status",record.getStatus());
			params.put("createBy",record.getCreateBy());
			params.put("createDateStr",record.getCreateDateStr());
			params.put("updateBy",record.getUpdateBy());
			params.put("updateDateStr",record.getUpdateDateStr());
			params.put("remarks",record.getRemarks());
			return (PageList<TmTerm>) getPageList(TmTermMapper.class, "selectBySelective",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("学期信息信息查询",e);
		}
	}
	
	/**
	 * 
	 * 查询（精确匹配有值字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Override
	public List<TmTerm> selectExactlyBySelective( TmTerm record ) throws ServiceException {
		try {
			return tmTermMapper.selectExactlyBySelective(record);
		}catch(Exception e){
			throw new ServiceException("通过精确条件取得学期信息信息",e);
		}
	}

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Override
	public TmTerm selectByPrimaryKey( String id ) throws ServiceException {
		try {
			return tmTermMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得学期信息信息",e);
		}
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String deleteByPrimaryKey( String id ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			JSONArray arr =  JSONObject.parseArray(id);
			// 拼装删除消息主键
			String msgIds = StringUtils.EMPTY;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject jo = (JSONObject) arr.get(i);
				String name = Tools.toString(jo.get("name"));
				String key = Tools.toString(jo.get("id"));
				int flag = tmTermMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "学期信息:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("学期信息信息删除",e);
		}
		return resMsg;
	}

	/**
	 * 
	 * 删除（根据匹配的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String deleteBySelective( TmTerm record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			tmTermMapper.deleteBySelective(record);
		}catch(Exception e){
			throw new ServiceException("学期信息信息删除",e);
		}
		return resMsg;
	}

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String insert( TmTerm record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = tmTermMapper.insert(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("学期信息信息添加",e);
		}
		return resMsg;
	}
	
	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String insertSelective( TmTerm record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try{
			// 历史班级数据复制到归档班级表
			tzAllClassMapper.insertFromHisClass();
			// 删除历史班级数据
			thLastClassMapper.deleteAll();
			// 历史班级学生数据复制到归档班级学生表
			tzAllClassStudentMapper.insertFromHisCS();
			// 删除历史班级学生数据
			thLastClassStudentMapper.deleteAll();
			// 班级数据复制到历史班级表
			thLastClassMapper.insertFromClass();
			// 删除班级数据
			tmClassMapper.deleteAll();
			// 班级学生数据复制到历史班级学生表
			thLastClassStudentMapper.insertFromClassStudent();
			// 删除班级学生数据
			ttClassStudentMapper.deleteAll();
			// 订单表数据复制到订单归档表
			tzAllOrderMapper.insertOrder();
			// 订单明细表数据复制到订单明细归档表
			tzAllOrderDetailMapper.insertOrderDetail();
			// 删除订单表数据
			ttOrderMapper.deleteAll();
			// 删除订单明细表数据
			ttOrderDetailMapper.deleteAll();
			// 删除选课车数据
			ttShoppingCartMapper.deleteAll();
			// 上学期学期状态变更为其他学期
			tmTermMapper.updateTermStatus(Constant.TERM_STATUS_LAST, Constant.TERM_STATUS_OTHERS);
			// 本学期学期状态变更为上学期
			tmTermMapper.updateTermStatus(Constant.TERM_STATUS_NEW, Constant.TERM_STATUS_LAST);
			// 新建新学期,学期状态为新建学期
			record.setStatus(Constant.TERM_STATUS_NEW);
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			int flag = tmTermMapper.insertSelective(record);
			if(flag > 0){
				resMsg = Message.SAVE_SUCCESS;
			}else{
				throw new ServiceException(Message.SAVE_FAILED);
			}
		}catch(Exception e){
			throw new ServiceException("学期信息信息添加",e);
		}
		return resMsg;
	}

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String updateByPrimaryKeySelective( TmTerm record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = tmTermMapper.updateByPrimaryKeySelective(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("学期信息信息修改",e);
		}
		return resMsg;
	}

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String updateByPrimaryKey( TmTerm record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = tmTermMapper.updateByPrimaryKey(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("学期信息信息修改",e);
		}
		return resMsg;
	}
}
