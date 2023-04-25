import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from 'recoil';
import { loginKakao } from "../features/api/userApi";
import { isLoginAtom, userNicknameAtom, userStautsMessageAtom } from "../atoms/userAtoms";
import { savingAccessToken } from "../features/userInfo";

function Kakao() {
  const navigate = useNavigate();

  const setIsLogin = useSetRecoilState(isLoginAtom)
  const setUserNickname = useSetRecoilState(userNicknameAtom)
  const setUserStatusMessage = useSetRecoilState(userStautsMessageAtom)

  let params = new URL(document.URL).searchParams;
  let code = params.get("code");

  useEffect(() => {
    async function kakaoLogin() {
      if (!code) return;
      const data = await loginKakao(code)
      if (data !== null) {
        console.log('카카오 됏당')
        if (data.nickname === '') {
          navigate('/setting')
        } else {
          const userInfo = {
            userId : data.userId,
            accessToken: data.accessToken,
          }
          savingAccessToken(userInfo)
          setIsLogin(true)
          setUserNickname(data.nickname)
          setUserStatusMessage(data.statusMessage)
          navigate(-3);
        }
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
