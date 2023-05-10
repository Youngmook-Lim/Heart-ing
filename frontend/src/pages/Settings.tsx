import React, { useState } from "react";
import { useNavigate } from "react-router";
import { useRecoilState } from "recoil";
import { userNicknameAtom, userStautsMessageAtom } from "../atoms/userAtoms";
import { modifyNickname, modifyStatusMessage } from "../features/api/userApi";
import { getUserInfo } from "../features/userInfo";
import LogoEffect from "../assets/images/logo/logo_effect.png";

function Settings() {
  const navigate = useNavigate();

  const [myNickname, setMyNickname] = useRecoilState(userNicknameAtom);
  const [Nickname, setNickname] = useState(myNickname);
  const [myStatusMessage, setMyStatusMessage] = useRecoilState(userStautsMessageAtom);
  const [StatusMessage, setStatusMessage] = useState(myStatusMessage);
  const [countNickname, setCountNickname] = useState(myNickname.length);
  let initialLength;
  if (myStatusMessage) {
    initialLength = myStatusMessage.length
  } else {
    initialLength = 0;
  } 
  const [countStatus, setCountStatus] = useState(initialLength);

  const onNicknameHandler = async (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.currentTarget.value.length > 12) {
      const currentNickname = e.currentTarget.value.substr(0, 12);
      setNickname(currentNickname);
      setCountNickname(12);
    } else {
      const currentNickname = e.currentTarget.value;
      setNickname(currentNickname);
      setCountNickname(currentNickname.length);
    }
  };

  const onStatusMessageHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.currentTarget.value.length > 16) {
      const currentStatusMessage = e.currentTarget.value.substr(0, 16);
      setStatusMessage(currentStatusMessage);
      setCountStatus(16);
    } else {
      const currentStatusMessage = e.currentTarget.value;
      setStatusMessage(currentStatusMessage);
      setCountStatus(currentStatusMessage.length);
    }
  };

  const onSubmitHandler = async (e: React.MouseEvent<HTMLButtonElement>) => {
    const nicknameStatus = await modifyNickname({ nickname: Nickname });
    const statusMessageStatus = await modifyStatusMessage({
      statusMessage: StatusMessage,
    });
    if (nicknameStatus === "success" && statusMessageStatus === "success") {
      setMyNickname(Nickname);
      setMyStatusMessage(StatusMessage);
      alert("프로필 수정이 완료되었습니다");
      navigate(`/heartboard/user?id=${getUserInfo().userId}`);
    } else {
        alert(`프로필 수정이 실패했습니다.\n다시 시도해주세요.`);
      }
  };

  return (
    <div className="fullHeight container mx-auto px-6 fullHeight justify-between flex-col">
      <div className="flex flex-col justify-center items-center h-28">
        <img src={LogoEffect} alt="test" className=" px-14" />
      </div>
      <div className="heartBoard border-hrtColorPink flex-col justify-center items-center writingHeight">
        <div className="heartBoard-header bg-hrtColorPink w-full z-10 mb-2">
          프로필 설정
        </div>
        <div className="px-6 py-12 flex flex-col justify-start">
          <p className="text-left text-lg mt-4">닉네임</p>
          <div className="relative my-2">
            <input
              className="w-full border-b-2 pr-12 border-hrtColorPink outline-none"
              type="text"
              placeholder="1자 이상의 닉네임을 입력하세요"
              value={Nickname}
              onChange={onNicknameHandler}
            />
            <span className="text-gray-400 absolute right-0">{countNickname}/12</span>
          </div>
          <br />
          <p className="text-left text-lg mt-2">상태메세지</p>
          <div className="relative my-2">
            <input
              className="w-full border-b-2 pr-12 border-hrtColorPink outline-none"
              type="text"
              placeholder="상태메세지를 입력하세요"
              value={StatusMessage}
              onChange={onStatusMessageHandler}
            />
            <span className="text-gray-400 absolute right-0">{countStatus}/16</span>
          </div>
        </div>
      </div>
      <div className="flex justify-center items-center h-28">
        <button
          disabled={ Nickname ? false : true}
          className={`px-8 h-16 w-48 rounded-xl border-2 text-2xl ${Nickname ? 'bg-hrtColorYellow border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)]' : 'bg-gray-200 border-gray-300 shadow-[0_4px_4px_rgba(182,182,182,1)] text-white'}`}
          onClick={onSubmitHandler}
        >
          설정하기
        </button>
      </div>
    </div>
  );
}

export default Settings;
