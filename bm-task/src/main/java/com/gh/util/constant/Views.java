package com.gh.util.constant;

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
	
	/** 攻略管理  **/
	public static final String OPERATION_STRATEGY = "/operation/strategy";
	
	/** 系统用户管理  **/
	public static final String SYS_USER = "/sys/user";
	
	/** 系统菜单管理  **/
	public static final String SYS_MENU = "/sys/menu";
	
	/** 系统角色管理  **/
	public static final String SYS_ROLE = "/sys/role";
	
	/** 系统常量管理  **/
	public static final String SYS_CODE = "/sys/code";
	
	/** 计划任务  **/
	public static final String SYS_CONFIG_QUARTZ = "/sys/configquartz";

}
