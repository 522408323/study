package com.ceshi.study.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: Demo
 * @Author: shenyafei
 * @Date: 2019/12/6
 * @Desc
 **/
public class Demo {
    public static void main(String[] args){
        StringBuffer s1 = new StringBuffer("abc");
        StringBuffer s2 = new StringBuffer("abc");
        System.out.println("=="+s1.equals(s2));
        System.out.println(new String("123456".toCharArray(),1,3));
        String data = "{\"author_state\":0}";
        JSONObject json = JSON.parseObject(data);
        int authorState = json.getIntValue("author_state");
        int failNum = json.getIntValue("fail_num");
        System.out.println("====authorState:"+authorState + ",,failNum:" + failNum);

        Map<String,String> map11 = new HashMap<>();
        map11.put("t","123");

        Map<String,String> map = new ConcurrentHashMap<>();
        map.put("a","1111");

        String str = "{\"payMethods\":[{\"code\":\"RTC001\",\"icon\":\"\",\"isApply\":\"N\",\"name\":\"银行卡支付\"},{\"code\":\"RTC002\",\"icon\":\"\",\"isApply\":\"N\",\"name\":\"验密支付\"}, {\"code\":\"RTC003\",\"icon\":\"\",\"isApply\":\"Y\",\"name\":\"一键  付\"}],\"payReqDto\":{\"busAppId\":\"962813278\",\"busCode\":\"BL001\",\"cName\":\"生活口语\",\"credCode\":\"CC001\",\"frontJumpUrl\":\"https://txuedai.labifenqi.com/jyfq-front-custom/order/orderList\",\"orderDetailId\":\"HhqxA3l0Vt4ynXbHNsS\",\"orderId\":\"HhqxA3l0Vt4ynXbHNsS\",\"pid\":\"1471964\",\"planRepayDate\":1579017600000,\"platCode\":\"PL001\",\"receiptType\":\"G001\",\"repayAmt\":44.52,\"reqSno\":\"2eq0Nr9fY1Ko4H6oRmC\"} }";
        System.out.println("-=========="+JSONObject.toJSONString(JSONObject.parseObject(str)));
        String bankNo = "12345678";
        System.out.println(bankNo = bankNo.substring(bankNo.length()-4, bankNo.length()));
        System.out.println("remainSeconds===="+getRemainSecondsOneDay());
    }

    public static Long getRemainSecondsOneDay() {
        Date currentDate = new Date();
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (Long) seconds;
    }

}
