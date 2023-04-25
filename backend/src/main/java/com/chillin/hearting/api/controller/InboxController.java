package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.InboxData;
import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.api.service.InboxService;
import com.chillin.hearting.db.domain.User;
import com.chillin.hearting.exception.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages/inbox")
@RequiredArgsConstructor
public class InboxController {

    private final InboxService inboxService;

    private final String INBOX_STORE_SUCCESS = "메시지 영구 보관 저장에 성공했습니다.";
    private final String INBOX_FIND_SUCCESS = "영구 보관 메시지 조회에 성공했습니다.";

    @PostMapping("/{messageId}")
    public ResponseEntity<ResponseDTO> storeMessageToInbox(@PathVariable("messageId") String messageId, HttpServletRequest httpServletRequest) {
        Long mId = Long.parseLong(messageId);
        inboxService.storeMessage(mId);

        ResponseDTO responseDTO = ResponseDTO.builder().status("success").message(INBOX_STORE_SUCCESS).build();
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ResponseDTO> findInboxMessages(HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        if (user == null) throw new UnAuthorizedException();
        InboxData inboxData = inboxService.findInboxMessages(user.getId());

        ResponseDTO responseDTO = ResponseDTO.builder().status("success").data(inboxData).message(INBOX_FIND_SUCCESS).build();
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

}
