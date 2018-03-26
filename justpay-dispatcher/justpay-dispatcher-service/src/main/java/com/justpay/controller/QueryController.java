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
 * @date 2018/3/26 11:17
 */
@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    DispatcherService dispatcherService;

    /**
     * 查询阿里支付的订单
     *
     * @param outTradeNo
     * @return
     */
    @GetMapping("/ali/{out_trade_no}")
    public Mono<String> queryAli(@PathVariable("out_trade_no") String outTradeNo, ServerHttpResponse response) {
        return Mono.just(outTradeNo)
                .map(tradeNo -> {
                    RequestDTO requestDTO = new RequestDTO();
                    //设为ali支付的查询
                    requestDTO.setOperation(PayConstant.OPERATION_QUERY_ALI);
                    requestDTO.setOutTradeNo(tradeNo);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
                    return dispatcherService.dispatch(JsonUtils.objectToJson(requestDTO));
                });

    }

    /**
     * 查询微信支付的订单
     *
     * @param outTradeNo
     * @return
     */
    @GetMapping("/wechat/{out_trade_no}")
    public Mono<String> queryWechat(@PathVariable("out_trade_no") String outTradeNo, ServerHttpResponse response) {
        return Mono.just(outTradeNo)
                .map(tradeNo -> {
                    RequestDTO requestDTO = new RequestDTO();
                    //设为wechat支付的查询
                    requestDTO.setOperation(PayConstant.OPERATION_QUERY_WECHAT);
                    requestDTO.setOutTradeNo(tradeNo);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
                    return dispatcherService.dispatch(JsonUtils.objectToJson(requestDTO));
                });
    }
}
