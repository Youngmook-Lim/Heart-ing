export interface IHeartInfoTypes {
  heartId: number;
  name: string;
  heartUrl: string;
  type: string;
  isLocked: boolean;
  isAcq: boolean;
}

export interface IHeartConditionsTypes {
  heartId: number | null;
  name: string | null;
  heartUrl: string | null;
  currentValue: number;
  maxValue: number;
}

export interface IHeartDetailInfoTypes {
  heartId: number;
  name: string;
  heartUrl: string;
  shortDescription: string;
  longDescription: string;
  type: string;
  acqCondition: string;
  isLocked: boolean;
  isAcq: boolean;
  conditions: IHeartConditionsTypes[] | null;
}
