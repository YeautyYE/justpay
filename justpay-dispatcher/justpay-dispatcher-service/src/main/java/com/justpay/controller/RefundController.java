package com.justpay.controller;

import com.justpay.constant.PayConstant;
import com.justpay.pojo.RequestDTO;
import com.justpay.service.DispatcherService;
import com.justpay.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2018/3/26 11:28
 */
@RestController
@RequestMapping("refund")
public class RefundController {

    @Autowired
    DispatcherService dispatcherService;

    /**
     * 阿里支付的订单退款
     *
     * @param outTradeNo
     * @param response
     * @return
     */
    @GetMapping("/ali/{out_trade_no}/{refund_amount}")
    public Mono<String> refundAli(@PathVariable("out_trade_no") String outTradeNo, @PathVariable("refund_amount") String refundAmount, ServerHttpResponse response) {
        return Mono.just(outTradeNo)
                .zipWith(Mono.just(refundAmount), (tradeNo, amout) -> {
                    RequestDTO requestDTO = new RequestDTO();
                    //设为ali支付的退款
                    requestDTO.setOperation(PayConstant.OPERATION_REFUND_ALI);
                    requestDTO.setOutTradeNo(tradeNo);
                    requestDTO.setRefundAmount(amout);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
                    return dispatcherService.dispatch(JsonUtils.objectToJson(requestDTO));
                });
    }

    /**
     * 微信支付的订单退款
     *
     * @param outTradeNo
     * @param response
     * @return
     */
    @GetMapping("/wechat/{out_trade_no}/{refund_amount}/{out_refund_no}/{amount}")
    public Mono<String> refundWechat(@PathVariable("out_trade_no") String outTradeNo, @PathVariable("refund_amount") String refundAmount, @PathVariable("out_refund_no") String outRefundNo, @PathVariable String amount, ServerHttpResponse response) {
        return Mono.just(outTradeNo)
                .zipWith(Mono.just(refundAmount), (tradeNo, refundAmount1) -> {
                    RequestDTO requestDTO = new RequestDTO();
                    requestDTO.setOutTradeNo(tradeNo);
                    requestDTO.setRefundAmount(refundAmount1);
                    return requestDTO;
                })
                .zipWith(Mono.just(outRefundNo), (requestDTO, refundNo) -> {
                    requestDTO.setOutRefundNo(refundNo);
                    return requestDTO;
                })
                .zipWith(Mono.just(amount), (requestDTO, amount1) -> {
                    requestDTO.setAmount(amount1);
                    //设为ali支付的退款
                    requestDTO.setOperation(PayConstant.OPERATION_REFUND_WECHAT);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
                    return dispatcherService.dispatch(JsonUtils.objectToJson(requestDTO));
                });


    }

}
