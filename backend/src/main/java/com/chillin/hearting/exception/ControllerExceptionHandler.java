package com.chillin.hearting.exception;

import com.chillin.hearting.api.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    private static final String FAIL = "fail";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message("RequestBody를 확인해 주세요.")
                .build();
    }

    @ExceptionHandler(WrongUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseDTO handleWrongUserException(WrongUserException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO handleUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseDTO handleDuplicateException(DuplicateException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseDTO handleUnAuthorizedException(UnAuthorizedException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(TokenValidFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseDTO handleTokenValidFailedException(TokenValidFailedException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }


}