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
    <div
      className="modal flex items-center border-2 border-hrtColorPink rounded-lg p-2 m-2"
      onClick={() => readMessage(props.messageId)}
    >
      <div className="flex-none">
        <HeartItemIcon id={props.heartId} />
      </div>
      <div className="flex-auto text-xl">{props.context}</div>
      <div className="flex-none">{props.emojiId}</div>
    </div>
  );
}

export default HeartBoxListItem;
