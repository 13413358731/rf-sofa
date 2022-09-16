package com.realfinance.sofa.cg.controller;

import com.realfinance.sofa.common.datascope.DataAccessForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessForbiddenException.class)
    public ResponseEntity<?> dataAccessForbiddenException(DataAccessForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
