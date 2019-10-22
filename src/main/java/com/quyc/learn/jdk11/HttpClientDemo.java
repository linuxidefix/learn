package com.quyc.learn.jdk11;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author: andy
 * @create: 2019/5/9 16:11
 * @description: 标准 HTTP Client 升级
 * <p>
 * 新版 Java 中，Http Client 的包名由 jdk.incubator.http 改为 java.net.http，
 * 该 API 通过 CompleteableFutures 提供非阻塞请求和响应语义，
 * 可以联合使用以触发相应的动作，并且 RX Flow 的概念也在 Java 11 中得到了实现。
 * 现在，在用户层请求发布者和响应发布者与底层套接字之间追踪数据流更容易了。
 * 这降低了复杂性，并最大程度上提高了 HTTP / 1 和 HTTP / 2 之间的重用的可能性。
 */
public class HttpClientDemo {

    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://admin.qa.weierai.com:8087/qnApi/qnShopList.html?_=1557381402264"))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }
}
