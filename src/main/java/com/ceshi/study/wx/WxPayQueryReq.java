package com.ceshi.study.wx;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 微信支付请求实体类
 * 实体类参数名顺序按照ASCII编号顺序排
 */
@Data
public class WxPayQueryReq implements Serializable {
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
     * 商户订单号
     */
    private String out_trade_no;

    /***
     * 签名
     */
    private String sign;

}
