![](https://img.shields.io/badge/just-pay-lightgrey.svg?colorA=d9d0c7&colorB=9fe0f6)

### 感谢、致敬
---
>感谢xxpay聚合支付的开源！这是个很棒的项目~本项目在xxpay中学到了很多好的思想及代码规范！
>XxPay官网：http://www.xxpay.org

>感谢Binary Wang的开源微信支付sdk，里面实现好了绝大多数微信支付的功能，使得我们简单的去调用(他还有很多关于微信的tool)
>Binary Wang主页：https://github.com/binarywang

---



### 项目介绍
![](https://img.shields.io/badge/build-passing-brightgreen.svg) ![](https://img.shields.io/badge/downloads-3M-brightgreen.svg) ![](https://img.shields.io/badge/jdk-1.8-blue.svg) ![](https://img.shields.io/badge/springboot-2.0.0-blue.svg)  ![](https://img.shields.io/badge/springwebflux-2.0.0-blue.svg) ![](https://img.shields.io/badge/maven-3.3.9-blue.svg)  ![](https://img.shields.io/badge/Dubbox-2.8.4-blue.svg) ![](https://img.shields.io/badge/zookeeper-3.4.10-blue.svg) ![](https://img.shields.io/badge/IDEA-2017.2.3-blue.svg)
---
- `Justpay` 使用Java开发，使用spring-boot2.0.0、dubbox实现SOA的分布式架构
- 只完成与支付相关的功能（支付、回调、查询、退款），没有其它业务逻辑相关模块，可直接接入生产环境
- 不同功能拆分成不同的服务，使得不同的支付服务更容易根据并发进行集群（可插拔）
- 使用了spring-boot2.0.0中新集成的spring-webflux (reactor)
- 目前实现了微信支付（4种模式：NATIVE、JSAPI、APP、MWEB），支付宝支付（4种模式：PC、WAP、APP）
- Justpay架构图![](https://i.imgur.com/frTlp7H.png)
- Justpay接入到原系统中的架构位置![](https://i.imgur.com/daz7TbV.png)

### 项目结构
---
```
justpay
├── justpay-common -- 公共模块
|    ├── justpay-config-interface -- 配置服务接口
|    ├── justpay-config-pojo -- 支付配置相关类
|    └── justpay-config-service -- 配置服务生产者
├── justpay-dispatcher -- 功能分发层
|    ├── justpay-dispatcher-interface -- 功能分发层接口
|    └── justpay-dispatcher-service -- 功能分发层服务生产者
└── justpay-pay -- 支付服务
     ├── justpay-pay-ali -- 支付宝支付
     |    ├── justpay-pay-ali-interface --支付宝接口
     |    └── justpay-pay-ali-service -- 支付宝服务生产者
     └── justpay-pay-wechat -- 微信支付
          ├── justpay-pay-wechat-interface --微信接口
          └── justpay-pay-wechat-service -- 微信服务生产者
```
#### 说明

| 项目  | 端口 | 描述 | 依赖
|---|---|---|---
|justpay-pay-ali-service|9091|支付宝服务提供者、配置服务消费者|justpay-pay-ali-interface、justpay-config-interface、justpay-common
|justpay-pay-wechat-service|9092|微信服务提供者、配置服务消费者|justpay-pay-wechat-interface、justpay-config-interface、justpay-common
|justpay-dispatcher-service|9093|Justpay服务提供者、支付服务消费者|justpay-dispatcher-interface、justpay-pay-ali-interface、justpay-pay-wechat-interface、justpay-common


项目启动顺序：
```
justpay-pay-ali-service、justpay-pay-wechat-service > justpay-dispatcher-service
```

### API列表
---
#### 参数说明

| 字段名  | 变量名 | 类型 | 示例 | 描述
|---|---|---|---
|操作类型|operation|String(20)|PAY_ALIPAY_WAP|用于判断下单、回调、查询、退款
|订单号|outTradeNo|String(32)|XD20160427210604000490|根据业务逻辑生成的订单号
|Usre-Agent|userAgent|String(32)|Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36|请求头中的Usre-Agent
|支付金额|amount|String(10)|1000|单位：分，不能超过2147483647
|客户端IP|clientIp|String(15)|210.73.10.148|客户端IP地址
|商品标题|title|String(64)|justpay测试用商品|商品名称
|副标题|subtitle|String(128)|商品副标题|副标题
|通知地址|notifyUrl|String(200)|http://notify.justpay.com|支付结果回调URL(要在外网可访问)
|微信用户openid|openid|String(28)|o5nr6jl2kDeODxkHrp-AdBVud3N8|微信用户在商户对应appid下的唯一标识
|商品ID|productId|String(32)|PRO00011011|商户自行定义
|场景信息|sceneInfo|String(2000)|{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }|用于上报场景信息，目前支持上报实际门店信息
|同步地址|returnUrl|String(200)|http://return.justpay.com|支付宝支付时的同步地址
|设备号|deviceInfo|String(32)|YY-DD-001|记录在哪台设备完成支付
|回调数据|notifyData|String(2000)|-|微信/支付宝的回调数据，放这里
|退款单号|outRefundNo|String(32)|TC20160427210604000490|同一退款单号多次请求只退一笔
|退款金额|refundAmount|String(10)|100|单位：分，不能超过2147483647

---
###### 下单请求

| 字段名  | 变量名 | 必填 | 可选值/传入值 | 描述
|---|---|---|---
|操作类型|operation|是|PAY_AUTO（自动）、PAY_ALIPAY_WAP（支付宝手机网页。常用）、PAY_ALIPAY_PC（支付宝电脑网页）、PAY_ALIPAY_APP（支付宝APP支付，是自己的APP，不是支付宝APP）、PAY_WECHAT_NATIVE（微信扫码。常用）、PAY_WECHAT_JSAPI（微信公众号支付）、PAY_WECHAT_APP（微信公APP支付）、PAY_WECHAT_MWEB（微信H5支付）|支付宝PAY_ALIPAY_WAP可实现手机网页及支付宝支付，微信PAY_WECHAT_NATIVE通过跳转可实现手机网页及微信支付
|订单号|outTradeNo|是|自定义|根据业务逻辑生成的订单号
|User-Agent|userAgent|operation为PAY_AUTO时必填|用户请求中的User-Agent|当operation为PAY_AUTO时，根据User-Agent，自动选择使用哪种下单方式（不推荐使用）
|支付金额|amount|是|自定义|单位：分，不能超过2147483647
|客户端IP|clientIp|是|从用户中获取|客户端IP地址
|商品标题|title|是|自定义|商品名称，对应支付宝中的subject、对应微信中的body
|副标题|subtitle|否|商品副标题|副标题，对应支付宝中的body
|通知地址|notifyUrl|是|自定义|支付结果回调URL(要在外网可访问)
|微信用户openid|openid|operation为PAY_WECHAT_JSAPI时必填|在微信公众号后台获取|微信用户在商户对应appid下的唯一标识
|商品ID|productId|operation为PAY_WECHAT_NATIVE时必填|自定义|商户自行定义
|场景信息|sceneInfo|operation为PAY_WECHAT_MWEB时必填|根据微信接口格式自定义|用于上报场景信息，目前支持上报实际门店信息
|同步地址|returnUrl|否|自定义|支付宝支付时的同步地址（支付完成后跳转的页面）
|设备号|deviceInfo|否|自定义|记录在哪台设备完成支付

###### 回调请求

| 字段名  | 变量名 | 必填 | 可选值/传入值 | 描述
|---|---|---|---
|操作类型|operation|是|NOTIFY_ALI（支付宝）、NOTIFY_WECHAT（微信）、NOTIFY_AUTO（自动判断，不推荐）|用于判断进行哪种回调操作
|回调数据|notifyData|是|微信回调信息直接原格式放入；支付宝为form提交，需转为json再放入|微信/支付宝的回调数据，放这里

###### 查询请求

| 字段名  | 变量名 | 必填 | 可选值/传入值 | 描述
|---|---|---|---
|操作类型|operation|是|QUERY_ALI（支付宝）、QUERY_WECHAT（微信）|用于判断进行哪种查询操作
|订单号|outTradeNo|是|自定义|根据业务逻辑生成的订单号

###### 退款请求

| 字段名  | 变量名 | 必填 | 可选值/传入值 | 描述
|---|---|---|---
|操作类型|operation|是|REFUND_ALI（支付宝）、REFUND_WECHAT（微信）|用于判断进行哪种查询操作
|订单号|outTradeNo|是|自定义|根据业务逻辑生成的订单号
|退款金额|refundAmount|是|自定义|需要退款的金额
|退款单号|outRefundNo|REFUND_WECHAT时必填|自定义|微信退款时需要为每一笔退款生成一个退款单号
|金额|amount|REFUND_WECHAT时必填|自定义|outTradeNo订单的总金额

---

###### 下单响应

| 字段名  | 变量名 | 必须 | 示例值 | 描述
|---|---|---|---
|返回状态码|return_code|是|SUCCESS|SUCCESS/FAIL；SUCCESS则为业务成功
|返回信息|return_msg|否|下单失败，原因XXX|返回信息，如非空，为错误原因
|错误信息描述|err_code_des|否|INVALID_REQUEST:201 商户订单号重复|错误描述，如非空，为错误描述
|订单号|outTradeNo|是|XD20160427210604000490|订单号
|二维码链接|codeUrl|否|weixin://wxpay/bizpayurl?pr=GhvBPEd|微信NATIVE请求时返回，直接用于跳转（也可用于生成二维码）
|预付Id|prepayId|否|wx2017091316324779a6d4242a0142314121|微信返回，用于2小时内做特殊操作
|APP支付参数|payParams|否|json数据|微信JSAPI请求时返回，用于给公众号使用；支付宝APP请求时返回，用于自己的APP上支付宝支付
|表单|form|否|html数据|支付宝返回，需要把这段写到页面上

###### 回调响应

| 字段名  | 变量名 | 必须 | 示例值 | 描述
|---|---|---|---
|返回状态码|return_code|是|SUCCESS|SUCCESS/FAIL；SUCCESS则为回调签名验证成功
|返回信息|return_msg|否|验证失败，原因XXX|返回信息，如非空，为错误原因
|成功|successReturn|否|--支付宝/微信格式不同--|签名验证成功时才有，若逻辑判断通过，可直接返回此内容给平台
|错误返回格式|failReturn|是|--支付宝/微信格式不同--|无论签名验证成功失败都会有。若验证成功，但前端逻辑判断失败（如金额不对），可返回此内容给平台

###### 查询响应

| 字段名  | 变量名 | 必须 | 示例值 | 描述
|---|---|---|---
|返回状态码|return_code|是|SUCCESS|SUCCESS/FAIL；SUCCESS则为查找订单成功
|返回信息|return_msg|否|查询失败|返回信息，如非空，为错误原因
|错误信息描述|err_code_des|否|交易不存在|错误描述，如非空，为错误描述
|查询数据|queryData|否|平台返回的消息|从平台中查到的数据;属性已经解析出来了（支付状态在tradeState），若要拿到原生请求信息，从xmlString(微信)/body(支付宝)中获取

###### 退款响应

| 字段名  | 变量名 | 必须 | 示例值 | 描述
|---|---|---|---
|返回状态码|return_code|是|SUCCESS|SUCCESS/FAIL；SUCCESS则为退款成功
|返回信息|return_msg|否|退款失败|返回信息，如非空，为错误原因
|错误信息描述|err_code_des|否|交易不存在|错误描述，如非空，为错误描述
|退款数据|refundData|否|json数据|从平台中查到的数据;属性已经解析出来了，若要拿到原生请求信息，从xmlString(微信)/body(支付宝)中获取



### 快速启动
---
1. 修改配置服务中的支付宝/微信配置文件  
![](https://i.imgur.com/7TqAARJ.png)![](https://i.imgur.com/5iD3EEK.png)
2. 修改启动项目中的zookeeper地址（这里使用的是dev配置，对于配置的切换请参考spring-boot文档）  
![](https://i.imgur.com/GBiDaiZ.png)![](https://i.imgur.com/oRchUgF.png)![](https://i.imgur.com/C0IQ5ja.png)
3. 启动项目（justpay-pay-ali-service、justpay-pay-wechat-service > justpay-dispatcher-service；推荐使用debug启动）  
![](https://i.imgur.com/2Y9mEt9.png)![](https://i.imgur.com/esHvUBV.png)![](https://i.imgur.com/hNRiDQd.png)
4. 接入（Dubbox、HTTP）
	- Dubbox : 将功能分发层中的接口复制到业务逻辑模块，通过Dubbox调用即可  
	  ![](https://i.imgur.com/R5DZIro.png)
	- HTTP : 将请求发送到分发层的controller中（controller除了包含正常的下单、回调、查询、退款；还包含spring-webflux写出支付宝支付页面、微信支付二维码的展示代码）
	  ![](https://i.imgur.com/zIDQzYJ.png)


#### 测试用例
---
- 在justpay-dispatcher-service的test里面有所有操作的测试用例，可以直接设置参数进行测试![](https://i.imgur.com/qRSfRJa.png)
- 也可以通过HTTP请求controller进行测试


