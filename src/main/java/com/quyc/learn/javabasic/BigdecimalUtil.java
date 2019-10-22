package com.quyc.learn.javabasic;

import java.math.BigDecimal;

/**
 * @author: andy
 * @create: 2019/7/16 17:33
 * @description:
 */
public class BigdecimalUtil {
    public static BigDecimal fen2Yuan(Integer fen) {
        int little = fen % 100;
        int big = fen / 100;
        return new BigDecimal(big + "." + little);
    }


    public static void main(String[] args) {
        System.out.println(fen2Yuan(84));
    }
}
