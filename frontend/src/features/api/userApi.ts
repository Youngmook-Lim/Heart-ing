import { ITwitterUserTokenTypes } from "../../types/userType";
import { axios, nonAuthAxios } from "./https";

export async function login(provider: string, code: string) {
  try {
    const res = await nonAuthAxios.get(
      `api/v1/auth/guests/social/${provider}?code=${code}`
    );
    const data = res.data;
    return data;
  } catch (err) {
    return err;
  }
}

export async function twitterRedirectApi() {
  try {
    const res = await nonAuthAxios.get(
      "/api/v1/auth/guests/twitter/redirect-url"
    );
    const data = res.data;
    return data;
  } catch (err) {
    return err;
  }
}

export async function twitterLoginApi(body: ITwitterUserTokenTypes) {
  try {
    const res = await nonAuthAxios.post(
      `api/v1/auth/guests/twitter/user-info`,
      body
    );
    const data = res.data;
    return data;
  } catch (err) {
    return err;
  }
}

export async function modifyNickname(nickname: object) {
  try {
    const res = await axios.patch("api/v1/auth/users/nickname", nickname);
    const status = res.data.status;
    return status;
  } catch (err) {
    return err;
  }
}

export async function modifyStatusMessage(statusMessage: object) {
  try {
    const res = await axios.patch(
      "api/v1/auth/users/status-message",
      statusMessage
    );
    const status = res.data.status;
    return status;
  } catch (err) {
    return err;
  }
}

export async function logout() {
  try {
    const res = await axios.patch("api/v1/auth/users/logout");
    const status = res.data.status;
    return status;
  } catch (err) {
    return err;
  }
}

export async function getProfile(userId: string) {
  try {
    const res = await nonAuthAxios.get(`api/v1/auth/guests/${userId}`);
    const data = res.data;
    return data;
  } catch (err) {
    return err;
  }
}

export async function reissueTokenApi() {
  try {
    const res = await axios.get("api/v1/auth/users/access-token");
    const data = res.data;
    return data;
  } catch (err) {
    return err;
  }
}

export async function getNotificationApi() {
  try {
    const res = await axios.get("/api/v1/notifications");
    const data = res.data;
    return data;
  } catch (err) {
    return err;
  }
}

export async function readNotificationApi(notificationId: number) {
  try {
    const res = await axios.post(`/api/v1/notifications/${notificationId}`);
    const data = res.data;
    return data;
  } catch (err) {
    return err;
  }
}
