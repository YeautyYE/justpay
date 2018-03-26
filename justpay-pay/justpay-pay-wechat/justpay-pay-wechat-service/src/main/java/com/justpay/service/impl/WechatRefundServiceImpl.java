package com.justpay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.justpay.pojo.RequestDTO;
import com.justpay.service.WechatConfigService;
import com.justpay.service.WechatRefundService;
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
 * @date 2017/9/12 15:35
 */
@Service
@Component
public class WechatRefundServiceImpl implements WechatRefundService {

    private static final Logger logger = LoggerFactory.getLogger(WechatRefundServiceImpl.class);

    @Autowired
    WechatConfigService wechatConfigService;

    @Override
    public String refund(RequestDTO reqDTO) {
        WxPayService wxPayService = wechatConfigService.getWxPayService(null, null);
        WxPayRefundRequest request = new WxPayRefundRequest();
        //订单号
        request.setOutTradeNo(reqDTO.getOutTradeNo());
        //退单号
        request.setOutRefundNo(reqDTO.getOutRefundNo());
        //总金额
        request.setTotalFee(Integer.parseInt(reqDTO.getAmount()));
        //退款金额
        request.setRefundFee(Integer.parseInt(reqDTO.getRefundAmount()));
        try {
            Map<String, Object> map = ResponseUtils.makeSuccessMap();
            WxPayRefundResult wxPayRefundResult = wxPayService.refund(request);
            map.put("refundData", wxPayRefundResult);
            logger.info("订单号为：" + reqDTO.getOutTradeNo() + " >>> 微信退款成功");
            return JsonUtils.objectToJson(map);
        } catch (WxPayException e) {
            logger.error("退款失败", e);
            //出现业务错误
            logger.info("订单号为：" + reqDTO.getOutTradeNo() + " 微信退款失败");
            logger.info("err_code:", e.getErrCode());
            logger.info("err_code_des:", e.getErrCodeDes());
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap("调用微信退款失败", e.getErrCode() + ":" + e.getErrCodeDes()));
        } catch (Exception e) {
            logger.error("退款失败", e);
            logger.info("订单号为：" + reqDTO.getOutTradeNo() + " 微信退款失败");
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap("调用微信退款失败(非平台错误)", e.getMessage()));
        }
    }
}
