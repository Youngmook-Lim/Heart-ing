package com.chillin.hearting.api.data;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusMessageData implements Data {

    private String statusMessage;
}
