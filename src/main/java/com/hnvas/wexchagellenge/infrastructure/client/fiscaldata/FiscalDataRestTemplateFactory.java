package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FiscalDataRestTemplateFactory {

  private final RestTemplateBuilder builder;
  private final FiscalDataProperties properties;
  private final ObjectMapper jsonObjectMapper;

  public RestTemplate fiscalDataRestTemplate() {
    return builder
        .rootUri(properties.getUrl())
        .setConnectTimeout(properties.getConnectTimeout())
        .setReadTimeout(properties.getReadTimeout())
        .messageConverters(new MappingJackson2HttpMessageConverter(jsonObjectMapper))
        .build();
  }
}
