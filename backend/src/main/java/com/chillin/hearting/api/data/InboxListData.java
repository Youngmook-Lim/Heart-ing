package com.chillin.hearting.api.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class InboxListData implements Data {
    private List<InboxData> inboxList;

    @Builder
    public InboxListData(List<InboxData> inboxList) {
        this.inboxList = inboxList;
    }
}
