package com.ejs.algaworksCurso.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("ejs.com.spring.swagger.resources"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(this.apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API do curso Realizado na AlgaWorks",
				"Esta API é utilizada", "Versão 1.0",
				"Curso Spring",
				new Contact("Edson Jose de Souza", "AlgaWorks", "nosdejs32@gmail.com"),
				"Permitido uso para estudantes", "teste", 
				Collections.emptyList() 
		);
	}
}
