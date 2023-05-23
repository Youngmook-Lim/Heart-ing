package com.chillin.hearting.api.controller;

import com.chillin.hearting.api.data.HeartData;
import com.chillin.hearting.api.data.HeartListData;
import com.chillin.hearting.api.service.HeartService;
import com.chillin.hearting.db.domain.Heart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class HeartControllerTest {

    @InjectMocks
    private HeartController heartController;

    @Mock
    private HeartService heartService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(heartController)
                .build();
    }

    @Test
    void 도감조회_비로그인() throws Exception {
        // given
        final String url = "/api/v1/hearts";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 도감조회_로그인() throws Exception {

        // given
        final String url = "/api/v1/hearts";
        List<HeartData> heartDataList = new ArrayList<>();
        heartDataList.add(HeartData.of(createDefaultHeart(1L), false));
        heartDataList.add(HeartData.of(createSpecialHeart(7L), true));
        HeartListData data = HeartListData.builder()
                .heartList(heartDataList)
                .build();

        // mocking
        when(heartService.findAllHearts(any())).thenReturn(data);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.data.heartList[0].isLocked", is(false)))
                .andExpect(jsonPath("$.data.heartList[1].isLocked", is(true)));
    }


    @Test
    void 유저메시지조회_비로그인() throws Exception {
        // given
        final String url = "/api/v1/hearts/user-hearts";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 유저메시지조회_로그인() throws Exception {

        // given
        final String url = "/api/v1/hearts/user-hearts";
        List<HeartData> heartDataList = new ArrayList<>();
        heartDataList.add(HeartData.of(createDefaultHeart(1L), false));
        heartDataList.add(HeartData.of(createSpecialHeart(6L), false));

        // mocking
        when(heartService.findUserMessageHearts(any())).thenReturn(heartDataList);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.data.heartList[0].isLocked", is(false)))
                .andExpect(jsonPath("$.data.heartList[1].isLocked", is(false)));
    }

    public Heart createDefaultHeart(Long id) {
        Heart heart = Heart.builder()
                .id(id)
                .name("호감 하트")
                .imageUrl("test.com")
                .shortDescription("짧은 설명 !")
                .longDescription("호감의 탄생 스토리")
                .acqCondition("기본 제공")
                .type("DEFAULT")
                .build();

        return heart;
    }

    public Heart createSpecialHeart(Long id) {
        Heart heart = Heart.builder()
                .id(id)
                .name("행성 하트")
                .imageUrl("universe.com")
                .shortDescription("우주에 단 하나 뿐인 너 !")
                .longDescription("우주의 탄생 스토리")
                .acqCondition("특정인에게 5회 이상 메시지 전송")
                .type("SPECIAL")
                .build();

        return heart;
    }
}
