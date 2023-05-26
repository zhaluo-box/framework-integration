package org.framework.integration.fi.mg.client.config;

import org.framework.integration.fi.mg.client.bootstrap.OperationLogInitBootstrapService;
import org.framework.integration.fi.mg.client.config.feign.SysOperationLogFeignInterceptor;
import org.framework.integration.fi.mg.client.config.properties.MGSysOperationLogConfigProperties;
import org.framework.integration.fi.mg.client.config.web.LogResponseDataAdvice;
import org.framework.integration.fi.mg.client.config.web.SysOperationLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created  on 2023/5/26 13:13:46
 *
 * @author zl
 */
@Configuration
@ConditionalOnProperty(prefix = "mg.log", name = "enabled", havingValue = "true")
@ConditionalOnBean(value = MGSysOperationLogConfigProperties.class)
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private MGSysOperationLogConfigProperties mgSysOperationLogConfigProperties;

    @Autowired
    private SysOperationLogInterceptor sysOperationLogInterceptor;

    @Bean
    public OperationLogInitBootstrapService operationLogAuditBootstrapService() {
        return new OperationLogInitBootstrapService();
    }

    @Bean
    public SysOperationLogFeignInterceptor sysOperationLogFeignInterceptor() {
        return new SysOperationLogFeignInterceptor();
    }

    @Bean
    public ResponseBodyAdvice<Object> responseBodyAdvice() {
        return new LogResponseDataAdvice<>();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysOperationLogInterceptor).addPathPatterns("/**").excludePathPatterns(getWhitePaths()).order(Ordered.LOWEST_PRECEDENCE);
    }

    private List<String> getWhitePaths() {
        List<String> result = new ArrayList<>();
        MGSysOperationLogConfigProperties.LogConfigProperties configProperties;
        if (Objects.nonNull(configProperties = mgSysOperationLogConfigProperties.getGlobalConfig())) {
            result.addAll(configProperties.getWhitePathList());
        }
        if (Objects.nonNull(configProperties = mgSysOperationLogConfigProperties.getLocalConfig())) {
            result.addAll(configProperties.getWhitePathList());
        }
        return result;
    }

}
