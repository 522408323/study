package com.ceshi.study.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @ClassName: QuartzTest
 * @Author: shenyafei
 * @Date: 2020/12/28
 * @Desc
 **/
public class QuartzTest {


    public static void main(String[] args) throws Exception{
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1","group1")
                .usingJobData("ggg","1111")
                .usingJobData("mmm","222")
                .build();
        //触发器
        /***
         * 1.Simpler Trigger :固定时刻，固定时间间隔（毫秒）
         * 2.CalendarIntervalTrigger :时间单位
         * 3.DailyTimeIntervalTrigger:
         * 4.CronTrigger:Cron表达式
         */
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group11")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        //单例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
    }
}
