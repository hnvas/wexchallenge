package com.hnvas.wexchagellenge.infrastructure.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hnvas.wexchagellenge.configuration.annotation.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

@UnitTest
class ExecutionRecorderTest {

  private ExecutionRecorder executionRecorder;

  @BeforeEach
  void setUp() {
    executionRecorder = new ExecutionRecorder();
  }

  @Test
  void testExecuteWithTimingSuccess() {
    Supplier<String> supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Success");

    String result = executionRecorder.executeWithTiming(supplier);

    assertEquals("Success", result);
    verify(supplier, times(1)).get();
  }

  @Test
  void testExecuteWithTimingFailure() {
    Supplier<String> supplier = mock(Supplier.class);
    when(supplier.get()).thenThrow(new RuntimeException("Failure"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      executionRecorder.executeWithTiming(supplier);
    });

    assertEquals("Failure", exception.getMessage());
    verify(supplier, times(1)).get();
  }
}