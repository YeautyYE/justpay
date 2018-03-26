package com.justpay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.justpay.constant.PayConstant;
import com.justpay.pojo.RequestDTO;
import com.justpay.pojo.WechatpayConfigDTO;
import com.justpay.service.WechatConfigService;
import com.justpay.service.WechatpayService;
import com.justpay.util.JsonUtils;
import com.justpay.util.ResponseUtils;
import com.justpay.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/11 13:44
 */
@Service
@Component
public class WechatpayServiceImpl implements WechatpayService {

    private static final Logger logger = LoggerFactory.getLogger(WechatpayServiceImpl.class);

    @Autowired
    WechatConfigService wechatConfigService;
    @Autowired
    WechatpayConfigDTO wechatpayConfigDTO;

    @Override
    public String UnifiedOrder(RequestDTO reqDTO) {
        //获取支付订单
        String outTradeNo = reqDTO.getOutTradeNo();

        //通过配置对象和params中拿出的notifyUrl、tradeType获取第三方的WxPayService
        WxPayService wxPayService = wechatConfigService.getWxPayService(reqDTO.getNotifyUrl(), reqDTO.getTradeType());
        //构建第三方请求对象
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = buildUnifiedOrderRequest(reqDTO, wechatpayConfigDTO);
        //声明微信统一订单结果
        WxPayUnifiedOrderResult wxPayUnifiedOrderResult;

        try {
            //发送请求进行下单，并返回结果
            wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
            logger.info("订单号为：" + outTradeNo + " >>> 微信下单成功");
            //下单成功，失败时会抛异常，这个map是返回给请求对象的
            Map<String, Object> map = ResponseUtils.makeSuccessMap();
            //支付订单ID
            map.put("outTradeNo", outTradeNo);
            //微信那边 预支付交易会话标识
            map.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());
            //判断支付类型，确定返回参数
            switch (reqDTO.getTradeType()) {
                case PayConstant.WechatConstant.TRADE_TYPE_NATIVE: {
                    map.put("codeUrl", wxPayUnifiedOrderResult.getCodeURL());   // 二维码支付链接
                    break;
                }
                case PayConstant.WechatConstant.TRADE_TYPE_APP: {
                    map.put("payParams", wxPayService.getPayInfo(wxPayUnifiedOrderRequest));
                    break;
                }
                case PayConstant.WechatConstant.TRADE_TYPE_JSPAI: {
                    map.put("payParams", wxPayService.getPayInfo(wxPayUnifiedOrderRequest));
                    break;
                }
                case PayConstant.WechatConstant.TRADE_TYPE_MWEB: {
                    map.put("payUrl", wxPayUnifiedOrderResult.getMwebUrl());    // h5支付链接地址
                    break;
                }
            }
            return JsonUtils.objectToJson(map);
        } catch (WxPayException e) {
            logger.error("下单失败", e);
            //出现业务错误
            logger.info("订单号为：" + outTradeNo + " 微信下单失败");
            logger.info("err_code:", e.getErrCode());
            logger.info("err_code_des:", e.getErrCodeDes());
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap("调用微信支付失败", e.getErrCode() + ":" + e.getErrCodeDes()));
        } catch (Exception e) {
            logger.error("下单失败", e);
            logger.info("订单号为：" + outTradeNo + " 微信下单失败");
            return JsonUtils.objectToJson(ResponseUtils.makeFailMap("调用微信支付失败(非业务错误)", e.getMessage()));
        }
    }

    private WxPayUnifiedOrderRequest buildUnifiedOrderRequest(RequestDTO reqDTO, WechatpayConfigDTO wechatpayConfigDTO) {

        // 微信统一下单请求对象
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();

        //必填
        request.setBody(reqDTO.getTitle());                  //标题
        request.setTotalFee(Integer.parseInt(reqDTO.getAmount()));          // 订单总金额 ,单位分
        request.setOutTradeNo(reqDTO.getOutTradeNo());      //商户订单号 要求32位内
        request.setSpbillCreateIp(reqDTO.getClientIp());  //终端IP(APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP；可简单理解为支付者的IP)
        request.setTradeType(reqDTO.getTradeType());        //交易类型(取值如下：JSAPI，NATIVE，APP等)
        request.setNotifyURL(reqDTO.getNotifyUrl());        //通知地址(异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。)

        //选填
        try {
            //过期时间不为null时，设置过期时间
            if (wechatpayConfigDTO.getTimeout() != null) {
                request.setTimeExpire(TimeUtils.dateToString(TimeUtils.addDate(new Date(), wechatpayConfigDTO.getTimeout(), Calendar.MINUTE), "yyyyMMddHHmmss"));      //订单失效时间；格式为"yyyyMMddHHmmss"
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.setFeeType("CNY");              //标价币种  ； 这里直接设为人民币
        request.setProductId(reqDTO.getProductId());        //商品ID （trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。）
        request.setOpenid(reqDTO.getOpenid());              //用户标识（trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。）
        request.setSceneInfo(reqDTO.getSceneInfo());        //使用H5支付时的sceneInfo;场景信息，该字段用于上报场景信息，目前支持上报实际门店信息。该字段为JSON对象数据，对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }}
        request.setDeviceInfo(reqDTO.getDeviceInfo());      //设备号（自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"）
        request.setAttach(null);              //附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
        request.setTimeStart(null);        //订单生成时间
        request.setGoodsTag(null);          //订单优惠标记(订单优惠标记，使用代金券或立减优惠功能时需要的参数)
        request.setLimitPay(null);          //指定支付方式（上传此参数no_credit--可限制用户不能使用信用卡支付）
        request.setDetail(null);              //商品详情

        return request;
    }
}
