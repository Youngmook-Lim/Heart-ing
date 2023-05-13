import React from "react";
import { useNavigate } from "react-router-dom";
import kakao_login_button from "../../assets/images/social/kakao_login.png";
import twitter_login_button from "../../assets/images/social/twitter_login.png";
import google_login_button from "../../assets/images/social/google_login.png";
import main_home_logo from "../../assets/images/logo/main_home_logo.png";
import { useRecoilValue } from "recoil";
import { isLoginAtom } from "../../atoms/userAtoms";
import { getUserInfo } from "../../features/userInfo";
import { ITotalHeartPropsTypes } from "../../types/messageType";

import ManualHomeCount from "./ManualHomeCount";
import ManualHomeIntro from "./ManualHomeIntro";
import { twitterRedirectApi } from "../../features/api/userApi";

function ManualHome({ onGetTotalHeart }: ITotalHeartPropsTypes) {
  const navigate = useNavigate();

  const KAKAO_API = process.env.REACT_APP_KAKAO_API;
  const KAKAO_CLIENT_ID = process.env.REACT_APP_KAKAO_CLIENT_ID;
  const KAKAO_REDIRECT_URI = process.env.REACT_APP_KAKAO_REDIRECT_URI;
  const KAKAO_REQUEST = `${KAKAO_API}/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;

  const GOOGLE_API = process.env.REACT_APP_GOOGLE_API;
  const GOOGLE_REDIRECT_URI = process.env.REACT_APP_GOOGLE_REDIRECT_URI;
  const GOOGLE_CLIENT_ID = process.env.REACT_APP_GOOGLE_CLIENT_ID;
  const GOOGLE_REQUEST = `${GOOGLE_API}/oauth2/v2/auth?client_id=${GOOGLE_CLIENT_ID}&redirect_uri=${GOOGLE_REDIRECT_URI}&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email`;

  const isLogin = useRecoilValue(isLoginAtom);
  const userId = getUserInfo().userId;

  const onMyBoardHandler = (e: React.MouseEvent<HTMLSpanElement>) => {
    navigate(`/heartboard/user?id=${userId}`);
  };

  const onGoogleLoginHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    alert(`지원하지 않는 브라우저입니다.\n다른 브라우저로 접속해주세요.`);
  };

  const onTwitterLoginHandler = async (e: React.MouseEvent<HTMLDivElement>) => {
    const data= await twitterRedirectApi()
    if (data.data.redirectUrl) {
      window.location.href = data.data.redirectUrl
    }
    else{
      alert(`로그인을 실패했습니다.\n잠시 후 다시 시도해주세요.`)
    }
  }

  return (
    <div className="flex flex-col items-center">
      <div className="h-1/4 flex justify-center items-center">
        <ManualHomeCount onGetTotalHeart={onGetTotalHeart} />
      </div>
      <div className="h-1/3 flex justify-center items-center">
        <img src={main_home_logo} alt="hearting_logo" className="max-h-40" />
      </div>
      <div className="flex flex-col h-1/3 pt-4 w-full">
        {isLogin ? (
          <div className="h-1/2 my-auto">
            <button
              className="bg-hrtColorYellow px-8 h-16 rounded-xl border-2 border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)]"
              onClick={onMyBoardHandler}
            >
              <div className="text-2xl">나의 하트판 가기</div>
            </button>
          </div>
        ) : (
          <div className="flex flex-col my-6">
            <div className="pt-2">
              <span className="flex justify-center items-center my-2">
                <p className="border-b border-hrtColorPink w-1/4 my-4"></p>
                <p className="mx-4 text-sm text-hrtColorPink">
                  {" "}
                  로그인/회원가입{" "}
                </p>
                <p className="border-b border-hrtColorPink w-1/4 my-4"></p>
              </span>
            </div>
            <div className="h-1/2">
              <div className="flex flex-col items-center mx-12">
                <div className="flex">
                  <a href={KAKAO_REQUEST}>
                    <img
                      src={kakao_login_button}
                      alt="kakao_login_button"
                      className="p-2 px-4"
                    />
                  </a>
                  {/* 트위터 로그인 링크가 들어갑니다 */}
                  <div onClick={onTwitterLoginHandler}>
                    <img
                      src={twitter_login_button}
                      alt="twitter_login_button"
                      className="p-2 px-4"
                    />
                  </div>
                  {/* 페이스북 로그인이 생긴다면 a>img의 클래스 태그 중 px-4를 없애고 mx-12 => mx-8로 수정합니다 */}
                  {navigator.userAgent.includes("KAKAOTALK") ? (
                    <div onClick={(e) => onGoogleLoginHandler(e)}>
                      <img
                        src={google_login_button}
                        alt="google_login_button"
                        className="p-2 px-4"
                      />
                    </div>
                  ) : (
                    <a href={GOOGLE_REQUEST}>
                      <img
                        src={google_login_button}
                        alt="google_login_button"
                        className="p-2 px-4"
                      />
                    </a>
                  )}
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
      <ManualHomeIntro />
    </div>
  );
}

export default ManualHome;
