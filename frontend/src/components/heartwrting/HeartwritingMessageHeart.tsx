import React from 'react'
import HeartItem from '../common/HeartItem'

function HeartwritingMessageHeart({...props}) {
  return (
    <div className='text-3xl'>
      <HeartItem heartId={props.selectedHeartInfo.heartId} context={props.selectedHeartInfo.name}/>
    </div>
  )
}

export default HeartwritingMessageHeart
