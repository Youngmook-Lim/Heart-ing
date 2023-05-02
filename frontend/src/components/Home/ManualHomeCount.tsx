import React from 'react';
import { useEffect,useState } from 'react'
import { ITotalHeartPropsTypes } from '../../types/messageType'

function ManualHomeCount({ onGetTotalHeart }: ITotalHeartPropsTypes ) {

  const [totalHeartCnt, setTotalHeartCnt] = useState<number>(0)

  useEffect(() => {
    handleGetTotalHeartCnt()

    const interval = setInterval(async() => {
      handleGetTotalHeartCnt()
    }, 3000)
  
    return () => {
      clearInterval(interval)
    }
  }, [])

  const handleGetTotalHeartCnt = async () => {
    const totalCnt = await onGetTotalHeart();
    setTotalHeartCnt(totalCnt);
  };

  console.log("카운트 렌더링")
  return (
    <div className='text-xl textShadow my-12'>
      <p className='purple text-white'>지금까지 주고 받은 마음의 갯수</p>
      <span className='flex justify-center text-white items-center textShadow my-2'>
        <p className='purple'>총</p>
        <p className='mx-4 white text-hrtColorPink tracking-widest' id="number">{totalHeartCnt}</p>
        <p className='purple'>개</p></span>
    </div>
  )
}

export default React.memo(ManualHomeCount)