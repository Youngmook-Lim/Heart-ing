import React from 'react'
import HeartwritingMessageHeart from './HeartwritingMessageHeart'
import HeartwritingMessageForm from './HeartwritingMessageForm'

function HeartwritingMessage({...props}) {
  return (
    <div>
      <HeartwritingMessageHeart {...props}/>
      <HeartwritingMessageForm {...props}/>
    </div>
  )
}

export default HeartwritingMessage
