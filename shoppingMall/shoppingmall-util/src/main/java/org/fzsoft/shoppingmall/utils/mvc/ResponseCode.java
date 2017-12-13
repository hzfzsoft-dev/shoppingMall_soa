package org.fzsoft.shoppingmall.utils.mvc;

/**
 * Created by yhj on 17/2/23.
 */
public class ResponseCode {
    public static final int SUCCESS = 200;  //  | 成功 |
    public static final String SUCCESS_MSG = "success";

    /**
     * 认证相关
     */
    public static final int AUTH_FAIL = 210;  //  | 用户认证失败 |
    public static final int AUTH_NOT_EXIST = 211;  //  | 用户不存在 |
    public static final int AUTH_KEY_ERROR = 212;  //  | 用户秘钥认证失败 |
    public static final int MSG_CODE_TIMEOUT = 213; //  | 消息验证码失效|

    /**
     * 权限相关
     */
    public static final int PERMISSION_DENY = 251;  //  | 没有访问权限 |
    public static final int PERMISSION_REQ = 252;  //  | 请联系管理员分配 |


    /**
     * 请求参数相关
     */
    public static final int PARAM_ERROR = 300;  //  | 参数错误 |
    public static final int PARAM_METHOD_JSON = 301;  //  | 请求参数格式需要为application/json |
    public static final int PARAM_METHOD_HTTP = 303;  //  | 请求必须是HTTP请求 |
    public static final int PARAM_METHOD_ERROR = 302;  //  | method 错误 |
    public static final int PARAM_METHOD_GET = 304;  //  | 请求必须是GET请求 |
    public static final int PARAM_METHOD_POST = 305;  //  | 请求必须是POST请求 |

    public static final int PARAM_TYPE_NUM = 320;  //  | 参数必须为数字 |
    public static final int PARAM_NUM_DOUBLE = 321;  //  | 参数为数字且必须是小数 |
    public static final int PARAM_NUM_INT = 322;  //  | 参数为字符串且必须是整数 |
    public static final int PARAM_NUM_FORMAT = 323;  //  | 参数为字符串且必须符合特定格式 |

    public static final int PARAM_TYPE_STRING = 340;  //  | 参数必须为字符串 |
    public static final int PARAM_STR_NOT_EMPTY = 341;  //  | 字符串不能为空 |
    public static final int PARAM_STR_TOO_LONG = 342;  //  | 参数为字符串且长度太长 |
    public static final int PARAM_STR_CH = 345;  //  | 参数为字符串且需要为中文 |
    public static final int PARAM_STR_EN = 346;  //  | 参数为字符串且需要为英文 |
    public static final int PARAM_STR_NUMBER = 347;  //  | 参数为字符串且需要为数字 |
    public static final int PARAM_STR_UNSPECIAL = 348;  //  | 参数为字符串且需要不能包含特殊字符 |
    public static final int PARAM_STR_EMAIL = 351;  //  | 参数为字符串且需要为邮箱 |
    public static final int PARAM_STR_IDCARD = 352;  //  | 参数为字符串且需要为身份证 |

    public static final int PARAM_TYPE_DATE = 360;  //  | 参数必须为时间类型 |
    public static final int PARAM_DATE_TIME = 362;  //  | 参数必须为时间类型(HH:mm:ss) |
    public static final int PARAM_DATE_DATE = 364;  //  | 参数必须为日期类型(yyyy-MM-dd) |
    public static final int PARAM_DATE_DATETIME = 366;  //  | 参数必须为长时间类型(yyyy-MM-dd HH:mm:ss) |
    public static final int SUBMIT_LIMIT=367;//请勿重复提交

    /**
     * 资源相关
     */

    public static final int RESOURCE_NOT_FOUND = 404;  //  | 请求没有被找到 |

    /**
     * 服务器相关
     */

    public static final int SERVER_ERROR = 500;  //  | 服务器内部异常 |
    public static final String SERVER_ERROR_MSG = "服务器内部异常，请联系技术人员！";// 将error改成了内容信息
    public static final int SERVER_ERROR_CPU = 501;  //  | 服务器CPU异常 |
    public static final int SERVER_ERROR_MEM = 502;  //  | 服务器MEM异常 |
    public static final int SERVER_ERROR_DISK = 503;  //  | 服务器DISK异常 |
    public static final int SERVER_DB_ERROR = 510;  //  | 数据库请求异常 |
    public static final int SERVER_DB_UNCONN = 511;  //  | 数据库连接不可用 |
    public static final int SERVER_DB_TIMEOUT = 512;  //  | 数据库请求超时 |
    public static final int SERVER_MD_ERROR = 520;  //  | 中间件异常 |
    public static final int SERVER_MD_ZOO = 521;  //  | 中间件异常(ZOOKEPER) |
    public static final int SERVER_MD_MQ = 522;  //  | 中间件异常(MQ) |
    public static final int SERVER_MD_LOCK = 523;  //  | 中间件异常(LOCK) |

