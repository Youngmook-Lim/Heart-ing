import HeartGuideListItem from "./HeartGuideListItem"
import { IHeartInfoTypes } from '../../types/guideType'

interface HeartGuideListProps {
    allHeartInfoList: IHeartInfoTypes[];
    onGetHeartDetailData: (value: number) => void;
}

function HeartGuideList({ allHeartInfoList, onGetHeartDetailData }: HeartGuideListProps) {
    
    return (
        <div>
            {allHeartInfoList.map((heartInfo) => (
                <HeartGuideListItem
                    key={heartInfo.heartId}
                    heartInfo={heartInfo}
                    onGetHeartDetailData={onGetHeartDetailData}
                />
            ))}
        </div>
    )
}

export default HeartGuideList