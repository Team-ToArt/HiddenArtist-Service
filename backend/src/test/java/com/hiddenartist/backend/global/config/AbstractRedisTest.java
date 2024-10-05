package com.hiddenartist.backend.global.config;

import com.hiddenartist.backend.global.redis.LockApplicationTimeClient;
import com.hiddenartist.backend.global.redis.RefreshTokenClient;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;

@DataRedisTest
@Import({TestRedisConfig.class, LockApplicationTimeClient.class, RefreshTokenClient.class})
public abstract class AbstractRedisTest {

  private static final GenericContainer<?> redisContainer = new GenericContainer<>("redis:latest");

  static {
    redisContainer.start();
  }

  @DynamicPropertySource
  static void configureProperty(DynamicPropertyRegistry registry) {
    registry.add("spring.data.redis.host", redisContainer::getHost);
    registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
  }

}