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
      const data = await login("kakao", code);
      if (data !== null) {
        const userInfo = {
          userId: data.data.userId,
          accessToken: data.data.accessToken,
        };
        savingUserInfo(userInfo);
        setIsLogin(true);
        setUserNickname(data.data.nickname);
        setUserStatusMessage(data.data.statusMessage);
        if (data.data.isFirst) {
          navigate("/profilesettings");
        } else {
          navigate(`/heartboard/user?id=${data.data.userId}`);
        }
      } else {
        navigate("/notfound");
      }
    }
    kakaoLogin();
  }, [code, navigate, setIsLogin, setUserNickname, setUserStatusMessage]);

  return <div></div>;
}

export default Kakao;
