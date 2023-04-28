import { atom } from "recoil";

export const openDetailInfoAtom = atom<boolean>({
  key: "openDetailInfo",
  default: false,
});