package org.framework.integration.example.web.mvc.config;

import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created  on 2023/5/16 16:16:35
 *
 * @author zl
 */
public class AsyncInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //        System.out.println("执行开始");
        //        System.out.println(Thread.currentThread().getName());
        //        System.out.println(new Date());
        //        System.out.println(handler);
        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //        System.out.println("异步执行完毕");
        //        System.out.println(Thread.currentThread().getName());
        //        System.out.println(new Date());
        AsyncHandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
