package com.justpay.util;

import com.justpay.constant.PayConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yeauty
 * @version 1.0
 * @Description:返回统一格式结果
 * @date 2017/9/5 13:39
 */
public class ResponseUtils {

    private ResponseUtils() {}


    /**
     * 支付业务请求成功时，返回封装好的map
     * @return
     */
    public static Map<String, Object> makeSuccessMap() {
        Map<String, Object> retMap = makeReturnMap(PayConstant.RETURN_VALUE_SUCCESS,"",null);
        return retMap;
    }

    /**
     * 支付业务请求失败时，返回封装好的map
     * @return
     */
    public static Map<String, Object> makeFailMap(String retMsg, String errCodeDesc) {
        Map<String, Object> retMap = makeReturnMap(PayConstant.RETURN_VALUE_FAIL,retMsg,errCodeDesc);
        return retMap;
    }

    /**
     *
     * @param retCode       返回状态码 SUCCESS/FAI
     * @param retMsg        返回信息  返回信息，如非空，为错误原因
     * @param errCodeDesc   错误代码描述
     * @return
     */
    public static Map<String, Object> makeReturnMap(String retCode, String retMsg, String errCodeDesc) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(retCode != null) retMap.put(PayConstant.RETURN_PARAM_RETCODE, retCode);
        if(retMsg != null) retMap.put(PayConstant.RETURN_PARAM_RETMSG, retMsg);
        if(errCodeDesc != null) retMap.put(PayConstant.RESULT_PARAM_ERRCODEDES, errCodeDesc);
        return retMap;
    }

}
