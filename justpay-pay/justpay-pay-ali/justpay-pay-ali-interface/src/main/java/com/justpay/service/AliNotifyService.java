package com.justpay.service;

import java.util.Map;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/7 18:35
 */
public interface AliNotifyService {

    /**
     * 验证回调参数。验证成功时会有successReturn和failReturn，用于接下来业务逻辑判断之后是返回哪个给阿里；失败时只有failReturn，可直接返回给阿里
     * @param paramsMap
     * @return
     */
    public String doAliNotifyRes(Map<String, String> paramsMap);
}
