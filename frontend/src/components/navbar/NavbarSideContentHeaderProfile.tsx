import React, { useState } from "react";
import { useRecoilState } from "recoil";
import { userNicknameAtom, userStautsMessageAtom } from "../../atoms/userAtoms";
import {
  modifyNickname,
  modifyStatusMessage,
} from "../../features/api/userApi";

function NavbarSideContentHeaderProfile({ ...props }) {
  const [myNickname, setMyNickname] = useRecoilState(userNicknameAtom);
  const [myStatus, setMyStatus] = useRecoilState(userStautsMessageAtom);
  const [newNickname, setNewNickname] = useState(myNickname);
  const [newStatusMessage, setNewStatusMessage] = useState(myStatus);
  const [countNickname, setCountNickname] = useState(0);
  const [countStatus, setCountStatus] = useState(0);

  const onSubmitHandler = async (e: React.FocusEvent<HTMLFormElement>) => {
    e.preventDefault();

    const nicknameStatus = await modifyNickname({ nickname: newNickname });
    const statusMessageStatus = await modifyStatusMessage({
      statusMessage: newStatusMessage,
    });

    if (nicknameStatus === "success" && statusMessageStatus === "success") {
      setMyNickname(newNickname);
      setMyStatus(newStatusMessage);
      props.setIsSetting(false);
      props.onOpenHandler();
    }
  };

  const onNicknameHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.currentTarget.value.length > 8) {
      const currentNickname = e.currentTarget.value.substr(0, 8);
      setNewNickname(currentNickname);
      setCountNickname(8);
    } else {
      const currentNickname = e.currentTarget.value;
      setNewNickname(currentNickname);
      setCountNickname(e.currentTarget.value.length);
    }
  };

  const onStatusMessageHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.currentTarget.value.length > 16) {
      const currentStatusMessage = e.currentTarget.value.substr(0, 16);
      setNewStatusMessage(currentStatusMessage);
      setCountStatus(16);
    } else {
      const currentStatusMessage = e.currentTarget.value;
      setNewStatusMessage(currentStatusMessage);
      setCountStatus(e.currentTarget.value.length);
    }
  };

  return (
    <div className="z-100">
      <form onSubmit={onSubmitHandler} className="my-4">
        <div className="flex flex-wrap items-center">
          <div className="w-11 text-sm leading-10 my-1">닉네임</div>
          <input
            type="text"
            value={newNickname}
            onChange={onNicknameHandler}
            className="bg-hrtColorLightPink rounded m-2 p-1 flex-auto w-2"
          />
          <span className="text-hrtColorGray">{countNickname}/8</span>
        </div>
        <div className="flex flex-wrap items-center">
          <div className="w-11 leading-10 my-2">
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
          <span className="text-hrtColorGray">{countStatus}/16</span>
        </div>
        <button
          disabled={newNickname.length > 0 ? false : true}
          className={` ${
            newNickname.length > 0
              ? "border-hrtColorOutline"
              : "text-hrtColorGray"
          } border-2 rounded-md text-sm px-2 mt-2`}
        >
          저장
        </button>
      </form>
    </div>
  );
}

export default NavbarSideContentHeaderProfile;
