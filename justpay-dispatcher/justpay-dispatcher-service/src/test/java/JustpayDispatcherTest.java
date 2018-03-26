/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/13 17:45
 */

import com.justpay.JustpayDispatcherApplication;
import com.justpay.constant.PayConstant;
import com.justpay.pojo.RequestDTO;
import com.justpay.service.DispatcherService;
import com.justpay.util.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JustpayDispatcherApplication.class)
public class JustpayDispatcherTest {

    @Autowired
    DispatcherService dispatcherService;

    //=====================================支付==========================================

    /**
     * 支付宝WAP支付
     */
    @Test
    public void testAlipayWAP(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_PAY_ALIPAY_WAP);
        //金额
        reqDTO.setAmount("1");
        //订单号
        reqDTO.setOutTradeNo("justpay-ali-001");
        //标题
        reqDTO.setTitle("title");
        //回调url
        reqDTO.setNotifyUrl("http://notify.justpay.com");
        //客户端ip
        reqDTO.setClientIp("127.0.0.1");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    /**
     * 支付宝PC支付
     */
    @Test
    public void testAlipayPC(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_PAY_ALIPAY_PC);
        //金额
        reqDTO.setAmount("1");
        //订单号
        reqDTO.setOutTradeNo("justpay-ali-002");
        //标题
        reqDTO.setTitle("title");
        //回调url
        reqDTO.setNotifyUrl("http://notify.justpay.com");
        //客户端ip
        reqDTO.setClientIp("127.0.0.1");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    /**
     * 支付宝APP支付
     */
    @Test
    public void testAlipayAPP(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_PAY_ALIPAY_APP);
        //金额
        reqDTO.setAmount("1");
        //订单号
        reqDTO.setOutTradeNo("justpay-ali-003");
        //标题
        reqDTO.setTitle("title");
        //回调url
        reqDTO.setNotifyUrl("http://notify.justpay.com");
        //客户端ip
        reqDTO.setClientIp("127.0.0.1");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    /**
     * 微信NATIVE支付
     */
    @Test
    public void testWechatpayNATIVE(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_PAY_WECHAT_NATIVE);
        //金额
        reqDTO.setAmount("1");
        //订单号
        reqDTO.setOutTradeNo("justpay-wechat-001");
        //标题
        reqDTO.setTitle("title");
        //回调url
        reqDTO.setNotifyUrl("http://notify.justpay.com");
        //客户端ip
        reqDTO.setClientIp("127.0.0.1");
        //产品Id
        reqDTO.setProductId("justpay-product-001");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    /**
     * 微信JSAPI支付
     */
    @Test
    public void testWechatpayJSAPI(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_PAY_WECHAT_JSAPI);
        //金额
        reqDTO.setAmount("1");
        //订单号
        reqDTO.setOutTradeNo("justpay-wechat-002");
        //标题
        reqDTO.setTitle("title");
        //回调url
        reqDTO.setNotifyUrl("http://notify.justpay.com");
        //客户端ip
        reqDTO.setClientIp("127.0.0.1");
        //用户在公众号中的openid(在公众号后台获取)
        reqDTO.setOpenid("o5nr6jl2kDeODxkHrp-AdBVud3N8");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    /**
     * 微信APP支付
     */
    @Test
    public void testWechatpayAPP(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_PAY_WECHAT_APP);
        //金额
        reqDTO.setAmount("1");
        //订单号
        reqDTO.setOutTradeNo("justpay-wechat-003");
        //标题
        reqDTO.setTitle("title");
        //回调url
        reqDTO.setNotifyUrl("http://notify.justpay.com");
        //客户端ip
        reqDTO.setClientIp("127.0.0.1");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    /**
     * 微信MWEB支付
     */
    @Test
    public void testWechatpayMWEB(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_PAY_WECHAT_MWEB);
        //金额
        reqDTO.setAmount("1");
        //订单号
        reqDTO.setOutTradeNo("justpay-wechat-004");
        //标题
        reqDTO.setTitle("title");
        //回调url
        reqDTO.setNotifyUrl("http://notify.justpay.com");
        //客户端ip
        reqDTO.setClientIp("127.0.0.1");
        //场景信息
        reqDTO.setSceneInfo("{\"store_info\":{\"id\": \"门店ID\",\"name\": \"名称\",\"area_code\": \"编码\",\"address\": \"地址\" }}");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    //=====================================回调==========================================

