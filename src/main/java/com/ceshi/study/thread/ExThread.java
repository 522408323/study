package com.ceshi.study.thread;

/**
 * @ClassName: ExThread
 * @Author: shenyafei
 * @Date: 2021/1/21
 * @Desc
 **/
public class ExThread extends Thread {
    @Override
    public void run() {
        System.out.println("=========extends Thread==========");
    }
}
