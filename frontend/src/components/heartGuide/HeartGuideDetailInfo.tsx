import { useEffect, useState } from "react";

import { useSetRecoilState } from "recoil";
import { openDetailInfoAtom } from "../../atoms/guideAtoms";
import { IHeartDetailInfoTypes } from "../../types/guideType";
import { acquireHeart } from "../../features/api/guideApi";
import { ReactComponent as HeartLock } from "../../assets/images/pixel/heart/heart_lock_1.svg";
import HeartGuideDetailInfoAcqCondition from "./HeartGuideDetailInfoAcqCondition";
import HeartGuideDetailInfoStory from "./HeartGuideDetailInfoStory";
import ButtonIcon from "../common/ButtonIcon";

interface HeartGuideDetailInfoProps {
  heartDetailInfo: IHeartDetailInfoTypes | null;
}

function HeartGuideDetailInfo({ heartDetailInfo }: HeartGuideDetailInfoProps) {
  const [heartId, setHeartId] = useState(0);
  const setOpenDetailInfoAtom = useSetRecoilState(openDetailInfoAtom);

  useEffect(() => {
    if (heartDetailInfo) setHeartId(heartDetailInfo.heartId);
  }, [heartDetailInfo]);

  const closeModal = () => {
    setOpenDetailInfoAtom(false);
  };

  const onAcquireHandler = async (e: React.MouseEvent<HTMLDivElement>) => {
    const data = await acquireHeart(heartId);
    if (data.status === "success") {
      alert("하트를 획득했습니다.");
      window.location.reload();
    } else {
      window.location.reload();
    }
  };

  return (
    <>
      <div className="h-screen w-full fixed left-0 top-0 bg-black bg-opacity-30 text-center flex  items-center justify-center z-40">
        <div className="container modal border-hrtColorOutline m-6 max-w-xs maxFullHeight">
          <div className="modal-header bg-hrtColorOutline border-hrtColorOutline mb-4 flex">
            <div className="flex-auto cursor-default">정보</div>
            <button onClick={() => closeModal()} className="flex-none">
              <ButtonIcon id={0} />
            </button>
          </div>
          <div className="mx-6 my-auto">
            {heartDetailInfo && (
              <>
                {heartDetailInfo.isLocked ? (
                  (heartDetailInfo.heartId === 14 ?
                    <div className="flex justify-center items-center py-2"><HeartLock /> </div>
                    :
                    <img
                    className="w-2/6 mx-auto my-auto opacity-20"
                    src={heartDetailInfo.heartUrl}
                    alt="heartIcon"
                  />) 
                ) : (
                  <img
                    className="w-2/6 mx-auto my-auto"
                    src={heartDetailInfo.heartUrl}
                    alt="heartIcon"
                  />
                )}
                <div className="text-2xl cursor-default">
                  {heartDetailInfo.heartId === 14 && heartDetailInfo.isLocked ? "??"
                    :
                  heartDetailInfo.name}
                  하트</div>
                <div className="mt-4">
                  <HeartGuideDetailInfoStory
                    heartStory={heartDetailInfo.longDescription}
                    heartId={heartDetailInfo.heartId}
                    isLocked={heartDetailInfo.isLocked}
                  />
                  <HeartGuideDetailInfoAcqCondition
                    acqCondition={heartDetailInfo.acqCondition}
                    conditions={heartDetailInfo.conditions}
                    type={heartDetailInfo.type}
                  />
                </div>
              </>
            )}
            {heartDetailInfo && heartDetailInfo.isAcq === true ? (
              <div
                className="mx-auto my-auto mt-5 mb-4 modal-button text-hrtColorOutline bg-hrtColorYellow cursor-pointer"
                onClick={(e) => onAcquireHandler(e)}
              >
                획득
              </div>
            ) : (
              <div
                className="mx-auto my-auto mt-5 mb-4 modal-button text-hrtColorOutline cursor-pointer"
                onClick={() => closeModal()}
              >
                닫기
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
}

export default HeartGuideDetailInfo;
