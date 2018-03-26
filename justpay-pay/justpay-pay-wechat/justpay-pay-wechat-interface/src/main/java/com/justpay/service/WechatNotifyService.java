package com.justpay.service;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:微信回调
 * @date 2017/9/5 19:50
 */
public interface WechatNotifyService {


    /**
     * 验证回调参数。验证成功时会有successReturn和failReturn，用于接下来业务逻辑判断之后是返回哪个给微信；失败时只有failReturn，可直接返回给微信
     * @param xmlResult
     * @return
     */
    String doWxNotifyRes(String xmlResult) ;
}
