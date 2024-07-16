package com.imooc.cloud.mall.practice.cartorder.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述： RabbitMQ配置类，用于配置队列、交换机和绑定关系。
 */
@Configuration
public class MQConfig {

    // 在RabbitMQ中，队列是消息的终点，生产者将消息发送到队列，消费者从队列中接收消息。
    @Bean
    public Queue queue1() {
        return new Queue("queue1");
    }  //创建了一个名为"queue1"的队列对象

    // 交换机在RabbitMQ中用于路由消息，它决定了消息从生产者到队列的路由规则。
    @Bean
    DirectExchange exchange() {
        return new DirectExchange("cloudExchange");  //  创建了一个名为"cloudExchange"的Direct类型交换机对象
    }

    @Bean
    Binding bindingExchangeMessage(Queue queue1, DirectExchange exchange) {  // 两个参数：一个Queue对象和一个DirectExchange对象
        /**
         * BindingBuilder.bind(queue1).to(exchange).with("productStock")创建了一个绑定关系。
         * bind(queue1)将队列queue1绑定到交换机exchange。
         * .with("productStock")定义了路由键为"productStock"，这表示消息通过这个路由键从交换机发送到队列。
         * 绑定关系决定了如何将消息从交换机路由到队列
         */
        return BindingBuilder.bind(queue1).to(exchange).with("productStock");
    }
}
