package com.hiddenartist.backend.global.exception.response;

import com.hiddenartist.backend.global.exception.type.ServiceException;

public class ServiceErrorResponse extends ErrorResponse {

  public ServiceErrorResponse(ServiceException ex) {
    super(ex.getStatus(), ex.getErrorDetail());
  }

}