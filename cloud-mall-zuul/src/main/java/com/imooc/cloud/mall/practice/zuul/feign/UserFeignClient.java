package com.imooc.cloud.mall.practice.zuul.feign;

import com.imooc.cloud.mall.practice.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述： UserFeignClient
 */
@FeignClient(value = "cloud-mall-user")  // 用于创建和配置Feign客户端
public interface UserFeignClient {
    // 复制调用的接口，不包括具体实现
    @PostMapping("/checkAdminRole")
    public Boolean checkAdminRole(@RequestBody User user);
}
