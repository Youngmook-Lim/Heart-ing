import React from 'react'

function NavbarNotificationItem({...props}) {
  const now = new Date();
  
  const nowHour = Number((new Intl.DateTimeFormat('ko-KR', {
    timeZone: 'Asia/Seoul',
    hour: '2-digit',
    hour12: false,
  }).format(now)).substr(0, 2))

  const nowMinute = Number(new Intl.DateTimeFormat('ko-KR', {
    timeZone: 'Asia/Seoul',
    hour12: false,
    minute: '2-digit',
  }).format(now))

  const createdHour = Number(props.messageInfo.createdDate.substr(11, 2))
  const createdMinute = Number(props.messageInfo.createdDate.substr(14, 2))

  const timer = () => {
    if (nowHour-createdHour > 1 || (nowHour-createdHour === 1 && nowMinute-createdMinute > 0)) {
      return `${nowHour-createdHour}시간 전`
    } else {
      if (nowHour-createdHour < 0) {
        return `${nowHour-createdHour+24}시간 전`
      } else {
        if (nowMinute-createdMinute > 0) {
          return `${nowMinute-createdMinute}분 전`
        } else {
          if (nowHour === createdHour && nowMinute ===createdMinute) {
            return '지금'
          } else {
            return `${nowMinute-createdMinute+60}분 전`
          }
        }
      }
    }
  }
  return (
    <div className='text-sm ml-2 mb-1' onClick={props.onClickHandler}>
      <img src={props.messageInfo.heartUrl} alt='heart' className='w-4 h-4 mr-1 inline'/>
      {(function() {
        if (props.messageInfo.type === 'R') {
          return (<span>{props.messageInfo.heartName} 하트를 받았어요!</span>)
        } else if (props.messageInfo.type === 'E') {
          return (<span>새로운 반응을 받았어요!</span>)
        } else {
          return (<span>새로운 하트를 획득하러 가요!</span>)
        }
      })()}
      <span className='text-hrtColorGray'> {timer()}</span>
    </div>
  )
}

export default NavbarNotificationItem
