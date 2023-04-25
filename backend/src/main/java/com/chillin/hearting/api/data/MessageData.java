package com.chillin.hearting.api.data;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageData extends Data {

    private long messageId;
    private long heartId;
    private String heartName;
    private String heartUrl;
    private boolean isRead;
    
}
