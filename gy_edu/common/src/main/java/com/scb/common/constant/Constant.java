package com.scb.common.constant;

/**
 * <全局常量>
 *
 * @author
 * @create
 */
public interface Constant {

    // ==================== Redis 存储-键前缀 ====================
    // 系统配置信息
    String SETTING_PREFIX = "SETTINGS:";

    // ==================== 管理后台用户信息键 ====================
    // 默认用户编号（充当系统）
    Long DEFAULT_USER_ID = 0L;
    // 默认用户名称（充当系统）
    String DEFAULT_USERNAME = "system";
    // 用户编号
    String USER_ID = "userId";
    // 用户名称
    String USERNAME = "username";
    // 用户角色
    String USER_ROLE = "user_role";
    // 用户权限列表
    String PERMISSIONS = "permissions";

    // ==================== session 后台web用户 ===================
    public static final String SESSION_KEY = "session_key";          //session key
    public final static String SESSION_USER = "session_user";         // session中存放登录用户
    public final static String SESSION_ROLE = "session_role";         // session中存放登录用户角色
    public final static String SESSION_AUTHORITY = "session_authority";    // session中存放权限
    public final static long SESSION_USER_TIME = 30 * 60L;                          // 指定用户登录过期时间 半个小时

    // ==================== 调用微信API返回值 ===================
    public final static String WX_RESP_OPENID = "openid";
    public final static String WX_RESP_SESSIONKEY = "session_key";
    public final static String WX_ACCESS_TOKEN = "access_token";        //微信getAccessToken接口返回字段
    public final static String WX_EXPIRES_IN = "expires_in";            //微信getAccessToken接口返回字段
    public final static String WX_ERROR_CODE = "errcode";            //微信错误码字段
    public final static String WX_ERRO_MSG = "errmsg";                //微信错误信息字段
    // ==================== 小程序会员redis存储 ===================
    public final static long SMS_VALIDATE_CODE_TIMEOUT = 1 * 60;                 //短信验证码超时时间
    public final static String SMS_BUSINESS = "SMS_VALIDATE_CODE:";   //短信验证码redis统一业务前缀
    public final static String WX_ACCESS_TOKEN_BUSINESS = "WX_ACCESS_TOKEN";      //微信小程序redis统一ACCESS_TOKEN键值
    // ==================== 小程序常量 ===================
    public final static String WX_REGISTER = "001";                //注册
    public final static String WX_FORGET_PWD = "002";                //忘记密码


    //==================== 操作结果 提示============================
    public final static String OP_FAILURE = "000000";                                     //操作失败
    public final static String OP_SUCCESS = "000001";                                     //操作成功

    //====================   提示信息    ============================
    public final static String MSG_LOGIN_ACCOUNT = "100000";                                     //用户或密码错误
    public final static String MSG_LOGIN_SESSION_EXPIRE = "100001";                                     //登录信息过期
    public final static String MSG_LOGIN_ACCCESS_LIMIT = "100002";                                     //您的权限不够
    public final static String MSG_ACCOUNT_DISABLE = "100003";                                     //该账号未激活
    public final static String MSG_MOBILE_NOT_REGIST = "100004";                                    //该手机号码未注册
    public final static String MSG_SMS_CODE_MORE = "100005";                                     //发送短信验证码过于频繁
    public final static String MSG_DUPLICATED = "100006";                                     //已存在相同记录
    public final static String MSG_DUPLICATED_MOBILE = "100007";                                     //该手机号码已被注册
    public final static String MSG_DUPLICATED_ORDER = "100008";                                     //已存在相同经营类型的商户排序
    public final static String MSG_FOREIGN_KEY_LIMIT = "100009";                                     //已关联其他业务数据，无法删除

    //====================   错误信息   ==============================
    public final static String ERR_REQ_PARAM = "400001";                                     //请求参数异常
    public final static String ERR_UPLOAD_FILE = "400002";                                     //上传文件错误
    public final static String ERR_MOBILE = "400003";                                     //手机号码错误
    public final static String ERR_SMS_CODE = "400004";                                     //短信验证码验证错误


    // =================== 业务字典 ===================
    public final static String DICT_BUSINESS_TYPE = "001";                // 经营类型
    public final static String DICT_COURSE_TYPE = "002";                // 课程类型

    public final static String ACTIVE_1 = "1";            //已激活，有效
    public final static String ACTIVE_0 = "0";            //未激活，无效

    // =================== 操作行为 ===================
  /*public final static String	OP_LOGIN					    = "01";				// 登录
 	public final static String	OP_LOGOUT				        = "02";				// 登出 
	public final static String	OP_SEARCH					    = "03";				// 查询
 	public final static String	OP_SAVE					        = "04";				// 保存
 	public final static String	OP_ADD					        = "05";				// 新增
 	public final static String	OP_CREATE					    = "06";				// 创建 
 	public final static String	OP_MODIFY				        = "07";				// 修改
 	public final static String	OP_REMOVE			         	= "08";				// 删除
 	public final static String	OP_SUBMIT				        = "09";				// 提交
 	public final static String	OP_TJ				            = "10";				// 统计
 	public final static String	OP_IMPORT			            = "11";				// 导入
 	public final static String	OP_EXPORT			            = "12";				// 导出 
 	public final static String	OP_PRINT				        = "13";				// 打印
 	public final static String	OP_UPLOAD				        = "14";				// 上传
 	 
 	// =================== 操作模块 ===================
 	public final static String	MODULE_LOGIN					= "1000";		        // 系统管理
 	public final static String	MODULE_USERADMIN				= "1001";		        // 系统管理-用户管理
 	public final static String	MODULE_ROLEADMIN				= "1002";		        // 系统管理-角色管理
 	public final static String	MODULE_USERROLE					= "1003";		        // 系统管理-角色分配
 	public final static String	MODULE_RESADMIN					= "1004";		        // 系统管理-权限资源
	public final static String	MODULE_ROLERES				    = "1005";		        // 系统管理-权限分配
  */


}
