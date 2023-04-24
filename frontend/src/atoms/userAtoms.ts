import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const IsLoginAtom = atom<boolean>({
  key: 'isLogin',
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const userIdAtom = atom<string>({
  key: 'userId',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

export const userNicknameAtom = atom<string>({
  key: 'nickname',
  default: '',
  effects_UNSTABLE: [persistAtom],
});
