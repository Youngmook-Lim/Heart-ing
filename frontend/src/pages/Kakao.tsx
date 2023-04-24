import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from 'recoil';
import { loginKakao } from "../features/api/userApi";
import { IsLoginAtom } from "../atoms/userAtoms";

function Kakao() {
  const navigate = useNavigate();

  const setIsLogin = useSetRecoilState(IsLoginAtom)

  let params = new URL(document.URL).searchParams;
  let code = params.get("code");

  useEffect(() => {
    async function kakaoLogin() {
      if (!code) return;
    const data = await loginKakao(code)
    if (data !== null) {
      console.log('카카오 됏당')
      // saveUserInfo(data)
      setIsLogin(true)
      // setNickname(data.nickname)
      navigate("/");
    } else {
      console.log('로그인 실패ㅜ;')
    } 
  }
  kakaoLogin();
}, [code])

  return (
    <div>
      카카오 로그인
    </div>
  )
}

export default Kakao
