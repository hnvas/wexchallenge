package com.hnvas.wexchagellenge.configuration.annotation;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag("unitTest")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnitTest {
}