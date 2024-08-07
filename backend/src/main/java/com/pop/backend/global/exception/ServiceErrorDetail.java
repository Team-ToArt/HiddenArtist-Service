package com.pop.backend.global.exception;

public class ServiceErrorDetail extends ErrorDetail {

  public ServiceErrorDetail(ServiceErrorCode errorCode) {
    super(errorCode);
  }

}