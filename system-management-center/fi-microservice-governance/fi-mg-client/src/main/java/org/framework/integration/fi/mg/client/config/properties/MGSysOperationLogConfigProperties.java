package org.framework.integration.fi.mg.client.config.properties;

import lombok.Data;
import org.framework.integration.fi.mg.common.enums.BusinessType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created  on 2023/5/26 13:13:49
 *
 * @author zl
 */
@Data
@Component
@ConfigurationProperties(prefix = "mg.log")
public class MGSysOperationLogConfigProperties {

    /**
     * 默认等于false
     */
    private boolean enabled = false;

    /**
     * 系统名称
     * eg: XXX 系统
     */
    private String systemName;

    /**
     * 模块名称
     * eg : 审批模块
     */
    private String moduleName;

    /**
     * 全局配置
     */
    private LogConfigProperties globalConfig;

    /**
     * 本地配置
     */
    private LogConfigProperties localConfig;

    /**
     * 是否开启异步配置
     */
    private boolean openAsync;

    /**
     * 异步线程配置
     */
    private AsyncThreadPoolProperties asyncThreadPoolProperties;

    /**
     * 基本上内置了一系列的filter  进行扩展使用
     */
    @Data
    public static class LogConfigProperties {

        /**
         * 请求参数与响应体 敏感属性过滤
         */
        private List<String> propertyFilter;

        /**
         * 白名单列表
         */
        private List<String> whitePathList;

        /**
         * 全局无需扫描Controller类
         */
        private List<String> ignoreClass;

        /**
         * 需要忽略的业务操作类型
         */
        private List<BusinessType> ignoreBusinessType;

        /**
         * 基于请求头过滤
         */
        private List<HeaderValue> requestHeaderFilter;

    }

    @Data
    public static class HeaderValue {

        private String key;

        private String value;

    }

    @Data
    public static class AsyncThreadPoolProperties {

        private int coreSize = Runtime.getRuntime().availableProcessors();

        private int maximumPoolSize = Runtime.getRuntime().availableProcessors();

        private long keepAliveTime = 180;

        private int blockDequeSize = 100;

    }

}
