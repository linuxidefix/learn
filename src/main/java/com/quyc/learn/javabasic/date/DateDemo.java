package com.quyc.learn.javabasic.date;

import org.apache.commons.lang3.time.DateUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author: andy
 * @create: 2019/8/14 17:29
 * @description:
 */
public class DateDemo {

    public static void main(String[] args) throws ParseException {
        localTimeDemo();
//        testLocalDate();
    }

    public static void localTimeDemo() throws ParseException {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now.getLong(ChronoField.MILLI_OF_SECOND) = " + now.getLong(ChronoField.MILLI_OF_SECOND));
        System.out.println("now.getLong(ChronoField.MILLI_OF_DAY) = " + now.getLong(ChronoField.MILLI_OF_DAY));
        System.out.println("now.getLong(ChronoField.MICRO_OF_SECOND) = " + now.getLong(ChronoField.MICRO_OF_SECOND));
        System.out.println("now.getLong(ChronoField.MICRO_OF_DAY) = " + now.getLong(ChronoField.MICRO_OF_DAY));
        System.out.println("now.getLong(ChronoField.DAY_OF_WEEK) = " + now.getLong(ChronoField.DAY_OF_WEEK));
        System.out.println("now.getLong(ChronoField.DAY_OF_MONTH) = " + now.getLong(ChronoField.DAY_OF_MONTH));
        System.out.println("now.getLong(ChronoField.DAY_OF_YEAR) = " + now.getLong(ChronoField.DAY_OF_YEAR));
        System.out.println("now.getSecond() = " + now.getSecond());
        Date date = new Date();
        System.out.println("date.getTime() = " + date.getTime());
        Duration between = Duration.between(now, now.plus(12, ChronoUnit.SECONDS));
        long l = between.get(ChronoUnit.SECONDS);
        System.out.println("l = " + l);
        LocalTime localTime = LocalTime.of(8, 10, 00);
        System.out.println("localTime = " + localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        LocalTime nowTime = LocalTime.now();
        Date date1 = DateUtils.parseDate("2019-11-03 15:00:00", "yyyy-MM-dd HH:mm:ss");
        LocalTime toLocalTime = LocalDateTimeUtils.convertDateToLDT(date1).toLocalTime();
        System.out.println("nowTime.isAfter(toLocalTime) = " + nowTime.isAfter(toLocalTime));
    }

    public static void testLocalDate() throws ParseException {
        Date date = DateUtils.parseDate("2019-08-20 08:00:01","yyyy-MM-dd HH:mm:ss");
        Date date2 = DateUtils.parseDate("2019-08-20 08:00:02","yyyy-MM-dd HH:mm:ss");
        LocalDate localDateStart = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateEnd = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(date.compareTo(date2));
        System.out.println(localDateStart.compareTo(localDateEnd));
        System.out.println("StandardCharsets.UTF_8.displayName() = " + StandardCharsets.UTF_8.displayName());
        System.out.println("StandardCharsets.UTF_8.name() = " + StandardCharsets.UTF_8.name());
        System.out.println("StandardCharsets.UTF_8 = " + StandardCharsets.UTF_8);
    }

}
