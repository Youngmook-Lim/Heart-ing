import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'

function HeartTestLoading({...props}) {
  const navigate = useNavigate()

  useEffect(() => {
    if (props.result === 'ISTP' || props.result === 'INFJ') {
      navigate('/heartresult?result=0')
    } else if (props.result === 'ENFP' || props.result === 'ESFP') {
      navigate('/heartresult?result=1')
    } else if (props.result === 'ENTP' || props.result === 'INTP') {
      navigate('/heartresult?result=2')
    } else if (props.result === 'ENFJ' || props.result === 'ENTJ') {
      navigate('/heartresult?result=3')
    } else if (props.result === 'ISTJ' || props.result === 'INTJ') {
      navigate('/heartresult?result=4')
    } else if (props.result === 'INFP' || props.result === 'ESTP') {
      navigate('/heartresult?result=5')
    } else if (props.result === 'ISFJ' || props.result === 'ISFP') {
      navigate('/heartresult?result=6')
    } else if (props.result === 'ESFJ' || props.result === 'ESTJ') {
      navigate('/heartresult?result=7')
    } else {
      navigate('/heartresult?result=0')
    }
  }) 
  return (
    <div>
      분석중...
    </div>
  )
}

export default HeartTestLoading