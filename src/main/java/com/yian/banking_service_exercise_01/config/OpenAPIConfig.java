package com.yian.banking_service_exercise_01.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

import java.security.Security;
import java.util.List;

@SecurityScheme(
        name = "Bearer Token",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "Authorization"
)

public class OpenAPIConfig {
    @Value("${yian.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.url(devUrl);
        devServer.description("Yian URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("yian.choi05@gmail.com");
        contact.setName("Yian");
        contact.url("https://www.yian.choi05.com");

        License license = new License()
                .name("MIT License")
                .url("https://naver.com");

        Info info = new Info()
                .title("Banking Service Exercise 01")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage tutorials")
                .termsOfService("https://www.yian.choi05.com")
                .license(license);
        return new OpenAPI().info(info).servers(List.of(devServer));


    }
}