    public static final int SERVER_NOT_SUPPORT = 530;  //  | 不支持该服务 |


    /**
     * 内部服务器异常.
     */
    public static final int INNER_ERROR = 600; //内部调用错误.

    public static final int INNER_RESULT_ERROR = 610; // 返回数据异常.
    public static final int INNER_RESULT_JSON_ERROR = 611; // 返回JSON解析异常.
    public static final int INNER_RESULT_CODE_ERROR = 612; // 返回Code 不存在.

    public static final int INNER_RESULT_DATA_ERROR = 630;    //返回data异常.
    public static final int INNER_JSON_ERROR = 631;    //返回data不存在

    /**
     * 用户相关
     */
    public static final int USER_NOT_EXSIT = 2000;// 用户不存在
    public static final int USER_BACK_LIST = 2001;// 用户存在于黑名单
    public static final int USER_UNVILIDATE = 2002;// 用户失效
    public static final int USER_STATUS_ERROR = 2003;// 用户状态异常
    public static final int USER_PWD_ERROR = 2004;// 密码错误
    // 用户基础信息
    public static final int USER_INFO_PHONE_FAIL = 2010;// 号码不存在
    public static final int USER_INFO_NAME_FAIL = 2011;// 用户名字不存在

    /**
     * 用户认证信息 2100-2199.
     */
    public static final int USER_CERT_ERROR = 2100;// 用户认证异常
    public static final int USER_CERT_NOT = 2101;// 用户没有认证
    public static final int USER_CERT_CARD_ERROR = 2102;// 用户身份证错误
    public static final int USER_CERT_PHONE_ERROR = 2103;// 用户认证号码错误
    public static final int USER_CERT_WEIXIN_BINDED = 2104;// 用户微信号码已经被绑定

    public static final int USER_CERT_WEIXIN = 2140;// 微信公众号异常
    public static final int USER_CERT_WEIXIN_OPEN_NOT = 2141;// 微信公众号异常

    /**
     * 用户金额相关.2200-2299.
     */
    public static final int USER_MONEY_ERROR = 2200;// 资金账户错误.
    public static final int USER_MONEY_NOT_ENOUGH = 2201; // 余额不足
    public static final int USER_MONEY_SAFE_NOT_SET = 2202;  //未设置安全密码
    public static final int USER_MONEY_SAFE_ERROR = 2203;  //安全密码错误 
    public static final int USER_MONEY_EXCHANGE_IS_NULL = 2204;  //兑换项目不存在
    public static final int USER_MONEY_EXCHANGE_IS_UPDATE = 2205;  //兑换项目已修改
    public static final int USER_MONEY_EXCESS_MAX_MONEY = 2207;  //金额超过最高限制


    public static final int USER_REDBAG_ERROR = 2210;  //用户红包账户异常.
    public static final int USER_REDBAG_NOT_ENOUGH = 2211;  //用户红包账户不足.

    public static final int USER_COUPONS_ERROR = 2220;  //用户优惠券异常
    public static final int USER_COUPONS_NOT_EXSITS = 2221;  //用户优惠券 不存在
    public static final int USER_COUPONS_USED = 2221;  //用户优惠券已经被使用.


    /**
     * 3000- 3999 为互助相关的业务代码
     */
    public static final int POLICY_NOT_EXISTS = 3101; //互助凭证不存在;
    public static final int POLICY_STATUS_ERROR = 3102; // 订单不是待支付状态;
    public static final int POLICY_PAY_NOT_MATCH = 3201; //支付金额不匹配;
    public static final int POLICY_SELF_EXISTS = 3202;//已有其他用户绑定了该互助凭证，如有疑问，请联系客服;


    public static final int POLICY_CARD_EXISTS = 3105;//该身份证的保单已经存在

    public static final int PRODUCT_NOT_EXISTS = 3111;//已有本人关系的保单存在;

    /**
     * 消息
     */
    public static final int MSG_NOT_EXSIT = 4000;

    /**
     * 鱼塘
     */
    public static final int USER_FISH_FINISH = 5000;//该鱼已经养成
    public static final int FEED_DAY_FINISH = 5001;//该鱼今日喂食已达到上线
    public static final int FEED_FOOD_LESS = 5002;//鱼粮小于喂食鱼粮


    /**
     * 互助申请
     */
    public static final int USER_POLICY_INVALID = 6000;//申请保单失效
    public static final int APPLY_POLICY_EXSIT = 6001;//申请保单已经存在
}
