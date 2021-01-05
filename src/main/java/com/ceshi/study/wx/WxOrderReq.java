package com.ceshi.study.wx;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 微信下单请求实体类
 */
@Data
public class WxOrderReq implements Serializable {
    /***
     * 小程序id
     */
   private String appid;

    /***
     * 商品简单描述
     */
    private String body;
    /***
     * 商户号
     */
   private String mch_id;
    /***
     * 随机数
     */
   private String nonce_str;

    /***
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
     */
    private String notify_url;

    /***
     * 用户标识 trade_type=JSAPI，此参数必传
     */
    private String openid;

    /***
     * 商户订单号
     */
    private String out_trade_no;


    /***
     * 调用微信支付API的机器IP
     */
    private String spbill_create_ip;

    /***
     * 订单金额：分
     */
    private Integer total_fee;

    /***
     * 交易类型 JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付
     */
    private String trade_type;

    /***
     * 签名
     */
   private String sign;


}
