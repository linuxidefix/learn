package com.quyc.learn;

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
    }

}
