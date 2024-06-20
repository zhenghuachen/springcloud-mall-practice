package com.imooc.cloud.mall.practice.zuul.filter;

import com.imooc.cloud.mall.practice.common.common.Constant;
import com.imooc.cloud.mall.practice.user.model.pojo.User;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 描述： 用户鉴权过滤器
 */
@Component
public class UserFilter extends ZuulFilter {
    // FilterConstants.PRE_TYPE，表示这是一个前置过滤器，将在请求被路由之前执行
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
    // 指定过滤器的执行顺序，数值越小优先级越高
    @Override
    public int filterOrder() {
        return 0;
    }
    // 根据请求的URI判断是否需要执行过滤逻辑
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        if (requestURI.contains("images") || requestURI.contains("pay")) {
            return false;
        }
        // 请求的URI包含"cart"或"order"，则返回 true，执行过滤器
        if (requestURI.contains("cart") || requestURI.contains("order")) {
            return true;
        }
        return false;
    }
    // 实际的过滤逻辑
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        // 从当前请求中获取HttpSession，进而获取用户信息
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseBody("{\n"
                    + "    \"status\": 10007,\n"
                    + "    \"msg\": \"NEED_LOGIN\",\n"
                    + "    \"data\": null\n"
                    + "}");
            currentContext.setResponseStatusCode(200);
        }
        
        return null;
    }
}
