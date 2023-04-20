package org.framework.integration.sys.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created  on 2022/8/9 13:13:40
 *
 * @author zl
 */

@Configuration
public class MvcLogConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MvcLogTraceInterceptor()).addPathPatterns("/**").excludePathPatterns("/log/**").order(-10);
    }

}
