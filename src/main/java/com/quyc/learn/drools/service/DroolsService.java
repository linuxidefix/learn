package com.quyc.learn.drools.service;

import com.quyc.learn.drools.domain.Message;
import com.quyc.learn.drools.tool.KieUtils;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

/**
 * @author: andy
 * @create: 2019/6/13 16:13
 * @description: Drools Service
 */
@Service
public class DroolsService {

    public String fireRule() {
        // load up the knowledge base
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules");
        // go !
        Message message = new Message();
        message.setMessage("Hello World");
        message.setStatus(Message.HELLO);
        // 插入
        kSession.insert(message);
        // 执行规则
        kSession.fireAllRules();
        kSession.dispose();
        return message.getMessage();
    }

    public String fireRuleByJavaConfig() {
        Message message = new Message();
        message.setMessage("Hello World");
        message.setStatus(Message.HELLO);
        KieSession kieSession = KieUtils.getKieSession();
        kieSession.insert(message);
        kieSession.fireAllRules();
//        kieSession.dispose();
        return message.getMessage();
    }

}
