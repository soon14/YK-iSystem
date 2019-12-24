package com.yksystem.isystem.test;

import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 16:23
 **/
public class test {

    public static void main(String[] args) {
        List<String> list1 = Lists.newArrayList("1", "2", "3");
        List<String> list2 = Lists.newArrayList("4", "2", "3");

        list1.addAll(list2);
        System.out.println(list1);
    }
}