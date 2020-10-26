package com.ceshi.study.test;

import com.alibaba.fastjson.JSON;
import com.ceshi.study.model.User;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: CeshiTest
 * @Author: shenyafei
 * @Date: 2020/8/11
 * @Desc
 **/
public class CeshiTest {

    /**
     * 获取当天凌晨时间
     * @author wuqiang
     */
    public static Date getMorningDate(Date d){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String str = df.format(d);
        Date m = null;
        try {
            m = df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return m;
    }

    /**
     * 获取当天结束时间
     * @params: []
     * @author: wxn
     * @date: 2020/4/29 14:13
     */
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static String moneyConvertWY(Double num, int size, boolean clearZero){
        String value = "";
        if(num == null){
            return "0";
        }
        if (size < 0) {
            size = 0;
        }

        BigDecimal bg1 = new BigDecimal(String.valueOf(num));
        BigDecimal bg2 = new BigDecimal("10000");
        if (num < 10000) {
            bg2 = new BigDecimal("1");
        }
        BigDecimal result = bg1.divide(bg2, size, BigDecimal.ROUND_HALF_UP);
        if (clearZero) {
            System.out.println("======"+ JSON.toJSONString(result));
            value = result.stripTrailingZeros().toPlainString();
        } else {
            value = result.toPlainString();
        }
        if (num >= 10000) {
            value += "W+";
        }
        return value;
    }
    public static void main(String[] args){

        Random random = new Random();
        /*for (int i=0;i<=10;i++) {
            System.out.println(100000 + random.nextInt(899999));
        }*/
        /*String[] arr = {"1","2","3","4"};
        List<String> collect =  Arrays.stream(arr).collect(Collectors.toList());
        for (String s: collect) {
            System.out.println("======"+s);
        }*/


      /*  for (int i=0;i<=50;i++) {
            System.out.println(random.nextInt(9));
        }*/

       /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(getDayEnd()));*/

    /*   Long a = 103L;
       Double b = 11111.123d;
        System.out.println(moneyConvertWY(Double.valueOf(a.toString()),0,true));
        System.out.println(moneyConvertWY(b,2,true));*/
       /* System.out.println("2020-09-09".replace("-","."));
        System.out.println(divideOperate("5","8"));*/


            /*StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("1，2，3，");
        System.out.println(stringBuffer.substring(0,stringBuffer.length()));*/
        /*List<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("张三");
        u1.setAge(20);
        users.add(u1);
        User u2 = new User();
        u2.setName("张三");
        u2.setAge(30);
        users.add(u2);

        User u3 = new User();
        u3.setName("张三2");
        u3.setAge(30);
        users.add(u3);
        List<User> list = users.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User :: getName))), ArrayList::new));
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach(e->{
                System.out.println(e.getName()+"---"+e.getAge());
            });
        }*/

       /* List<String> listA = new ArrayList<>();
        listA.add("1");
        listA.add("2");
        List<Integer> listB = listA.stream().map(Integer::valueOf).collect(Collectors.toList());
        for(Integer s : listB) {
            System.out.println(s);
        }*/
        System.out.println(doubleToStr(232.00));
    }

    public static BigDecimal divideOperate(String div1, String div2){
        BigDecimal d1 = new BigDecimal(div1);
        BigDecimal d2 = new BigDecimal(div2);
        if (d2.compareTo(BigDecimal.ZERO) == 0 || d1.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return d1.divide(d2,4, BigDecimal.ROUND_HALF_UP);
    }

    public static String doubleToStr(Double d){
        if (d == null) {
            return "";
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return (nf.format(d));
    }
}
