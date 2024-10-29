package com.hnvas.wexchagellenge.infrastructure.client.fiscaldata;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "fiscal-data.api")
public class FiscalDataProperties {
  private String url;
  private Duration connectTimeout;
  private Duration readTimeout;
}
