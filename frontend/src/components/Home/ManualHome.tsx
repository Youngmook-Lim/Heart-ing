import React from 'react'
import { useNavigate } from "react-router-dom";
import kakao_login_button from '../../assets/images/png/kakao_login_button_1.png'
import main_home_logo from '../../assets/images/logo/main_home_logo.png'
import { useRecoilValue } from 'recoil';
import { isLoginAtom } from '../../atoms/userAtoms';
import { getUserInfo } from "../../features/userInfo";
import { ITotalHeartPropsTypes } from '../../types/messageType'

import ManualHomeCount from './ManualHomeCount'

function ManualHome({ onGetTotalHeart }: ITotalHeartPropsTypes ) {
  const navigate = useNavigate();

  console.log("홈 렌더링")

  const KAKAO_API = process.env.REACT_APP_KAKAO_API;
  const KAKAO_CLIENT_ID = process.env.REACT_APP_KAKAO_CLIENT_ID;
  const KAKAO_REDIRECT_URI = process.env.REACT_APP_KAKAO_REDIRECT_URI;
  const KAKAO_REQUEST = `${KAKAO_API}/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;

  const isLogin = useRecoilValue(isLoginAtom)
  const userId = getUserInfo().userId;

  const onMyBoardHandler = (e: React.MouseEvent<HTMLSpanElement>) => {
    navigate(`/heartboard/user?id=${userId}`);
  };

  return (
    <div className="flex flex-col items-center h-full screen">
      <ManualHomeCount onGetTotalHeart={onGetTotalHeart}/>
      <div className="w-4/5 my-6 flex justify-center items-center">
        <img src={main_home_logo} alt='hearting_logo' />
      </div>
      <div className="flex flex-col my-24 w-4/5">
        {isLogin ? 
            <button className="bg-hrtColorYellow px-8 h-16 rounded-xl border-2 border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)]"
              onClick={onMyBoardHandler}>
              <div className="text-2xl">나의 하트판 가기</div>
            </button>
          :
            <>
            <div>
              <span className='flex justify-center items-center my-2'>
                <p className="border-b border-hrtColorPink w-1/4 my-4"></p>
                <p className='mx-4 text-sm text-hrtColorPink'> 하팅! 시작하기 </p>
                <p className="border-b border-hrtColorPink w-1/4 my-4"></p>
              </span>
              </div>
              <a href={KAKAO_REQUEST}>
                <img src={kakao_login_button} alt='kakao_login_button' />
              </a>
            </>
          }
        </div>
    </div>
  )
}

export default ManualHome