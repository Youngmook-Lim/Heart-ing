package com.chillin.hearting.api.data;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationListData implements Data {

    public List<NotificationData> notificationList;
}
