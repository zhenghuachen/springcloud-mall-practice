package com.imooc.cloud.mall.practice.user.server.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    //    @Mock
    //    HashMap myHashMap;
    @Test
    public void myFirstMock() {
        // 创建模拟对象
        HashMap mockHashMap = Mockito.mock(HashMap.class);
        // 设定模拟对象的行为,  when().thenReturn() 方法用于定义模拟对象在特定方法调用时的返回值
        Mockito.when(mockHashMap.size()).thenReturn(5);
        System.out.println(mockHashMap.size());
        mockHashMap.put(new Object(), new Object());
        System.out.println(mockHashMap.size());
    }

    @Spy
    HashMap<String, String> hashMap = new HashMap<>();
    @Test
    public void myFirstSpy() {
        HashMap<String, String> spy = Mockito.spy(hashMap);
        spy.put("1", "2");
        System.out.println(spy.size());
        spy.put("3", "4");
        Mockito.when(spy.size()).thenReturn(10);
        System.out.println(spy.size());
    }

}