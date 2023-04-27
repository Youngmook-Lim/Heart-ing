import HeartItemIcon from "../common/HeartItemIcon"
import HeartGuideDetailInfoAcqCondition from "./HeartGuideDetailInfoAcqCondition"
import HeartGuideDetailInfoStory from "./HeartGuideDetailInfoStory";
import ButtonIcon from "../common/ButtonIcon";

import { useSetRecoilState } from "recoil";
import { openDetailInfoAtom } from "../../atoms/guideAtoms";
import { IHeartDetailInfoTypes } from "../../types/guideType"

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
            <div className="modal border-hrtColorOutline">
                <div className="modal-header bg-hrtColorOutline border-hrtColorOutline mb-4 flex">
                    <div className="flex-auto">하트 정보</div>
                    <button onClick={() => closeModal()} className="flex-none">
                        <ButtonIcon id={0} />
                    </button>
                </div>
                {heartDetailInfo && (
                    <>
                        <HeartItemIcon id={heartDetailInfo.heartId} />
                        <HeartGuideDetailInfoStory heartStory={heartDetailInfo.longDescription} />
                        <HeartGuideDetailInfoAcqCondition
                            acqCondition={heartDetailInfo.acqCondition}
                            sendCnt={heartDetailInfo.sendCnt}
                            receivedCnt={heartDetailInfo.receivedCnt}
                        />
                    </>
                )}
            </div>
        </>
    )
}

export default HeartGuideDetailInfo