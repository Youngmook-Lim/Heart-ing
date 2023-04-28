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
                    <div className="flex-auto">정보</div>
                    <button onClick={() => closeModal()} className="flex-none">
                        <ButtonIcon id={0} />
                    </button>
                </div>
                <div className="mx-6 my-auto">
                    {/* 여기서부터 */}
                    <img className="mx-auto my-auto" src='https://heart-ing.s3.ap-northeast-2.amazonaws.com/heart/heart_yellow_1.svg' alt='heartIcon' />
                    <div>하트하트</div>
                    <div className="mt-5">
                        <p className="text-left">스토리</p>
                    </div>
                        {/* <div>{ heartStory }</div> */}
                    <div className="border-2 border-purple-200">
                        <p className="text-base	leading-5 m-2">두려움을 이길 용기를 전할 때 태어난 하트
                            <br></br>너의 하늘을 날아봐! 더 높은 세상을 꿈꾸는 상대에게 날개를 달아준다.</p>
                    </div>
                    <div className="mt-5">
                        <p className="text-left">획득조건</p>
                    </div>
                    <div className="border-2 border-purple-200">
                        <p className="text-base	leading-5 m-2">기본제공</p>
                    </div>
                    {/* 여기까지 나중에 삭제 */}

                    {/* {heartDetailInfo && (
                        <>
                            <img className="mx-auto my-auto" src='https://heart-ing.s3.ap-northeast-2.amazonaws.com/heart/heart_yellow_1.svg' alt='heartIcon' />
                            {heartInfo.isLocked ? <div>??</div> : <div>{heartInfo.name}</div>}
                            <HeartGuideDetailInfoStory heartStory={heartDetailInfo.longDescription} />
                            <HeartGuideDetailInfoAcqCondition
                                acqCondition={heartDetailInfo.acqCondition}
                                sendCnt={heartDetailInfo.sendCnt}
                                receivedCnt={heartDetailInfo.receivedCnt}
                            />
                        </>
                    )} */}
                    <div className="mx-auto my-auto mt-5 mb-4 modal-button text-hrtColorOutline ">확인</div>
                </div>
            </div>
        </>
    )
}

export default HeartGuideDetailInfo