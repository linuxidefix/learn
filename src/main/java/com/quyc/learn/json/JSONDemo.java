package com.quyc.learn.json;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: andy
 * @create: 2019/9/11 20:23
 * @description:
 */
public class JSONDemo {

    public static void main(String[] args) {
        JSONObject object = JSONObject.parseObject("{\"pt\":\"20190909\",\"odpsTable\":\"tmp_buyer_portrait_calculate_mansway\",\"index\":\"\"}   ");
        System.out.println("object.containsKey(\"index\") = " + object.containsKey("index"));
        System.out.println("object.getString(\"index\") = " + object.getString("index"));
    }

}
