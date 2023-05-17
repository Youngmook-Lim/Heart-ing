import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { twitterLoginApi } from "../features/api/userApi";
import {
  isLoginAtom,
  userNicknameAtom,
  userStautsMessageAtom,
} from "../atoms/userAtoms";
import { savingUserInfo } from "../features/userInfo";

function Twitter() {
  const navigate = useNavigate();

  const setIsLogin = useSetRecoilState(isLoginAtom);
  const setUserNickname = useSetRecoilState(userNicknameAtom);
  const setUserStatusMessage = useSetRecoilState(userStautsMessageAtom);

  let params = new URL(document.URL).searchParams;
  let token = params.get("oauth_token");
  let verifier = params.get("oauth_verifier");

  useEffect(() => {
    async function twitterLogin() {
      if (!token || !verifier) return;
      const data = await twitterLoginApi({
        oauthToken: token,
        oauthVerifier: verifier,
      });
      if (data.data) {
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
    twitterLogin();
  }, [
    token,
    verifier,
    navigate,
    setIsLogin,
    setUserNickname,
    setUserStatusMessage,
  ]);

  return <div></div>;
}

export default Twitter;
