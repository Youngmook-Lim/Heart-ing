import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const isLoginAtom = atom<boolean>({
  key: 'isLogin',
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const userStautsMessageAtom = atom<string>({
  key: 'userStatusMessage',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

export const userNicknameAtom = atom<string>({
  key: 'nickname',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

export const sharingAtom = atom<boolean>({
  key: 'sharing',
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const sharingModeAtom = atom<string>({
  key: 'sharingMode',
  default: 'board',
});
