package com.justpay.service.impl;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.justpay.pojo.WechatpayConfigDTO;
import com.justpay.service.WechatConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/11 11:16
 */
@Service
public class WechatConfigServiceImpl implements WechatConfigService {

    private static final Logger logger = LoggerFactory.getLogger(WechatConfigServiceImpl.class);

    @Autowired
    WechatpayConfigDTO wechatpayConfigDTO;
    //第三方微信请求服务类，用于实现单例
    WxPayService wxPayService;
    //第三方微信配置文件，用于实现单例
    WxPayConfig wxPayConfig;


    @Override
    public WxPayService getWxPayService(String notifyUrl, String tradeType) {
        if (wxPayService == null) {
            wxPayService = new WxPayServiceImpl();
        }
        if (wxPayConfig == null) {
            wxPayConfig = new WxPayConfig();
            //将我们自己的配置放到第三方配置类中
            wxPayConfig.setAppId(wechatpayConfigDTO.getAppId());
            wxPayConfig.setMchId(wechatpayConfigDTO.getMchId());
            wxPayConfig.setMchKey(wechatpayConfigDTO.getMchKey());
            wxPayConfig.setKeyPath(wechatpayConfigDTO.getKeyPath());
        }

        //将第三方配置类及其它参数set到请求服务类中
        wxPayConfig.setNotifyUrl(notifyUrl);
        wxPayConfig.setTradeType(tradeType);
        wxPayService.setConfig(wxPayConfig);
        return wxPayService;

    }

}
