import React, { useState } from "react";
import { useNavigate } from 'react-router';
import { useSetRecoilState } from 'recoil';
import { userNicknameAtom } from '../atoms/userAtoms';
import nicknameValidation from "../features/nicknameValidation";
import { modifyNickname } from "../features/api/userApi";

function Setting() {
  const navigate = useNavigate();

  const [Nickname, setNickname] = useState("");
  const [nicknameFormError, setNicknameFormError] = useState("2자 이상 8자 이하의 문자를 입력해주세요");
  const setUserNickname = useSetRecoilState(userNicknameAtom)

  const onNicknameHandler = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const currentNickname = e.currentTarget.value;
    nicknameValidation(currentNickname)
      ? setNicknameFormError("")
      : setNicknameFormError("2자 이상 8자 이하의 문자를 입력해주세요");
    setUserNickname(e.currentTarget.value);
  };

  const onSaveNicknameHandler = async (e: React.MouseEvent<HTMLButtonElement>) => {
    const body = { nickname: Nickname };
    const message = await modifyNickname(body);

    if (message === "success") {
      setUserNickname(Nickname)
      alert("닉네임 입력이 완료되었습니다");
      navigate(-4);
    } else {
      alert("닉네임 입력이 실패했습니다. 다시 시도해주세요");
    }
  };

  return (
    <div>
      닉네임 세팅
      <input
        type='text'
        onChange={onNicknameHandler} />
      {nicknameFormError}
      <button
        onClick={onSaveNicknameHandler}>수정</button>
    </div>
  )
}

export default Setting