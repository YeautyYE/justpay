package com.justpay.service;

import com.justpay.pojo.RequestDTO;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/12 15:28
 */
public interface WechatRefundService {

    /**
     * 微信退款
     *
     * @param reqDTO
     * @return
     */
    String refund(RequestDTO reqDTO);
}
