package com.human.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Li JieGui
 * @date 2021/4/8 21:09
 * @Configuration 表明这是一个配置类
 * @EnableSwagger2 开启Swagger
 * 接口文档
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * webApi 组名称
     * apis 扫描的包
     * @return
     */
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.human.controller"))
                .paths(PathSelectors.any())
                .build();

    }
    /**
     * 该套 API 说明，包含作者、简介、版本、等信息
     * @return
     */
    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("企业管理系统-API文档")
                .description("本文档描述了企业管理系统接口定义")
                .version("1.0")
                .build();
    }

}
