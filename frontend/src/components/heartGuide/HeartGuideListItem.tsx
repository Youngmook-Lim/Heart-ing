import { IHeartInfoTypes } from "../../types/guideType";

import { useSetRecoilState } from "recoil";
import { openDetailInfoAtom } from "../../atoms/guideAtoms";

interface HeartGuideListItemProps {
  heartInfo: IHeartInfoTypes;
  onGetHeartDetailData: (value: number) => void;
}

function HeartGuideListItem({
  heartInfo,
  onGetHeartDetailData,
}: HeartGuideListItemProps) {
  const setOpenDetailInfoAtom = useSetRecoilState(openDetailInfoAtom);

  const openDetailInfo = () => {
    setOpenDetailInfoAtom(true);
    onGetHeartDetailData(heartInfo.heartId);
  };

  return (
    <div onClick={() => openDetailInfo()} className="p-2">
        <div className="flex justify-center relative">
        <img className="mx-auto" src={heartInfo.heartUrl} alt="heartIcon" />
        { heartInfo.isAcq && heartInfo.isLocked ? <div className="bg-hrtColorNewRed w-4	h-4	mx-4 my-3 rounded-full border-2	border-white absolute left-1/2 bottom-1/2	"></div> : null }
        </div>
      <div className="cursor-default">
        {heartInfo.heartId === 14 && heartInfo.isLocked ? "??" : heartInfo.name}</div>
    </div>
  );
}

export default HeartGuideListItem;
