package com.justpay.pojo;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:支付宝配置文件
 * @date 2017/9/7 14:37
 */
public class AlipayConfigDTO {
    // 商户appid
    private String APPID ;
    // 私钥 pkcs8格式的
    private String RSA_PRIVATE_KEY ;
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    private String notify_url ;
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    private String return_url ;
    // 请求网关地址
    private String URL ;
    // 编码
    private String CHARSET ;
    // 返回格式
    private String FORMAT ;
    // 支付宝公钥
    private String ALIPAY_PUBLIC_KEY ;
    // 日志记录目录
    private String log_path ;
    // RSA2
    private String SIGNTYPE ;
    //过期时间，单位分钟
    private Integer timeout;

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getAPPID() {
        return APPID;
    }

    public void setAPPID(String APPID) {
        this.APPID = APPID;
    }

    public String getRSA_PRIVATE_KEY() {
        return RSA_PRIVATE_KEY;
    }

    public void setRSA_PRIVATE_KEY(String RSA_PRIVATE_KEY) {
        this.RSA_PRIVATE_KEY = RSA_PRIVATE_KEY;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getCHARSET() {
        return CHARSET;
    }

    public void setCHARSET(String CHARSET) {
        this.CHARSET = CHARSET;
    }

    public String getFORMAT() {
        return FORMAT;
    }

    public void setFORMAT(String FORMAT) {
        this.FORMAT = FORMAT;
    }

    public String getALIPAY_PUBLIC_KEY() {
        return ALIPAY_PUBLIC_KEY;
    }

    public void setALIPAY_PUBLIC_KEY(String ALIPAY_PUBLIC_KEY) {
        this.ALIPAY_PUBLIC_KEY = ALIPAY_PUBLIC_KEY;
    }

    public String getLog_path() {
        return log_path;
    }

    public void setLog_path(String log_path) {
        this.log_path = log_path;
    }

    public String getSIGNTYPE() {
        return SIGNTYPE;
    }

    public void setSIGNTYPE(String SIGNTYPE) {
        this.SIGNTYPE = SIGNTYPE;
    }

    public AlipayConfigDTO(String APPID, String RSA_PRIVATE_KEY, String notify_url, String return_url, String URL, String CHARSET, String FORMAT, String ALIPAY_PUBLIC_KEY, String log_path, String SIGNTYPE) {
        this.APPID = APPID;
        this.RSA_PRIVATE_KEY = RSA_PRIVATE_KEY;
        this.notify_url = notify_url;
        this.return_url = return_url;
        this.URL = URL;
        this.CHARSET = CHARSET;
        this.FORMAT = FORMAT;
        this.ALIPAY_PUBLIC_KEY = ALIPAY_PUBLIC_KEY;
        this.log_path = log_path;
        this.SIGNTYPE = SIGNTYPE;
    }

    public AlipayConfigDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlipayConfigDTO that = (AlipayConfigDTO) o;

        if (APPID != null ? !APPID.equals(that.APPID) : that.APPID != null) return false;
        if (RSA_PRIVATE_KEY != null ? !RSA_PRIVATE_KEY.equals(that.RSA_PRIVATE_KEY) : that.RSA_PRIVATE_KEY != null)
            return false;
        if (notify_url != null ? !notify_url.equals(that.notify_url) : that.notify_url != null) return false;
        if (return_url != null ? !return_url.equals(that.return_url) : that.return_url != null) return false;
        if (URL != null ? !URL.equals(that.URL) : that.URL != null) return false;
        if (CHARSET != null ? !CHARSET.equals(that.CHARSET) : that.CHARSET != null) return false;
        if (FORMAT != null ? !FORMAT.equals(that.FORMAT) : that.FORMAT != null) return false;
        if (ALIPAY_PUBLIC_KEY != null ? !ALIPAY_PUBLIC_KEY.equals(that.ALIPAY_PUBLIC_KEY) : that.ALIPAY_PUBLIC_KEY != null)
            return false;
        if (log_path != null ? !log_path.equals(that.log_path) : that.log_path != null) return false;
        return SIGNTYPE != null ? SIGNTYPE.equals(that.SIGNTYPE) : that.SIGNTYPE == null;
    }

    @Override
    public int hashCode() {
        int result = APPID != null ? APPID.hashCode() : 0;
        result = 31 * result + (RSA_PRIVATE_KEY != null ? RSA_PRIVATE_KEY.hashCode() : 0);
        result = 31 * result + (notify_url != null ? notify_url.hashCode() : 0);
        result = 31 * result + (return_url != null ? return_url.hashCode() : 0);
        result = 31 * result + (URL != null ? URL.hashCode() : 0);
        result = 31 * result + (CHARSET != null ? CHARSET.hashCode() : 0);
        result = 31 * result + (FORMAT != null ? FORMAT.hashCode() : 0);
        result = 31 * result + (ALIPAY_PUBLIC_KEY != null ? ALIPAY_PUBLIC_KEY.hashCode() : 0);
        result = 31 * result + (log_path != null ? log_path.hashCode() : 0);
        result = 31 * result + (SIGNTYPE != null ? SIGNTYPE.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AlipayConfig{" +
                "APPID='" + APPID + '\'' +
                ", RSA_PRIVATE_KEY='" + RSA_PRIVATE_KEY + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", return_url='" + return_url + '\'' +
                ", URL='" + URL + '\'' +
                ", CHARSET='" + CHARSET + '\'' +
                ", FORMAT='" + FORMAT + '\'' +
                ", ALIPAY_PUBLIC_KEY='" + ALIPAY_PUBLIC_KEY + '\'' +
                ", log_path='" + log_path + '\'' +
                ", SIGNTYPE='" + SIGNTYPE + '\'' +
                '}';
    }
}
