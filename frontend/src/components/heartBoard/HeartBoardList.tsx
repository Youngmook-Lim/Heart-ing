import React from "react";
import { useState, useEffect } from "react";
import { useRecoilValue } from "recoil";

import { IMessageInfoTypes } from "../../types/messageType";
import { isMyBoardAtom } from "../../atoms/messageAtoms";

import HeartItem from "../common/HeartItem";
import Loading from "../common/Loading";

function HeartBoardList({ ...props }) {
  const isMyBoard = useRecoilValue(isMyBoardAtom);

  const [recentMessageList, setRecentMessageList] = useState(
    props.receivedList
  );

  useEffect(() => {
    // 최근 24시간 내의 하트 리스트를 받습니다.
    // 받은 리스트는 recentMessageList에 저장합니다.
    setRecentMessageList(props.receivedList);
  }, [props.receivedList]);

  // recentMessageList 길이 만큼 반복해 HeartItem를 불러옵니다
  if (recentMessageList && recentMessageList.length > 0) {
    return (
      <div className="grid grid-cols-3">
        {recentMessageList.map(
          ({ messageId, heartUrl, title, isRead }: IMessageInfoTypes) => (
            <HeartItem
              key={messageId}
              messageId={messageId}
              heartUrl={heartUrl}
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
            친구들에게 하트 수신함을 공유해보세요!
          </div>
        ) : (
          <div className="text-sm text-hrtColorPink m-2 my-6">
            여러분의 하트를 보내보세요!
          </div>
        )}
      </div>
    );
  } else {
    return <Loading />;
  }
}

export default HeartBoardList;
