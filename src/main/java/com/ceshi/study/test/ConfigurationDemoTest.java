package com.ceshi.study.test;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@ComponentScan
public class ConfigurationDemoTest {

    public static void main(String[] args) {
        /*AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ConfigurationDemo.class);
        DemoClass demoClass = configApplicationContext.getBean(DemoClass.class);
        DemoClass demoClass2 = configApplicationContext.getBean(DemoClass.class);
        demoClass.say();*/
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ConfigurationDemo.class);
        String[] names = configApplicationContext.getBeanDefinitionNames();


    }
}
