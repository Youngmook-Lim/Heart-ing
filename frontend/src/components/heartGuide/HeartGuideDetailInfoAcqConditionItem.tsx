import { IHeartConditionsTypes } from "../../types/guideType";

interface AcqConditionItemProps {
  condition: IHeartConditionsTypes;
}

function HeartGuideDetailInfoAcqConditionItem({
  condition,
}: AcqConditionItemProps) {
  const currentValue =
    condition.currentValue > condition.maxValue
      ? condition.maxValue
      : condition.currentValue;
  const maxValue = condition.maxValue;

  return (
    <>
      <div className="flex items-center cursor-default">
        {condition.heartId ? (
          <img
            className="w-6 mr-2"
            src={condition.heartUrl ? condition.heartUrl : undefined}
            alt="heartIcon"
          />
        ) : null}
        <div className="w-full h-4 bg-white border-2 border-hrtColorPink">
          <div
            className="h-full bg-hrtColorPink"
            style={{ width: `${(currentValue / maxValue) * 100}%` }}
          ></div>
        </div>
        <div className="ml-2">
          <p className="w-10">
            {currentValue} / {maxValue}
          </p>
        </div>
      </div>
    </>
  );
}

export default HeartGuideDetailInfoAcqConditionItem;
