package com.chillin.hearting.api.data;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNicknameData implements Data {

    private String nickname;

}
