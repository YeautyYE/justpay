package com.justpay.service;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/11 17:50
 */
public interface DispatcherService {

    /**
     * 分发（这里是整个聚合支付的入口）
     * @param request
     * @return
     */
    String dispatch(String request);
}
