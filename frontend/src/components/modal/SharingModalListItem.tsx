import React from 'react'

function SharingModalListItem({...props}) {
  return (
    <div className='m-2 w-12 flex flex-col items-center cursor-pointer' onClick={props.click}>
      <img src={props.icon} className='w-10 mb-1'/>
      <p className='text-xs'>{props.name}</p>
    </div>
  )
}

export default SharingModalListItem
