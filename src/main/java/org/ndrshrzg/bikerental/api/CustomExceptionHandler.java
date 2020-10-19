package org.ndrshrzg.bikerental.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ndrshrzg.bikerental.ex.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    // todo implement an ErrorResponseBuilder class that turns these RuntimeExceptions into nice JSON Errors as specified

    // client errors

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
    @ExceptionHandler(BikeNotRentedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bikeNotRentedHandler(BikeNotRentedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BikeNotRentedByUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bikeNotRentedByUserHandler(BikeNotRentedByUserException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserHasActiveSessionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String userHasActiveSessionHandler(UserHasActiveSessionException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserHasNoActiveSessionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String userHasNoActiveSessionHandler(UserHasNoActiveSessionException ex) {
        return ex.getMessage();
    }

    // server side errors

    @ResponseBody
    @ExceptionHandler(MethodNotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    String methodNotImplementedHandler(MethodNotImplementedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String jsonProcessingExceptionHandler() {
        return "A problem mapping data to JSON occurred.";
    }


}
