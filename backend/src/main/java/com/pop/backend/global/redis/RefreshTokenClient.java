package com.pop.backend.global.redis;

import com.pop.backend.global.jwt.RefreshToken;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("RefreshTokenClient")
@RequiredArgsConstructor
public class RefreshTokenClient implements RedisClient<RefreshToken> {

  private static final long TTL = 7L;
  private final RedisTemplate<String, RefreshToken> redis;

  @Override
  public void save(RefreshToken value) {
    String key = REFRESH_TOKEN_PREFIX + value.getEmail();
    redis.opsForValue().set(key, value, Duration.ofDays(TTL));
  }

  @Override
  public RefreshToken findBy(String key) {
    return Optional.ofNullable(redis.opsForValue().get(REFRESH_TOKEN_PREFIX + key)).orElseThrow();
  }

  @Override
  public void deleteBy(String key) {
    redis.delete(REFRESH_TOKEN_PREFIX + key);
  }
}
