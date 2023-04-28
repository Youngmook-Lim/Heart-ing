import { axios } from "./https";

export async function getAllHeartInfo() {
  try {
    const res = await axios.get(`api/v1/hearts`);
    const data = res.data;
    return data.data.heartList;
  } catch (err) {
    console.log("하트 정보들을 불러오지 못했음~!");
    console.log(err)
    return null;
  }
}

export async function getHeartDetailInfo(heartId:number) {
  try {
    const res = await axios.get(`api/v1/hearts/${heartId}`);
    const data = res.data;
    return data;
  } catch (err) {
    return null;
  }
}