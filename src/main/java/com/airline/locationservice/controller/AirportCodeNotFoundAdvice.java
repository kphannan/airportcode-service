
package com.airline.locationservice.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class AirportCodeNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(AirportCodeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String airportCodeNotFoundHandler(AirportCodeNotFoundException ex) {
    return ex.getMessage();
  }
}