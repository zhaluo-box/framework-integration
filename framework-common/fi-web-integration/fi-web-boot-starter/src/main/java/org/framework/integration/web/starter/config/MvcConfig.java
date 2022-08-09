package org.framework.integration.web.starter.config;

import org.framework.integration.web.starter.interceptors.SecurityContextInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2022/8/9 13:13:40
 *
 * @author zl
 */

@RefreshScope
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${spring.mvc.interceptor.exclude-paths}")
    private List<String> excludePath = new ArrayList<>();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(new SecurityContextInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePath);
    }
    
}
