import React from "react";
import { useState, useEffect } from "react";

import { IMessageInfoTypes } from "../../types/messageType";

import HeartItem from "../common/HeartItem";

// 더미데이터
const messageList: IMessageInfoTypes[] = [
  {
    messageId: "message1",
    title: "test title1",
    heartId: "heartid1",
    heartName: "heartname1",
    heartUrl: "url1",
    emojiId: "emoji1",
    emojiName: "emoji1",
    emojiUrl: "url1",
    isRead: false,
    createdDate: "00000",
    expiredDate: "00000",
  },
  {
    messageId: "message2",
    title: "test title2",
    heartId: "heartid2",
    heartName: "heartname2",
    heartUrl: "url2",
    emojiId: "emoji2",
    emojiName: "emoji2",
    emojiUrl: "url2",
    isRead: false,
    createdDate: "00000",
    expiredDate: "00000",
  },
];

function HeartBoardList() {
  const [recentMessageList, setRecentMessageList] = useState(messageList);

  useEffect(() => {
    // 최근 24시간 내의 하트 리스트를 받습니다.
    // 받은 리스트는 recentMessageList에 저장합니다.
    setRecentMessageList(messageList);
  }, []);

  // recentMessageList 길이 만큼 반복해 HeartItem를 불러옵니다
  return (
    <div className="grid grid-cols-3">
      {recentMessageList.map(
        ({ messageId, heartId, title, isRead }: IMessageInfoTypes) => (
          <HeartItem
            key={messageId}
            messageId={messageId}
            heartId={heartId}
            context={title}
            isRead={isRead}
          />
        )
      )}
    </div>
  );
}

export default HeartBoardList;
