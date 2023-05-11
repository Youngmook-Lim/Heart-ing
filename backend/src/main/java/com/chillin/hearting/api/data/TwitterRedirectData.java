package com.chillin.hearting.api.data;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwitterRedirectData implements Data {

    private String redirectUrl;
}
