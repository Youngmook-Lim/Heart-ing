package com.chillin.hearting.api.data;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalMessageCountData implements Data {

    // 서비스 누적 메시지 수
    private Long totalHeartCount;

}
