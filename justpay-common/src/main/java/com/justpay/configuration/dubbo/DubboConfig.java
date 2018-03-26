package com.justpay.configuration.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 
 * @Description:TODO
 * @author Yeauty
 * @version 1.0
 * @date 2017年8月25日 下午2:02:52
 */
@Configuration
public class DubboConfig {

    @Bean
    @ConfigurationProperties(prefix="dubbo.application")
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        return applicationConfig;
    }

    @Bean
    @ConfigurationProperties(prefix="dubbo.registry")
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        return registryConfig;
    }

    @Bean
    @ConfigurationProperties(prefix="dubbo.annotation")
    public AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        return annotationBean;
    }
    
    @Bean
    @ConfigurationProperties(prefix="dubbo.protocol")
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();

        InetSocketAddress inetSocketAddress = new InetSocketAddress(0);
        Socket socket = new Socket();
        try {
            socket.bind(inetSocketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int localPort = socket.getLocalPort();
        protocolConfig.setPort(localPort);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return protocolConfig;
    }
}
