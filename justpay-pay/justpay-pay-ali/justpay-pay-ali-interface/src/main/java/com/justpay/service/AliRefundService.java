package com.justpay.service;

import com.justpay.pojo.RequestDTO;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/12 15:28
 */
public interface AliRefundService {

    /**
     * 支付宝退款
     *
     * @param reqDTO
     * @return
     */
    String refund(RequestDTO reqDTO);
}
