package com.ceshi.study.test;

import com.ceshi.study.model.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: LambdaTest
 * @Author: shenyafei
 * @Date: 2020/6/28
 * @Desc
 **/
public class LambdaTest {

   public static void filter(){
       //过滤
       List<String> list = new ArrayList<>();
       list.add("a");list.add("b");list.add("c");
       System.out.println("过滤前：");
       list.stream().forEach(e-> System.out.println(e));
       list = list.stream().filter(e -> e.equals("b")).collect(Collectors.toList());
       System.out.println("过滤后：");
       list.stream().forEach(e-> System.out.println(e));
   }

   public static void distinct(){
       //去重
       List<String> list = new ArrayList<>();
       list.add("a");list.add("a");list.add("b");list.add("c");list.add("c");
       System.out.println("去重前：");
       list.stream().forEach(e-> System.out.println(e));
       list = list.stream().distinct().collect(Collectors.toList());
       System.out.println("去重后：");
       list.stream().forEach(e-> System.out.println(e));
   }

   public static void distinctObject(){
       List<User> list = new ArrayList<>();
       User user = new User();
       user.setId(1);
       user.setName("张三");
       user.setAge(20);
       list.add(user);
       list.add(user);
       System.out.println("实体去重前：");
       list.stream().forEach(e-> System.out.println(e.toString()));
       list = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getId))),ArrayList::new));
       System.out.println("实体去重后：");
       list.stream().forEach(e-> System.out.println(e.toString()));
   }

   public static void mapObject(){
       //映射
       List<User> list = new ArrayList<>();
       User user = new User();
       user.setId(1);
       user.setName("张三");
       user.setAge(20);
       list.add(user);
       User user2 = new User();
       user2.setId(2);
       user2.setName("张三2");
       user2.setAge(22);
       list.add(user2);
       System.out.println("映射前：");
       list.stream().forEach(e-> System.out.println(e.toString()));
       List<Integer> finalList = list.stream().map(User::getId).collect(Collectors.toList());
       System.out.println("映射后：");
       finalList.stream().forEach(e-> System.out.println(e));
   }

   public static void sort(){
       List<Integer> list = new ArrayList<>();
       list.add(10); list.add(1);list.add(2);
       System.out.println("ASCII排序前：");
       list.stream().forEach(e-> System.out.println(e));
       list = list.stream().sorted().collect(Collectors.toList());
       System.out.println("ASCII排序后：");
       list.stream().forEach(e-> System.out.println(e));
       list = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
       System.out.println("ASCII倒叙排序后：");
       list.stream().forEach(e-> System.out.println(e));
   }

    public static void moreSort(){
        List<User> list = new ArrayList<>();
        list.add(new User(1,"张三1",10));
        list.add(new User(2,"张三2",20));
        list.add(new User(3,"张三3",30));
        System.out.println("复杂排序前：");
        list.stream().forEach(e-> System.out.println(e.toString()));
        list = list.stream().sorted(Comparator.comparing(User::getAge)).collect(Collectors.toList());
        System.out.println("复杂排序后：");
        list.stream().forEach(e-> System.out.println(e.toString()));
        list = list.stream().sorted(Comparator.comparing(User::getAge).reversed()).collect(Collectors.toList());
        System.out.println("复杂倒叙排序后：");
        list.stream().forEach(e-> System.out.println(e.toString()));
    }

    public static void main(String[] args) {
       /* //过滤
        filter();
        //去重
        distinct();
        distinctObject();
        //映射
        mapObject();
        //匹配

        //排序
        sort();
        //复杂排序
        moreSort();*/

       /* List<User> list = new ArrayList<>();
        list.add(new User(1,"张三1",10));
        list = list.stream().limit(2).collect(Collectors.toList());
        list.stream().forEach(e-> System.out.println(e.toString()));*/

        List<User> list = new ArrayList<>();
        list.add(new User(1,"张三1",10));
        list.add(new User(1,"张三2",20));
        list.add(new User(2,"张三3",30));
        System.out.println(list.stream().map(User::getId).count());
        System.out.println(list.stream().map(User::getId).distinct().count());
        for (int i=0;i<10;i++) {
           // System.out.println((int)Math.random() * 10);
            System.out.println(new Random().nextInt(10));
        }

    }
}
