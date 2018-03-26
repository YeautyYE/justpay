package com.justpay.constant;

import java.io.File;

/**
 * 支付常量类
 */
public class PayConstant {


    public final static String OPERATION_PAY_AUTO = "PAY_AUTO";                         // 自动判断属于哪种支付
    public final static String OPERATION_PAY_WECHAT_JSAPI = "PAY_WECHAT_JSAPI";         // 微信公众号支付
    public final static String OPERATION_PAY_WECHAT_NATIVE = "PAY_WECHAT_NATIVE";       // 微信原生扫码支付
    public final static String OPERATION_PAY_WECHAT_APP = "PAY_WECHAT_APP";             // 微信APP支付
    public final static String OPERATION_PAY_WECHAT_MWEB = "PAY_WECHAT_MWEB";           // 微信H5支付
    public final static String OPERATION_PAY_IAP = "PAY_IAP";                           // 苹果应用内支付
    public final static String OPERATION_PAY_ALIPAY_APP = "PAY_ALIPAY_APP";             // 支付宝移动支付
    public final static String OPERATION_PAY_ALIPAY_PC = "PAY_ALIPAY_PC";               // 支付宝PC支付
    public final static String OPERATION_PAY_ALIPAY_WAP = "PAY_ALIPAY_WAP";             // 支付宝WAP支付
    public final static String OPERATION_NOTIFY_AUTO = "NOTIFY_AUTO";                   // 自动判断属于哪种回调
    public final static String OPERATION_NOTIFY_WECHAT = "NOTIFY_WECHAT";               // 微信回调
    public final static String OPERATION_NOTIFY_ALI = "NOTIFY_ALI";                     // 支付宝回调
    public final static String OPERATION_QUERY_WECHAT = "QUERY_WECHAT";                 // 微信查询
    public final static String OPERATION_QUERY_ALI = "QUERY_ALI";                       // 支付宝查询
    public final static String OPERATION_REFUND_WECHAT = "REFUND_WECHAT";               // 微信退款
    public final static String OPERATION_REFUND_ALI = "REFUND_ALI";                     // 支付宝退款


    public final static String PREFIX_PAY_ALIPAY = "PAY_ALIPAY";                        // 支付宝支付前缀
    public final static String PREFIX_PAY_WECHAT = "PAY_WECHAT";                        // 微信支付前缀
    public final static String PREFIX_PAY = "PAY";                                     // 支付前缀
    public final static String PREFIX_NOTIFY = "NOTIFY";                                  // 回调前缀
    public final static String PREFIX_QUERY = "QUERY";                                   // 查询前缀
    public final static String PREFIX_REFUND = "REFUND";                                  // 退款前缀


    public final static String TRANS_CHANNEL_WECHAT_APP = "TRANS_WECHAT_APP";            // 微信APP转账
    public final static String TRANS_CHANNEL_WECHAT_JSAPI = "TRANS_WECHAT_JSAPI";        // 微信公众号转账


    public final static String RESP_UTF8 = "UTF-8";            // 通知业务系统使用的编码

    public static final String RETURN_PARAM_RETCODE = "return_code";
    public static final String RETURN_PARAM_RETMSG = "return_msg";
    public static final String RESULT_PARAM_ERRCODEDES = "err_code_des";
    public static final String RESULT_PARAM_SIGN = "sign";

    public static final String RETURN_VALUE_SUCCESS = "SUCCESS";
    public static final String RETURN_VALUE_FAIL = "FAIL";

    public static final String RETURN_ALIPAY_VALUE_SUCCESS = "success";
    public static final String RETURN_ALIPAY_VALUE_FAIL = "fail";

    public static class JdConstant {
        public final static String CONFIG_PATH = "jd" + File.separator + "jd";    // 京东支付配置文件路径
    }

    public static class WechatConstant {
        public final static String TRADE_TYPE_APP = "APP";                                    // APP支付
        public final static String TRADE_TYPE_JSPAI = "JSAPI";                                // 公众号支付或小程序支付
        public final static String TRADE_TYPE_NATIVE = "NATIVE";                              // 原生扫码支付
        public final static String TRADE_TYPE_MWEB = "MWEB";                                  // H5支付
        public final static String TRADE_STATE_SUCCESS = "SUCCESS";                           // 支付成功
        public final static String TRADE_STATE_REFUND = "REFUND";                             // 转入退款
        public final static String TRADE_STATE_NOTPAY = "NOTPAY";                             // 未支付
        public final static String TRADE_STATE_CLOSED = "CLOSED";                             // 已关闭
        public final static String TRADE_STATE_REVOKED = "SUCCESS";                           // 已撤销（刷卡支付）
        public final static String TRADE_STATE_USERPAYING = "USERPAYING";                     // 用户支付中
        public final static String TRADE_STATE_PAYERROR = "PAYERROR";                         // 支付失败(其他原因，如银行返回失败)

    }

    public static class IapConstant {
        public final static String CONFIG_PATH = "iap" + File.separator + "iap";        // 苹果应用内支付
    }

    public static class AlipayConstant {
        public final static String TRADE_TYPE_WAP = "WAP";                                  // 手机网站支付
        public final static String TRADE_TYPE_PC = "PC";                                    // 电脑支付
        public final static String TRADE_TYPE_APP = "APP";                                  // APP支付
        public final static String CONFIG_PATH = "alipay" + File.separator + "alipay";      // 支付宝移动支付
        public final static String TRADE_STATUS_WAIT = "WAIT_BUYER_PAY";                    // 交易创建,等待买家付款
        public final static String TRADE_STATUS_CLOSED = "TRADE_CLOSED";                    // 交易关闭
        public final static String TRADE_STATUS_SUCCESS = "TRADE_SUCCESS";                  // 交易成功
        public final static String TRADE_STATUS_FINISHED = "TRADE_FINISHED";                // 交易成功且结束
    }

    public static final String NOTIFY_BUSI_PAY = "NOTIFY_VV_PAY_RES";
    public static final String NOTIFY_BUSI_TRANS = "NOTIFY_VV_TRANS_RES";

}