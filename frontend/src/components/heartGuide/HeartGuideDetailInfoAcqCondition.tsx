interface HeartGuideDetailInfoAcqConditionProps {
    acqCondition: string,
    sendCnt: number,
    receivedCnt: number,
}

function HeartGuideDetailInfoAcqCondition({ acqCondition, sendCnt, receivedCnt }:HeartGuideDetailInfoAcqConditionProps) {
    return (
        <>
            <div>
                <p>획득조건</p>
            </div>
            <div>{ acqCondition }</div>
        </>
    )
}

export default HeartGuideDetailInfoAcqCondition