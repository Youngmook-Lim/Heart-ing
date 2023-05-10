import { atom } from 'recoil';

export const isFirstTimeAtom = atom<boolean>({
  key: 'isFirstTime',
  default: false,
});

export const isPopupShowAtom = atom<boolean>({
  key: 'isPopupShow',
  default: false,
})

export const isUpdateShowAtom = atom<boolean>({
  key: 'isUpdateShow',
  default: false,
})