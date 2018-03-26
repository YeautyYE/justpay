package com.justpay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.justpay.pojo.RequestDTO;
import com.justpay.service.AliRefundService;
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
 * @date 2017/9/12 16:00
 */
@Service
@Component
public class AliRefundServiceImpl implements AliRefundService {

    private static final Logger logger = LoggerFactory.getLogger(AliRefundServiceImpl.class);

    @Autowired
    AlipayClient alipayClient;

    @Override
    public String refund(RequestDTO reqDTO) {
        //请求对象
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        //封装请求退款信息
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        //订单号
        model.setOutTradeNo(reqDTO.getOutTradeNo());
        //退款金额
        model.setRefundAmount((Double.parseDouble(reqDTO.getRefundAmount()) / 100d) + "");
        request.setBizModel(model);
        try {
            Map<String, Object> map = null;
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if ("10000".equals(response.getCode())) {
                logger.info("订单号为：" + reqDTO.getOutTradeNo() + " >>> 支付宝退款成功");
                map = ResponseUtils.makeSuccessMap();
            } else {
                logger.info("订单号为：" + reqDTO.getOutTradeNo() + " 支付宝退款失败");
                map = ResponseUtils.makeFailMap("退款失败", response.getSubMsg());
            }
            map.put("refundData", response);
            return JsonUtils.objectToJson(map);
        } catch (AlipayApiException e) {
            logger.error("退款失败", e);
            //出现业务错误
            logger.info("订单号为：" + reqDTO.getOutTradeNo() + " 支付宝退款失败");
            logger.info("err_code:", e.getErrCode());
            logger.info("err_msg:", e.getErrMsg());
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap("调用支付宝退款失败", e.getErrCode() + ":" + e.getErrMsg()));
        } catch (Exception e) {
            logger.error("退款失败", e);
            logger.info("订单号为：" + reqDTO.getOutTradeNo() + " 支付宝退款失败");
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap("调用支付宝退款失败(非平台错误)", e.getMessage()));
        }
    }
}
