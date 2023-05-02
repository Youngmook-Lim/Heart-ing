import React from 'react'

function HeartwritingSelectedHeartItem({...props}) {
  return (
    <div>
      <div className="place-content-center py-2">
      {props.heartInfo.isLocked ? (
        <div className="flex justify-center opacity-30">
          <img src={props.heartInfo.heartUrl} />
        </div>
      ) : (
        <div className="flex justify-center relative">
          <img src={props.heartInfo.heartUrl} />
        </div>
      )}

      <div className="px-2 leading-5 tracking-tight"> {props.heartInfo.name} </div>
    </div>
    </div>
  )
}

export default HeartwritingSelectedHeartItem
