import React from 'react'
import { useNavigate } from "react-router-dom";
import kakao_login_button from '../../assets/images/png/kakao_login_button_1.png'
import logo_line from '../../assets/images/logo/logo_line.png'
import { useRecoilValue } from 'recoil';
import { isLoginAtom } from '../../atoms/userAtoms';
import { getUserInfo } from "../../features/userInfo";
import { ITotalHeartType } from '../../types/messageType'

import ManualHomeCount from './ManualHomeCount'
import ManualSection from './ManualSection';
import heart_select from '../../assets/images/png/heart_select.png'

function ManualHome( props: ITotalHeartType ) {
  const navigate = useNavigate();

  console.log("매뉴얼홈 렌더링")

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
    <div className="flex flex-col items-center h-screen">
      <div className="w-3/4 my-6 flex justify-center items-center">
        <img src={logo_line} alt='hearting_logo' />
      </div>
      <ManualHomeCount totalHeartCnt={props.totalHeartCnt}/>
      <div className="w-4/5 my-6 flex justify-center">
        {isLogin ? 
            <button className="bg-hrtColorYellow px-8 h-16 rounded-xl border-2 border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)]"
              onClick={onMyBoardHandler}>
              <div className="text-2xl">나의 하트판 가기</div>
            </button>
          :
            <a href={KAKAO_REQUEST}>
              <img src={kakao_login_button} alt='kakao_login_button' />
            </a>
          }
        </div>
        {/* <ManualSection /> */}
        <div className="container mx-auto px-6 mt-8">
          <div className="text-4xl py-3 textShadow">
            <p className="text-hrtColorYellow">사용 설명서</p></div>
              <div className="w-8/10 bg-white bg-opacity-80">
                <section className="my-2 mx-auto max-w-5xl px-6">
                  <div className="flex justify-center items-center"> 
                    <img src={heart_select} alt="heart_select" className="w-3/5 mx-auto mt-10" />
                  </div>
                    <h2 className="text-2xl font-semibold mt-4 mb-4">마음 전달하기</h2>
                      <p className="text-sm">
                        상대방에게 전달하고싶은 감정하트를 선택 후, 
                        전달하고 싶은 메세지가 있다면 함께 보내보세요.
                        전달한 마음은 24시간만 유지되며 수정, 삭제 할 수 없다는 것을 주의해주세요!
                      </p>
                  </section>
              </div>
          </div>
    </div>
  )
}

export default ManualHome