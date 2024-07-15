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
    private AmqpTemplate rabbitmqTemplate;

    public void send(Integer productId, Integer stock) {
        rabbitmqTemplate.convertAndSend("cloudExchange", "productStock", productId +"," + stock);
    }

}
