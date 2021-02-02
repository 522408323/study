package com.ceshi.study.thread;

import java.util.concurrent.Callable;

/**
 * @ClassName: CallThread
 * @Author: shenyafei
 * @Date: 2021/1/21
 * @Desc
 **/
public class CallThread implements Callable {
    @Override
    public String call() throws Exception {
        System.out.println("==========implements Callable===========");
        return "测试返回值";
    }
}
