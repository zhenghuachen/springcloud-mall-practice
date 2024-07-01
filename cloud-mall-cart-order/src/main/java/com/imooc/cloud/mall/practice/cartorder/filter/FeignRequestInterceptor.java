package com.imooc.cloud.mall.practice.cartorder.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 描述：     Feign请求拦截器
 * 使用Feign客户端的请求拦截器来传递当前请求的所有头部信息到远程服务
 */
@EnableFeignClients  // 启用Feign客户端的自动配置
@Configuration  // 配置类，Spring会在启动时加载它
public class FeignRequestInterceptor implements RequestInterceptor {  // RequestInterceptor是Feign提供的用于修改请求的拦截器接口。

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // RequestContextHolder.getRequestAttributes()用于获取当前线程的请求属性。
        // 在Spring Web应用中，RequestContextHolder可以让我们访问当前请求的相关信息。
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        // ServletRequestAttributes 将 requestAttributes 转换为 HttpServletRequest，从而获取请求对象
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // request.getHeaderNames() 获取所有请求头的名称
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {  // 遍历当前请求的所有头部名称 (headerNames)
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);
                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    // 使用requestTemplate.header(name, value)将请求头信息设置到Feign的请求模板中，这样在发出的远程请求中就包含了当前请求的所有头部信息
                    requestTemplate.header(name, value);
                }
            }
        }
    }
}
