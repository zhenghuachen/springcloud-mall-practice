package com.imooc.cloud.mall.practice.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 描述： 网关启动类
 */
@EnableZuulProxy
@EnableFeignClients
@SpringCloudApplication
@EnableRedisHttpSession  // 用于配置Spring Session支持将会话信息存储在Redis中，而不是默认的基于内存的存储方式
public class ZuulGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }
}
