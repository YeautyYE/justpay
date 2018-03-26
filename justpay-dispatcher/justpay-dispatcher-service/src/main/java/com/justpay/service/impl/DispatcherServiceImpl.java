package com.justpay.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.justpay.constant.PayConstant;
import com.justpay.pojo.RequestDTO;
import com.justpay.service.*;
import com.justpay.util.JsonUtils;
import com.justpay.util.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/11 19:24
 */
@Service
@Component
public class DispatcherServiceImpl implements DispatcherService {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServiceImpl.class);

    @Reference(timeout = 2000)
    AlipayService alipayService;
    @Reference(timeout = 2000)
    WechatpayService wechatpayService;
    @Reference(timeout = 2000)
    AliNotifyService aliNotifyService;
    @Reference(timeout = 2000)
    WechatNotifyService wechatNotifyService;
    @Reference(timeout = 2000)
    AliQueryService aliQueryService;
    @Reference(timeout = 2000)
    WechatQueryService wechatQueryService;
    @Reference(timeout = 2000)
    AliRefundService aliRefundService;
    @Reference(timeout = 2000)
    WechatRefundService wechatRefundService;
    @Autowired
    PayChannelService payChannelService;

    @Override
    public String dispatch(String request) {
        RequestDTO reqDTO = JsonUtils.jsonToPojo(request, RequestDTO.class);
        //校验返回对象
        Object validateObj = null;

        //根据操作类型进行参数校验
        validateObj = validateParams(reqDTO);

        //校验不通过时，将错误信息返回
        if (validateObj instanceof String) {
            logger.info("参数校验不通过: " + validateObj);
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap(validateObj.toString(), null));
        }
        reqDTO = (RequestDTO) validateObj;

        //选择操作类型
        if (reqDTO.getOperation().startsWith(PayConstant.PREFIX_PAY_ALIPAY)) {
            //支付宝支付
            return alipayService.UnifiedOrder(reqDTO);
        } else if (reqDTO.getOperation().startsWith(PayConstant.PREFIX_PAY_WECHAT)) {
            //微信支付
            return wechatpayService.UnifiedOrder(reqDTO);
        } else if (reqDTO.getOperation().equals(PayConstant.OPERATION_NOTIFY_ALI)) {
            //支付宝回调
            return aliNotifyService.doAliNotifyRes(JsonUtils.jsonToMap(reqDTO.getNotifyData(), String.class, String.class));
        } else if (reqDTO.getOperation().equals(PayConstant.OPERATION_NOTIFY_WECHAT)) {
            //微信回调
            return wechatNotifyService.doWxNotifyRes(reqDTO.getNotifyData());
        } else if (reqDTO.getOperation().equals(PayConstant.OPERATION_QUERY_ALI)) {
            //支付宝查询
            return aliQueryService.tradeQuery(reqDTO.getOutTradeNo());
        } else if (reqDTO.getOperation().equals(PayConstant.OPERATION_QUERY_WECHAT)) {
            //微信查询
            return wechatQueryService.tradeQuery(reqDTO.getOutTradeNo());
        } else if (reqDTO.getOperation().equals(PayConstant.OPERATION_REFUND_ALI)) {
            //支付宝退款
            return aliRefundService.refund(reqDTO);
        } else if (reqDTO.getOperation().equals(PayConstant.OPERATION_REFUND_WECHAT)) {
            //微信退款
            return wechatRefundService.refund(reqDTO);
        }
        return null;
    }

    /**
     * 验证参数
     *
     * @param reqDTO
     * @return
     */
    private Object validateParams(RequestDTO reqDTO) {
        // 验证请求参数,参数有问题返回错误提示
        String errorMessage;
        String operation = reqDTO.getOperation();
        if (operation == null) {
            errorMessage = "request params[operation] error.";
            return errorMessage;
        }
        if (operation.startsWith(PayConstant.PREFIX_PAY)) {
            //支付类型
            return validatePay(reqDTO);
        } else if (operation.startsWith(PayConstant.PREFIX_NOTIFY)) {
            //回调类型
            return validateNotify(reqDTO);
        } else if (operation.startsWith(PayConstant.PREFIX_QUERY)) {
            //查询类型
            return validateQuery(reqDTO);
        } else if (operation.startsWith(PayConstant.PREFIX_REFUND)) {
            //退款类型
            return validateRefund(reqDTO);
        } else {
            logger.info("不支持操作类型: " + reqDTO.getOperation());
            return "不支持操作类型: " + reqDTO.getOperation();
        }
    }

    private Object validateRefund(RequestDTO reqDTO) {
        // 验证请求参数,参数有问题返回错误提示
        String errorMessage;

        String operation = reqDTO.getOperation();
        if (StringUtils.isBlank(operation)) {
            errorMessage = "request params[operation] error.";
            return errorMessage;
        }
        //订单号
        if (StringUtils.isBlank(reqDTO.getOutTradeNo())) {
            errorMessage = "request params[outTradeNo] error.";
            return errorMessage;
        }
        //退款金额
        if (!StringUtils.isNumeric(reqDTO.getRefundAmount())) {
            errorMessage = "request params[refundAmount] error.";
            return errorMessage;
        }

        switch (operation) {
            case PayConstant.OPERATION_REFUND_WECHAT:
                //退款单号
                if (StringUtils.isBlank(reqDTO.getOutRefundNo())) {
                    errorMessage = "request params[outRefundNo] error.";
                    return errorMessage;
                }
                //金额
                if (!StringUtils.isNumeric(reqDTO.getAmount())) {
                    errorMessage = "request params[amount] error.";
                    return errorMessage;
                }
                return reqDTO;
            case PayConstant.OPERATION_REFUND_ALI:
                return reqDTO;
            default:
                errorMessage = "不支持操作类型: " + reqDTO.getOperation();
                return errorMessage;
        }
    }

    private Object validateQuery(RequestDTO reqDTO) {
        // 验证请求参数,参数有问题返回错误提示
        String errorMessage;

        String operation = reqDTO.getOperation();
        if (operation == null) {
            errorMessage = "request params[operation] error.";
            return errorMessage;
        }

        //订单号
        if (StringUtils.isBlank(reqDTO.getOutTradeNo())) {
            errorMessage = "request params[outTradeNo] error.";
            return errorMessage;
        }

        switch (operation) {
            case PayConstant.OPERATION_QUERY_WECHAT:
                return reqDTO;
            case PayConstant.OPERATION_QUERY_ALI:
                return reqDTO;
            default:
                errorMessage = "不支持操作类型: " + reqDTO.getOperation();
                return errorMessage;
        }
    }

    private Object validateNotify(RequestDTO reqDTO) {
        // 验证请求参数,参数有问题返回错误提示
        String errorMessage;

        String operation = reqDTO.getOperation();
        if (operation == null) {
            errorMessage = "request params[operation] error.";
            return errorMessage;
        }

        //回调信息
        if (StringUtils.isBlank(reqDTO.getNotifyData())) {
            errorMessage = "request params[notifyData] error.";
            return errorMessage;
        }
        //当为自动判断试
        if (PayConstant.OPERATION_NOTIFY_AUTO.equals(operation)) {
            JsonNode jsonNode = JsonUtils.jsonToJsonNode(reqDTO.getNotifyData());
            if (jsonNode != null) {
                reqDTO.setOperation(PayConstant.OPERATION_NOTIFY_ALI);
            } else {
                reqDTO.setOperation(PayConstant.OPERATION_NOTIFY_WECHAT);
            }
        }

        switch (reqDTO.getOperation()) {
            case PayConstant.OPERATION_NOTIFY_WECHAT:
                return reqDTO;
            case PayConstant.OPERATION_NOTIFY_ALI:
                return reqDTO;
            default:
                errorMessage = "不支持操作类型: " + reqDTO.getOperation();
                return errorMessage;
        }
    }

    /**
     * 验证请求参数,参数通过返回requestDTO对象,否则返回错误文本信息
     *
     * @param reqDTO
     * @return
     */
    private Object validatePay(RequestDTO reqDTO) {
        // 验证请求参数,参数有问题返回错误提示
        String errorMessage;

        String operation = reqDTO.getOperation();
        if (StringUtils.isBlank(operation)) {
            errorMessage = "request params[operation] error.";
            return errorMessage;
        }
        // 验证请求参数有效性（必选项）
        //订单号
        if (StringUtils.isBlank(reqDTO.getOutTradeNo())) {
            errorMessage = "request params[outTradeNo] error.";
            return errorMessage;
        }
        //支付金额
        if (!StringUtils.isNumeric(reqDTO.getAmount())) {
            errorMessage = "request params[amount] error.";
            return errorMessage;
        }
        //终端IP （客户端IP）
        if (StringUtils.isBlank(reqDTO.getClientIp())) {
            errorMessage = "request params[clientIp] error.";
            return errorMessage;
        }
        //商品标题
        if (StringUtils.isBlank(reqDTO.getTitle())) {
            errorMessage = "request params[title] error.";
            return errorMessage;
        }
        //通知地址（回调通知）
        if (StringUtils.isBlank(reqDTO.getNotifyUrl())) {
            errorMessage = "request params[notifyUrl] error.";
            return errorMessage;
        }

        if (PayConstant.OPERATION_PAY_AUTO.equals(operation)) {
            //User-Agent
            if (StringUtils.isBlank(reqDTO.getUserAgent())) {
                errorMessage = "request params[userAgent] error.";
                return errorMessage;
            }
            //根据请求头选择支付方式
            reqDTO.setOperation(payChannelService.selectChannelByUserAgent(reqDTO.getUserAgent()));
        }
        switch (reqDTO.getOperation()) {
            case PayConstant.OPERATION_PAY_WECHAT_APP:
                reqDTO.setTradeType(PayConstant.WechatConstant.TRADE_TYPE_APP);
                return reqDTO;
            case PayConstant.OPERATION_PAY_WECHAT_JSAPI:
                //公众号内用户的openid
                if (StringUtils.isBlank(reqDTO.getOpenid())) {
                    errorMessage = "request params[openid] error.";
                    return errorMessage;
                }
                reqDTO.setTradeType(PayConstant.WechatConstant.TRADE_TYPE_JSPAI);
                return reqDTO;
            case PayConstant.OPERATION_PAY_WECHAT_NATIVE:
                //商品Id
                if (StringUtils.isBlank(reqDTO.getProductId())) {
                    errorMessage = "request params[productId] error.";
                    return errorMessage;
                }
                reqDTO.setTradeType(PayConstant.WechatConstant.TRADE_TYPE_NATIVE);
                return reqDTO;
            case PayConstant.OPERATION_PAY_WECHAT_MWEB:
                //场景信息
                if (StringUtils.isBlank(reqDTO.getSceneInfo())) {
                    errorMessage = "request params[sceneInfo] error.";
                    return errorMessage;
                }
                reqDTO.setTradeType(PayConstant.WechatConstant.TRADE_TYPE_MWEB);
                return reqDTO;
            case PayConstant.OPERATION_PAY_ALIPAY_APP:
                reqDTO.setTradeType(PayConstant.AlipayConstant.TRADE_TYPE_APP);
                return reqDTO;
            case PayConstant.OPERATION_PAY_ALIPAY_PC:
                reqDTO.setTradeType(PayConstant.AlipayConstant.TRADE_TYPE_PC);
                return reqDTO;
            case PayConstant.OPERATION_PAY_ALIPAY_WAP:
                reqDTO.setTradeType(PayConstant.AlipayConstant.TRADE_TYPE_WAP);
                return reqDTO;
            default:
                errorMessage = "不支持操作类型: " + reqDTO.getOperation();
                return errorMessage;
        }


    }
}
