package com.chillin.hearting.api.data;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNicknameData extends Data {

    private String nickname;

}
