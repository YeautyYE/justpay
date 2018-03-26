package com.justpay.controller;

import com.justpay.constant.PayConstant;
import com.justpay.pojo.RequestDTO;
import com.justpay.service.DispatcherService;
import com.justpay.util.JsonUtils;
import com.justpay.util.QRCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2018/3/5 11:19
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    DispatcherService dispatcherService;

    /**
     * HTTP接入service层下单请求
     *
     * @param requestDTO
     * @return
     */
    @CrossOrigin
    @PostMapping("/order")
    public Mono<String> buildOrder(@RequestBody Mono<RequestDTO> requestDTO) {
        return requestDTO.map(request ->
                dispatcherService.dispatch(JsonUtils.objectToJson(request))
        );
    }


    /**
     * 展示wechat的native时，如何使用webflux生成二维码
     * @param amout
     * @param title
     * @param outTradeNo
     * @param response
     * @return
     */
    @CrossOrigin
    @GetMapping("/wechat_native/qrcode/{amout}/{title}/{out_trade_no}")
    public Mono<Void> wechatNative(@PathVariable String amout, @PathVariable String title, @PathVariable("out_trade_no") String outTradeNo, ServerHttpResponse response) {
        return response.writeWith(Mono.just(amout)
                .zipWith(Mono.just(title), (amout1, title1) -> {
                    RequestDTO requestDTO = new RequestDTO();
                    requestDTO.setAmount(amout1);
                    requestDTO.setTitle(title1);
                    return requestDTO;
                })
                .zipWith(Mono.just(outTradeNo), (requestDTO, tradeNo) -> {
                    requestDTO.setOperation(PayConstant.OPERATION_PAY_WECHAT_NATIVE);
                    //此controller用于展示，clientIp可以写死127.0.0.1
                    requestDTO.setClientIp("127.0.0.1");
                    //此controller用于展示，订单号随机生成
                    requestDTO.setOutTradeNo(outTradeNo);
                    //此controller用于展示，商品id随机生成
                    requestDTO.setProductId(UUID.randomUUID().toString().replaceAll("-", ""));
                    //此controller用于展示，回调地址必填，所以写死
                    requestDTO.setNotifyUrl("http://www.just.com/notify/wechat");
                    //调用dispatcherService进行下单
                    String result = dispatcherService.dispatch(JsonUtils.objectToJson(requestDTO));
                    //获取dataBuffer，这是写出流的关键
                    DataBuffer dataBuffer = response.bufferFactory().allocateBuffer();
                    Map<String, String> resultMap = JsonUtils.jsonToMap(result, String.class, String.class);
                    String returnCode = resultMap.get(PayConstant.RETURN_PARAM_RETCODE);
                    //如果下单成功，生成二维码并写出
                    if (!StringUtils.isEmpty(returnCode) && PayConstant.RETURN_VALUE_SUCCESS.equals(returnCode)) {
                        //获取codeUrl
                        String codeUrl = resultMap.get("codeUrl");
                        //通过dataBuffer获取他的输出流
                        OutputStream outputStream = dataBuffer.asOutputStream();
                        //生成二维码，写到dataBuffer里面
                        try {
                            QRCodeUtils.encode(codeUrl, null, outputStream, 300, 300, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //返回dataBuffer
                        return dataBuffer;
                    }
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
                    return dataBuffer.write(result.getBytes());
                }));

    }

    /**
     * 展示ali的pc时，如何使用webflux刷出页面
     *
     * @param amout
     * @param title
     * @return
     */
    @CrossOrigin
    @GetMapping("/ali_pc/page/{amout}/{title}/{out_trade_no}")
    public Mono<String> aliPc(@PathVariable String amout, @PathVariable String title, @PathVariable("out_trade_no") String outTradeNo) {
        return Mono.just(amout)
                .zipWith(Mono.just(title), (amout1, title1) -> {
                    RequestDTO requestDTO = new RequestDTO();
                    requestDTO.setAmount(amout1);
                    requestDTO.setTitle(title1);
                    return requestDTO;
                })
                .zipWith(Mono.just(outTradeNo), (requestDTO, tradeNo) -> {
                    requestDTO.setOperation(PayConstant.OPERATION_PAY_ALIPAY_PC);
                    //此controller用于展示，clientIp可以写死127.0.0.1
                    requestDTO.setClientIp("127.0.0.1");
                    //此controller用于展示，订单号随机生成
                    requestDTO.setOutTradeNo(tradeNo);
                    //此controller用于展示，回调地址必填，所以写死
                    requestDTO.setNotifyUrl("http://www.just.com/notify/ali");
                    //调用dispatcherService进行下单
                    String result = dispatcherService.dispatch(JsonUtils.objectToJson(requestDTO));
                    Map<String, String> resultMap = JsonUtils.jsonToMap(result, String.class, String.class);
                    String returnCode = resultMap.get(PayConstant.RETURN_PARAM_RETCODE);
                    //成功，则把整个form里面的内容原样写出
                    if (!StringUtils.isEmpty(returnCode) && PayConstant.RETURN_VALUE_SUCCESS.equals(returnCode)) {
                        String form = resultMap.get("form");
                        return form;
                    }
                    return result;
                });
    }

}
