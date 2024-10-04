package com.hiddenartist.backend.global.redis;

import com.hiddenartist.backend.domain.mentoring.persistence.LockApplicationTime;
import com.hiddenartist.backend.domain.mentoring.service.MentoringServiceWithLock;
import com.hiddenartist.backend.global.exception.type.RedisLockException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisLockFacade {

  private final RedissonClient redissonClient;
  private final MentoringServiceWithLock mentoringServiceWithLock;

  public void lockApplicationTime(LockApplicationTime lockApplicationTime) {
    String key = generateApplicationLockKey(lockApplicationTime.getTokenValue(), lockApplicationTime.getApplicationTime());
    RLock lock = redissonClient.getLock(key);
    try {
      boolean available = lock.tryLock(10, 2, TimeUnit.SECONDS);
      if (!available) {
        throw new RedisLockException(ServiceErrorCode.LOCK_ACQUISITION_FAILED);
      }
      mentoringServiceWithLock.reservationApplicationTime(lockApplicationTime);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
      throw new RedisLockException(ServiceErrorCode.LOCK_ACQUISITION_FAILED);
    } finally {
      lock.unlock();
    }
  }

  private String generateApplicationLockKey(String tokenValue, LocalDateTime applicationTime) {
    return EntityToken.MENTORING.identifyToken(tokenValue) + ":" +
        applicationTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
  }
}