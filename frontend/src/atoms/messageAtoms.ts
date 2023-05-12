import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();

export const readMessageAtom = atom<boolean>({
  key: "readMessage",
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const selectedMessageIdAtom = atom<number>({
  key: "selectedMessageId",
  default: -1,
  effects_UNSTABLE: [persistAtom],
});

export const isMyBoardAtom = atom<boolean>({
  key: "isMyBoard",
  default: false,
});

export const isOpenEmojiListAtom = atom<boolean>({
  key: "isOpenEmojiList",
  default: false,
})

export const isSelectedEmojiIdAtom = atom<number>({
  key: "isSelectedEmojiId",
  default: 0,
})

export const isSelectedEmojiUrlAtom = atom<string>({
  key: "isSelectedEmojiUrl",
  default: ""
})

export const isOpenReportingAtom = atom<boolean>({
  key: 'isOpenReporting',
  default: false,
});