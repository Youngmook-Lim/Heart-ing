import React from 'react'
import MessageModalButtonBox from './MessageModalButtonBox'
import MessageModalHeart from './MessageModalHeart'
import MessageModalTextbox from './MessageModalTextbox'
import MessageModalTime from './MessageModalTime'

function MessageModal() {
  return (
    <div>
      여긴 모달창
      <MessageModalHeart />
      <MessageModalTime />
      <MessageModalTextbox />
      <MessageModalButtonBox />
    </div>
  )
}

export default MessageModal