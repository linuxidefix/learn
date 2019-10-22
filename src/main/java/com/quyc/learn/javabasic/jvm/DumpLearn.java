package com.quyc.learn.javabasic.jvm;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: andy
 * @create: 2019/6/18 13:51
 * @description: dump learn
 */
@Slf4j
@Component
public class DumpLearn {

//    @PostConstruct
    public void makeOOM() {
        String aa = "aaaaaaaaaaaaaaaaaaaaaa";
        List<String> list = Lists.newArrayList();
        int i = 0;
        while (i++<100000) {
            list.add(aa);
            log.info("add aa={}", aa);
        }
    }

}
