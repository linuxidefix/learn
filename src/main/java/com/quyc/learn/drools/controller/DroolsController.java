package com.quyc.learn.drools.controller;

import com.quyc.learn.drools.service.DroolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: andy
 * @create: 2019/6/13 16:14
 * @description: Drools Controller
 */
@RestController
@RequestMapping("/drools")
public class DroolsController {

    @Autowired
    private DroolsService droolsService;

    @GetMapping("/showRules")
    public String showRules() {
        return droolsService.fireRule();
    }
    @GetMapping("/showRulesByJavaConfig")
    public String showRulesByJavaConfig() {
        return droolsService.fireRuleByJavaConfig();
    }

    @GetMapping("/testBoot")
    public String testBoot() {
        return "123456";
    }
}
