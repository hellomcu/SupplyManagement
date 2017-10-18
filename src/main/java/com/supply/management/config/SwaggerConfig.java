package com.supply.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = { "com.supply.management" })
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport
{

	@Bean
	public Docket createRestApi()
	{
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo()
	{
		return new ApiInfoBuilder().title("Welcome to SupplyManagement APIs").termsOfServiceUrl("http://localhost:8080").version("1.0.0.0").build();
	}
}
