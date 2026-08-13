package com.example.moviewreviewapplication.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI movieReviewAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie Review API")
                        .description("REST API for managing movies, users and reviews")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your@email.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation"));

    }

}
