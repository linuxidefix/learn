package com.quyc.learn.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: andy
 * @create: 2019/5/28 12:06
 * @description: check health
 */
@RestController
public class HealthController {

    @RequestMapping("/checkHealth")
    public String checkHealth() {
        return "success";
    }
}
