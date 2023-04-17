package org.interviewmate.global.config.openApi;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        Server devServer = new Server();
        devServer.setDescription("dev");
        devServer.setUrl("https://interviewmate.site");

        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8082");


        return new OpenAPI()
                .info(getInfo())
                .servers(Arrays.asList(devServer, localServer));

    }

    @Bean
    public GroupedOpenApi SecurityGroup() {
        return GroupedOpenApi
                .builder()
                .group("토큰 필요 API")
                .pathsToExclude("/users/sign-up", "/auth/**")
                .addOpenApiCustomiser(buildSecurityOpenApi())
                .build();
    }

    @Bean
    public GroupedOpenApi NonSecurityGroup() {
        return GroupedOpenApi
                .builder()
                .group("토큰 불필요 API")
                .pathsToMatch("/users/sign-up", "/auth/**")
                .build();
    }

    private Info getInfo() {
        return new Info()
                .title("InterviewMate REST API")
                .description("InterviewMate API DOCS");
    }

    private OpenApiCustomiser buildSecurityOpenApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("bearer");

        return OpenApi -> OpenApi
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .getComponents().addSecuritySchemes("Authorization", securityScheme);
    }

}

