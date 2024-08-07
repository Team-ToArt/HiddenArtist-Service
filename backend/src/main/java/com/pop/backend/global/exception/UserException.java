package com.pop.backend.global.exception;

public class UserException extends ServiceException {

  public UserException(ServiceErrorCode serviceErrorCode) {
    super(serviceErrorCode);
  }
}