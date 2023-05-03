import { IHeartConditionsTypes } from "../../types/guideType"
import HeartGuideDetailInfoAcqConditionItem from "./HeartGuideDetailInfoAcqConditionItem"

interface HeartGuideDetailInfoAcqConditionProps {
    acqCondition: string,
    conditions: IHeartConditionsTypes[] | null,
    type: string,
}

function HeartGuideDetailInfoAcqCondition({ acqCondition, conditions, type }:HeartGuideDetailInfoAcqConditionProps) {
    return (
        <>
            <div className="textShadow mt-4">
                <p className="purple text-white text-left tracking-wider mx-1">획득조건</p>
            </div>
            <div className="border-2 border-hrtColorNewPurple p-4 mt-1 text-start rounded-sm">
                <p>{ acqCondition }</p>
                {conditions ? 
                    conditions.map((condition, index) => (
                        <HeartGuideDetailInfoAcqConditionItem 
                            key={index}
                            condition={condition}
                        />
                    ))
                    : (type === 'SPECIAL' ?
                        <div className="flex">
                            <div className="w-full h-4 bg-hrtColorPink my-2" style={{ width: "100%" }}></div>
                            <p className="w-10 ml-2 text-xl"> max </p>
                        </div>
                        : null )}
            </div>
        </>
    )
}

export default HeartGuideDetailInfoAcqCondition