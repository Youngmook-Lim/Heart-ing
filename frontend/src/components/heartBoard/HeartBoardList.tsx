import React from "react";
import { useState, useEffect } from "react";
import { useRecoilValue } from "recoil";

import { IMessageInfoTypes } from "../../types/messageType";
import { isMyBoardAtom } from "../../atoms/messageAtoms";

import HeartItem from "../common/HeartItem";

function HeartBoardList({ ...props }) {
  const isMyBoard = useRecoilValue(isMyBoardAtom);

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
    // const copyRecentMessageList = [...recentMessageList].reverse();
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
  } else if (recentMessageList.length === 0) {
    return (
      <div className="">
        {isMyBoard ? (
          <div className="text-sm text-hrtColorPink m-2 my-6">
            친구들에게 마음 수신함을 공유해보세요!
          </div>
        ) : (
          <div className="text-sm text-hrtColorPink m-2 my-6">
            여러분의 마음을 보내보세요!
          </div>
        )}
      </div>
    );
  } else {
    return <div className="grid grid-cols-3 p-2"></div>;
  }
}

export default HeartBoardList;
