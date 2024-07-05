package com.imooc.cloud.mall.practice.cartorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 描述： 购物车订单模块启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "com.imooc.cloud.mall.practice.cartorder.model.dao")
@EnableRedisHttpSession
@EnableFeignClients
// 用于告诉Spring在哪些包下寻找标记有@Component及其衍生注解（如@Service、@Repository、@Controller等）的类，并注册为Spring容器的Bean。
@ComponentScan({"com.imooc.cloud.mall.practice.cartorder", "com.imooc.cloud.mall.practice.common"})
public class CartOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartOrderApplication.class,args);
    }
}
