package com.pop.backend.global.exception.type;

public class EntityException extends ServiceException {

  public EntityException(ServiceErrorCode serviceErrorCode) {
    super(serviceErrorCode);
  }
}