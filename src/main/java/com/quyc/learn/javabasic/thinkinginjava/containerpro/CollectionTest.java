package com.quyc.learn.javabasic.thinkinginjava.containerpro;

import com.quyc.learn.javabasic.thinkinginjava.net.mindview.atunit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 集合类测试
 * @author quyuanchao
 * @date 2018/9/2 21:36
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class CollectionTest {

    @Test
    public void testFill() {
        List<String> list = new ArrayList<>(Collections.nCopies(4,"helloWorld"));
        System.out.println("list = " + list);
        Collections.fill(list,"hello world");
        System.out.println("list = " + list);

    }



}
