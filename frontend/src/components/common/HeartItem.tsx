import React from "react";

import { useRecoilValue, useSetRecoilState } from "recoil";
import {
  readMessageAtom,
  isMyBoardAtom,
  selectedMessageIdAtom,
} from "../../atoms/messageAtoms";

import HeartItemIcon from "./HeartItemIcon";

function HeartItem({ ...props }) {
  const setReadMessageAtom = useSetRecoilState(readMessageAtom);
  const setSelectedMessgeIdAtom = useSetRecoilState(selectedMessageIdAtom);
  const isMyBoard = useRecoilValue(isMyBoardAtom);
  const isRead = props.isRead ? "true" : "false";

  const readMessage = (messageId: number) => {
    console.log(messageId + " 메시지를 읽습니다");
    // messageId의 메시지를 열람합니다
    setReadMessageAtom(true);
    setSelectedMessgeIdAtom(messageId);
  };

  return (
    <div className="place-content-center py-2">
      {isMyBoard ? (
        <div
          className="flex justify-center relative "
          onClick={() => readMessage(props.messageId)}
        >
          <HeartItemIcon id={props.heartId} />
          {props.isRead ? null : (
            <div className="bg-hrtColorNewRed w-4	h-4	mx-4 my-3 rounded-full border-2	border-white absolute left-1/2 bottom-1/2	"></div>
          )}
        </div>
      ) : (
        <div className="flex justify-center">
          <HeartItemIcon id={props.heartId} />
        </div>
      )}

      <div> {props.context} </div>
    </div>
  );
}

export default HeartItem;
