package com.ceshi.study.QuartzTask;

import com.ceshi.study.util.SpringContextUtil;

/**
 * @ClassName: TimeTask
 * @Author: shenyafei
 * @Date: 2020/12/28
 * @Desc
 **/
public class TimeTask {

    public static void main(String[] args) {
       /* String a = "com.jdbb.bus.quartz.ser.job.task.CustomerTask";
        a = "A";
        String b = a.substring(a.lastIndexOf(".")+1,a.length());
        b = b.substring(0,1).toLowerCase() + b.substring(1,b.length());
        System.out.println(b);
        Object bean = SpringContextUtil.getBean("ceshiTask");*/
       CeshiTask c1 = new CeshiTask();
        CeshiTask c2 = new CeshiTask();
        CeshiTask  c3 = c2;
        System.out.println(c1 == c2);
        System.out.println(c2 == c3);
    }
}
