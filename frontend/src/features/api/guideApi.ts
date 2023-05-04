import { axios } from "./https";

export async function getAllHeartInfo() {
  try {
    const res = await axios.get(`api/v1/hearts`);
    const data = res.data;
    return data.data.heartList;
  } catch (err) {
    // console.log("하트 정보들을 불러오지 못했음~!");
    // console.log(err);
    return null;
  }
}

export async function getHeartDetailInfo(heartId: number) {
  try {
    const res = await axios.get(`api/v1/hearts/${heartId}`);
    const data = res.data;
    // console.log("하트 상세정보다 얍", data);
    return data.data;
  } catch (err) {
    // console.log("안대~상세정보 불러오기 실패자나~", err);
    return null;
  }
}

export async function acquireHeart(heartId: number) {
  try {
    const res = await axios.post(`api/v1/hearts/user-hearts/${heartId}`);
    const status = res.data.status;
    return status;
  } catch (err) {
    // console.log("획득 못함ㅠ");
    return null;
  }
}
