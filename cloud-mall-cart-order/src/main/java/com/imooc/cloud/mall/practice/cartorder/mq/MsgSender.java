package com.imooc.cloud.mall.practice.cartorder.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述： 发送消息
 */
@Component
public class MsgSender {
    @Autowired
    private AmqpTemplate rabbitmqTemplate;  // 自动注入AmqpTemplate类型的bean。AmqpTemplate是RabbitMQ提供的一个接口，用于在消息中间件中发送和接收消息。

    public void send(Integer productId, Integer stock) {
        /**
         * rabbitmqTemplate.convertAndSend()方法用于将消息发送到指定的交换机和路由键。
         * "cloudExchange"是目标交换机的名称，这里指定了之前配置的名为"cloudExchange"的Direct交换机。
         * "productStock"是消息的路由键，用于将消息路由到队列。在之前的配置中，我们定义了一条从"cloudExchange"到"queue1"的绑定，使用了路由键"productStock"。
         * productId + "," + stock 是要发送的消息体，这里将产品ID和库存信息组合成一个字符串发送。
         * convertAndSend()方法负责将对象转换为消息并发送到指定的交换机和路由键。
         */
        rabbitmqTemplate.convertAndSend("cloudExchange", "productStock", productId +"," + stock);
    }

}
