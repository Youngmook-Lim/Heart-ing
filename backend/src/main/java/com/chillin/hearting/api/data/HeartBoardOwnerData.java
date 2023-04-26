package com.chillin.hearting.api.data;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartBoardOwnerData extends Data {

    private String nickname;
    private String statusMessage;
    private Long messageTotal;
}
