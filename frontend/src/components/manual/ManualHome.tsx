import React from 'react'
import kakao_login_button from '../../assets/images/png/kakao_login_button_1.png'
import logo_line from '../../assets/images/logo/logo_line.png'
import { useRecoilValue } from 'recoil';
import { isLoginAtom } from '../../atoms/userAtoms';

// import ManualHomeCount from './ManualHomeCount'

function ManualHome() {
  const KAKAO_API = process.env.REACT_APP_KAKAO_API;
  const KAKAO_CLIENT_ID = process.env.REACT_APP_KAKAO_CLIENT_ID;
  const KAKAO_REDIRECT_URI = process.env.REACT_APP_KAKAO_REDIRECT_URI;
  const KAKAO_REQUEST = `${KAKAO_API}/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;

  const isLogin = useRecoilValue(isLoginAtom)

  return (
    <div className="flex flex-col items-center h-screen">
      {/* <ManualHomeCount /> */}
      <div className="w-4/5 flex justify-center items-center h-4/5">
        <img src={logo_line} alt='hearting_logo' />
      </div>
      {isLogin ? 
        null
        :
        <div className="w-4/5 flex justify-center">
          <a href={KAKAO_REQUEST}>
            <img src={kakao_login_button} alt='kakao_login_button' />
          </a>
        </div>
        }
    </div>
  )
}

export default ManualHome