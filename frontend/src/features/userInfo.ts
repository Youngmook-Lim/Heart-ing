import { IUserSavingInfoTypes } from "./../types/userType";

export const savingUserInfo = (userInfo: IUserSavingInfoTypes) => {
  window.localStorage.setItem("userId", userInfo.userId);
  window.localStorage.setItem("accessToken", userInfo.accessToken);
};

export const getUserInfo = () => {
  return {
    userId: window.localStorage.getItem("userId"),
    accessToken: window.localStorage.getItem("accessToken"),
  };
};
export const deleteUserInfo = () => {
  window.localStorage.removeItem("userId");
  window.localStorage.removeItem("accessToken");
  window.localStorage.removeItem("recoil-persist");
};
