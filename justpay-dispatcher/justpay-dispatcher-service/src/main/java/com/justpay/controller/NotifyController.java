package com.justpay.controller;

import com.justpay.constant.PayConstant;
import com.justpay.pojo.RequestDTO;
import com.justpay.service.DispatcherService;
import com.justpay.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2018/3/5 17:53
 */
@RestController
@RequestMapping("/notify")
public class NotifyController {

    @Autowired
    DispatcherService dispatcherService;

    /**
     * 展示如何组装支付宝回调参数，并调用dispatcher的回调验证
     *
     * @param serverWebExchange
     * @return
     */
    @PostMapping("/ali")
    public Mono<String> aliNotify(ServerWebExchange serverWebExchange) {
        return serverWebExchange.getFormData()
                .map(stringStringMultiValueMap -> {
                    //支付宝的回调是form，所以要从serverWebExchange中获取
                    Map<String, String> paramsMap = stringStringMultiValueMap.toSingleValueMap();
                    //将获取到的内容转json
                    String notifyJson = JsonUtils.objectToJson(paramsMap);
                    //组装回调的统一json
                    RequestDTO reqDTO = new RequestDTO();
                    reqDTO.setNotifyData(notifyJson);
                    reqDTO.setOperation(PayConstant.OPERATION_NOTIFY_ALI);
                    //调用dispatcherService进行验证
                    String resultJson = dispatcherService.dispatch(JsonUtils.objectToJson(reqDTO));
                    Map<String, String> resultMap = JsonUtils.jsonToMap(resultJson, String.class, String.class);
                    //如果验证成功，返回成功
                    String successReturn = resultMap.get("successReturn");
                    if (!StringUtils.isEmpty(successReturn)) {
                        return successReturn;
                    } else {
                        return resultMap.get("failReturn");
                    }
                });
    }


    /**
     * 展示如何组装微信回调参数，并调用dispatcher的回调验证
     *
     * @param xmlResult
     * @return
     */
    @PostMapping("/wechat")
    public Mono<String> wechatNotify(@RequestBody Mono<String> xmlResult) {
        return xmlResult
                .map(xml -> {
                    //组装回调的统一json
                    RequestDTO reqDTO = new RequestDTO();
                    reqDTO.setNotifyData(xml);
                    reqDTO.setOperation(PayConstant.OPERATION_NOTIFY_WECHAT);
                    //调用dispatcherService进行验证
                    String resultJson = dispatcherService.dispatch(JsonUtils.objectToJson(reqDTO));
                    Map<String, String> resultMap = JsonUtils.jsonToMap(resultJson, String.class, String.class);
                    //如果验证成功，返回成功
                    String successReturn = resultMap.get("successReturn");
                    if (!StringUtils.isEmpty(successReturn)) {
                        return successReturn;
                    } else {
                        return resultMap.get("failReturn");
                    }
                });
    }

}
