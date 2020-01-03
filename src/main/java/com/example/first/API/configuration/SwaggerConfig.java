package com.example.first.API.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.first.API"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/Produits.*"))
                .build().apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {

        return new ApiInfo(

                "Contact Application API",

                "This is a sample Spring Boot RESTful service using SpringBoot + Swagger 2",

                "V1",

                "urn:tos",

                new Contact("Pierre", "https://www.simplon.com", "hello@pierre.com"),

                "CC BY-SA 3.0",

                "https://creativecommons.org/licenses/by-sa/3.0/",

                Collections.emptyList()

        );

    }
}
