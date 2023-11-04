package com.dime.wadiag.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  @Bean
  OpenAPI usersMicroserviceOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("Wadiag")
            .description("Wadiag game")
            .version("1.0"));
  }

}
