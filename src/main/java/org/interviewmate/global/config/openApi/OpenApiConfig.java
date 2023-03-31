package org.interviewmate.global.config.openApi;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
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
    private Info getInfo() {
        return new Info()
                .title("InterviewMate REST API")
                .description("InterviewMate API DOCS");
    }

}

