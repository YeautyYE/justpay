package com.justpay.pojo;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:TODO
 * @date 2017/9/11 10:03
 */
public class WechatpayConfigDTO {

    //微信公众号身份的唯一标识。审核通过后，在微信发送的邮件中查看
    private String appId;
    //商户号：开通微信支付后分配
    private String mchId;
    //商户支付密钥Key。审核通过后，在微信发送的邮件中查看
    private String mchKey;
    //p12证书的路径(apiclient_cert.p12的文件的绝对路径)
    private String keyPath;
    //过期时间，单位分钟
    private Integer timeout;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public WechatpayConfigDTO() {
    }

    public WechatpayConfigDTO(String appId, String mchId, String mchKey, String keyPath, Integer timeout) {
        this.appId = appId;
        this.mchId = mchId;
        this.mchKey = mchKey;
        this.keyPath = keyPath;
        this.timeout = timeout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WechatpayConfigDTO that = (WechatpayConfigDTO) o;

        if (appId != null ? !appId.equals(that.appId) : that.appId != null) return false;
        if (mchId != null ? !mchId.equals(that.mchId) : that.mchId != null) return false;
        if (mchKey != null ? !mchKey.equals(that.mchKey) : that.mchKey != null) return false;
        if (keyPath != null ? !keyPath.equals(that.keyPath) : that.keyPath != null) return false;
        return timeout != null ? timeout.equals(that.timeout) : that.timeout == null;
    }

    @Override
    public int hashCode() {
        int result = appId != null ? appId.hashCode() : 0;
        result = 31 * result + (mchId != null ? mchId.hashCode() : 0);
        result = 31 * result + (mchKey != null ? mchKey.hashCode() : 0);
        result = 31 * result + (keyPath != null ? keyPath.hashCode() : 0);
        result = 31 * result + (timeout != null ? timeout.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WechatpayConfig{" +
                "appId='" + appId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", mchKey='" + mchKey + '\'' +
                ", keyPath='" + keyPath + '\'' +
                ", timeout=" + timeout +
                '}';
    }
}