    /**
     * 支付宝回调
     */
    @Test
    public void testAlipayNotify(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_NOTIFY_ALI);
        //回调数据
        reqDTO.setNotifyData("{\"gmt_create\":\"2017-09-13 18:02:59\",\"charset\":\"UTF-8\",\"seller_email\":\"justpay@justpay.com\",\"subject\":\"justpay-ali-001\",\"sign\":\"UYmEGs+9kkDMf9kFv0lfJ9XYR6hlyAKCbfQM32fx1j6oar3REf9+wAVsb5xJ05JSl5kmN4IdEDgWd3eh4j7409EwDi3ujTLgkVPO3aETQRCKDe5jHSpIQPckxI6fglrUTxvQwWPEf/9qHHsdbJwivVc6iCxpLByrtmR1g2kZQsagw3nVAaunbZpakMpurG+tCejuMiuXRSGDTZYhwJjNgugvMIF5XZm858UdxdeuCTRq7qr6hfUi/q7kuEq2vUOsUhxkDNQtRcD4ekPghuW6H8HFF/nKHIwc1V0Xqge0ig4XHuYLZGhZ7ZeQlug9QwVtl6V80ZRR2Y/ZCLRyEmBTdQ==\",\"buyer_id\":\"2088502465354536\",\"invoice_amount\":\"0.01\",\"notify_id\":\"9a18d9bd13c066b389490a1f69d8741k3a\",\"fund_bill_list\":\"[{\\\"amount\\\":\\\"0.01\\\",\\\"fundChannel\\\":\\\"ALIPAYACCOUNT\\\"}]\",\"notify_type\":\"trade_status_sync\",\"trade_status\":\"TRADE_SUCCESS\",\"receipt_amount\":\"0.01\",\"app_id\":\"2017090708599123\",\"buyer_pay_amount\":\"0.01\",\"sign_type\":\"RSA2\",\"seller_id\":\"2088121119106321\",\"gmt_payment\":\"2017-09-13 18:03:00\",\"notify_time\":\"2017-09-13 18:03:00\",\"version\":\"1.0\",\"out_trade_no\":\"just-ali-00555\",\"total_amount\":\"0.01\",\"trade_no\":\"2017091321001004530165893586\",\"auth_app_id\":\"2017090708522000\",\"buyer_logon_id\":\"317***@qq.com\",\"point_amount\":\"0.00\"}\n");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    /**
     * 微信回调
     */
    @Test
    public void testWechatNotify(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_NOTIFY_WECHAT);
        //回调数据
        reqDTO.setNotifyData("<xml><appid><![CDATA[wx4cf299df653d6a10]]></appid>\n" +
                "<bank_type><![CDATA[CFT]]></bank_type>\n" +
                "<cash_fee><![CDATA[1]]></cash_fee>\n" +
                "<fee_type><![CDATA[CNY]]></fee_type>\n" +
                "<is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "<mch_id><![CDATA[1256656301]]></mch_id>\n" +
                "<nonce_str><![CDATA[1505297116396]]></nonce_str>\n" +
                "<openid><![CDATA[o5nr6joE6wVYW92AYy8E333jeFj4]]></openid>\n" +
                "<out_trade_no><![CDATA[justpay-wechat-001]]></out_trade_no>\n" +
                "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<sign><![CDATA[7F46E15594901340BCD2CF4AC80272CE]]></sign>\n" +
                "<time_end><![CDATA[20170913180537]]></time_end>\n" +
                "<total_fee>1</total_fee>\n" +
                "<trade_type><![CDATA[NATIVE]]></trade_type>\n" +
                "<transaction_id><![CDATA[4001282001201709131886019757]]></transaction_id>\n" +
                "</xml>");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    //=====================================查询==========================================

    /**
     * 支付宝查询
     */
    @Test
    public void testAlipayQuery(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_QUERY_ALI);
        //回调数据
        reqDTO.setOutTradeNo("justpay-ali-001");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    /**
     * 微信查询
     */
    @Test
    public void testWechatQuery(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_QUERY_WECHAT);
        //回调数据
        reqDTO.setOutTradeNo("justpay-wechat-001");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    //=====================================退款==========================================

    /**
     * 支付宝退款
     */
    @Test
    public void testAlipayRefund(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_REFUND_ALI);
        //回调数据
        reqDTO.setOutTradeNo("justpay-ali-001");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }

    /**
     * 微信退款
     */
    @Test
    public void testWechatRefund(){
        RequestDTO reqDTO = new RequestDTO();
        //操作类型
        reqDTO.setOperation(PayConstant.OPERATION_REFUND_WECHAT);
        //回调数据
        reqDTO.setOutTradeNo("justpay-wechat-001");
        //转为json
        String jsonRequest = JsonUtils.objectToJson(reqDTO);
        //打印看一下json长什么样~
        System.out.println(jsonRequest);
        //发起支付下单请求
        String result = dispatcherService.dispatch(jsonRequest);
        //打印一下，看看返回了什么
        System.out.println(result);
    }
}
