package org.ndrshrzg.bikerental.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ndrshrzg.bikerental.ex.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final ObjectMapper om = new ObjectMapper();

    // client errors

    @ResponseBody
    @ExceptionHandler(BikeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bikeNotFoundHandler(BikeNotFoundException ex) throws JsonProcessingException {
        return om.writeValueAsString(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(BikeAlreadyRentedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bikeAlreadyRentedHandler(BikeAlreadyRentedException ex) throws JsonProcessingException {
        return om.writeValueAsString(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(BikeNotRentedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bikeNotRentedHandler(BikeNotRentedException ex) throws JsonProcessingException {
        return om.writeValueAsString(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(BikeNotRentedByUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bikeNotRentedByUserHandler(BikeNotRentedByUserException ex) throws JsonProcessingException {
        return om.writeValueAsString(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(UserHasActiveSessionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userHasActiveSessionHandler(UserHasActiveSessionException ex) throws JsonProcessingException {
        return om.writeValueAsString(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(UserHasNoActiveSessionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userHasNoActiveSessionHandler(UserHasNoActiveSessionException ex) throws JsonProcessingException {
        return om.writeValueAsString(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    String methodNotImplementedHandler(HttpRequestMethodNotSupportedException ex) throws JsonProcessingException {
        return om.writeValueAsString(new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage()));
    }

    // server side errors

    @ResponseBody
    @ExceptionHandler(MethodNotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    String methodNotImplementedHandler(MethodNotImplementedException ex) throws JsonProcessingException {
        return om.writeValueAsString(new ErrorResponse(HttpStatus.NOT_IMPLEMENTED, ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String jsonProcessingExceptionHandler() throws JsonProcessingException {
        return om.writeValueAsString(new ErrorResponse(HttpStatus.NOT_IMPLEMENTED, "A problem mapping data to JSON occurred."));
    }


}
