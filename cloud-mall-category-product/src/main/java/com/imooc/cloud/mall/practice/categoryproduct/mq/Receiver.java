package com.imooc.cloud.mall.practice.categoryproduct.mq;

import com.imooc.cloud.mall.practice.categoryproduct.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述： 消费者1
 */
@Component
@RabbitListener(queues = "queue1")  // 用于声明一个方法或类是RabbitMQ消息监听器，指定监听的队列名称为"queue1"。当有消息到达指定队列时，与该注解关联的方法将被自动调用来处理消息。
public class Receiver {

    @Autowired
    ProductService productService;

    @RabbitHandler  // 指示Spring当前方法用来处理RabbitMQ接收到的消息
    public void process(String message) {
        System.out.println("收到消息" + message);
        String[] split = message.split(",");
        productService.updateStock(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }
}
