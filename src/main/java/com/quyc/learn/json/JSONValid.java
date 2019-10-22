package com.quyc.learn.json;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;

/**
 * @author: andy
 * @create: 2019/4/24 11:26
 * @description: json格式校验
 */
public class JSONValid {

    public static void main(String[] args) {
        JSONObject object = new JSONObject();
        object.put("aa", Arrays.asList(1, 2, 3, 4, 5));
        object.put("ss", 1);
        object.put("vvv", "String");
        String str = object.toJSONString();
        System.out.println("str = " + str);
        boolean valid = JSONObject.isValid(str);
        boolean validObject = JSONObject.isValidObject(str);
        boolean validArray = JSONObject.isValidArray(str);
        System.out.println("valid = " + valid);
        System.out.println("validObject = " + validObject);
        System.out.println("validArray = " + validArray);
        String str1 = "aall|abc|ddd";
        System.out.println(JSONObject.isValid(str1));

    }
}
