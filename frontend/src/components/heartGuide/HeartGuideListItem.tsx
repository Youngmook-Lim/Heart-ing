import { IHeartInfoTypes } from '../../types/guideType'
import HeartItemIcon from "../common/HeartItem"

import { useSetRecoilState } from "recoil";
import { openDetailInfoAtom } from "../../atoms/guideAtoms"

interface HeartGuideListItemProps {
    heartInfo: IHeartInfoTypes;
    onGetHeartDetailData: (value: number) => void;
}

function HeartGuideListItem({ heartInfo, onGetHeartDetailData }: HeartGuideListItemProps) {
    const setOpenDetailInfoAtom = useSetRecoilState(openDetailInfoAtom)
    
    const openDetailInfo = () => {
        setOpenDetailInfoAtom(true)
        onGetHeartDetailData(heartInfo.heartId)
    }

    return (
        <div onClick={() => openDetailInfo()}>
            <img className='mx-auto my-auto' src={heartInfo.heartUrl} alt='heartIcon' />
            {heartInfo.isLocked ? <div>??</div> : <div>{heartInfo.name}</div>}
        </div>
    )
}

export default HeartGuideListItem