/*
 * @(#)SwaggerConfig.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: SwaggerConfig.java ${currentTime} Exp $
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Value("${swagger.enable:true}")
    private Boolean swaggerEnable;
	
//	@Bean
//    public Docket apiDocket() { // 带Token
//        
//        ParameterBuilder ticketPar = new ParameterBuilder();
//		List<Parameter> pars = new ArrayList<Parameter>();
//		ticketPar.name("token").description("Login User Token").modelRef(new ModelRef("string")).parameterType("header")
//                // header中的ticket参数非必填，传空也可以
//				.required(false).build();
//		pars.add(ticketPar.build()); 
//		
//		return new Docket(DocumentationType.SWAGGER_2)
//				.enable(swaggerEnable)
//				.apiInfo(apiInfo())
//				.groupName("internal")
//				.select()
//                // 指定提供接口所在的基包
//				.apis(RequestHandlerSelectors.basePackage("com.mozhisoft.databox.web.api"))
//				.build().globalOperationParameters(pars);
//
//    }
	
	@Bean
    public Docket publicApiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
        		.enable(swaggerEnable)
                .apiInfo(apiInfo())
                .select()
                // 指定提供接口所在的基包
                .apis(RequestHandlerSelectors.basePackage("${packagePrefix}.web"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API 接口说明")
                .version("1.0.0")
                .build();
    }
	
}
