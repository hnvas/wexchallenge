package com.hnvas.wexchagellenge.infrastructure.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

  private static final ObjectMapper JSON_OBJECT_MAPPER_INSTANCE =
      JsonMapper.builder()
          .serializationInclusion(JsonInclude.Include.NON_NULL)
          .addModule(new JavaTimeModule())
          .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .build();

  @Bean
  public ObjectMapper jsonObjectMapper() {
    return JSON_OBJECT_MAPPER_INSTANCE;
  }
}
