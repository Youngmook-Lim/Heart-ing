import { IHeartConditionsTypes } from "../../types/guideType"
import HeartGuideDetailInfoAcqConditionItem from "./HeartGuideDetailInfoAcqConditionItem"

interface HeartGuideDetailInfoAcqConditionProps {
    acqCondition: string,
    conditions: IHeartConditionsTypes[] | null,
}

function HeartGuideDetailInfoAcqCondition({ acqCondition, conditions }:HeartGuideDetailInfoAcqConditionProps) {
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
                : null}
            </div>
        </>
    )
}

export default HeartGuideDetailInfoAcqCondition