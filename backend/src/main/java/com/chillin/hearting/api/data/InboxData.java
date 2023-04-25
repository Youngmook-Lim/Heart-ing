package com.chillin.hearting.api.data;

import com.chillin.hearting.db.domain.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class InboxData extends Data {
    private List<Message> inboxList;

    @Builder
    public InboxData(List inboxList) {
        this.inboxList = inboxList;
    }
}
