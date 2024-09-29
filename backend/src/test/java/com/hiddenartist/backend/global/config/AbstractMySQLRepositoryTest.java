package com.hiddenartist.backend.global.config;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;

@DataJpaTest
@Transactional
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public abstract class AbstractMySQLRepositoryTest {

  private static final String MYSQL_IMAGE = "mysql:8.0.34";

  private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>(MYSQL_IMAGE)
      .withDatabaseName("pop")
      .withUsername("test")
      .withPassword("test")
      .withCommand("--ft_min_word_len=2", "--innodb_ft_min_token_size=2")
      .withInitScript("init.sql");

  static {
    mysqlContainer.start();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    System.out.println(mysqlContainer.getJdbcUrl());
    registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mysqlContainer::getUsername);
    registry.add("spring.datasource.password", mysqlContainer::getPassword);
  }

}
