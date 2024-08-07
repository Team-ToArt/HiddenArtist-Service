package com.pop.backend.global.exception;

import com.pop.backend.global.exception.response.ErrorResponse;
import com.pop.backend.global.exception.response.ServiceErrorResponse;
import com.pop.backend.global.exception.type.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {ServiceException.class})
  public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
    ErrorResponse errorResponse = new ServiceErrorResponse(ex);
    return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
  }

}