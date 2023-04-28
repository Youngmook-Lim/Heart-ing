import React, { useState } from "react";
import { useRecoilValue } from "recoil";
import { isMyBoardAtom } from "../../atoms/messageAtoms";
import { IUpdateProfileTypes } from "../../types/userType";
import BubbleArrow from "../../assets/images/png/bubble_arrow.png";

function HeartBoardProfileBox({ ...props }) {
  console.log("내가 프롭", props);
  const isMyBoard = useRecoilValue(isMyBoardAtom);
  const [isSetting, setIsSetting] = useState(false);
  const [newNickname, setNewNickname] = useState(props.userProfile.nickname);
  const [newStatusMessage, setNewStatusMessage] = useState(
    props.userProfile.statusMessage
  );

  const onStateHandler = (e: React.MouseEvent<HTMLButtonElement>) => {
    setIsSetting(true);
  };

  const onSubmitHandler = async (e: React.FocusEvent<HTMLFormElement>) => {
    e.preventDefault();

    const messageInfo: IUpdateProfileTypes = {
      nickname: newNickname,
      statusMessage: newStatusMessage,
    };

    props.onChangeProfile(messageInfo);
    setIsSetting(false);
  };

  const onNicknameHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const currentNickname = e.currentTarget.value;
    setNewNickname(currentNickname);
  };

  const onStatusMessageHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const currentStatusMessage = e.currentTarget.value;
    setNewStatusMessage(currentStatusMessage);
  };

  return (
    <div>
      {isSetting ? (
        <form onSubmit={onSubmitHandler} className="mx-2 my-4">
          <div className="flex flex-wrap">
            <div className="w-11 text-sm leading-10 my-1 ml-2">닉네임</div>
            <input
              type="text"
              value={newNickname}
              onChange={onNicknameHandler}
              className="bg-hrtColorLightPink rounded m-2 p-1 flex-auto w-2"
            />
          </div>
          <div className="flex flex-wrap">
            <div className="w-11 leading-10 my-2 ml-2">
              <div className="text-sm leading-4">
                상태
                <br />
                메세지
              </div>
            </div>

            <input
              type="text"
              value={newStatusMessage}
              onChange={onStatusMessageHandler}
              className="bg-hrtColorLightPink rounded m-2 p-1 flex-auto w-2"
            />
          </div>
          <button>수정완료</button>
        </form>
      ) : (
        <div className="flex flex-col items-center relative">
          <div className="text-xl bg-hrtColorYellow my-4 px-4 leading-9 rounded  ">
            {props.userProfile.statusMessage}
          </div>
          {props.userProfile.statusMessage &&
          props.userProfile.statusMessage.trim() !== "" ? (
            <img
              src={BubbleArrow}
              alt="test"
              className="w-4 pb-2 absolute bottom-1/3"
            />
          ) : null}
          <div className="flex align-middle">
            <span className="text-4xl">{props.userProfile.nickname}</span>
            {isMyBoard ? (
              <button onClick={onStateHandler}>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth="1.5"
                  stroke="currentColor"
                  className="w-6 h-6"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M9.594 3.94c.09-.542.56-.94 1.11-.94h2.593c.55 0 1.02.398 1.11.94l.213 1.281c.063.374.313.686.645.87.074.04.147.083.22.127.324.196.72.257 1.075.124l1.217-.456a1.125 1.125 0 011.37.49l1.296 2.247a1.125 1.125 0 01-.26 1.431l-1.003.827c-.293.24-.438.613-.431.992a6.759 6.759 0 010 .255c-.007.378.138.75.43.99l1.005.828c.424.35.534.954.26 1.43l-1.298 2.247a1.125 1.125 0 01-1.369.491l-1.217-.456c-.355-.133-.75-.072-1.076.124a6.57 6.57 0 01-.22.128c-.331.183-.581.495-.644.869l-.213 1.28c-.09.543-.56.941-1.11.941h-2.594c-.55 0-1.02-.398-1.11-.94l-.213-1.281c-.062-.374-.312-.686-.644-.87a6.52 6.52 0 01-.22-.127c-.325-.196-.72-.257-1.076-.124l-1.217.456a1.125 1.125 0 01-1.369-.49l-1.297-2.247a1.125 1.125 0 01.26-1.431l1.004-.827c.292-.24.437-.613.43-.992a6.932 6.932 0 010-.255c.007-.378-.138-.75-.43-.99l-1.004-.828a1.125 1.125 0 01-.26-1.43l1.297-2.247a1.125 1.125 0 011.37-.491l1.216.456c.356.133.751.072 1.076-.124.072-.044.146-.087.22-.128.332-.183.582-.495.644-.869l.214-1.281z"
                  />
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                  />
                </svg>
              </button>
            ) : null}
          </div>
        </div>
      )}
    </div>
  );
}

export default HeartBoardProfileBox;
