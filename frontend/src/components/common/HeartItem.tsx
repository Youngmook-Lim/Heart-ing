import React, { useState } from "react";

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
  const [isRead, setIsRead] = useState(false);
  const [isAnimation, setIsAnimation] = useState(false);

  const readMessage = (messageId: number) => {
    // messageId의 메시지를 열람합니다
    setReadMessageAtom(true);
    setSelectedMessgeIdAtom(messageId);
    setIsRead(true);
  };

  const bounceAnimation = () => {
    setIsAnimation(true);
    setTimeout(function () {
      setIsAnimation(false);
    }, 500);
  };

  return (
    <div className="place-content-center py-2">
      {isMyBoard ? (
        <div
          className={`flex justify-center relative ${
            isAnimation ? "jello-vertical" : null
          }`}
          onClick={() => {
            readMessage(props.messageId);
            bounceAnimation();
          }}
        >
          <img
            className="mx-auto my-auto"
            src={props.heartUrl}
            alt="heartIcon"
          />
          {props.isRead || isRead ? null : (
            <div className="bg-hrtColorNewRed w-4	h-4	mx-4 my-3 rounded-full border-2	border-white absolute left-1/2 bottom-1/2	"></div>
          )}
        </div>
      ) : (
        <div
          className={`flex justify-center ${
            isAnimation ? "shake-horizontal" : null
          }`}
          onClick={() => bounceAnimation()}
        >
          <img
            className="mx-auto my-auto"
            src={props.heartUrl}
            alt="heartIcon"
          />
        </div>
      )}

      <div className="px-2 leading-5 tracking-tight whitespace-pre-line cursor-default">
        {" "}
        {props.context}{" "}
      </div>
    </div>
  );
}

export default HeartItem;
