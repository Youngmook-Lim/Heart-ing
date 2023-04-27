import React from "react";
import { useState, useEffect } from "react";

import { IMessageInfoTypes } from "../../types/messageType";

import HeartItem from "../common/HeartItem";

function HeartBoardList({ ...props }) {
  const [recentMessageList, setRecentMessageList] = useState(
    props.receivedList
  );

  useEffect(() => {
    // 최근 24시간 내의 하트 리스트를 받습니다.
    // 받은 리스트는 recentMessageList에 저장합니다.
    console.log(props.receivedList);
    setRecentMessageList(props.receivedList);
  }, [props.receivedList]);

  // recentMessageList 길이 만큼 반복해 HeartItem를 불러옵니다
  if (recentMessageList && recentMessageList.length > 0) {
    // 원본이 reverse되는 것을 막기 위해 복사본을 만듭니다
    const copyRecentMessageList = [...recentMessageList].reverse();
    return (
      <div className="grid grid-cols-3 p-2">
        {copyRecentMessageList.map(
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
    return <div className="grid grid-cols-3 p-2"></div>;
  }
}

export default HeartBoardList;
