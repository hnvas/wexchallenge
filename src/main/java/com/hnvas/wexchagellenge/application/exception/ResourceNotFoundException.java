package com.hnvas.wexchagellenge.application.exception;

public class ResourceNotFoundException extends RuntimeException {

  private static final String RESOURCE_NOT_FOUND_MESSAGE = "Entity %s was not found";

  private ResourceNotFoundException(String entityClassName) {
    super(RESOURCE_NOT_FOUND_MESSAGE.formatted(entityClassName));
  }

  public static ResourceNotFoundException of(String entityClassName) {
    return new ResourceNotFoundException(entityClassName);
  }
}
