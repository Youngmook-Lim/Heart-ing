import { useEffect, useState } from 'react'
import { getAllHeartInfo, getHeartDetailInfo } from '../features/api/guide'
import { useRecoilValue } from 'recoil'
import { openDetailInfoAtom } from '../atoms/guideAtoms'
import { IHeartDetailInfoTypes } from '../types/guideType'
import HeartGuideList from '../components/heartGuide/HeartGuideList'
import HeartGuideDetailInfo from '../components/heartGuide/HeartGuideDetailInfo'

function HeartGuide() {

  const [allHeartInfoList, setAllHeartInfoList] = useState([])
  const [heartDetailInfo, setHeartDetailInfo] = useState<IHeartDetailInfoTypes | null>(null)
  const openDetailInfo = useRecoilValue(openDetailInfoAtom)


  async function getHeartData() {
    const allHeartInfoList = await getAllHeartInfo()
    setAllHeartInfoList(allHeartInfoList)
  }

  async function handleGetHeartDetailInfo(heartId: number) {
    const heartDetailInfoData = await getHeartDetailInfo(heartId)
    setHeartDetailInfo(heartDetailInfoData)
  }

  useEffect(() => {
    getHeartData()
  }, [])
  

  return (
    <div>
      <HeartGuideList allHeartInfoList={allHeartInfoList} onGetHeartDetailData={handleGetHeartDetailInfo}/>
      {openDetailInfo ? <HeartGuideDetailInfo heartDetailInfo={heartDetailInfo} /> : null }
    </div>
  )
}

export default HeartGuide