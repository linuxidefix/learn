package com.quyc.learn.javabasic.designpattern.stracture.proxy;

import java.net.URL;

/**
 * e.g. java.lang.reflect.Proxy
 *       RMI
 *
 * Created by quyuanchao on 2019/2/16 23:56.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ImageViewer {

    public static void main(String[] args) throws Exception {
        String image = "http://image.jpg";
        URL url = new URL(image);
        HighResolutionImage highResolutionImage = new HighResolutionImage(url);
        ImageProxy imageProxy = new ImageProxy(highResolutionImage);
        imageProxy.showImage();
    }
}
