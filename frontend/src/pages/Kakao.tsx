import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { loginKakao } from "../features/api/userApi";
import {
  isLoginAtom,
  userNicknameAtom,
  userStautsMessageAtom,
} from "../atoms/userAtoms";
import { savingUserInfo } from "../features/userInfo";

function Kakao() {
  const navigate = useNavigate();

  const setIsLogin = useSetRecoilState(isLoginAtom);
  const setUserNickname = useSetRecoilState(userNicknameAtom);
  const setUserStatusMessage = useSetRecoilState(userStautsMessageAtom);

  let params = new URL(document.URL).searchParams;
  let code = params.get("code");

  useEffect(() => {
    async function kakaoLogin() {
      if (!code) return;
      const data = await loginKakao(code);
      if (data !== null) {
        console.log("카카오 됏당");
        console.log("닉네임 머임?", data.data);
        const userInfo = {
          userId: data.data.userId,
          accessToken: data.data.accessToken,
        };
        savingUserInfo(userInfo);
        setIsLogin(true);
        if (data.data.nickname === "") {
          navigate("/setting");
        } else {
          setUserNickname(data.data.nickname);
          setUserStatusMessage(data.data.statusMessage);
          navigate(`/heartboard/user?id=${data.data.userId}`);
        }
      } else {
        console.log("로그인 실패ㅜ;");
      }
    }
    kakaoLogin();
  }, [code, navigate, setIsLogin, setUserNickname, setUserStatusMessage]);

  return <div>카카오 로그인</div>;
}

export default Kakao;
