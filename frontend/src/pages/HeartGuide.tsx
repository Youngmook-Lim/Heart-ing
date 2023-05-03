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
      <div className="text-3xl py-3 textShadow">
        <p className="text-hrtColorYellow">하트 도감</p>
      </div>
        <div className="">
          {allHeartInfoList ?
            <HeartGuideList allHeartInfoList={allHeartInfoList} onGetHeartDetailData={handleGetHeartDetailInfo} /> : null}
          </div>
          {openDetailInfo ?
            <HeartGuideDetailInfo heartDetailInfo={heartDetailInfo} /> : null }
    </>
  )
}

export default HeartGuide