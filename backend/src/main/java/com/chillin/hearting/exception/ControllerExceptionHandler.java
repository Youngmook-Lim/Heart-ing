package com.chillin.hearting.exception;

import com.chillin.hearting.api.response.ErrorRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(WrongUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorRes handleWrongUserException(WrongUserException e) {
        log.error(e.getMessage());
        return ErrorRes.make(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorRes handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return ErrorRes.make(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorRes handleUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage());
        return ErrorRes.make(e.getMessage());
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorRes handleDuplicateException(DuplicateException e) {
        log.error(e.getMessage());
        return ErrorRes.make(e.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorRes handleUnAuthorizedException(UnAuthorizedException e) {
        log.error(e.getMessage());
        return ErrorRes.make(e.getMessage());
    }

    @ExceptionHandler(TokenValidFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorRes handleTokenValidFailedException(TokenValidFailedException e) {
        log.error(e.getMessage());
        return ErrorRes.make(e.getMessage());
    }


}