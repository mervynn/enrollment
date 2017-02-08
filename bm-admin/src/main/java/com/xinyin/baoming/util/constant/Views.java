package com.xinyin.baoming.util.constant;

/**
 * 视图路径
 * 
 * @author HeMingwei
 *
 */
public class Views {
	// 注：
	/** 菜单管理*/
	public static final String MENU_INIT_PAGE = "/menu/initMenu";
	/** 系统用户列表*/
	public static final String ADMIN_USER_LIST = "/user/userList";
	/** 用户修改密码*/
	public static final String ADMIN_USER_MODIFY = "/user/userModify";
	/** 用户编辑*/
	public static final String TO_MODIFY_USER = "/user/userToModify";
	/** 用户新增*/
	public static final String TO_ADD_USER = "/user/userToAdd";
	/** 用户角色权限管理*/
	public static final String AUTH_INIT_PAGE = "/auth/initAuth";
	/** 角色列表*/
	public static final String AUTH_ROLE_LIST = "/auth/roleList";
	/** 角色新增*/
	public static final String AUTH_TO_ADD_ROLE = "/auth/toAddRole";

	//版本页面
	/** 版本列表*/
	public static final String VERSION_LIST="/version/versionList";
	public static final String VERSION_ADD="/version/versionToAdd";
	
	//手机订单页面跳转
	public static final String ORDER_LIST="/order/orderList";
	//商品
	public static final String COMMODITY_LIST="/commodity/commodityList";
	public static final String COMMODITY_ADD="/commodity/commodityToAdd";
	//游戏类型
	public static final String GAME_TYPE_LIST="/gametype/gameTypeList";
	public static final String GAME_TYPE_TOADD="/gametype/gameTypeToAdd";
	//渠道管理
	public static final String CHANNEL_LIST="/channel/channelList";
	public static final String CHANNEL_ADD="/channel/channelToAdd";
	
	//游戏热用户
	public static final String GAME_HOT_USER_LIST="/gamehotuser/gameHotUserList";
	
	//游戏管理
	public static final String GAME_LIST="/game/gameList";
	public static final String GAME_ADD="/game/gameToAdd";
	public static final String GAME_KEY_ADD="/game/gameKeyToAdd";
	
	//游戏版本管理
	public static final String GAME_VERSION_LIST="/game/gameVersionList";
	public static final String GAME_VERSIONE_ADD="/game/gameVersionToAdd";
	
	/** 登录界面 **/
	public static final String LOGIN_PAGE="/login";
	/** 主页面 **/
	public static final String HOME="/home";
	
	/******************************↓以下均为新增功能页面********************************/
	/** CP管理  **/
	public static final String GH_CP = "/cp/main";
	/** CP用户管理  **/
	public static final String GH_CP_USER = "/cp/user";
	/** CP游戏管理  **/
	public static final String GH_CP_GAME = "/cp/game";
	/** CP管理  **/
	public static final String GH_CHANNEL = "/channel/main";
	/** 渠道用户管理  **/
	public static final String GH_CHANNEL_USER = "/channel/user";
	/** 渠道游戏管理  **/
	public static final String GH_CHANNEL_GAME = "/channel/game";
	/** 支付通道管理  **/
	public static final String GH_PAY_WAY= "/payway/main";
	/** 支付结果报表查询  **/
	public static final String GH_PAY_WAY_REPORT = "/payway/report";
	/** 游戏热用户管理  **/
	public static final String GH_USER = "/gh/user/main";
	/** 游戏热用户意见管理  **/
	public static final String GH_USER_SUGGESTION = "/gh/user/suggestion";
	/** 订单信息管理  **/
	public static final String GH_ORDER = "/gh/order";
	/** 评论查看  **/
	public static final String GH_COMMENT = "/gh/comment";
	/** 公司信息管理  **/
	public static final String OPERATION_COMPANY = "/operation/company";
	/** 新闻管理  **/
	public static final String OPERATION_NEWS = "/operation/news";
	/** 新闻资源网站管理  **/
	public static final String OPERATION_NEWS_WEBSITES = "/operation/newswebsites";
	/** SDK版本信息管理  **/
	public static final String OPERATION_SDK_VERSION = "/operation/sdkversion";
	/** APK版本信息管理  **/
	public static final String OPERATION_VERSION = "/operation/version";
	/** 内容管理  **/
	public static final String OPERATION_CONTENT = "/operation/content";
	/** H5管理  **/
	public static final String OPERATION_STRATEGY = "/operation/strategy";
	/** 系统用户管理  **/
	public static final String SYS_USER = "/sys/user";
	/** 系统菜单管理  **/
	public static final String SYS_MENU = "/sys/menu";
	/** 系统角色管理  **/
	public static final String SYS_ROLE = "/sys/role";
	/** 系统常量管理  **/
	public static final String BASEDATA_CODE = "/basedata/code";
	/** 计划任务  **/
	public static final String SYS_CONFIGQUARTZ = "/sys/configquartz";
	/** 班级归档  **/
	public static final String ZIP_ALLCLASS = "/zip/allclass";
	/** 班级学生关联归档  **/
	public static final String ZIP_ALLCLASSSTUDENT = "/zip/allclassstudent";
	/** 订单归档  **/
	public static final String ZIP_ALLORDER = "/zip/allorder";
	/** 订单明细归档  **/
	public static final String ZIP_ALLORDERDETAIL = "/zip/allorderdetail";
	/** 班级学生关联  **/
	public static final String USER_CLASSSTUDENT = "/user/classstudent";
	/** 订单管理  **/
	public static final String USER_ORDER = "/user/order";
	/** 订单明细管理  **/
	public static final String USER_ORDERDETAIL = "/user/orderdetail";
	/** 推荐课程管理  **/
	public static final String USER_RECOMMEND = "/user/recommend";
	/** 购物车管理  **/
	public static final String USER_SHOPPINGCART = "/user/shoppingcart";
	/** 上期班级管理  **/
	public static final String HISTORY_LASTCLASS = "/history/lastclass";
	/** 上期班级学生关联管理  **/
	public static final String HISTORY_LASTCLASSSTUDENT = "/history/lastclassstudent";
	
