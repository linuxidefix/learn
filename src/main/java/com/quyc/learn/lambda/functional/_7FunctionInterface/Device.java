package com.quyc.learn.lambda.functional._7FunctionInterface;

import lombok.Data;

/**
 * @author: andy
 * @create: 2019/5/5 19:57
 * @description: 设备
 */
@Data
public class Device {
    private int id;
    private volatile int checkStatus = 0;

    public Device(int id) {
        this.id = id;
    }

    public void checkIn() {
        checkStatus = 1;
        System.out.println("device " + id + " check in, checkStatus = " + checkStatus);
    }

    public void checkOut() {
        checkStatus = 0;
        System.out.println("device " + id + " check out, checkStatus = " + checkStatus);
    }

}
