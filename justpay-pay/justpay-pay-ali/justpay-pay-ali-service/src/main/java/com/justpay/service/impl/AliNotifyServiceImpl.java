package com.justpay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.justpay.constant.PayConstant;
import com.justpay.pojo.AlipayConfigDTO;
import com.justpay.service.AliNotifyService;
import com.justpay.util.JsonUtils;
import com.justpay.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/7 18:36
 */
@Service
@Component
@PropertySource(value = "classpath:config/alipay.properties", encoding = "UTF-8")
public class AliNotifyServiceImpl implements AliNotifyService {

    private static final Logger logger = LoggerFactory.getLogger(AliNotifyServiceImpl.class);

    @Autowired
    AlipayClient alipayClient;
    @Autowired
    AlipayConfigDTO alipayConfigDTO;

    @Override
    public String doAliNotifyRes(Map<String, String> paramsMap) {
        //验证结果
        boolean checkV1 = false;
        try {
            checkV1 = AlipaySignature.rsaCheckV1(paramsMap, alipayConfigDTO.getALIPAY_PUBLIC_KEY(), alipayConfigDTO.getCHARSET(), alipayConfigDTO.getSIGNTYPE());
        } catch (AlipayApiException e) {
            logger.warn(paramsMap.get("out_trade_no") + " 支付宝订单回调验证失败", e);
            Map<String, Object> map = ResponseUtils.makeFailMap(paramsMap.get("out_trade_no") + " 订单回调验证失败", e.getErrCode() + ":" + e.getErrMsg());
            map.put("failReturn", PayConstant.RETURN_ALIPAY_VALUE_FAIL);
            return JsonUtils.objectToJson(map);
        } catch (Exception e) {
            logger.warn(paramsMap.get("out_trade_no") + " 支付宝订单回调验证失败", e);
            Map<String, Object> map = ResponseUtils.makeFailMap(paramsMap.get("out_trade_no") + " 订单回调验证失败", e.getMessage());
            map.put("failReturn", PayConstant.RETURN_ALIPAY_VALUE_FAIL);
            return JsonUtils.objectToJson(map);
        }
        if (checkV1) {
            logger.info(paramsMap.get("out_trade_no") + " 支付宝订单回调验证成功");
            Map<String, Object> map = ResponseUtils.makeSuccessMap();
            map.put("successReturn", PayConstant.RETURN_ALIPAY_VALUE_SUCCESS);
            map.put("failReturn", PayConstant.RETURN_ALIPAY_VALUE_FAIL);
            return JsonUtils.objectToJson(map);
        } else {
            logger.info(paramsMap.get("out_trade_no") + " 支付宝订单回调验证失败");
            Map<String, Object> map = ResponseUtils.makeFailMap(paramsMap.get("out_trade_no") + " 订单回调验证失败", null);
            map.put("failReturn", PayConstant.RETURN_ALIPAY_VALUE_FAIL);
            return JsonUtils.objectToJson(map);
        }
    }


}
