package com.hiddenartist.backend.global.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public class TestContainerSetup {

  private static final String MYSQL_IMAGE = "mysql:8.0.34";
  private static final MySQLContainer<?> mysqlContainer;

  static {
    mysqlContainer = new MySQLContainer<>(MYSQL_IMAGE)
        .withDatabaseName("pop")
        .withUsername("test")
        .withPassword("test");
    mysqlContainer.start();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mysqlContainer::getUsername);
    registry.add("spring.datasource.password", mysqlContainer::getPassword);
  }

}
