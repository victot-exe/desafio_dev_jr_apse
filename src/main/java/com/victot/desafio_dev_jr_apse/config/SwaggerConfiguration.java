package com.victot.desafio_dev_jr_apse.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Products API",
        description = "API Rest dos produtos",
        version = "1.0",
        contact = @Contact(name = "Victor", url = "https://github.com/victot-exe", email = "victorfarian@gmail.com")
))
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi customOpenAPI() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("com.victot.desafio_dev_jr_apse.controller")
                .pathsToMatch("/**")
                .build();
    }
}
