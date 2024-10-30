package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FiscalDataRestTemplateFactory {

  private static final ObjectMapper JSON_OBJECT_MAPPER_INSTANCE =
      JsonMapper.builder()
          .serializationInclusion(JsonInclude.Include.NON_NULL)
          .addModule(new JavaTimeModule())
          .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .build();

  private final RestTemplateBuilder builder;
  private final FiscalDataProperties properties;

  public RestTemplate fiscalDataRestTemplate() {
    return builder
        .rootUri(properties.getUrl())
        .setConnectTimeout(properties.getConnectTimeout())
        .setReadTimeout(properties.getReadTimeout())
        .messageConverters(new MappingJackson2HttpMessageConverter(JSON_OBJECT_MAPPER_INSTANCE))
        .build();
  }
}
