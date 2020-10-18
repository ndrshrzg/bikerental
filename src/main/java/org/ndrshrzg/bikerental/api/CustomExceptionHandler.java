package org.ndrshrzg.bikerental.api;

import org.ndrshrzg.bikerental.ex.BikeAlreadyRentedException;
import org.ndrshrzg.bikerental.ex.BikeNotFoundException;
import org.ndrshrzg.bikerental.ex.MethodNotImplementedException;
import org.ndrshrzg.bikerental.ex.UserHasActiveSessionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BikeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bikeNotFoundHandler(BikeNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BikeAlreadyRentedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String bikeAlreadyRentedHandler(BikeAlreadyRentedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserHasActiveSessionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String userHasActiveSessionHandler(UserHasActiveSessionException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MethodNotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    String methodNotImplementedHandler(MethodNotImplementedException ex) {
        return ex.getMessage();
    }


}
