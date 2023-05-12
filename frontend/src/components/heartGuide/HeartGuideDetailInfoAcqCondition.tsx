import { IHeartConditionsTypes } from "../../types/guideType";
import { useRecoilValue } from "recoil";
import { isLoginAtom } from "../../atoms/userAtoms";

import HeartGuideDetailInfoAcqConditionItem from "./HeartGuideDetailInfoAcqConditionItem";

interface HeartGuideDetailInfoAcqConditionProps {
  acqCondition: string;
  conditions: IHeartConditionsTypes[] | null;
  type: string;
}

function HeartGuideDetailInfoAcqCondition({
  acqCondition,
  conditions,
  type,
}: HeartGuideDetailInfoAcqConditionProps) {
  const isLogin = useRecoilValue(isLoginAtom); // 로그인 유무 확인

  return (
    <>
      <div className="textShadow mt-4">
        <p className="purple text-white text-left tracking-wider mx-1 cursor-default">
          획득조건
        </p>
      </div>
      <div className="border-2 border-hrtColorNewPurple p-4 mt-1 text-start rounded-sm cursor-default">
        {!isLogin && type !== "DEFAULT" ? (
          <p>로그인 후 확인 가능합니다.</p>
        ) : (
          <p>{acqCondition}</p>
        )}
        {conditions ? (
          conditions.map((condition, index) => (
            <HeartGuideDetailInfoAcqConditionItem
              key={index}
              condition={condition}
            />
          ))
        ) : type === "SPECIAL" && conditions !== null ? (
          <div className="flex">
            <div
              className="w-full h-4 bg-hrtColorPink my-2"
              style={{ width: "100%" }}
            ></div>
            <p className="w-10 ml-2 text-xl"> max </p>
          </div>
        ) : null}
      </div>
    </>
  );
}

export default HeartGuideDetailInfoAcqCondition;
