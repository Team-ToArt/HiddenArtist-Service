package com.pop.backend.global.exception.response;

import com.pop.backend.global.exception.type.ServiceException;

public class ServiceErrorResponse extends ErrorResponse {

  public ServiceErrorResponse(ServiceException ex) {
    super(ex.getStatus(), ex.getErrorDetail());
  }

}