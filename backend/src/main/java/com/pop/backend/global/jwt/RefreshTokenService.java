package com.pop.backend.global.jwt;

import com.pop.backend.global.redis.RedisClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

  private final RedisClient<RefreshToken> redisClient;

  public RefreshTokenService(@Qualifier("RefreshTokenClient") RedisClient<RefreshToken> redisClient) {
    this.redisClient = redisClient;
  }

  public void save(RefreshToken refreshToken) {
    redisClient.save(refreshToken);
  }
}