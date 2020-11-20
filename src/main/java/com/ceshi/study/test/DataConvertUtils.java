package com.ceshi.study.test;

import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName: DataConvertUtils
 * @Author: shenyafei
 * @Date: 2020/11/13
 * @Desc
 **/
public class DataConvertUtils {


    public static String getRandomVisitNum(String desc){
        if (desc == null) {
            desc = "";
        }
        //0~9000
        int num = new Random().nextInt(9001) + 1000;
        if (num < 10000) {
            return num + desc;
        }
        String value = "";
        BigDecimal bg1 = new BigDecimal(num);
        BigDecimal bg2 = new BigDecimal("10000");
        BigDecimal result = bg1.divide(bg2, 1, BigDecimal.ROUND_HALF_UP);
        value = result.stripTrailingZeros().toPlainString();
        value += "万";
        return value + desc;
    }

    public static List<String> randomHeadImgList(int size){
        List<String> finalList = new ArrayList<>();
        String[] headImgArray = new String[]{"a","b","c","d","e","f","g"};
        int count = headImgArray.length;
        if (size > count) {
            size = count;
        }
        for (int i = 0; i< size; i++) {
            String randomStr = getRandom(headImgArray);
            finalList.add(randomStr);
            headImgArray = ArrayUtils.removeElement(headImgArray,randomStr);
        }
        return finalList;
    }

    public static String getRandom(String[] headImgArray){
        int count = headImgArray.length;
        Random random = new Random();
        int randomNum = random.nextInt(count);
        return headImgArray[randomNum];
    }

    public static void main(String[] args){
        System.out.println(randomHeadImgList(3));

       /* String[] headImgArray = new String[]{"a","b","c","d","e","f","g"};
        String a = getRandom2(headImgArray);
        System.out.println( a);
        headImgArray = ArrayUtils.removeElement(headImgArray, a);
        for (String str : headImgArray) {
            System.out.print(","+str);
        }*/
    }
}
