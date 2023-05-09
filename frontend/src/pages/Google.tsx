import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { login } from "../features/api/userApi";
import {
  isLoginAtom,
  userNicknameAtom,
  userStautsMessageAtom,
} from "../atoms/userAtoms";
import { savingUserInfo } from "../features/userInfo";

function Google() {
  const navigate = useNavigate();
  const userAgent = window.navigator.userAgent;
  console.log(userAgent);
  const setIsLogin = useSetRecoilState(isLoginAtom);
  const setUserNickname = useSetRecoilState(userNicknameAtom);
  const setUserStatusMessage = useSetRecoilState(userStautsMessageAtom);

  let params = new URL(document.URL).searchParams;
  let code = params.get("code");

  useEffect(() => {
    Object.defineProperties(navigator, {
      userAgent: {
        get: () =>
          `Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36`,
      },
    });
    console.log(navigator.userAgent);

    async function kakaoLogin() {
      if (!code) return;
      const data = await login("google", code);
      if (data !== null) {
        // console.log("구글 됏당");
        // console.log("닉네임 머임?", data.data);
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
        // console.log("ㄱ글로그인 실패ㅜ;");
      }
    }
    kakaoLogin();
  }, [code, navigate, setIsLogin, setUserNickname, setUserStatusMessage]);

  return <div></div>;
}

export default Google;
