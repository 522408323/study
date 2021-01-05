package com.ceshi.study.test;

import com.ceshi.study.model.Node;

/**
 * @ClassName: ErTest
 * @Author: shenyafei
 * @Date: 2020/12/31
 * @Desc
 **/
public class ErTest {

    private static Node getNodeByArr(Integer[] arr){
        Node n = new Node();
        for (Integer a : arr) {
            insert(n,a);
        }
        return n;
    }

    private static void insert(Node n, Integer a){
            if (n.getCurr() == null) {
                n.setCurr(a);
            } else {
                if (n.getCurr() > a) {
                    //左边
                    if (n.getLeft() == null) {
                        Node left = new Node();
                        left.setCurr(a);
                        n.setLeft(left);
                    } else {
                        insert(n.getLeft(),a);
                    }
                } else {
                    //右边
                    if (n.getRight() == null) {
                        Node right = new Node();
                        right.setCurr(a);
                        n.setRight(right);
                    } else {
                        insert(n.getRight(),a);
                    }
                }
            }
    }

    private static void printNode(Node n){
        if (n.getCurr() != null) {
            System.out.println(n.getCurr());
            System.out.println("=============左边=========");
            if (n.getLeft() != null) {
                printNode(n.getLeft());
            }
            System.out.println("=============右边=========");
            if (n.getRight() != null) {
                printNode(n.getRight());
            }
        }
    }

    public static void main(String[] args){
        Integer[] a = new Integer[]{4,3,6,9,2,7};
        Node n = getNodeByArr(a);
        printNode(n);
    }
}
