package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.MigrationService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/migration")
@RequiredArgsConstructor
public class MigrationController {
    private final MigrationService migrationService;
    private static final String MESSAGE_SUCCESS = "success";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String UNAUTHORIZED_MESSAGE = "관리자 권한이 필요합니다.";
    private static final String MIGRATE_HEART_INFO_SUCCESS = "Redis 하트 정보를 MySQL과 동기화에 성공했습니다.";
    private static final String MIGRATE_USER_SENT_HEART_SUCCESS = "Redis 유저 보낸 하트 정보를 MySQL과 동기화에 성공했습니다.";
    private static final String MIGRATE_USER_RECEIVED_HEART_SUCCESS = "Redis 유저 받은 하트 정보를 MySQL과 동기화에 성공했습니다.";

    @GetMapping("/heartInfo")
    public ResponseEntity<ResponseDTO> migrateHeartInfo(HttpServletRequest httpServletRequest) {
        log.info("MySQL to Redis 데이터 마이그레이션 - heartInfo");

        User user = (User) httpServletRequest.getAttribute("user");
        if (!ROLE_ADMIN.equals(user.getRole())) throw new UnAuthorizedException(UNAUTHORIZED_MESSAGE);

        migrationService.migrateHeartInfo();
        ResponseDTO responseDTO = ResponseDTO.builder().status(MESSAGE_SUCCESS).message(MIGRATE_HEART_INFO_SUCCESS).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/userSentHeart")
    public ResponseEntity<ResponseDTO> migrateUserSentHeart(HttpServletRequest httpServletRequest) {
        log.info("MySQL to Redis 데이터 마이그레이션 - userSentHeart");

        User user = (User) httpServletRequest.getAttribute("user");
        if (!ROLE_ADMIN.equals(user.getRole())) throw new UnAuthorizedException(UNAUTHORIZED_MESSAGE);

        migrationService.migrateAllUserSentHeart();
        ResponseDTO responseDTO = ResponseDTO.builder().status(MESSAGE_SUCCESS).message(MIGRATE_USER_SENT_HEART_SUCCESS).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/userReceivedHeart")
    public ResponseEntity<ResponseDTO> migrateUserReceivedHeart(HttpServletRequest httpServletRequest) {
        log.info("MySQL to Redis 데이터 마이그레이션 - userReceivedHeart");

        User user = (User) httpServletRequest.getAttribute("user");
        if (!ROLE_ADMIN.equals(user.getRole())) throw new UnAuthorizedException(UNAUTHORIZED_MESSAGE);

        migrationService.migrateAllUserReceivedHeart();
        ResponseDTO responseDTO = ResponseDTO.builder().status(MESSAGE_SUCCESS).message(MIGRATE_USER_RECEIVED_HEART_SUCCESS).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
