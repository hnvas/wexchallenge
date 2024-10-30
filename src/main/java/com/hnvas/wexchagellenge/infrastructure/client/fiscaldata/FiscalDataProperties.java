package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "fiscal-data.api")
public class FiscalDataProperties {
  private String url;
  private Duration connectTimeout;
  private Duration readTimeout;
}
