import React, { useState, useEffect } from 'react'
import HeartTestOptions from '../components/heartTest/HeartTestOptions'
import HeartTestStart from '../components/heartTest/HeartTestStart'
import HeartTestLoading from '../components/heartTest/HeartTestLoading'

function HeartTest() {
  const [testMode, setTestMode] = useState('start')
  const [result, setResult] = useState('')

  const onTestModeHanlder = (e: React.MouseEvent<HTMLDivElement>) => {
    setTestMode('test')
  }

  return (
    <div>
      {(function() {
        if (testMode === 'start') {
          return (<HeartTestStart onTestModeHanlder={onTestModeHanlder}/>)
        } else if (testMode === 'test') {
          return (<HeartTestOptions setTestMode={setTestMode} setResult={setResult}/>)
        } else {
          return (<HeartTestLoading result={result} />)
        }
      })()}

    </div>
  )
}

export default HeartTest
