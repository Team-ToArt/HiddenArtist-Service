package com.hiddenartist.backend.global.aop;

import com.hiddenartist.backend.global.exception.type.RedisLockException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

  private static final String REDISSON_LOCK_PREFIX = "LOCK:";

  private final RedissonClient redissonClient;
  private final AopForTransaction aopForTransaction;

  @Around("@annotation(com.hiddenartist.backend.global.aop.DistributedLock)")
  public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

    String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(),
        distributedLock.key());
    RLock lock = redissonClient.getLock(key);

    try {
      boolean available = lock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
      if (!available) {
        throw new RedisLockException(ServiceErrorCode.LOCK_ACQUISITION_FAILED);
      }
      return aopForTransaction.proceed(joinPoint);
    } catch (InterruptedException e) {
      throw new RedisLockException(ServiceErrorCode.LOCK_ACQUISITION_FAILED);
    } finally {
      try {
        lock.unlock();
      } catch (IllegalMonitorStateException e) {
        log.info("Redisson Lock Already UnLock {} {}", method.getName(), key);
      }
    }
  }
}