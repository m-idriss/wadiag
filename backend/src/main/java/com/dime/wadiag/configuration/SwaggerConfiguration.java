package com.dime.wadiag.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public OpenAPI customOpenAPI() {
    final String securitySchemeName = "Wadiag";

    return new OpenAPI()
        .info(apiInfo())
        .externalDocs(apiExternalDocs())
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(new Components()
            .addSecuritySchemes(securitySchemeName,
                new SecurityScheme()
                    .description("For test use : " + securitySchemeName)
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
  }

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("public-apis")
        .pathsToMatch("/**")
        .pathsToExclude("/actuator/**")
        .build();
  }

  @Bean
  public GroupedOpenApi actuatorApi() {
    return GroupedOpenApi.builder()
        .group("actuators")
        .pathsToMatch("/actuator/**")
        .build();
  }

  private Info apiInfo() {
    return new Info()
        .title("Wadiag")
        .description(
            "This is a API Wadiag game Server based on the OpenAPI 3.0 specification. Click [here](https://github.com/m-idriss/wadiag). You can load via the `Code > Clone`, enjoy.")
        .version("1.0")
        .contact(apiContact())
        .license(apiLicence());
  }

  private Contact apiContact() {
    return new Contact()
        .name("Idriss")
        .email("contact@3dime.com")
        .url("https://github.com/m-idriss");
  }

  private License apiLicence() {
    return new License()
        .name("MIT Licence")
        .url("https://opensource.org/licenses/mit-license.php");
  }

  private ExternalDocumentation apiExternalDocs() {
    return new ExternalDocumentation()
        .description("Test Documentation")
        .url("https://github.com/m-idriss/wadiag?tab=readme-ov-file#readme");
  }

}
