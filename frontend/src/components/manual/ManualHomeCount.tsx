import React from 'react';
// import {useState } from 'react'
import { ITotalHeartType } from '../../types/messageType'

function ManualHomeCount( props: ITotalHeartType ) {

  // const [totalHeartCnt, setTotalHeartCnt] = useState(props.totalHeartCnt)
  console.log("카운트 렌더링")
  return (
    <div>지금까지 주고 받은 총 마음의 갯수: {props.totalHeartCnt}</div>
  )
}

export default React.memo(ManualHomeCount)