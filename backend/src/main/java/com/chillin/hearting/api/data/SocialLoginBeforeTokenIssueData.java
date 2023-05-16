package com.chillin.hearting.api.data;

import com.chillin.hearting.db.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginBeforeTokenIssueData {

    private User user;

    @Getter(onMethod_ = {@JsonProperty("isFirst")})
    @Setter(onMethod_ = {@JsonProperty("isFirst")})
    private boolean isFirst;
}
