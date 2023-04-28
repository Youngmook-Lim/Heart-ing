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
      <button onClick={props.onSettingMode} className="bg-hrtColorYellow px-8 h-16 rounded-xl border-2 border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)]" disabled={selectedHeartId ? false : true}>
        <div className="text-2xl">다음</div>
      </button>
    </div>
  )
}

export default HeartwritingSelectHeart
