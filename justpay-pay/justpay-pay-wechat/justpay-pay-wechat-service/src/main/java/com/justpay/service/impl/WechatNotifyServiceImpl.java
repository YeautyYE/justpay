package com.justpay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.binarywang.wxpay.bean.WxPayOrderNotifyResponse;
import com.github.binarywang.wxpay.bean.result.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.justpay.service.WechatConfigService;
import com.justpay.service.WechatNotifyService;
import com.justpay.util.JsonUtils;
import com.justpay.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/11 16:01
 */
@Service
@Component
public class WechatNotifyServiceImpl implements WechatNotifyService {

    private static final Logger logger = LoggerFactory.getLogger(WechatNotifyServiceImpl.class);
    @Autowired
    WechatConfigService wechatConfigService;

    @Override
    public String doWxNotifyRes(String xmlResult) {
        WxPayService wxPayService = wechatConfigService.getWxPayService(null, null);
        try {
            WxPayOrderNotifyResult orderNotifyResult = wxPayService.getOrderNotifyResult(xmlResult);
            logger.info(orderNotifyResult.getOutTradeNo() + " 订单回调验证成功");
            String successReturn = WxPayOrderNotifyResponse.success("处理成功");
            String failReturn = WxPayOrderNotifyResponse.fail("失败");
            Map<String, Object> map = ResponseUtils.makeSuccessMap();
            map.put("successReturn", successReturn);
            map.put("failReturn", failReturn);
            return JsonUtils.objectToJson(map);
        } catch (WxPayException e) {
            //出现业务错误
            logger.error("微信回调结果异常,异常原因", e);
            logger.info("请求数据result_code=FAIL");
            logger.info("err_code:", e.getErrCode());
            logger.info("err_code_des:", e.getErrCodeDes());
            String failReturn = WxPayOrderNotifyResponse.fail(e.getMessage());
            Map<String, Object> map = ResponseUtils.makeFailMap("微信回调结果异常,异常原因", e.getErrCode() + ":" + e.getErrCodeDes());
            map.put("failReturn", failReturn);
            return JsonUtils.objectToJson(map);
        } catch (Exception e) {
            logger.error("微信回调结果异常,异常原因", e);
            String failReturn = WxPayOrderNotifyResponse.fail(e.getMessage());
            Map<String, Object> map = ResponseUtils.makeFailMap("微信回调结果异常,异常原因", e.getMessage());
            map.put("failReturn", failReturn);
            return JsonUtils.objectToJson(map);
        }
    }
}
