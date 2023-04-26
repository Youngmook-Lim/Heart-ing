package com.chillin.hearting.api.data;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReceivedMessageData extends Data {

    private List<MessageData> messageList;
    
}
