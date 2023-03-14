package org.interviewmate.global.config.openApi;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(getInfo());

    }
    private Info getInfo() {
        return new Info()
                .title("InterviewMate REST API")
                .description("InterviewMate API DOCS");
    }

}

