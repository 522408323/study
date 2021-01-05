package com.ceshi.study.model;

import lombok.Data;

/**
 * @ClassName: Node
 * @Author: shenyafei
 * @Date: 2020/12/31
 * @Desc
 **/
@Data
public class Node {
    private Integer curr;

    private Node left;

    private Node right;
}
