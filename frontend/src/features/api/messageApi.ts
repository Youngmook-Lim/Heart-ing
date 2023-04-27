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

export async function sendMessage(body: IMessageSendTypes) {
  try {
    const res = await axios.post(`api/v1/messages`, body);
    const data = res.data;
    return data;
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
    const res = await axios.post(`api/v1/messages/inbox/${messageId}`)
    const status = res.data.status
    return status
  } catch (err) {
    console.log('저장 못함ㅠ')
    return null
  }
}

export async function deletepermanentMessageApi(messageId: number) {
  try {
    const res = await axios.delete(`api/v1/messages/inbox/${messageId}`)
    const status = res.data.status
    return status
  } catch (err) {
    console.log('영구 삭제 못함ㅠ')
    return null
  }
}

export async function deleteTemporaryMessageApi(messageId: number) {
  try {
    const res = await axios.delete(`api/v1/messages/${messageId}`)
    const status = res.data.status
    return status
  } catch (err) {
    console.log('24시간 삭제 못함ㅠ')
    return null
  }
}