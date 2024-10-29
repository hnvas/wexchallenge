package com.hnvas.wexchagellenge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer(DynamicPropertyRegistry registry) {
		try (var psqlContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"))) {
			psqlContainer.withDatabaseName("challenge_db");
			psqlContainer.withLogConsumer(new Slf4jLogConsumer(log));
			registry.add("db.host", psqlContainer::getHost);
			registry.add("db.port", psqlContainer::getFirstMappedPort);
			registry.add("db.username", psqlContainer::getUsername);
			registry.add("dbpassword", psqlContainer::getPassword);
			return psqlContainer;
		}
	}

	@Bean
	MockServerContainer mockServerContainer(DynamicPropertyRegistry registry) {
		var mockServerContainer = new MockServerContainer(DockerImageName.parse("mockserver/mockserver:5.15.0"));
		mockServerContainer.withLogConsumer(new Slf4jLogConsumer(log));
		registry.add("fiscal-data.api.url", mockServerContainer::getEndpoint);
		return mockServerContainer;
	}
}
