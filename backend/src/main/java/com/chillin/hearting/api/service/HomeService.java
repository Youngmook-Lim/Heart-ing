package com.chillin.hearting.api.service;

import com.chillin.hearting.api.data.TotalMessageCountData;
import com.chillin.hearting.db.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@code HomeService}는 메인 홈페이지 관련 로직을 처리하는 서비스입니다.
 *
 * @author wjdwn03
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final MessageRepository messageRepository;

    /**
     * "하팅!"서비스 전체 누적 메시지 수를 반환합니다.
     *
     * @return 관리자가 전송한 메시지를 제외하고 카운트한 개수를 TotalMessageCountData 타입의 객체로 반환합니다.
     */
    // 홈 화면 - 서비스 전체 누적 메시지 수
    public TotalMessageCountData totalMessageCount() {

        Long count = messageRepository.countBySenderIpNotOrIsNull("ADMIN");

        log.info("서비스 누적 메시지 수(관리자가 전송한 메시지 제외) : {}", count);

        return TotalMessageCountData.builder().totalHeartCount(count).build();
    }

}

