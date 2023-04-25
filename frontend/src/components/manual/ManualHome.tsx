import React from 'react'
import { useNavigate } from "react-router-dom"

import ManualHomeCount from './ManualHomeCount'

function ManualHome() {
  const navigate = useNavigate();

  const KAKAO_API = process.env.REACT_APP_KAKAO_API;
  const KAKAO_CLIENT_ID = process.env.REACT_APP_KAKAO_CLIENT_ID;
  const KAKAO_REDIRECT_URI = process.env.REACT_APP_KAKAO_REDIRECT_URI;
  const KAKAO_REQUEST = `${KAKAO_API}/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;

  return (
    <div>
      <ManualHomeCount />
      <a href={KAKAO_REQUEST}>카카오로 로그인</a>
    </div>
  )
}

export default ManualHome