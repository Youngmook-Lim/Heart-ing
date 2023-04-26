package com.chillin.hearting.api.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class InboxData extends Data {
    private List<InboxDTO> inboxList;

    @Builder
    public InboxData(List inboxList) {
        this.inboxList = inboxList;
    }
}
