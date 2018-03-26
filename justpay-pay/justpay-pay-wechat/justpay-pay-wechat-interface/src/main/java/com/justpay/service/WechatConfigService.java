package com.justpay.service;

import com.github.binarywang.wxpay.service.WxPayService;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/11 11:14
 */
public interface WechatConfigService {


    /**
     * 得到WxPayService(第三方)，用于对wechat发起请求
     *
     * @param notifyUrl 回调地址
     * @param tradeType 支付类型
     * @return
     */
    WxPayService getWxPayService(String notifyUrl, String tradeType);

}
