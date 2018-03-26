package com.justpay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.justpay.constant.PayConstant;
import com.justpay.pojo.AlipayConfigDTO;
import com.justpay.pojo.RequestDTO;
import com.justpay.service.AlipayService;
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
 * @date 2017/9/11 14:20
 */
@Service
@Component
@PropertySource(value = "classpath:config/alipay.properties", encoding = "UTF-8")
public class AlipayServiceImpl implements AlipayService {

    private static final Logger logger = LoggerFactory.getLogger(AlipayServiceImpl.class);

    @Autowired
    AlipayClient alipayClient;
    @Autowired
    AlipayConfigDTO alipayConfigDTO;

    @Override
    public String UnifiedOrder(RequestDTO reqDTO) {
        //支付类型
        String tradeType = reqDTO.getTradeType();
        //支付订单号
        String outTradeNo = reqDTO.getOutTradeNo();


        try {
            Map<String, Object> map = ResponseUtils.makeSuccessMap();
            //根据不同的交易类型下单
            switch (tradeType) {
                //手机网站支付
                case PayConstant.AlipayConstant.TRADE_TYPE_WAP: {
                    map = wapOrder(map, reqDTO);
                    break;
                }
                //电脑网站支付
                case PayConstant.AlipayConstant.TRADE_TYPE_PC: {
                    map = pcOrder(map, reqDTO);
                    break;
                }
                //APP支付
                case PayConstant.AlipayConstant.TRADE_TYPE_APP: {
                    map = appOrder(map, reqDTO);
                    break;
                }
            }
            //下单成功
            logger.info("订单号为：" + outTradeNo + " >>> 支付宝下单成功");

            //支付订单ID
            map.put("outTradeNo", outTradeNo);
            return JsonUtils.objectToJson(map);
        } catch (AlipayApiException e) {
            logger.error("订单号为：" + outTradeNo + " 支付宝下单失败", e);
            logger.info("err_code:", e.getErrCode());
            logger.info("err_msg:", e.getErrMsg());
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap("调用支付宝支付失败", e.getErrCode() + ":" + e.getErrMsg()));

        } catch (Exception e) {
            logger.error("下单失败", e);
            logger.info("订单号为：" + outTradeNo + " 支付宝下单失败", e);
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap("调用支付宝支付失败(非平台错误)", e.getMessage()));
        }
    }

    /**
     * APP支付
     *
     * @param map
     * @param reqDTO
     * @return
     * @throws AlipayApiException
     */
    private Map<String, Object> appOrder(Map<String, Object> map, RequestDTO reqDTO) throws AlipayApiException {
        //请求对象
        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
        // 封装请求支付信息
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //订单号
        model.setOutTradeNo(reqDTO.getOutTradeNo());
        //标题
        model.setSubject(reqDTO.getTitle());
        //金额以分为单位,所以/100
        model.setTotalAmount((Double.parseDouble(reqDTO.getAmount()) / 100d) + "");
        //非必要参数
        model.setBody(reqDTO.getSubtitle());
        //支付方式固定写法
        model.setProductCode("QUICK_MSECURITY_PAY");
        //设置支付过期时间
        if (alipayConfigDTO.getTimeout() != null) {
            model.setTimeoutExpress(alipayConfigDTO.getTimeout() + "m");
        }
        alipayRequest.setBizModel(model);
        // 设置异步通知地址(异步回调)
        alipayRequest.setNotifyUrl(reqDTO.getNotifyUrl());
        // 设置同步地址(支付成功后跳转，非必要)
        alipayRequest.setReturnUrl(reqDTO.getReturnUrl());
        //调用SDK生成表单
        String form = alipayClient.sdkExecute(alipayRequest).getBody();
        map.put("payParams", form);
        return map;
    }

    /**
     * 电脑网站支付
     *
     * @param map
     * @param reqDTO
     * @return
     * @throws AlipayApiException
     */
    private Map<String, Object> pcOrder(Map<String, Object> map, RequestDTO reqDTO) throws AlipayApiException {
        //请求对象
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 封装请求支付信息
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        //订单号
        model.setOutTradeNo(reqDTO.getOutTradeNo());
        //标题
        model.setSubject(reqDTO.getTitle());
        //金额以分为单位,所以/100
        model.setTotalAmount((Double.parseDouble(reqDTO.getAmount()) / 100d) + "");
        //非必要参数
        model.setBody(reqDTO.getSubtitle());
        //支付方式固定写法
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        //设置支付过期时间
        if (alipayConfigDTO.getTimeout() != null) {
            model.setTimeoutExpress(alipayConfigDTO.getTimeout() + "m");
        }
        alipayRequest.setBizModel(model);
        // 设置异步通知地址(异步回调)
        alipayRequest.setNotifyUrl(reqDTO.getNotifyUrl());
        // 设置同步地址(支付成功后跳转，非必要)
        alipayRequest.setReturnUrl(reqDTO.getReturnUrl());
        //调用SDK生成表单
        String form = alipayClient.pageExecute(alipayRequest).getBody();
        map.put("form", form);
        return map;
    }

    /**
     * 手机网站支付
     *
     * @param map
     * @param reqDTO
     * @return
     * @throws AlipayApiException
     */
    private Map<String, Object> wapOrder(Map<String, Object> map, RequestDTO reqDTO) throws AlipayApiException {
        //请求对象
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        //订单号
        model.setOutTradeNo(reqDTO.getOutTradeNo());
        //标题
        model.setSubject(reqDTO.getTitle());
        //金额以分为单位,所以/100
        model.setTotalAmount((Double.parseDouble(reqDTO.getAmount()) / 100d) + "");
        //非必要参数
        model.setBody(reqDTO.getSubtitle());
        //支付方式固定写法
        model.setProductCode("QUICK_WAP_PAY");
        //设置支付过期时间
        if (alipayConfigDTO.getTimeout() != null) {
            model.setTimeoutExpress(alipayConfigDTO.getTimeout() + "m");
        }
        //将支付信息放入请求对象
        alipayRequest.setBizModel(model);
        // 设置异步通知地址(异步回调)
        alipayRequest.setNotifyUrl(reqDTO.getNotifyUrl());
        // 设置同步地址(支付成功后跳转，非必要)
        alipayRequest.setReturnUrl(reqDTO.getReturnUrl());
        //调用SDK生成表单
        String form = alipayClient.pageExecute(alipayRequest).getBody();
        map.put("form", form);
        return map;
    }
}
