package com.justpay.configuration;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.justpay.pojo.AlipayConfigDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 将配置文件交给spring管理
 */
@Configuration
@PropertySource(value = "classpath:config/alipay.properties", encoding = "UTF-8")
public class AlipayConfiguration {

    // 商户appid
    @Value("${ali.pay.app-id}")
    public String APPID;
    // 私钥 pkcs8格式的
    @Value("${ali.pay.rsa-private-key}")
    public String RSA_PRIVATE_KEY;
    // 请求网关地址
    @Value("${ali.pay.url}")
    public String URL;
    // 编码
    @Value("${ali.pay.charset}")
    public String CHARSET;
    // 返回格式
    @Value("${ali.pay.format}")
    public String FORMAT;
    // 支付宝公钥
    @Value("${ali.pay.alipay-public-key}")
    public String ALIPAY_PUBLIC_KEY;
    // 日志记录目录
    @Value("${ali.pay.log-path}")
    public String log_path;
    // RSA2
    @Value("${ali.pay.signtype}")
    public String SIGNTYPE;
    // 订单过期时间，单位：分钟
    @Value("${ali.pay.timeout:null}")
    private Integer timeout;


    @Bean
    public AlipayConfigDTO alipayConfigDTO() {
        AlipayConfigDTO payConfig = new AlipayConfigDTO();
        payConfig.setAPPID(APPID);
        payConfig.setRSA_PRIVATE_KEY(RSA_PRIVATE_KEY);
        payConfig.setURL(URL);
        payConfig.setCHARSET(CHARSET);
        payConfig.setFORMAT(FORMAT);
        payConfig.setALIPAY_PUBLIC_KEY(ALIPAY_PUBLIC_KEY);
        payConfig.setLog_path(log_path);
        payConfig.setSIGNTYPE(SIGNTYPE);
        payConfig.setTimeout(timeout);
        return payConfig;
    }

    @Bean
    public AlipayClient alipayClient(@Qualifier("alipayConfigDTO") AlipayConfigDTO alipayConfigDTO) {
        AlipayClient client = new DefaultAlipayClient(alipayConfigDTO.getURL(), alipayConfigDTO.getAPPID(), alipayConfigDTO.getRSA_PRIVATE_KEY(), alipayConfigDTO.getFORMAT(), alipayConfigDTO.getCHARSET(), alipayConfigDTO.getALIPAY_PUBLIC_KEY(), alipayConfigDTO.getSIGNTYPE());
        return client;

    }


}