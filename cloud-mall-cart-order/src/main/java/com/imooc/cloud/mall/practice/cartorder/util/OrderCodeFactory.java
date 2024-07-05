package com.imooc.cloud.mall.practice.cartorder.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 描述：     生成订单No工具类
 *
 */
public class OrderCodeFactory {
    // 创建静态ThreadLocal对象simpleDateFormatThreadLocal，用于存储SimpleDateFormat对象；
    public static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>(){
        // 重写了initialValue()方法，该方法会在首次调用get()方法时被调用，为当前线程初始化一个SimpleDateFormat对象;确保了每个线程都有自己独立的SimpleDateFormat实例
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };

    private static String getDateTime() {
        // 订单量较大时，频繁生成和销毁SimpleDateFormat对象，对新能有影响
        // DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // 获取当前线程的SimpleDateFormat对象
        DateFormat sdf = simpleDateFormatThreadLocal.get();
        return sdf.format(new Date());
    }

    private static int getRandom(Long n) {
        Random random = new Random();
        // 获取5位随机数
        return (int) (random.nextDouble() * (90000)) + 10000;
    }

    public static String getOrderCode(Long userId) {
        return getDateTime() + getRandom(userId);
    }
}
