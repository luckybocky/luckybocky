package com.project.luckybocky.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
    @Value("${front_uri}")
    private String frontUri;

    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server().url("http://localhost:8080").description("Local HTTP Server");
        Server productionServer = new Server().url(frontUri).description("Production HTTPS Server");
        return new OpenAPI()
                .servers(Arrays.asList(productionServer, localServer))
                .info(new Info()
                        .title("LuckyBocky")
                        .description("대규모 프로젝트.")
                        .version("1.0.0"));
    }
}