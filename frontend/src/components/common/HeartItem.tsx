import React from "react";

import { useRecoilValue, useSetRecoilState } from "recoil";
import {
  readMessageAtom,
  isMyBoardAtom,
  selectedMessageIdAtom,
} from "../../atoms/messageAtoms";

function HeartItem({ ...props }) {
  const setReadMessageAtom = useSetRecoilState(readMessageAtom);
  const setSelectedMessgeIdAtom = useSetRecoilState(selectedMessageIdAtom);
  const isMyBoard = useRecoilValue(isMyBoardAtom);
  const isRead = props.isRead ? "true" : "false";

  const readMessage = (messageId: string) => {
    console.log(messageId + " 메시지를 읽습니다");
    // messageId의 메시지를 열람합니다
    setReadMessageAtom(true);
    setSelectedMessgeIdAtom(messageId);
  };

  return (
    <div>
      {isMyBoard ? (
        <button onClick={() => readMessage(props.messageId)}>
          메시지 id : {props.messageId} / 하트 정보 : {props.heartId} / 메시지
          읽음 상태 : {isRead}
        </button>
      ) : (
        props.heartId
      )}

      <div> 제목 : {props.context} </div>
    </div>
  );
}

export default HeartItem;
