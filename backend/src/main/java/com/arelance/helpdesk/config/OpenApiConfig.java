package com.arelance.helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Documentacion de la API en Swagger: cabecera y autenticacion con JWT
 * (boton "Authorize") - Jhon (Backend)
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI helpdeskOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Arelance HelpDesk API")
                        .version("1.0.0")
                        .description("API REST para la gestión de tickets de soporte técnico "
                                + "y el control del cumplimiento de SLA.")
                        .contact(new Contact().name("Equipo HelpDesk")))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
