import React from "react";
import { useState, useEffect } from "react";
import { IMessageInfoTypes } from "../../types/messageType";

import HeartBoxListItem from "./HeartBoxListItem";
import HeartBoxListTimeline from "./HeartBoxListTimeline";
import Loading from "../common/Loading";

function HeartBoxList({ ...props }) {
  const [inboxMessageList, setInboxMessageList] = useState(props.inboxList);

  useEffect(() => {
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
            heartUrl,
            title,
            emojiId,
            emojiUrl,
            createdDate,
          }: IMessageInfoTypes) => (
            <HeartBoxListItem
              key={messageId}
              messageId={messageId}
              heartUrl={heartUrl}
              context={title}
              emojiId={emojiId}
              emojiUrl={emojiUrl}
              createdDate={createdDate}
            />
          )
        )}
      </div>
    );
  } else if (inboxMessageList && inboxMessageList.length === 0) {
    return <div className="text-sm text-hrtColorPink m-2 my-6">비어있어요</div>;
  } else {
    return <Loading />;
  }
}

export default HeartBoxList;
