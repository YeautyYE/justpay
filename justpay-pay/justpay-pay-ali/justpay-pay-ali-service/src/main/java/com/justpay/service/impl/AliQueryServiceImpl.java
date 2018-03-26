package com.justpay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.justpay.pojo.AlipayConfigDTO;
import com.justpay.service.AliQueryService;
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
 * @date 2017/9/11 17:05
 */
@Service
@Component
public class AliQueryServiceImpl implements AliQueryService {

    private static final Logger logger = LoggerFactory.getLogger(AliQueryServiceImpl.class);

    @Autowired
    AlipayConfigDTO alipayConfigDTO;
    @Autowired
    AlipayClient alipayClient;

    @Override
    public String tradeQuery(String outTradeNo) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);
        Map<String, Object> map = null;
        try {
            AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(request);
            if ("10000".equals(alipayTradeQueryResponse.getCode())) {
                map = ResponseUtils.makeSuccessMap();
            } else {
                map = ResponseUtils.makeFailMap("查询失败", alipayTradeQueryResponse.getSubMsg());
            }
            map.put("queryData", alipayTradeQueryResponse);
        } catch (AlipayApiException e) {
            logger.error(outTradeNo + " 支付宝订单查询失败 ", e);
            logger.info(e.getErrCode() + ":" + e.getErrMsg());
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap(outTradeNo + " 支付宝订单查询失败 ", e.getErrCode() + ":" + e.getErrMsg()));
        } catch (Exception e) {
            logger.error(outTradeNo + " 支付宝订单查询失败 ", e);
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap(outTradeNo + " 支付宝订单查询失败 ", e.getMessage()));
        }
        logger.info(outTradeNo + " 支付宝订单查询成功");
        return JsonUtils.objectToJson(map);
    }
}