	/** 会员账户管理  **/
	public static final String OPERATION_ACCOUNT = "/operation/account";
	/** 班级管理  **/
	public static final String OPERATION_CLASS = "/operation/class";
	/** 教室管理  **/
	public static final String OPERATION_CLASSROOM = "/operation/classroom";
	/** 课程类型管理  **/
	public static final String OPERATION_CLASSTYPE = "/operation/classtype";
	/** 学生资料管理  **/
	public static final String OPERATION_STUDENT = "/operation/student";
	/** 授课老师管理  **/
	public static final String OPERATION_TEACHER = "/operation/teacher";
	/** 学期管理  **/
	public static final String OPERATION_TERM = "/operation/term";
	/** 学校管理  **/
	public static final String OPERATION_SCHOOL = "/operation/school";
	/** 现场报名  **/
	public static final String OPERATION_ONSIDEREGISTRATION = "/operation/onsideregistration";
	
	/******************************↓移动端页面********************************/
	/** 登录界面  **/
	public static final String WAP_INDEX_PAGE = "/wap/index";
	/** 注册界面  **/
	public static final String WAP_REGISTER_PAGE = "/wap/register";
	/** 课程类型  **/
	public static final String WAP_CLASSTYPE = "/wap/classtype";
	/** 班级列表界面  **/
	public static final String WAP_CLASS_LIST = "/wap/class/list";
	/** 班级信息明细  **/
	public static final String WAP_CLASS_DETAIL = "/wap/class/detail";
	/** 个人中心界面  **/
	public static final String WAP_USER_CENTER = "/wap/user/center";
	/** 购课车界面  **/
	public static final String WAP_USER_SHOPPINGCART = "/wap/user/shoppingcart";
	/** 修改密码第一步  **/
	public static final String WAP_USER_CHANGEPASSWORD_ONE = "/wap/user/changepassword/one";
	/** 修改密码第二步  **/
	public static final String WAP_USER_CHANGEPASSWORD_TWO = "/wap/user/changepassword/two";
	/** 找回密码第一步  **/
	public static final String WAP_USER_GETPASSWORD_ONE = "/wap/user/getpassword/one";
	/** 找回密码第二步  **/
	public static final String WAP_USER_GETPASSWORD_TWO = "/wap/user/getpassword/two";
	/** 找回密码第三步  **/
	public static final String WAP_USER_GETPASSWORD_THREE = "/wap/user/getpassword/three";
	/** 填写订单界面  **/
	public static final String WAP_USER_ORDER_EDIT = "/wap/user/order/edit";
	/** 报名结果界面  **/
	public static final String WAP_USER_ORDER_RESULT = "/wap/user/order/result";
	/** 课程订单界面  **/
	public static final String WAP_USER_ORDER_LIST = "/wap/user/order/list";
	/** 课程订单明细界面  **/
	public static final String WAP_USER_ORDER_DETAIL = "/wap/user/order/detail";
	/** 学生资料列表  **/
	public static final String WAP_USER_STUDENT_LIST = "/wap/user/student/list";
	/** 添加学生资料  **/
	public static final String WAP_USER_STUDENT_ADD = "/wap/user/student/add";
	/** 修改学生资料  **/
	public static final String WAP_USER_STUDENT_MODIFY = "/wap/user/student/modify";
	
}
