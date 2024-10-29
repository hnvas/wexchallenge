package com.hnvas.wexchagellenge.infrastructure.client;

import lombok.RequiredArgsConstructor;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
public abstract class RetryableApiClient {

  protected final RetryTemplate retryTemplate;
  protected final RestTemplate restTemplate;

  protected final <T> T getForObject(String requestUrl, Class<T> responseType) {
    return Objects.requireNonNull(
        retryTemplate.execute(
            context -> restTemplate.getForObject(requestUrl, responseType)));
  }
}
