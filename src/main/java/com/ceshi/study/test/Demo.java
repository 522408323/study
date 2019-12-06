package com.ceshi.study.test;

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
    }
}
