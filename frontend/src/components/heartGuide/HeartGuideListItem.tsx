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
        <>
            <HeartItemIcon id={heartInfo.heartId} onClick={openDetailInfo} />
            <div>{heartInfo.name}</div>
        </>
    )
}

export default HeartGuideListItem