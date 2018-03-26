package com.justpay.service;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/8 13:51
 */
public interface AliQueryService {

    /**
     * 查询交易信息
     * @param outTradeNo    订单号
     * @return
     */
    String tradeQuery(String outTradeNo);
}
