package com.chillin.hearting.exception;

import com.chillin.hearting.api.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    private static final String FAIL = "fail";

    @ExceptionHandler(EmojiFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseDTO handleEmojiFailException(EmojiFailException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(EmojiNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO handleEmojiNotFoundException(EmojiNotFoundException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ReportFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseDTO handleReportFailException(ReportFailException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ServerLogicException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseDTO handleServerLogicException(ServerLogicException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(MessageAlreadyReportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO handleMessageAlreadyReportedException(MessageAlreadyReportedException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(MessageAlreadyDeletedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO handleMessageAlreadyDeletedException(MessageAlreadyDeletedException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(MessageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO handleMessageNotFoundException(MessageNotFoundException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(DeleteMessageFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseDTO handleDeleteMessageFailException(DeleteMessageFailException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message("RequestBody가 없거나 잘못되었습니다.")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message("RequestBody 형식이 잘못되었습니다.")
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

    @ExceptionHandler(MessageAlreadyExpiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO handleMessageNotFoundException(MessageNotFoundException e) {
        log.error(e.getMessage());
        return ResponseDTO.builder()
                .status(FAIL)
                .message(e.getMessage())
                .build();
    }
}