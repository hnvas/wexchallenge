package com.hnvas.wexchagellenge.configuration;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.SneakyThrows;

public class FreshDbExtension {

  public static class AfterTestClass implements AfterAllCallback {

    @Override
    public void afterAll(ExtensionContext extensionContext) {
      cleanDb(extensionContext);
    }
  }

  public static class AfterTestMethod implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext extensionContext) {
      cleanDb(extensionContext);
    }
  }

  @SneakyThrows
  private static void cleanDb(ExtensionContext extensionContext) {
    ApplicationContext applicationContext = SpringExtension.getApplicationContext(extensionContext);
    Resource resource = applicationContext.getResource("classpath:db/cleanup.sql");
    DataSource bean = applicationContext.getBean(DataSource.class);
    try (Connection conn = bean.getConnection()) {
      ScriptUtils.executeSqlScript(conn, resource);
    }
  }
}
