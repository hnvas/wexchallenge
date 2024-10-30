package com.hnvas.wexchagellenge.infrastructure.client;

import java.util.Objects;

import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RetryableApiClient {

  protected final RetryTemplate retryTemplate;
  protected final RestTemplate restTemplate;

  protected final <T> T getForObject(String requestUrl, Class<T> responseType) {
    return Objects.requireNonNull(
        retryTemplate.execute(context -> restTemplate.getForObject(requestUrl, responseType)));
  }
}
