import HeartGuideListItem from "./HeartGuideListItem"
import { IHeartInfoTypes } from '../../types/guideType'

interface HeartGuideListProps {
    allHeartInfoList: IHeartInfoTypes[];
    onGetHeartDetailData: (value: number) => void;
}

function HeartGuideList({ allHeartInfoList, onGetHeartDetailData }: HeartGuideListProps) {
    
    return (
        <div className="container mx-auto p-6 pb-8 h-[calc(100vh-8rem)]">
            <div className="bg-hrtColorWhiteTrans border-2 border-hrtColorPink rounded-lg relative">
                <div className="grid grid-cols-3 p-3 mx-auto my-auto mb-8">
                    {allHeartInfoList.map((heartInfo) => (
                        <HeartGuideListItem
                        key={heartInfo.heartId}
                        heartInfo={heartInfo}
                        onGetHeartDetailData={onGetHeartDetailData}
                        />
                        ))}
                </div>
                <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute left-1 top-1"></div>
                <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute right-1 top-1"></div>
                <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute left-1 bottom-1"></div>
                <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute right-1 bottom-1"></div>
            </div>
        </div>
    )
}

export default HeartGuideList