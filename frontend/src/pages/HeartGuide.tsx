import { useEffect, useState } from 'react'
import { getAllHeartInfo, getHeartDetailInfo } from '../features/api/guideApi'
import { useRecoilValue } from 'recoil'
import { openDetailInfoAtom } from '../atoms/guideAtoms'
import { IHeartInfoTypes, IHeartDetailInfoTypes } from '../types/guideType'
import HeartGuideList from '../components/heartGuide/HeartGuideList'
import HeartGuideDetailInfo from '../components/heartGuide/HeartGuideDetailInfo'

function HeartGuide() {

  const [allHeartInfoList, setAllHeartInfoList] = useState<IHeartInfoTypes[]>([])
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
    <>
      <h1>마음 도감</h1>
      <div className="container mx-auto px-6 py-8">
      <div className="modal border-hrtColorPink">
        {allHeartInfoList ?
          <HeartGuideList allHeartInfoList={allHeartInfoList} onGetHeartDetailData={handleGetHeartDetailInfo} /> : null}
        </div>
        {openDetailInfo ?
          <HeartGuideDetailInfo heartDetailInfo={heartDetailInfo} /> : null }
      </div>
    </>
  )
}

export default HeartGuide