import React, { useEffect } from "react";
import resultInfo from '../assets/result.json'
import LogoEffect from "../assets/images/logo/logo_effect.png";
import SharingModalList from '../components/modal/SharingModalList';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from "recoil";
import { isLoginAtom } from "../atoms/userAtoms";

function HeartTestResult() {
  const navigate = useNavigate()

  const isLogin = useRecoilValue(isLoginAtom)

  let params = new URL(document.URL).searchParams;
  let result = params.get("result");
  const resultIndex = Number(result)

  const onHomeHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    navigate('/')
  }
  const onTestHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    navigate('/hearttest')
  }

  useEffect(() => {
    if (!result || resultIndex > 8) {
      navigate('/notfound')
    }
  }, [])

  return (
    <div className="container mx-auto px-6 fullHeight">
      <img src={LogoEffect} alt="test" className="w-full px-14 py-8" />
      <div className="mx-auto">
        <div className="bg-hrtColorWhiteTrans border-2 border-hrtColorPink rounded-lg relative">
          <div className="overflow-auto scrollbar-hide">
            <div className='my-4 mt-8'>
              <p className='text-xl'>당신의</p>
              <p className='text-xl'>심볼♥하트는...</p>
            </div>
            <div>
              <img src={resultInfo[resultIndex].srcUrl} className='w-72 mb-4 mx-auto'/>
              {!isLogin ? 
                <div onClick={onHomeHandler} className="mx-auto w-72 mt-2 modal-button text-hrtColorOutline bg-hrtColorOutline900 border-hrtColorYellow cursor-pointer">
                  <p>나의 심볼하트로 메세지 보내기</p>
                </div>
                : null
              }
            </div>
          <div className='flex flex-col items-center mt-4 mb-8'>
            <hr className='w-72'/>
            <p className='my-4'>♥ 결과공유하기 ♥</p>
            <SharingModalList />
              <div onClick={onTestHandler} className="mx-auto w-72 mt-2 modal-button text-hrtColorOutline bg-hrtColorYellow border-hrtColorNewPurple cursor-pointer">
                <p>다시 하기</p>
              </div>
          </div>
          </div>
          <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute left-1 top-1"></div>
          <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute right-1 top-1"></div>
          <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute left-1 bottom-1"></div>
          <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute right-1 bottom-1"></div>
        </div>
      </div>
      <div className="h-24"></div>
    </div>
  )
}

export default HeartTestResult
