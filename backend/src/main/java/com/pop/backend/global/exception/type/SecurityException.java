package com.pop.backend.global.exception.type;

public class SecurityException extends ServiceException {

  public SecurityException(ServiceErrorCode serviceErrorCode) {
    super(serviceErrorCode);
  }

}