package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.HeartService;
import com.chillin.hearting.db.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/hearts")
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> findAllHearts(HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        heartService.findAllHearts(user);
        return null;
    }
}
