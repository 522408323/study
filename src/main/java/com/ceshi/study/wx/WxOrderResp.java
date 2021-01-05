package com.ceshi.study.wx;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 微信下单返回实体类
 */
@Data
public class WxOrderResp implements Serializable {

    /***
     * 返回状态码 SUCCESS/FAIL
     *
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    private String return_code;
    /***
     * 返回信息，如非空，为错误原因
     * 签名失败
     * 参数格式校验错误
     */
    private String return_msg;

    /***
     * 小程序id
     */
   private String appid;

    /***
     * 商户号
     */
   private String mch_id;

    /***
     * 随机数
     */
   private String nonce_str;
    /***
     * 签名
     */
   private String sign;
    /***
     * 业务结果 SUCCESS/FAIL
     */
   private String result_code;
    /***
     * 错误代码
     */
   private String err_code;
    /***
     * 错误代码描述
     */
   private String err_code_des;
    /***
     * 交易类型，JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付
     */
   private String trade_type;
    /***
     * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     */
   private String prepay_id;
    /***
     * 二维码链接
     */
   private String code_url;

    /***
     * 请求参数字符串
     */
    private String requestData;
    /***
     * 返回参数字符串
     */
    private String respData;

}
