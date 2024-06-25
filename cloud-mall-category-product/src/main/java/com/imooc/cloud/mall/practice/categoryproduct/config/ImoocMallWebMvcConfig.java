package com.imooc.cloud.mall.practice.categoryproduct.config;

import com.imooc.cloud.mall.practice.categoryproduct.common.ProductConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 描述：     配置地址映射, 用于定制化静态资源处理的行为
 */
@Configuration
public class ImoocMallWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 当处理/images/** 路径的请求时，从指定的文件系统路径加载静态资源。具体路径由
         * ProductConstant.FILE_UPLOAD_DIR提供，使用file:前缀指示这是一个文件系统路径
         */
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + ProductConstant.FILE_UPLOAD_DIR);
        /**
         * Spring MVC在处理swagger-ui.html请求时，从类路径下的/META-INF/resources/
         * 目录加载资源。这是为了支持Swagger UI的访问
         */
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        /**
         * 处理/webjars/**路径请求时，从类路径下的/META-INF/resources/webjars/目录加载资源。
         * WebJars是一种将前端的JavaScript和CSS库打包成Java可用JAR文件的方法，这段配置确保这些资源能够被正确加载和使用
         */
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }
}
