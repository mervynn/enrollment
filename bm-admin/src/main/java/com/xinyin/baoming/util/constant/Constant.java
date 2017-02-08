package com.xinyin.baoming.util.constant;

import java.io.File;

public class Constant {
	/** 后台登录用户 session KEY*/
	public static final String LOGIN_KEY = "admin_login_user";
	/** 移动端登录用户 session KEY*/
	public static final String WAP_LOGIN_KEY = "wap_login_user";
	/** 图片验证码  session KEY**/
	public static final String WAP_VERIFY_CODE_KEY = "wap_verify_code";
	/** 手机号码  session KEY**/
	public static final String WAP_PHONENUMBER_KEY = "wap_phonenumber";
	/** 短信验证码  session KEY**/
	public static final String WAP_SMSCODE_KEY = "wap_sms_code";
	/** 系统名KEY*/
	public static final String SYSTEM_NAME_KEY = "systemName";
	/** 访问后台的用户类型代码-系统用户  */
	public static final String USER_ADMIN = "0";
	/** 访问后台的用户类型代码-CP用户   */
	public static final String USER_CP = "1";
	/** 访问后台的用户类型代码-渠道用户  */
	public static final String USER_CHANNEL = "2";
	/** 系统异常  **/
	public static final String SYSTEM_EXCEPTION = "系统异常,请联系管理员。";
	/** 默认首页页码  **/
	public static final String DEFAULT_PAGE = "1";
	/** 默认每页数据量  **/
	public static final String DEFAULT_PAGE_SIZE = "10";
	/** 默认每页最大数据量  **/
	public static final int DEFAULT_MAX_PAGE_SIZE = 1000;
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
	/** H5文件在容器下的路径 **/
	public static final String GUIDE_PATH = "ueditor" + File.separator + "html";
	/** 接口成功执行标识 **/
	public static final String API_SUCCESS_CODE = "0";
	/** 接口执行异常标识 **/
	public static final String API_EXCEPTION_CODE = "9";
	/** 接口参数不合法标识 **/
	public static final String API_ILLEGAL_PARAMETER_CODE = "1";
	/** 接口数据不存在标识 **/
	public static final String API_DATA_NOT_EXISTS_CODE = "2";
	/** 接口数据已经存在标识 **/
	public static final String API_DATA_EXISTS_CODE = "3";
	/** 普通提醒文字标识 **/
	public static final String API_DATA_NORMAL_MSG_CODE = "4";
	/** 多条数据需要选择 **/
	public static final String API_MULTI_DATA_NEED_SELECT = "5";
	/** 会话失效 **/
	public static final String API_SESSION_EXPIRED = "6";
	/** 数据添加失败 **/
	public static final String API_ADD_FAILED = "7";
	/** 手机号码已存在，可能需要解绑原有账号，绑定新账号  **/
	public static final String API_PHOEN_OWNER_CHANGE = "22";
	/** 短信验证码错误  **/
	public static final String API_SMS_CODE_MISTAKE = "25";
	/** 验证码错误 **/
	public static final String API_VERIFY_CODE_MISTAKE = "257";
	/** 应用程序编程接口URI **/
	public static final String API_START_URI = "/wap/";
	/** 应用程序编程接口版本 **/
	public static final String API_VERSION = "v1";
	/** 项目网址后缀 **/
	public static final String SUFFIX = ".shtml";
	/** 计划任务系统标识 **/
	public static final String SCHEDULED_TASK = "scheduledtask";
	/** 计划任务需要删除的历史新闻天数 **/
	public static final Integer NEED_TO_DELETE_NEWS_DAYS = 7;
	/** 新闻相似度判定指标：大于该指标即为相似，不归档 **/
	public static final double SIMILARITY_INDEX = 0.5;
	/** 默认的排序字段数值 **/
	public static final String MAX_SORT_NUM = "9999999999";
	/** 代码: 1 **/
	public static final String NUMBER_ONE = "1";
	/** 代码: 0 **/
	public static final String NUMBER_ZERO = "0";
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
	/** 分页页数参数名 **/
	public static final String PAGE_NO = "pno";
	/** 分页每页数据数量参数名 **/
	public static final String PAGE_SIZE = "size";
	/** 系统操作员 **/
	public static final String SYSTEM = "system";
	/******************↓以下内容系统初始化根据配置文件环境变更↓******************************************/
	/** 项目域名 **/
	public static String DOMAIN_NAME = "";
	/** 默认用户头像地址 **/
	public static String DEFAULT_GH_USER_PICTURE = DOMAIN_NAME + "/user/1440984463007temp.jpg";
	/** 移动端正文通用样式地址 **/
	public static String COMMON_MOBILE_CSS = DOMAIN_NAME + "/mobile/css/common_mobile.css";
	/** 移动端jquery地址 **/
	public static String JQUERY_2_1_4_MIN_JS = DOMAIN_NAME + "/mobile/scripts/jquery-2.1.4.min.js";
	/** 移动端jquery.lazyload地址 **/
	public static String JQUERY_LAZYLOAD_JS = DOMAIN_NAME + "/mobile/scripts/jquery.lazyload.js";
	/** 移动端jquery.imgAutoSize地址 **/
	public static String JQUERY_IMGAUTOSIZE_JS = DOMAIN_NAME + "/mobile/scripts/jquery.imgAutoSize.js";
	/** 移动端默认图片地址 **/
	public static String DEFAULT_IMAGE = DOMAIN_NAME + "/mobile/img/grey-default.png";
	/******************↑         THE END         ↑******************************************/
	/** 订单状态 :已提交 **/
	public static final String ORDER_STATUS_COMMITED = "1";
	/** 订单状态 :已付款 **/
	public static final String ORDER_STATUS_PAID_SUCCESS = "2";
	/** 订单状态 :已取消 **/
	public static final String ORDER_STATUS_CANCEL = "3";
	/** 回调第三方的日期格式 **/
	public static final String DATE_FORMAT_EXPRESSION = "yyyy-MM-dd HH:mm:ss";
	/** 加锁状态 :未加锁 **/
	public static final String DATA_UNLOCK = "0";
	/** 加锁状态 :已加锁 **/
	public static final String DATA_LOCKED = "1";
	/** base64图片前缀 **/
	public static final String BASE64_PREFIX = "data:image/jpeg;base64,";
	/** 上传文件、图片保存在服务器的绝对路径 **/
	public static String UPLOAD_FILE_PATH = "";
	/**  web路径分隔线 **/
	public static String WEB_SEPARATOR = "/";
	/**  招生阶段-关闭 **/
	public static String ENROL_STAGE_CLOSE = "0";
	/**  招生阶段-只对旧生 **/
	public static String ENROL_STAGE_FOR_OLD = "1";
	/**  招生阶段-全部 **/
	public static String ENROL_STAGE_OPEN = "2";
	/**  过滤筛选-综合 **/
	public static String FILTER_GENERAL = "1";
	/**  过滤筛选-销量 **/
	public static String FILTER_VOLUMN = "2";
	/**  过滤筛选-价格-升序 **/
	public static String FILTER_PRICE_UP = "3.1";
	/**  过滤筛选-价格-降序 **/
	public static String FILTER_PRICE_DOWN = "3.0";
	/**  过滤筛选-已满 **/
	public static String FILTER_SOLDOUT = "4";
	/**  字段排序-升序 **/
	public static String SORT_ASC = "1";
	/**  字段排序-降序 **/
	public static String SORT_DESC = "0";
	/**  商品状态-上架 **/
	public static String COMMODITY_STATUS_UP = "1";
	/**  商品状态-下架 **/
	public static String COMMODITY_STATUS_DOWN = "0";
	/**  学期状态-其他学期  **/
	public static final String TERM_STATUS_OTHERS = "2";
	/**  学期状态-上学期  **/
	public static final String TERM_STATUS_LAST = "1";
	/**  学期状态-新建学期  **/
	public static final String TERM_STATUS_NEW = "0";
	/**  推荐班级审核状态-待审核  **/
	public static final String REVIEW_STATUS_WAIT = "0";
	/**  推荐班级审核状态-审核成功  **/
	public static final String REVIEW_STATUS_SUCCESS = "1";
	/**  推荐班级审核状态-审核未通过  **/
	public static final String REVIEW_STATUS_FAILED = "2";
	/**  黑名单-是  **/
	public static final String BLACK_LIST_YES = "1";
	/**  黑名单-否  **/
	public static final String BLACK_LIST_NO = "0";
	/**  低保户-是  **/
	public static final String LOW_INCOME_YES = "1";
	/**  低保户-否  **/
	public static final String LOW_INCOME_NO = "0";

}
