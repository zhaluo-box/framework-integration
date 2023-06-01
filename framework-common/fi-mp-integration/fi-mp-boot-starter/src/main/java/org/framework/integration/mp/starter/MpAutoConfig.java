package org.framework.integration.mp.starter;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.framework.integration.mp.core.mateInject.DefaultMateObjectHandler;
import org.framework.integration.mp.core.service.SecurityInfoProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created  on 2022/8/10 13:13:48
 *
 * @author wmz
 */
@Configuration
@ComponentScan(basePackageClasses = MpAutoConfig.class)
public class MpAutoConfig {

    @Autowired
    private SecurityInfoProvider securityInfoProvider;

    /**
     * 自定义公共字段自动注入
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new DefaultMateObjectHandler(securityInfoProvider);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // TODO  数据库分页定义 可以基于方言 | 指定DBType, 目前先写死
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        return interceptor;
    }
}
