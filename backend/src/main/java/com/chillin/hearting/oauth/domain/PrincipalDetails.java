package com.chillin.hearting.oauth.domain;

import com.chillin.hearting.db.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PrincipalDetails {

    private Map<String, Object> attributes;
    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }
}