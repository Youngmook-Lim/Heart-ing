import { axios } from "./https";

export async function getAllHeartInfo() {
  try {
    const res = await axios.get(`api/v1/hearts`);
    const data = res.data;
    return data.data.heartList;
  } catch (err) {
    return null;
  }
}

export async function getHeartDetailInfo(heartId: number) {
  try {
    const res = await axios.get(`api/v1/hearts/${heartId}`);
    const data = res.data;
    return data.data;
  } catch (err) {
    return null;
  }
}

export async function acquireHeart(heartId: number) {
  try {
    const res = await axios.post(`api/v1/hearts/user-hearts/${heartId}`);
    const status = res.data.status;
    return status;
  } catch (err) {
    return null;
  }
}
