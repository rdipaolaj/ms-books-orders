package com.unir.missiact1.msbooksorders.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI globalOpenApi() {
    return new OpenAPI()
        .info(new Info()
            .title("MS Books Orders")
            .version("1.0.0")
            .description("Microservicio de gestión de carritos de compra"));
  }

  @Bean
  public GroupedOpenApi apiV1() {
    return GroupedOpenApi.builder()
        .group("v1")
        .pathsToMatch("/v1/api/**", "/health")
        .addOpenApiCustomizer(v1Info())
        .build();
  }

  @Bean
  public GroupedOpenApi apiV2() {
    return GroupedOpenApi.builder()
        .group("v2")
        .pathsToMatch("/v2/api/**")
        .addOpenApiCustomizer(v2Info())
        .build();
  }

  private OpenApiCustomizer v1Info() {
    return openApi -> openApi.setInfo(new Info()
        .title("Carrito API v1")
        .version("1.0.0-v1")
        .description("Operaciones de carrito versión 1"));
  }

  private OpenApiCustomizer v2Info() {
    return openApi -> openApi.setInfo(new Info()
        .title("Carrito API v2")
        .version("2.0.0-v2")
        .description("Operaciones de carrito versión 2"));
  }
}