package org.framework.integration.fi.mg.common.properties;

import lombok.Data;
import org.framework.integration.fi.mg.common.enums.BusinessType;

import java.util.*;

/**
 * Created  on 2023/5/26 13:13:49
 *
 * @author zl
 */
@Data
//@Component
//@ConfigurationProperties(prefix = MGSysOperationLogConfigProperties.PREFIX_NAME)
public class MGSysOperationLogConfigProperties {

    public static final String PREFIX_NAME = "mg.log";

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
    private boolean openAsync = true;

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
        private List<String> propertyFilter = new ArrayList<>();

        /**
         * 白名单列表
         */
        private List<String> whitePathList = new ArrayList<>();

        /**
         * 全局无需扫描Controller类
         */
        private List<String> ignoreClass = new ArrayList<>();

        /**
         * 需要忽略的业务操作类型
         */
        private List<BusinessType> ignoreBusinessType = new ArrayList<>();

        /**
         * 基于请求头过滤
         */
        private Map<String, String> requestHeaderFilter = new HashMap<>();

        /**
         * http 请求方法
         * eg: get  post ...
         */
        private Set<String> ignoreHttpMethods = new HashSet<>();

    }

    @Data
    public static class AsyncThreadPoolProperties {

        private int coreSize = Runtime.getRuntime().availableProcessors();

        private int maximumPoolSize = Runtime.getRuntime().availableProcessors();

        private long keepAliveTime = 180;

        private int blockDequeSize = 100;

    }

}
