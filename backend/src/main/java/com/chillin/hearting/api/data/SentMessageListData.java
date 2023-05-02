package com.chillin.hearting.api.data;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SentMessageListData implements Data {
    private List<SentMessageData> sentMessageList;
}
