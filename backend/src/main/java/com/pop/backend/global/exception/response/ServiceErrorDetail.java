package com.pop.backend.global.exception.response;

import com.pop.backend.global.exception.type.ServiceErrorCode;

public class ServiceErrorDetail extends ErrorDetail {

  public ServiceErrorDetail(ServiceErrorCode errorCode) {
    super(errorCode);
  }

}