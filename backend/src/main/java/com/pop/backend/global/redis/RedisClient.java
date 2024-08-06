package com.pop.backend.global.redis;

public interface RedisClient<V> {

  String REFRESH_TOKEN_PREFIX = "RefreshToken:";

  void save(V value);

  V findBy(String key);

  void deleteBy(String key);
}