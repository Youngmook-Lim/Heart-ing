import { IHeartInfoTypes } from '../../types/guideType'

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
            <img className='mx-auto mt-6' src={heartInfo.heartUrl} alt='heartIcon' />
             <div>{heartInfo.name}</div>
        </div>
    )
}

export default HeartGuideListItem