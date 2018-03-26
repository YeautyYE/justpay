package com.justpay.service;

import com.justpay.pojo.RequestDTO;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/7 15:11
 */
public interface AlipayService {

    /**
     * 统一下单
     *
     * @param reqDTO
     * @return
     */
    String UnifiedOrder(RequestDTO reqDTO);
}