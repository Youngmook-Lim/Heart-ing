import React from "react";
import BubbleArrow from "../../assets/images/png/bubble_arrow.png";
import { useRecoilValue } from "recoil";
import { isMyBoardAtom } from "../../atoms/messageAtoms";
import { userNicknameAtom, userStautsMessageAtom } from "../../atoms/userAtoms";

function HeartBoardProfileBox({ ...props }) {
  const isMyBoard = useRecoilValue(isMyBoardAtom);
  const myNickname = useRecoilValue(userNicknameAtom);
  const myStatus = useRecoilValue(userStautsMessageAtom);

  const nickname = isMyBoard ? myNickname : props.userProfile.nickname;
  const statusMessage = isMyBoard ? myStatus : props.userProfile.statusMessage;

  return (
    <div className="pt-3">
      <div className="flex flex-col items-center relative">
        <div className="text-l bg-hrtColorYellow mt-4 px-4 leading-9 rounded cursor-default">
          {statusMessage}
        </div>
        {statusMessage && statusMessage.trim() !== "" ? (
          <img
            src={BubbleArrow}
            alt="test"
            className="w-4 pb-3 absolute bottom-1/3"
          />
        ) : null}
        <div className="flex align-middle pt-2">
          <span className="text-4xl cursor-default">{nickname}</span>
        </div>
      </div>
    </div>
  );
}

export default HeartBoardProfileBox;
