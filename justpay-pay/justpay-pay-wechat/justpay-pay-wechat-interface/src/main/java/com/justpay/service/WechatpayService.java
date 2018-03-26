package com.justpay.service;

import com.justpay.pojo.RequestDTO;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:微信支付
 * @date 2017/9/4 19:51
 */
public interface WechatpayService {


    /**
     * 统一下单
     *
     * @param reqDTO
     * @return
     */
    String UnifiedOrder(RequestDTO reqDTO);
}