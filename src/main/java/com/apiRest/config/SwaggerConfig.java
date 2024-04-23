package com.apiRest.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.apiRest.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo())
				;
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Spring Boot JPA + H2",
				"Desarrollo de una aplicación que exponga una API RESTful para la creación de usuarios y manejo de JWT como token.",
				"1.0",
				"https://github.com/fhernandez204/apiRest",
				new Contact("Francisco", "https://github.com/fhernandez204/apiRest", "fhernandez204@gmail.com"),
				"LICENSE",
				"LICENSE URL",
				Collections.emptyList()
		);
	}
	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder()
				.title("")
				.description("")
				.version("")
				.version("2.0")
				.contact(new Contact(null, null, "this.developer"))
				.build();
	}

}
