package com.pop.backend.global.exception.type;

public class UserException extends ServiceException {

  public UserException(ServiceErrorCode serviceErrorCode) {
    super(serviceErrorCode);
  }
}