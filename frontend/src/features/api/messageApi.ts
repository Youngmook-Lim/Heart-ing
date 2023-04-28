import { axios } from "./https";
import { IMessageSendTypes } from "../../types/messageType";

export async function getReceived(userId: string) {
  try {
    const res = await axios.get(`api/v1/messages/received/${userId}`);
    const data = res.data;
    return data;
  } catch (err) {
    console.log("24시간 내 받은 메시지 조회에 실패했습니다.");
    console.log(err);
    return null;
  }
}

export async function sendMessageApi(body: IMessageSendTypes) {
  try {
    const res = await axios.post(`api/v1/messages`, body);
    const status = res.data.status;
    return status;
  } catch (err) {
    console.log(err);
    return null;
  }
}

export async function getMessageDetail(messageId: number) {
  try {
    const res = await axios.get(`api/v1/messages/received/detail/${messageId}`);
    const data = res.data;
    return data;
  } catch (err) {
    console.log("상세 메시지 보기에 실패했습니다");
    console.log(err);
    return null;
  }
}

export async function saveMessageApi(messageId: number) {
  try {
    const res = await axios.post(`api/v1/messages/inbox/${messageId}`);
    const status = res.data.status;
    return status;
  } catch (err) {
    console.log("저장 못함ㅠ");
    return null;
  }
}

export async function deletepermanentMessageApi(messageId: number) {
  try {
    const res = await axios.delete(`api/v1/messages/inbox/${messageId}`);
    const status = res.data.status;
    return status;
  } catch (err) {
    console.log("영구 삭제 못함ㅠ");
    return null;
  }
}

export async function deleteTemporaryMessageApi(messageId: number) {
  try {
    const res = await axios.delete(`api/v1/messages/${messageId}`);
    const status = res.data.status;
    return status;
  } catch (err) {
    console.log("24시간 삭제 못함ㅠ");
    return null;
  }
}

export async function getSave() {
  try {
    const res = await axios.get(`api/v1/messages/inbox`);
    const data = res.data;
    return data;
  } catch (err) {
    console.log("영구 보관 메시지 리스트 조회에 실패했습니다");
    console.log(err);
    return null;
  }
}

export async function getSent() {
  try {
    const res = await axios.get(`api/v1/messages/sent`);
    const data = res.data;
    return data;
  } catch (err) {
    console.log("보낸 메시지 리스트 조회에 실패했습니다");
    console.log(err);
    return null;
  }
}

export async function getMessageHeartApi() {
  try {
    const res = await axios.get("api/v1/hearts/user-hearts");
    const data = res.data;
    return data;
  } catch (err) {
    console.log("하트리스트 못 뽑았다여");
    console.log(err);
    return null;
  }
}
