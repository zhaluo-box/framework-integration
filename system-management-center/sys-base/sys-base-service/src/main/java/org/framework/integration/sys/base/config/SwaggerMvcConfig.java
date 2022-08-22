package org.framework.integration.sys.base.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created  on 2022/8/22 11:11:34
 *
 * @author zl
 */
//@Configuration
//@EnableSwagger2
public class SwaggerMvcConfig implements WebMvcConfigurer {

    /**
     * 注册swagger ui
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
