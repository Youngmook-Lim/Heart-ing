import HeartItemIcon from "../common/HeartItemIcon";
import HeartGuideDetailInfoAcqCondition from "./HeartGuideDetailInfoAcqCondition";
import HeartGuideDetailInfoStory from "./HeartGuideDetailInfoStory";
import ButtonIcon from "../common/ButtonIcon";

import { useSetRecoilState } from "recoil";
import { openDetailInfoAtom } from "../../atoms/guideAtoms";
import { IHeartDetailInfoTypes } from "../../types/guideType";

interface HeartGuideDetailInfoProps {
  heartDetailInfo: IHeartDetailInfoTypes | null;
}

function HeartGuideDetailInfo({ heartDetailInfo }: HeartGuideDetailInfoProps) {
  const setOpenDetailInfoAtom = useSetRecoilState(openDetailInfoAtom);

  const closeModal = () => {
    setOpenDetailInfoAtom(false);
  };

  return (
    <>
      <div className="fullHeight w-full fixed left-0 top-0 bg-black bg-opacity-70 text-center flex  items-center justify-center ">
        <div className="container modal border-hrtColorOutline m-6 w-full">
          <div className="modal-header bg-hrtColorOutline border-hrtColorOutline mb-4 flex">
            <div className="flex-auto">정보</div>
            <button onClick={() => closeModal()} className="flex-none">
              <ButtonIcon id={0} />
            </button>
          </div>
          <div className="mx-6 my-auto">
            {heartDetailInfo && (
              <>
                <img
                  className="mx-auto my-auto"
                  src="https://heart-ing.s3.ap-northeast-2.amazonaws.com/heart/heart_yellow_1.svg"
                  alt="heartIcon"
                />
                {heartDetailInfo.isLocked ? (
                  <div>??</div>
                ) : (
                  <div>{heartDetailInfo.name}</div>
                )}
                <HeartGuideDetailInfoStory
                  heartStory={heartDetailInfo.longDescription}
                />
                <HeartGuideDetailInfoAcqCondition
                  acqCondition={heartDetailInfo.acqCondition}
                  sendCnt={heartDetailInfo.sendCnt}
                  receivedCnt={heartDetailInfo.receivedCnt}
                />
              </>
            )}
            <div
              className="mx-auto my-auto mt-5 mb-4 modal-button text-hrtColorOutline"
              onClick={() => closeModal()}
            >
              확인
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default HeartGuideDetailInfo;
