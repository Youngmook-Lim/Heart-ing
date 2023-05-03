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

    const closeModal = () => {
        setOpenDetailInfoAtom(false);
    };

    return (
        <>
            <div className="h-screen w-full fixed left-0 top-0 bg-black bg-opacity-70 text-center flex  items-center justify-center ">
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
                            {heartDetailInfo.isLocked ? 
                                <img className="w-2/6 mx-auto my-auto opacity-30" src={heartDetailInfo.heartUrl} alt='heartIcon' />
                            : <img className="w-2/6 mx-auto my-auto" src={heartDetailInfo.heartUrl} alt='heartIcon' />}
                                 <div className="text-2xl">{heartDetailInfo.name}하트</div>
                                 <div className="mt-4">
                                    <HeartGuideDetailInfoStory heartStory={heartDetailInfo.longDescription} />
                                    <HeartGuideDetailInfoAcqCondition
                                        acqCondition={heartDetailInfo.acqCondition}
                                        conditions={heartDetailInfo.conditions}
                                        type={heartDetailInfo.type}
                                    />
                                 </div>
                            </>
                        )}
                        <div className="mx-auto my-auto mt-5 mb-4 modal-button text-hrtColorOutline" onClick={() => closeModal()}>닫기</div>
                        {/* <div className="mx-auto my-auto mt-5 mb-4 modal-button text-hrtColorOutline" onClick={() => closeModal()}>획득</div> */}
                    </div>
                </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default HeartGuideDetailInfo;
