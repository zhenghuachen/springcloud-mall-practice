package com.imooc.cloud.mall.practice.cartorder.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述： 创建一个Redis客户端
 */
@Configuration  // 配置类
public class RedissonConfig {
    @Bean
    public RedissonClient config() {
        Config config = new Config();  // 创建一个RedissonClient对象并返回
        // config.useSingleServer()方法配置Redisson连接到单个Redis服务器。
        // setAddress("redis://127.0.0.1:6379") 设置Redis服务器的地址，这里是本地Redis服务的地址，使用了默认的Redis端口6379
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        // 使用给定的配置对象config创建一个RedissonClient对象
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
