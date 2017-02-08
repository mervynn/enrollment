package com.gh.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gh.util.constant.Constant;

@Service
public class ContextServiceImpl{
	
	private static final Logger logger = Logger.getLogger(ContextServiceImpl.class);
	
	@PostConstruct
	public void initProperties() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		Properties p = new Properties();
		try{
			p.load(inputStream);
		} catch (IOException e){
			logger.error("初始化配置文件异常", e);
		}
		// 设定域名
		Constant.DOMAIN_NAME = p.getProperty("domain_name");
	}
}
