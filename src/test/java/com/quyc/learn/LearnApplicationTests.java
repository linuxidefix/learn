package com.quyc.learn;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnApplicationTests {

    @Test
    public void test() {
        System.out.println("\"abc\".hashCode() = " + "abc".hashCode());
        System.out.println("Math.abs(Integer.MIN_VALUE) = " + Math.abs(Integer.MIN_VALUE));
        System.out.println("Pattern.matches(\"1\\\\d{10}\", \"\") = " + Pattern.matches("1\\d{10}", ""));
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("methodName = " + methodName);
        System.out.println(("cntaobao郎沈剑".hashCode() & 0x7FFFFFFF) % 1024);
        System.out.println("numDecode(\"54eb997ac\") = " + numDecode("54eb997ac"));
        int a = 1000 / 30;
        int b = 1000 % 30;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    /**
     * 数字解码
     *
     * @param sourceCode
     * @return
     */
    public static String numDecode(String sourceCode) {
        if (StringUtils.isBlank(sourceCode)) {
            return null;
        } else {
            Long num = Long.parseLong(sourceCode.trim(), 16);
            return String.valueOf(num - 7067576061L);
        }
    }
}
