package com.pop.backend.global.aop;

import com.pop.backend.global.exception.type.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {

  @Pointcut("execution(* com.pop.backend..*(..)) && !within(com.pop.backend.global.security.filter..*)")
  private void exceptionLoggingPointcut() {
  }

  @AfterThrowing(pointcut = "exceptionLoggingPointcut()", throwing = "ex")
  public void loggingServiceException(ServiceException ex) {
    log.error("{} \n {}", ex.getErrorDetail().getMessage(), ex.getStackTrace()[0]);
  }

}