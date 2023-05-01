import React, { useState } from "react";
import { useNavigate } from 'react-router';
import { useSetRecoilState } from 'recoil';
import { userNicknameAtom } from '../atoms/userAtoms';
import nicknameValidation from "../features/nicknameValidation";
import { modifyNickname } from "../features/api/userApi";
import { getUserInfo } from "../features/userInfo";

function Setting() {
  const navigate = useNavigate();

  const [Nickname, setNickname] = useState("");
  const [nicknameFormError, setNicknameFormError] = useState("2자 이상 8자 이하의 문자를 입력해주세요");
  const setUserNickname = useSetRecoilState(userNicknameAtom)
  const userId = getUserInfo().userId

  const onNicknameHandler = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const currentNickname = e.currentTarget.value;
    console.log('닉네임은 이겁니다', currentNickname)
    nicknameValidation(currentNickname)
      ? setNicknameFormError("")
      : setNicknameFormError("2자 이상 8자 이하의 문자를 입력해주세요");
      setNickname(currentNickname);
  };

  const onSaveNicknameHandler = async (e: React.MouseEvent<HTMLButtonElement>) => {
    const body = { nickname: Nickname };
    const message = await modifyNickname(body);

    if (message === "success") {
      setUserNickname(Nickname)
      alert("닉네임 입력이 완료되었습니다");
      navigate(`/heartboard/user?id=${userId}`);
    } else {
      alert("닉네임 입력이 실패했습니다. 다시 시도해주세요");
    }
  };

  return (
    <div className="container mx-auto px-6 py-8">
      <div className="modal border-hrtColorPink pb-10">
        <div className="modal-header bg-hrtColorPink border-hrtColorPink">
          닉네임 설정
        </div>
        <input
        className="w-72 mt-10 mb-2 text-center border-b-2 border-hrtColorPink outline-none"
          type='text'
          placeholder='닉네임을 입력해주세요'
          onChange={onNicknameHandler} />
          <br/>
        {nicknameFormError}
        </div>
        <button
          className="bg-hrtColorYellow px-8 h-16 w-48 rounded-xl border-2 border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)] my-5"
          onClick={onSaveNicknameHandler}
          disabled={nicknameFormError ? true : false}>설정하기</button>
    </div>
  )
}

export default Setting