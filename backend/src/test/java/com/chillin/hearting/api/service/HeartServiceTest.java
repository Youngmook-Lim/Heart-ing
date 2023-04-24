package com.chillin.hearting.api.service;

import com.chillin.hearting.db.domain.Heart;
import com.chillin.hearting.db.repository.HeartRepository;
import com.chillin.hearting.db.repository.UserHeartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HeartServiceTest {

    @InjectMocks
    private HeartService heartService;

    @Mock
    private HeartRepository heartRepository;

    @Mock
    private UserHeartRepository userHeartRepository;

    @Test
    public void 도감조회() {

        // given
        Heart heart1 = Heart.builder()
                .name("heart1")
                .imageUrl("test-url1.com")
                .shortDescription("short-dsc1")
                .longDescription("long-dsc1")
                .acqCondition("test-condition1")
                .type("DEFAUT")
                .build();
        
        // moking
        when(heartRepository.save(any(Heart.class))).thenReturn(heart1);


        // when

        // then
    }


}