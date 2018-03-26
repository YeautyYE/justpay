package com.justpay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.justpay.service.WechatConfigService;
import com.justpay.service.WechatQueryService;
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
 * @date 2017/9/11 17:21
 */
@Service
@Component
public class WechatQueryServiceImpl implements WechatQueryService {

    private static final Logger logger = LoggerFactory.getLogger(WechatQueryServiceImpl.class);

    @Autowired
    WechatConfigService wechatConfigService;

    @Override
    public String tradeQuery(String outTradeNo) {
        WxPayService wxPayService = wechatConfigService.getWxPayService(null, null);
        Map<String, Object> map = ResponseUtils.makeSuccessMap();
        try {
            WxPayOrderQueryResult wxPayOrderQueryResult = wxPayService.queryOrder(null, outTradeNo);
            map.put("queryData", wxPayOrderQueryResult);
        } catch (WxPayException e) {
            logger.error(outTradeNo + " 微信订单查询失败 ", e);
            logger.info(e.getErrCode() + ":" + e.getErrCodeDes());
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap(outTradeNo + " 微信订单查询失败 ", e.getErrCode() + ":" + e.getErrCodeDes()));
        } catch (Exception e) {
            logger.error(outTradeNo + " 微信订单查询失败 ", e);
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap(outTradeNo + " 微信订单查询失败 ", e.getMessage()));
        }
        logger.info(outTradeNo + " 微信订单查询成功");
        return JsonUtils.objectToJson(map);
    }
}
