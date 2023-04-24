import React from 'react'
import HeartBoxListItem from './HeartBoxListItem'
import HeartBoxListTimeline from './HeartBoxListTimeline'

function HeartBoxList() {
  return (
    <div>
      <HeartBoxListTimeline />
      <HeartBoxListItem />
    </div>
  )
}

export default HeartBoxList