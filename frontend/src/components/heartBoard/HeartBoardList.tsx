import React from "react";
import { useState, useEffect } from "react";

import { IMessageInfoTypes } from "../../types/messageType";

import HeartItem from "../common/HeartItem";

// 더미데이터
const messageList: IMessageInfoTypes[] = [
  {
    messageId: 1,
    title: "test title1",
    heartId: 0,
    heartName: "heartname1",
    heartUrl: "url1",
    emojiId: 1,
    emojiName: "emoji1",
    emojiUrl: "url1",
    isRead: false,
    createdDate: "00000",
    expiredDate: "00000",
  },
  {
    messageId: 2,
    title: "test title2",
    heartId: 1,
    heartName: "heartname2",
    heartUrl: "url2",
    emojiId: 2,
    emojiName: "emoji2",
    emojiUrl: "url2",
    isRead: false,
    createdDate: "00000",
    expiredDate: "00000",
  },
];

function HeartBoardList({ ...props }) {
  const [recentMessageList, setRecentMessageList] = useState(
    props.receivedList
  );

  useEffect(() => {
    // 최근 24시간 내의 하트 리스트를 받습니다.
    // 받은 리스트는 recentMessageList에 저장합니다.
    console.log(
      "props가 바뀌었으니 useEffect가 호출됩니다 props.receivedList >> "
    );
    console.log(props.receivedList);
    setRecentMessageList(props.receivedList);
  }, [props]);

  // recentMessageList 길이 만큼 반복해 HeartItem를 불러옵니다
  if (recentMessageList && recentMessageList.length > 0) {
    return (
      <div className="grid grid-cols-3 p-2">
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
  } else {
    return (
      <div className="grid grid-cols-3 p-2">
        {recentMessageList.length}개라서 안보임~ 더미데이터로 테스트하는중
        {messageList.map(
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
}

export default HeartBoardList;
