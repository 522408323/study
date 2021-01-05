package com.ceshi.study.quartz;

import org.quartz.*;

/**
 * @ClassName: MyJob
 * @Author: shenyafei
 * @Date: 2020/12/28
 * @Desc
 **/
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobContext.getJobDetail().getJobDataMap();
        System.out.println("当前时间戳：" + System.currentTimeMillis() + " jobExecutionContext: "+ jobDataMap.getString("ggg"));
        Trigger trigger = jobContext.getTrigger();
        String triggerName = trigger.getKey().getName();
        String triggerGroup = trigger.getKey().getGroup();
        System.out.println("triggerName：" + triggerName + " triggerGroup: "+ triggerGroup);

    }
}
