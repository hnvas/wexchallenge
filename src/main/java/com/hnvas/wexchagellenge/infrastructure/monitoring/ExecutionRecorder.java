package com.hnvas.wexchagellenge.infrastructure.monitoring;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExecutionRecorder {

  private static final StopWatch stopWatch = new StopWatch();

  @SneakyThrows
  public <T> T executeWithTiming(Supplier<T> callback) {
    try {
      stopWatch.start();
      T result = callback.get();
      stopWatch.stop();
      log.info("Execution completed in {} ms, output: {}", stopWatch.getTotalTimeMillis(), result);
      return result;
    } catch (Exception e) {
      stopWatch.stop();
      log.error(
          "Execution failed in {} ms, output: {}", stopWatch.getTotalTimeMillis(), e.getMessage());
      throw e;
    }
  }
}
