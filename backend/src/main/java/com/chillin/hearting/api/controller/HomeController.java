package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.Data;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private static final String SUCCESS = "success";
    private final HomeService homeService;


    @GetMapping("/total-count")
    public ResponseEntity<ResponseDTO> totalMessageCount() {

        Data data = homeService.totalMessageCount();

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(SUCCESS)
                .message("서비스 전체 누적 메시지 수 반환합니다.")
                .data(data)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
}
