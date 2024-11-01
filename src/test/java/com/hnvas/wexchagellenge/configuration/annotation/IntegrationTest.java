package com.hnvas.wexchagellenge.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.SqlConfig;

import com.hnvas.wexchagellenge.configuration.FreshDbExtension;
import com.hnvas.wexchagellenge.configuration.TestcontainersConfiguration;

@Import({TestcontainersConfiguration.class})
@SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ExtendWith(FreshDbExtension.AfterTestMethod.class)
@Tag("integrationTest")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationTest {}
