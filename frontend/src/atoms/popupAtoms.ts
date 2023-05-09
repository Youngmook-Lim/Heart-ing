import { atom } from 'recoil';

export const isFirstTimeAtom = atom<boolean>({
  key: 'isFirstTime',
  default: false,
});

export const isPopupShowAtom = atom<boolean>({
  key: 'isPopupShow',
  default: false,
})