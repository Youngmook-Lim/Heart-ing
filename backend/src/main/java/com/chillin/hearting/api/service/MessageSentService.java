package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.SentMessageData;
import com.chillin.hearting.api.data.SentMessageListData;
import com.chillin.hearting.db.domain.Message;
import com.chillin.hearting.db.repository.SentMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageSentService {

    private final SentMessageRepository sentMessageRepository;

    public SentMessageListData getSentMessages(String userId) {
        List<Message> findMessages = sentMessageRepository.findBySenderIdAndExpiredDateAfterOrderByExpiredDate(userId, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        List<SentMessageData> sendMessageDatas = new ArrayList<>();
        for (Message message : findMessages) {
            sendMessageDatas.add(SentMessageData.of(message));
        }
        return SentMessageListData.builder().sentMessageList(sendMessageDatas).build();
    }
}
