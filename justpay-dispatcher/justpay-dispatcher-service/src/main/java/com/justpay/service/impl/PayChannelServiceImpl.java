package com.justpay.service.impl;

import com.justpay.constant.PayConstant;
import com.justpay.service.PayChannelService;
import org.springframework.stereotype.Service;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/12 18:18
 */
@Service
public class PayChannelServiceImpl implements PayChannelService {
    @Override
    public String selectChannelByUserAgent(String userAgent) {
        if (userAgent.contains("MicroMessenger")){
            return PayConstant.OPERATION_PAY_WECHAT_NATIVE;
        }else if(userAgent.contains("AliApp")){
            return PayConstant.OPERATION_PAY_ALIPAY_WAP;
        }else if(userAgent.contains("Mobile")){
            return PayConstant.OPERATION_PAY_ALIPAY_WAP;
        }else {
            return PayConstant.OPERATION_PAY_ALIPAY_PC;
        }
    }
}
