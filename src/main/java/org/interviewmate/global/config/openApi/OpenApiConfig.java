package org.interviewmate.global.config.openApi;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class        OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        Server devServer = new Server();
        devServer.setDescription("dev");
        devServer.setUrl("https://interviewmate.site");

        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8082");

        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("bearer");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Authorization");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Authorization", securityScheme))
                .security(Arrays.asList(securityRequirement))
                .info(getInfo())
                .servers(Arrays.asList(devServer, localServer));

    }
    private Info getInfo() {
        return new Info()
                .title("InterviewMate REST API")
                .description("InterviewMate API DOCS");
    }
}

