import { useEffect, useState } from 'react'
import ManualHome from '../components/manual/ManualHome'
import { getTotalHeartApi } from '../features/api/messageApi'

function Manual() {

  console.log("매뉴얼 페이지 렌더링")
  const [totalHeartCnt, setTotalHeartCnt] = useState<number>(0);

  useEffect(() => {
    const interval = setInterval(async() => {
      const totalCnt = await getTotalHeartApi()
      setTotalHeartCnt(totalCnt)
    }, 3000)
  
    return () => {
      clearInterval(interval)
    }
  }, [])

  return (    
    <div>
      <ManualHome totalHeartCnt={totalHeartCnt} />
    </div>
  )
}

export default Manual
