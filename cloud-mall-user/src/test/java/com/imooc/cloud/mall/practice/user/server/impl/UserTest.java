package com.imooc.cloud.mall.practice.user.server.impl;


import com.imooc.cloud.mall.practice.common.exception.ImoocMallException;
import com.imooc.cloud.mall.practice.common.exception.ImoocMallExceptionEnum;
import com.imooc.cloud.mall.practice.user.model.dao.UserMapper;
import com.imooc.cloud.mall.practice.user.model.pojo.User;
import com.imooc.cloud.mall.practice.user.server.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    UserMapper userMapper;

    @Test
    @Transactional
    @Rollback(true)   // 事务回滚，默认为true
    // 普通的不使用mock的单元测试
    public void testUpdateInformation() {
        // 1. currentTimeMillis
        long start = System.currentTimeMillis();

        User user = new User();
        user.setId(9);
        user.setPersonalizedSignature("新签名");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("currentTimeMillis:" + timeElapsed);

        //2 nanoTime 返回的是纳秒
        //更新个性签名
        long start2 = System.nanoTime();
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        long finish2 = System.nanoTime();
        timeElapsed = finish2 - start2;
        System.out.println("nanoTime：" + timeElapsed / 1000000);

        if (updateCount > 1) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }

        //3 Instant
        Instant start3 = Instant.now();
        User newUser = userMapper.selectByPrimaryKey(user.getId());
        Instant finish3 = Instant.now();
        long timeElapsed3 = Duration.between(start3, finish3).toMillis();
        System.out.println(timeElapsed3);

        Assert.assertEquals(newUser.getPersonalizedSignature(),user.getPersonalizedSignature());
    }
}