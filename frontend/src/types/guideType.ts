export interface IHeartInfoTypes {
    heartId : number,
    name : string,
    heartUrl : string,
    type: string,
    isLocked: boolean,
}

export interface IHeartDetailInfoTypes {
    heartId: number,
    name: string,
    imageUrl: string,
    shortDescription : string,
    longDescription : string,
    type : string,
    acqCondition : string,
    isLocked : boolean,
    sendCnt : number,
    receivedCnt : number,
}