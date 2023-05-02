import { IHeartConditionsTypes } from "../../types/guideType"

interface AcqConditionItemProps {
  condition: IHeartConditionsTypes
}

function HeartGuideDetailInfoAcqConditionItem({condition}: AcqConditionItemProps) {

  const currentValue = condition.currentValue
  const maxValue = condition.maxValue

  const percentage = () => {
    const calculated = (currentValue / maxValue) * 100
    if( calculated > 100) {
      return 100
    }
    return calculated
  }

  return (
    <>
    <div className="w-full h-4 bg-white border-1 border-hrtColorPink" style={{ width: `${percentage}%` }}>
      <div className="h-full bg-hrtColorPink">
      </div>
    </div>
    <p>{currentValue} / {maxValue}</p>
    </>
  )
}

export default HeartGuideDetailInfoAcqConditionItem