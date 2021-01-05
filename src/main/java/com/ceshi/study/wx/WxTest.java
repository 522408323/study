package com.ceshi.study.wx;

import com.alibaba.fastjson.JSON;
import com.ceshi.study.model.User;
import com.ceshi.study.util.IPUtil;

import java.io.File;
import java.util.Date;
import java.util.TreeMap;

/**
 * @ClassName: WxTest
 * @Date: 2020/12/18
 * @Desc
 **/
public class WxTest {

    public static void main(String[] args) throws Exception {
        String ip = IPUtil.getLocalIP();
        WxOrderReq wxOrderReq = new WxOrderReq();
        wxOrderReq.setAppid("wx1879254c819634f2");
        wxOrderReq.setBody("jellyfishcare-商品");
        wxOrderReq.setMch_id("1604854791");
        wxOrderReq.setNonce_str(WxUtil.getValidatecode(16));
        wxOrderReq.setNotify_url("https://jdbb-dev.shuimuglobal.com/sstation-api/order/notify/wx/pay/return");
        wxOrderReq.setOut_trade_no(WxUtil.getValidatecode(16));
        wxOrderReq.setSpbill_create_ip(ip);
        wxOrderReq.setTotal_fee(1);
        wxOrderReq.setTrade_type("JSAPI");
        wxOrderReq.setOpenid("oURQt5Ew8bWQFr23qZY4r9K8T6LU");
        TreeMap<String,String> map = WxUtil.objectToMap(wxOrderReq);
        String sign = WxUtil.sign(map,"shuimuglobal6666jellyfishcare666");
        wxOrderReq.setSign(sign);
        String xml = WxUtil.buildXml(wxOrderReq);
        System.out.println("构建xml请求报文："+xml);
        String resp = XmlTools.sendXml("https://api.mch.weixin.qq.com/pay/unifiedorder",xml);
        System.out.println("返回微信下单结果："+resp);
        WxOrderResp aa = WxUtil.parseWxOrderResp(resp);
        aa.setRequestData(xml);
        aa.setRespData(resp);
        System.out.println("1111返回微信下单结果："+ JSON.toJSONString(aa));
        /*String time = String.valueOf(System.currentTimeMillis());
        time = time.substring(0,time.length()-3);
        System.out.println(time);*/

        /*TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("a","1");
        map.put("b","2");
        System.out.println(JSON.toJSONString(map));

        JSON.parseObject("", User.class);*/
        /*WxPayQueryReq wxPayQueryReq = new WxPayQueryReq();
        wxPayQueryReq.setAppid("wx1879254c819634f2");
        wxPayQueryReq.setMch_id("1604854791");
        wxPayQueryReq.setNonce_str(WxUtil.getValidatecode(16));
        wxPayQueryReq.setOut_trade_no("48246906");
        TreeMap<String,String> map = WxUtil.objectToMap(wxPayQueryReq);
        String sign = WxUtil.sign(map,"shuimuglobal6666jellyfishcare666");
        wxPayQueryReq.setSign(sign);
        String xml = WxUtil.buildXml(wxPayQueryReq);
        System.out.println("【微信支付查询接口】构建xml请求报文：{}"+xml);
        String resp = XmlTools.sendXml("https://api.mch.weixin.qq.com/pay/orderquery",xml);
        System.out.println("【微信支付查询接口】 返回微信下单结果：{}"+resp);*/
        String filePath = WxTest.class.getResource("").getPath();
        System.out.println(filePath);
        String a = filePath +File.separator+"wx"+File.separator+"sss.txt";

        File file = new File(a);
        System.out.println(file.exists());
    }
}
