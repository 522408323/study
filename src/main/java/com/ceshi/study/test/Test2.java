package com.ceshi.study.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * @ClassName: Test2
 * @Date: 2020/11/30
 * @Desc
 **/
public class Test2 {
    public static void main(String[] args) {

        System.out.println(tableSizeFor(10000));
    }

    public static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1  : n + 1;
    }
}
