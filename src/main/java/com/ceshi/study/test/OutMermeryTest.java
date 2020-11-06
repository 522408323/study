package com.ceshi.study.test;

import com.ceshi.study.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: OutMermeryTest
 * @Author: shenyafei
 * @Date: 2020/10/26
 * @Desc
 **/
public class OutMermeryTest {

    private static int count=0;

    public static void out(){
        List<User> list = new ArrayList<>();
        while(true){
            list.add(new User());
            try {
                Thread.sleep(1000L);
                System.out.println("休息1s");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void m(long i){
        long a=1,b=2,c=3,d=4;
        count++;
        m(i);
    }
    public static void main(String[] args) {
        try {
            //本机16G内存 默认栈深度21715  Xss128K 1个局部变量 898 4个--549
            //Xss256K  1个局部变量2748 4个--1359
            //Xss512k  1个局部变量 5002
            //Xss1m  1个局部变量23605 4个--21408
            //Xss2m 1个局部变量41395 4个--31956
            m(1);//栈溢出
            //out();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(count);
        }

    }
}
