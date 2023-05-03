import baseAxios from "axios";
import { deleteUserInfo, getUserInfo } from "../userInfo";
import { reissueTokenApi } from "./userApi";

export const axios = baseAxios.create({
  baseURL: process.env.REACT_APP_API,
  headers: {
    "Content-Type": "application/json",
  },
});

axios.interceptors.request.use((config) => {
  config.headers.Authorization = `Bearer ${getUserInfo().accessToken}`;
  return config;
});

axios.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    if (error.response && error.response.status) {
      if (error.response.status === 401) {
        if (error.response.data.message === "reissue") {
          const reissueToken = async function () {
            const data = await reissueTokenApi();
            if (data.status === "success") {
              window.localStorage.setItem("accessToken", data.data.accessToken);
            }
          };
          reissueToken();
        } else {
          deleteUserInfo();
          alert("다시 로그인해주세요");
          window.location.replace("/");
        }
        return new Promise(() => {});
      } else {
        return Promise.reject(error);
      }
    }
    return Promise.reject(error);
  }
);


export const nonAuthAxios = baseAxios.create({
  baseURL: process.env.REACT_APP_API,
  headers: {
    "Content-Type": "application/json",
  },
});
