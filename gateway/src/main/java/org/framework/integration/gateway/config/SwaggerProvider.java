package org.framework.integration.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2022/8/22 11:11:31
 *
 * @author zl
 */
@Configuration
public class SwaggerProvider implements SwaggerResourcesProvider, WebFluxConfigurer {

    /**
     * Swagger2默认的url后缀
     */
    public static final String SWAGGER2URL = "/v2/api-docs";

    /**
     * 网关路由
     */
    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resourceList = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        // 获取网关中配置的route
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        gatewayProperties.getRoutes()
                         .stream()
                         .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                         .forEach(routeDefinition -> routeDefinition.getPredicates()
                                                                    .stream()
                                                                    //        - id: sys-base
                                                                    //          uri: lb://sys-base
                                                                    //          predicates:
                                                                    //            - Path=/sys/**
                                                                    //          filters:
                                                                    //            - StripPrefix=1
                                                                    // Path 参数
                                                                    .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                                                                    .forEach(predicateDefinition -> resourceList.add(swaggerResource(routeDefinition.getId(),
                                                                                                                                     predicateDefinition.getArgs()
                                                                                                                                                        .get(NameUtils.GENERATED_NAME_PREFIX
                                                                                                                                                             + "0")
                                                                                                                                                        .replace("/**",
                                                                                                                                                                 SWAGGER2URL)))));
        return resourceList;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    }
}
