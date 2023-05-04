import React from "react";
import { useState, useEffect } from "react";
import { IMessageInfoTypes } from "../../types/messageType";

import HeartBoxListItem from "./HeartBoxListItem";
import HeartBoxListTimeline from "./HeartBoxListTimeline";
import Loading from "../common/Loading";

function HeartBoxList({ ...props }) {
  const [inboxMessageList, setInboxMessageList] = useState(props.inboxList);

  useEffect(() => {
    // console.log(props.inboxList);
    setInboxMessageList(props.inboxList);
  }, [props.inboxList]);

  if (inboxMessageList && inboxMessageList.length > 0) {
    const copyInboxMessageList = [...inboxMessageList].reverse();

    return (
      <div>
        <HeartBoxListTimeline />
        {copyInboxMessageList.map(
          ({
            messageId,
            heartId,
            title,
            emojiId,
            emojiUrl,
            createdDate,
          }: IMessageInfoTypes) => (
            <HeartBoxListItem
              key={messageId}
              messageId={messageId}
              heartId={heartId}
              context={title}
              emojiId={emojiId}
              emojiUrl={emojiUrl}
              createdDate={createdDate}
            />
          )
        )}
      </div>
    );
  } else {
    return <Loading />;
  }
}

export default HeartBoxList;
