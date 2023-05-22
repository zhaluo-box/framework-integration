package org.framework.integration.example.web.mvc.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created  on 2023/5/16 16:16:39
 *
 * @author zl
 */
@Component
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AsyncInterceptor()).addPathPatterns("/**").excludePathPatterns("/log/**").order(-10);
    }
}
