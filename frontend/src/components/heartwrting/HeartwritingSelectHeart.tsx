import React, { useEffect, useState } from "react";
import { IHeartInfoTypes } from '../../types/messageType'
import HeartItem from '../common/HeartItem'
import { ReactComponent as SelectedHeart } from "../../assets/images/pixel/heart/heart_select_1.svg";
import HeartwritingSelectedHeartItem from "./HeartwritingSelectedHeartItem";

function HeartwritingSelectHeart({...props}) {
  const [selectedHeartId, setSelectedHeartId] = useState('')
  const onSelectHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.currentTarget.id === selectedHeartId) {
      setSelectedHeartId('')
    } else {
      setSelectedHeartId(e.currentTarget.id)
    }
  }

  useEffect(() => {
    props.onHeartNumberHandler(selectedHeartId)
  }, [selectedHeartId])

  return (
    <div>
        {selectedHeartId ? <div className="my-5 text-xl text-hrtColorPink">선택 완료</div> : <div className="my-5 text-xl">하트를 선택해주세요</div>}
      <div className="grid grid-cols-3 p-2 mb-32">
        {props.heartList.map((heart: IHeartInfoTypes) => (
          <div>
            {heart.isLocked ? 
            <div className="relative whitespace-no-wrap">
              <HeartwritingSelectedHeartItem heartInfo={heart} id={String(heart.heartId)}/>
              <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-2/3 text-xxs whitespace-no-wrap">
                <p>로그인 후</p>
                <p>이용</p>
                <p>가능합니다</p>
              </div>
            </div>
            :
            <div className="relative" onClick={onSelectHandler} id={String(heart.heartId)}>
              <div className="relative z-10">
                <HeartwritingSelectedHeartItem heartInfo={heart}/>
              </div>
              {String(heart.heartId) === selectedHeartId ? 
                <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-2/3"><SelectedHeart /></div> : null
              }
            </div>
            }
          </div>
        ))}        
      </div>
    </div>
  )
}

export default HeartwritingSelectHeart
