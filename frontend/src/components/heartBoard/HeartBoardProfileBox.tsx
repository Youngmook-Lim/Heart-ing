import React from 'react'

function HeartBoardProfileBox({ ...props }) {
  console.log('내가 프롭', props)
  return (
    <div>
      하트판에 있는 프로필박스
      {props.userProfile.nickname}
    </div>
  )
}

export default HeartBoardProfileBox
