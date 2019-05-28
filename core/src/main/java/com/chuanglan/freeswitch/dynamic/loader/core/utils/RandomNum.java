package com.chuanglan.freeswitch.dynamic.loader.core.utils;

public class RandomNum {

    /**
     * 获取随机长整型
     *
     * @param figure 位数
     * @return
     */
    public static Long getRandomLongNum(Integer figure) {
        return new Double((Math.random() * 9 + 1) * Math.pow(10, figure - 1)).longValue();
    }

    /**
     * 获取随机整型
     *
     * @param figure 位数
     * @return
     */
    public static Integer getRandomIntegerNum(Integer figure) {
        return new Double((Math.random() * 9 + 1) * Math.pow(10, figure - 1)).intValue();
    }

    public static void main(String[] args) {
        System.out.println(getRandomLongNum(6).toString());
    }
}
