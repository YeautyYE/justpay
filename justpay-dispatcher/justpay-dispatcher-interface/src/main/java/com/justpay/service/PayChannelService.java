package com.justpay.service;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:支付渠道服务
 * @date 2017/9/12 18:16
 */
public interface PayChannelService {

    /**
     * 通过请求头中的UserAgent来选择支付方式
     * @param userAgent
     * @return
     */
    String selectChannelByUserAgent(String userAgent);
}
