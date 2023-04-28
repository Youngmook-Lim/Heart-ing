import React, { useEffect, useState } from "react";
import { IHeartInfoTypes } from '../../types/messageType'
import HeartItem from '../common/HeartItem'
import { ReactComponent as SelectedHeart } from "../../assets/images/pixel/heart/heart_select_1.svg";

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
        {selectedHeartId ? <div>선택 완료</div> : <div className="my-5">하트를 선택해주세요</div>}
      <div className="grid grid-cols-3 p-2">
        {props.heartList.map((heart: IHeartInfoTypes) => (
          <div>
          {heart.isLocked ? 
          <div>
            <HeartItem heartId={heart.heartId} context={heart.name}/>
          </div>
          :
          <div className="relative" onClick={onSelectHandler} id={String(heart.heartId)}>
            {String(heart.heartId) === selectedHeartId ? 
              <div className="absolute top-0 left-0 z-2"><SelectedHeart /></div> : null
            }
            <div className="z-5">
              <HeartItem heartId={heart.heartId} context={heart.name}/>
            </div>
          </div>
          }
        </div>
        ))}        
      </div>
    </div>
  )
}

export default HeartwritingSelectHeart
