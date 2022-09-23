//package org.framework.integration.sys.base.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * Created  on 2022/8/22 11:11:31
// *
// * @author zl
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
//    }
//
//    private ApiInfo getApiInfo() {
//        return new ApiInfoBuilder().title("测试标题").version("测试版本").description("测试啊啊哈哈").license("没有证书").termsOfServiceUrl("服务条款").build();
//    }
//
//}
