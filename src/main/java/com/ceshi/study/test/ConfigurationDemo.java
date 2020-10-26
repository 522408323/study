package com.ceshi.study.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: ConfigurationDemo
 * @Author: shenyafei
 * @Date: 2020/7/21
 * @Desc
 **/
@Configuration
public class ConfigurationDemo {


    @Bean
    public DemoClass getDemoClass(){
        return new DemoClass();
    }
}
