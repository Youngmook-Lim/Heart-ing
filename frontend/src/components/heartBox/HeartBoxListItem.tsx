import React from "react";

import { useRecoilValue, useSetRecoilState } from "recoil";
import {
  readMessageAtom,
  isMyBoardAtom,
  selectedMessageIdAtom,
} from "../../atoms/messageAtoms";

import HeartItemIcon from "../common/HeartItemIcon";

function HeartBoxListItem({ ...props }) {
  const setReadMessageAtom = useSetRecoilState(readMessageAtom);
  const setSelectedMessgeIdAtom = useSetRecoilState(selectedMessageIdAtom);

  const readMessage = (messageId: number) => {
    console.log(messageId + " 메시지를 읽습니다");
    // messageId의 메시지를 열람합니다
    setReadMessageAtom(true);
    setSelectedMessgeIdAtom(messageId);
  };

  return (
    <div className="flex" onClick={() => readMessage(props.messageId)}>
      <HeartItemIcon id={props.heartId} />
      <div>{props.context}</div>
      <div>{props.emojiId}</div>
    </div>
  );
}

export default HeartBoxListItem;
