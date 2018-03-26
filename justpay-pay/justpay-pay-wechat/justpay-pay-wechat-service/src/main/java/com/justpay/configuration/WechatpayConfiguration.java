package com.justpay.configuration;

import com.justpay.pojo.WechatpayConfigDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 将配置文件交给spring管理
 */
@Configuration
@PropertySource(value = "classpath:config/wechatpay.properties", encoding = "UTF-8")
public class WechatpayConfiguration {
    // 设置微信公众号的appid
    @Value("${wechat.pay.app-id}")
    private String appId;
    // 微信支付商户号
    @Value("${wechat.pay.mch-id}")
    private String mchId;
    // 微信支付商户密钥
    @Value("${wechat.pay.mch-key}")
    private String mchKey;
    // apiclient_cert.p12的文件的绝对路径
    @Value("${wechat.pay.key-path}")
    private String keyPath;
    // 订单过期时间，单位：分钟
    @Value("${wechat.pay.timeout:null}")
    private Integer timeout;

    @Bean()
    public WechatpayConfigDTO wechatpayConfigDTO() {
        WechatpayConfigDTO wechatpayConfigDTO = new WechatpayConfigDTO();
        wechatpayConfigDTO.setAppId(appId);
        wechatpayConfigDTO.setMchId(mchId);
        wechatpayConfigDTO.setMchKey(mchKey);
        wechatpayConfigDTO.setKeyPath(keyPath);
        wechatpayConfigDTO.setTimeout(timeout);

        return wechatpayConfigDTO;
    }


}