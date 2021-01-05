package com.ceshi.study.test;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: CeshiTest
 * @Author: shenyafei
 * @Date: 2020/8/11
 * @Desc
 **/
public class CeshiTest2 {

    private static void sss(int sum){
        int index = 1;
        if (sum <= 2) {
            System.out.println("没有符合的");
            return;
        }
        while (true) {
            boolean end = false;
            List<Integer> list = new ArrayList<>();
            int flag = index;
            while (true) {
                list.add(flag);
                int count = total(list);
                if (count > sum) {
                    if (list.size() <= 2) {
                        end = true;
                    }
                    list = new ArrayList<>();
                    break;
                }
                if (count == sum) {
                    break;
                }
                flag++;
            }
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(e-> System.out.print(e+","));
                System.out.println();
            }
            if (end) {
                System.out.println("结束！");
                break;
            }
            list = new ArrayList<>();
            index++;
        }

    }

    private static int total(List<Integer> list){
        int count = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            for (Integer integer : list) {
                count += integer;
            }
        }
        return count;
    }

    private static void dddd(int size) {
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9};
        int[] flagArr = arr;
        while (true) {
            int count = arr.length;
            int[] currArr = new int[count];

            for (int i=0;i< count;i++) {
                int a = random(arr);
                currArr[i] = a;
                arr = newArr(arr,a);
                if (arr == null || arr.length == 0) {
                    break;
                }
            }
            //计算总和相等
            boolean f = total(currArr,size);
            /*for (int b : currArr) {
                System.out.print(b+",");
            }
            System.out.println(f);*/
            if (f) {
                for (int s : currArr) {
                    System.out.print(s+",");
                }
                System.out.println();
                break;
            }
            arr = flagArr;
        }
    }

    private static boolean total(int[] arr, int size){
        int sum = arr.length;
        int remain = sum%size;
        if (remain > 0) {
            System.out.println("remain>0");
            return false;
        }
        //总list
        List<Integer> totalList = new ArrayList<>();
        //行
        if (!checkHang(arr,size,totalList)) {
            return false;
        }
        //竖
        if (!checkShu(arr,size,totalList)) {
            return false;
        }
        //第一斜线
        int c = 0;
        for (int i = 0; i < sum; i +=4) {
            c += arr[i];
        }
        System.out.println(c);
        //第二斜线
        int d = 0;
        int sss = 1;
        for (int i = size - 1; i < sum; i +=2) {
            System.out.println(sss +"---"+ arr[i]);
            if (sss <= size) {
                d += arr[i];
            }
            sss++;
        }
        System.out.println(d);
        if (c != d) {
            System.out.println("xui");
            return false;
        }
        return check(totalList);
    }

    private static boolean checkHang(int[] arr, int size,List<Integer> totalList){
        int count = arr.length;
        if (CollectionUtils.isEmpty(totalList)) {
            totalList = new ArrayList<>();
        }
        int index = 0;
        List<Integer> hangList = new ArrayList<>();
        for (int z = 1; z <= size; z++) {
            int a = 0;
            for (int i = index; i < size + index; i ++) {
                a += arr[i];
            }
            hangList.add(a);
            totalList.add(a);
            index += 3;
        }
        return check(hangList);
    }

    private static boolean checkShu(int[] arr, int size,List<Integer> totalList){
        if (CollectionUtils.isEmpty(totalList)) {
            totalList = new ArrayList<>();
        }
        int index = 0;
        int count = arr.length;
        List<Integer> hangList = new ArrayList<>();
        for (int z = 1; z <= size; z++) {
            int a = 0;
            for (int i = index; i < count; i +=3) {
                a += arr[i];
            }
            hangList.add(a);
            totalList.add(a);
            index ++;
        }
        return check(hangList);
    }

    private static boolean check(List<Integer> list){
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(e-> System.out.print(e+","));
            System.out.println();
            for (int i = 0; i < list.size() - 1 ; i++) {
                if (list.get(i).intValue() != list.get(i+1)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static int random(int[] arr) {
        Random random = new Random();
        int num = random.nextInt(arr.length);
        return arr[num];
    }

    private static int[] newArr(int[] arr, int a){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (a != arr[i]) {
                list.add(arr[i]);
            }
        }
        if (CollectionUtils.isNotEmpty(list)) {
            int[] newArr = new int[list.size()];
            for (int i = 0; i < list.size() ; i++) {
                newArr[i] = list.get(i);
            }
            return newArr;
        } else {
            return null;
        }
    }



    private static void gg(){
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9};
        int count = arr.length;
        for (int i=0;i< count;i++) {
            int a = random(arr);
            System.out.println(a);
            arr = newArr(arr,a);
            for (int s : arr) {
                System.out.print(s+",");
            }
            System.out.println();
            if (arr == null || arr.length == 0) {
                break;
            }
        }
    }


    /***
     * 算法问题：
     * 1+2+3+4+5=15
     * 4+5+6=15
     * 7+8=15
     * @param args
     */
    public static void main(String[] args) throws Exception {
       // sss(16);
        //dddd(3);
        //gg();
        /*int[] arr = new int[]{8,1,6,3,5,7,4,9,2};
        System.out.println(total(arr,3));*/

        //System.out.println(UUID.randomUUID().toString().replace("-",""));
        StringBuffer outBuf = new StringBuffer();
        TreeMap<String,String> params = new TreeMap<String,String>();
        params.put("a","1");
        params.put("b","1");
        boolean isNotFirst = false;
        for (Map.Entry<String, String> entry: params.entrySet()){
            if (isNotFirst){
                outBuf.append('&');
            }
            isNotFirst = true;
            outBuf
                    .append(entry.getKey())
                    .append('=')
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        System.out.println("参数:"+outBuf.toString());
    }
}
