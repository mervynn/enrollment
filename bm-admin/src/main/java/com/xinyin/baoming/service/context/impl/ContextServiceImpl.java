package com.xinyin.baoming.service.context.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyin.baoming.mapper.TcCodeMapper;
import com.xinyin.baoming.mapper.TmClassTypeMapper;
import com.xinyin.baoming.mapper.TmSchoolMapper;
import com.xinyin.baoming.model.vo.TcCode;
import com.xinyin.baoming.model.vo.TmClassType;
import com.xinyin.baoming.model.vo.TmSchool;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.constant.Constant;

@Service("contextService")
public class ContextServiceImpl implements IContextService{
	
	private static final Logger logger = Logger.getLogger(ContextServiceImpl.class);
	
	@Autowired
	TcCodeMapper sysCodeMapper; // 码表数据映射
	
	@Autowired
	TmClassTypeMapper tmClassTypeMapper; // 课程类型映射
	
	@Autowired
	TmSchoolMapper tmSchoolMapper; // 学校
	
	/**
	 * 码表信息
	 */
	private List<TcCode> codeList = new ArrayList<TcCode>();
	
	/**
	 * 课程类型信息
	 */
	private List<TmClassType> classTypeList = new ArrayList<TmClassType>();
	
	/**
	 * wap端左边类型树
	 */
	private List<TmClassType> leftTree = new ArrayList<TmClassType>();
	
	/**
	 * 学校信息
	 */
	private List<TmSchool> schoolList = new ArrayList<TmSchool>();
	
	@PostConstruct
	@Override
	public void init() {
		logger.info("上下文服务初始化开始...");
		initDic();
		logger.info("码表信息初始化完成");
		initClassType();
		logger.info("课程类型菜单及课程类型左侧菜单树初始化完成");
		initSchool();
		logger.info("学校信息初始化完成");
		initProperties();
		logger.info("配置文件加载完成");
		logger.info("上下文服务初始化结束...");
	}

	@Override
	public void initDic() {
		codeList = sysCodeMapper.selectSysCode();
	}
	
	@Override
	public void initClassType() {
		classTypeList = tmClassTypeMapper.selectExactlyBySelective(new TmClassType());
		
		// 左侧根目录
		leftTree = new ArrayList<TmClassType>();
		for(TmClassType note : classTypeList){
			if(StringUtils.isBlank(note.getParentId())){
				leftTree.add(note);
			}
		}
	}
	
	@Override
	public void initSchool() {
		schoolList = tmSchoolMapper.selectAllSchool(new TmSchool());
	}
	
	@Override
	public void initProperties() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		Properties p = new Properties();
		try{
			p.load(inputStream);
		} catch (IOException e){
			logger.error("配置文件加载异常", e);
		}
		// 设定域名
		Constant.DOMAIN_NAME = p.getProperty("domain_name");
		// 用户默认头像
		Constant.DEFAULT_GH_USER_PICTURE= Constant.DOMAIN_NAME + "/user/1440984463007temp.jpg";
		// 手机端正文CSS通用样式地址设定
		Constant.COMMON_MOBILE_CSS = Constant.DOMAIN_NAME + "/mobile/css/common_mobile.css";
		// 加载jquery
		Constant.JQUERY_2_1_4_MIN_JS = Constant.DOMAIN_NAME + "/mobile/scripts/jquery-2.1.4.min.js";
		// 加载jquery.lazload
		Constant.JQUERY_LAZYLOAD_JS = Constant.DOMAIN_NAME + "/mobile/scripts/jquery.lazyload.js";
		// 加载jquery.imgAutoSize
		Constant.JQUERY_IMGAUTOSIZE_JS = Constant.DOMAIN_NAME + "/mobile/scripts/jquery.imgAutoSize.js";
		// 加载默认图片
		Constant.DEFAULT_IMAGE = Constant.DOMAIN_NAME + "/mobile/img/grey-default.png";
		// 设定上传文件、图片保存在服务器的绝对路径
		Constant.UPLOAD_FILE_PATH = p.getProperty("upload_file_path");
	}
	
	@Override
	public List<TcCode> getCodeList(String type){
		List<TcCode> cList = new ArrayList<TcCode>();
		for(TcCode gc : codeList){
			if(type != null && type.equals(gc.getCode())){
				cList.add(gc);
			}
		}
		return cList;
	}
	
	@Override
	public List<TmClassType> getClassTypeList(String parentId){
		List<TmClassType> cList = new ArrayList<TmClassType>();
		if(StringUtils.isBlank(parentId)){
			return classTypeList;
		}else{
			for(TmClassType tct : classTypeList){
				if(parentId.equals(tct.getParentId())){
					cList.add(tct);
				}
			}
		}
		return cList;
	}
	
	@Override
	public TmClassType getClassTypeById(String classTypeId){
		for(TmClassType tct : classTypeList){
			if(classTypeId.equals(tct.getId())){
				return tct;
			}
		}
		return null;
	}
	
	@Override
	public List<TmClassType> getLeftTree(){
		return leftTree;
	}
	
	@Override
	public List<TmSchool> getAllSchool(){
		return schoolList;
	}
	
}
