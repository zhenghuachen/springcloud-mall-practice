package com.imooc.cloud.mall.practice.cartorder.config;

import com.imooc.cloud.mall.practice.cartorder.model.pojo.Order;
import com.imooc.cloud.mall.practice.cartorder.service.OrderService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述： 定时任务类
 */
@Component
public class JobConfiguration {

//    private final Logger log = LoggerFactory.getLogger(JobConfiguration.class);

//    @Scheduled(cron = "0/3 * * * * ?")
//    public void process() throws InterruptedException{
//        log.info("start...");
//        Thread.sleep(2000);
//        log.info("stop");
//    }

//    @Scheduled(fixedRate = 3000)
//    public void process() throws InterruptedException{
//        log.info("start...");
//        Thread.sleep(2000);
//        log.info("stop");
//    }

    @Autowired
    OrderService orderService;

    @Autowired
    RedissonClient redissonClient;
    @Scheduled(cron = "0 0/5 * * * ?")   // 每5分钟执行一次
    public void cancelUnpaidOrders() {
        RLock redissonLock = redissonClient.getLock("redissonLock");
        boolean b = redissonLock.tryLock();
        if (b) {
            try {
                System.out.println("redisson锁+1");
                List<Order> unpaidOrders = orderService.getUnpaidOrders();
                for (int i = 0; i < unpaidOrders.size(); i++) {
                    Order order = unpaidOrders.get(i);
                    orderService.cancel(order.getOrderNo(), true);
                }
            } finally {
                redissonLock.unlock();
                System.out.println("redisson锁已释放");
            }
        } else {
            System.out.println("redisson锁未获取到");
        }
    }
}
