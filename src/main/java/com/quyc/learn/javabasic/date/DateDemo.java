package com.quyc.learn.javabasic.date;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author: andy
 * @create: 2019/8/14 17:29
 * @description:
 */
public class DateDemo {

    public static void main(String[] args) throws ParseException {
        test();
    }

    public static void test() throws ParseException {
        Date am8 = LocalDateTimeUtils.getCustomDate(new Date(), 8, 00, 00);
        Date date = DateUtils.parseDate("2019-08-20 08:00:01","yyyy-MM-dd HH:mm:ss");
        System.out.println("date = " + date);
        System.out.println("am8 = " + am8);
        System.out.println(am8.after(date));
        System.out.println(am8.compareTo(date));
    }


}
