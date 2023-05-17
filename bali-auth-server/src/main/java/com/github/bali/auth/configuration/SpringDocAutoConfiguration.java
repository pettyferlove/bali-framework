package com.github.bali.auth.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocAutoConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Bali SpringDoc API ")
                        .description("Bali")
                        .version("v1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Bali SpringDoc API ")
                        .url("https://gitee.com/union_tech_company/bali-framework.git")
                );
    }

}
