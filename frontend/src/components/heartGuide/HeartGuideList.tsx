import HeartGuideListItem from "./HeartGuideListItem";
import { IHeartInfoTypes } from "../../types/guideType";

interface HeartGuideListProps {
  allHeartInfoList: IHeartInfoTypes[];
  onGetHeartDetailData: (value: number) => void;
}

function HeartGuideList({
  allHeartInfoList,
  onGetHeartDetailData,
}: HeartGuideListProps) {
  return (
    <div className=" mx-auto">
      <div className="bg-hrtColorWhiteTrans border-2 border-hrtColorPink rounded-lg relative ">
        <div className="overflow-auto guideHeight scrollbar-hide">
          <div className="grid grid-cols-3 content-center p-4">
            {allHeartInfoList.map((heartInfo) => (
              <HeartGuideListItem
                key={heartInfo.heartId}
                heartInfo={heartInfo}
                onGetHeartDetailData={onGetHeartDetailData}
              />
            ))}
          </div>
        </div>
        <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute left-1 top-1"></div>
        <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute right-1 top-1"></div>
        <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute left-1 bottom-1"></div>
        <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute right-1 bottom-1"></div>
      </div>
    </div>
  );
}

export default HeartGuideList;
