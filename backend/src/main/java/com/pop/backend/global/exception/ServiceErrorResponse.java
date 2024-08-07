package com.pop.backend.global.exception;

public class ServiceErrorResponse extends ErrorResponse {

  public ServiceErrorResponse(ServiceException ex) {
    super(ex.getStatus(), ex.getErrorDetail());
  }

}