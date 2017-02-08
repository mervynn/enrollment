package com.gh.util.constant;

import java.io.File;

public class Constant {
	/** 登录用户KEY*/
	public static final String LOGIN_KEY = "admin_login_user";
	/** 系统名KEY*/
	public static final String SYSTEM_NAME_KEY = "systemName";
	/** 系统异常  **/
	public static final String SYSTEM_EXCEPTION = "系统异常,请联系管理员。";
	/** 默认首页页码  **/
	public static final String DEFAULT_PAGE = "1";
	/** 默认每页数据量  **/
	public static final String DEFAULT_PAGE_SIZE = "10";
	/** 登录异常  **/
	public static final String LOGIN_EXCEPTION = "登录异常";
	/** 等出异常 **/
	public static final String LOG_OUT_EXCEPTION = "登出异常";
	/** 顿号 **/
	public static final String COMMA = "、";
	/** 日期格式化 **/
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** 用户菜单KEY **/
	public static final String MENU_ROOTS = "userMenuRoots";
	/** apk在容器下的路径 **/
	public static final String APK_PATH = "apk";
	/** sdk在容器下的路径 **/
	public static final String SDK_PATH = "sdk";
	/** 图片在容器下的路径 **/
	public static final String IMG_PATH = "img";
	/** 攻略文件在容器下的路径 **/
	public static final String GUIDE_PATH = "ueditor" + File.separator + "html";
	/** 接口成功执行标识 **/
	public static final String API_SUCCESS_CODE = "00";
	/** 接口执行异常标识 **/
	public static final String API_EXCEPTION_CODE = "09";
	/** 接口参数不合法标识 **/
	public static final String API_ILLEGAL_PARAMETER_CODE = "01";
	/** 接口数据不存在标识 **/
	public static final String API_DATA_NOT_EXISTS_CODE = "02";
	/** 接口数据已经存在标识 **/
	public static final String API_DATA_EXISTS_CODE = "03";
	/** 应用程序编程接口URI **/
	public static final String API_START_URI = "/api/";
	/** 应用程序编程接口版本 **/
	public static final String API_VERSION = "v1";
	/** 项目网址后缀 **/
	public static final String SUFFIX = ".shtml";
	/** 计划任务系统标识 **/
	public static final String SCHEDULED_TASK = "scheduledtask";
	/** 删除{days}天前的非推荐历史新闻 **/
	public static final Integer NEED_TO_DELETE_NEWS_DAYS = 7;
	/** 新闻相似度判定指标：大于该指标即为相似，不归档 **/
	public static final double SIMILARITY_INDEX = 0.5;
	/** 默认的排序字段数值 **/
	public static final String MAX_SORT_NUM = "9999999999";
	/** 代码: 1 **/
	public static final String NUMBER_ONE = "1";
	/** 代码: 0 **/
	public static final String NUMBER_ZERO = "0";
	/** 保留新闻(推荐和非推荐)数量: 1000 **/
	public static final String PERSIST_NEWS_AMOUNT = "1000";
	/** 常量：是 **/
	public static final String WORD_YES = "是";
	/** 常量：否 **/
	public static final String WORD_NO = "否";
	/** 默认用户名 **/
	public static final String DEFAULT_GH_USER_NAME = "路人乙";
	/** 默认用户年龄 **/
	public static final String DEFAULT_GH_USER_AGE = "25";
	/** 默认用户性别 0:未设定 **/
	public static final String DEFAULT_GH_USER_GENDER = "0";
	/** 默认用户初始化积分 **/
	public static final String INIT_SCORE = "0";
	/** 默认用户初始化消费 **/
	public static final String INIT_CONSUMPTION = "0";
	/** 默认用户初始化等级 **/
	public static final String INIT_GRADE = "1";
	/** 默认用户初始化在线时长 **/
	public static final String INIT_TIME_LENGTH = "0";
	/** 默认用户密码(方便前端空值校验) **/
	public static final String DEFAULT_GH_USER_PASSWORD = "0";
	/** 显示标识 **/
	public static final String IS_SHOW = "是";
	/** 不显示标识 **/
	public static final String IS_NOT_SHOW = "否";
	/** 数据列表升序标识 **/
	public static final String ASC_CODE = "1";
	/** 数据列表降序标识 **/
	public static final String DESC_CODE = "0";
	/** 用户来源:手机 **/
	public static final String USER_SOURCE_MOBILE = "0";
	/** 用户来源:微信 **/
	public static final String USER_SOURCE_WECHAT = "1";
	/** 用户来源:微博 **/
	public static final String USER_SOURCE_BLOG = "2";
	/** 用户来源:游客 **/
	public static final String USER_SOURCE_TOURIST = "3";
	/** 支付方式:alipay **/
	public static final String PAY_TYPE_ALIPAY = "alipay";
	/** 支付方式:wechat **/
	public static final String PAY_TYPE_WECHAT = "wechat";
	/** 分页页数参数名 **/
	public static final String PAGE_NO = "pno";
	/** 分页每页数据数量参数名 **/
	public static final String PAGE_SIZE = "size";
	/** 系统操作员 **/
	public static final String SYSTEM = "system";
	/******************↓以下内容系统初始化根据配置文件环境变更↓******************************************/
	/** 项目域名 **/
	public static String DOMAIN_NAME = "http://admin.game-hot.tv";
	/** 默认用户头像地址 **/
	public static String DEFAULT_GH_USER_PICTURE = "http://admin.game-hot.tv/user/1440984463007temp.jpg";
	/** 手机端正文通用样式地址 **/
	public static String COMMON_MOBILE_CSS = "http://admin.game-hot.tv/mobile/css/common_mobile.css";
	/** 手机端jquery地址 **/
	public static String JQUERY_2_1_4_MIN_JS = "http://admin.game-hot.tv/mobile/scripts/jquery-2.1.4.min.js";
	/** 手机端jquery.lazyload地址 **/
	public static String JQUERY_LAZYLOAD_JS = "http://admin.game-hot.tv/mobile/scripts/jquery.lazyload.js";
	/** 手机端jquery.imgAutoSize地址 **/
	public static String JQUERY_IMGAUTOSIZE_JS = "http://admin.game-hot.tv/mobile/scripts/jquery.imgAutoSize.js";
	/** 手机端默认图片地址 **/
	public static String DEFAULT_IMAGE = "http://admin.game-hot.tv/mobile/img/grey-default.png";
	/******************↑         THE END         ↑******************************************/
	/** 站点区分：百度新闻 **/
	public static final String WEBSITE_TYPE_BAIDU_NEWS = "0";
	/** 站点区分：百度百家 **/
	public static final String WEBSITE_TYPE_BAIDU_BAIJIA = "1";
	/** 网络新闻最小像素:横向  **/
	public static final int MIN_NEWS_PICTURE_X = 160;
	/** 网络新闻最小像素:纵向  **/
	public static final int MIN_NEWS_PICTURE_Y = 120;
	
}
