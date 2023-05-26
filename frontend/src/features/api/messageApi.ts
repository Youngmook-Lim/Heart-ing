import { axios } from "./https";
import {
  IMessageSendTypes,
  IResponseHeartTypes,
} from "../../types/messageType";

export async function getReceived(userId: string) {
  try {
    const res = await axios.get(`api/v1/messages/received/${userId}`);
    const data = res.data;
    return data;
  } catch (err) {
    return null;
  }
}

export async function sendMessageApi(body: IMessageSendTypes) {
  try {
    const res = await axios.post(`api/v1/messages`, body);
    const data = res.data;
    return data;
  } catch (err) {
    return err;
  }
}

export async function getMessageDetail(messageId: number) {
  try {
    const res = await axios.get(`api/v1/messages/received/detail/${messageId}`);
    const data = res.data;
    return data;
  } catch (err) {
    return null;
  }
}

export async function saveMessageApi(messageId: number) {
  try {
    const res = await axios.post(`api/v1/messages/inbox/${messageId}`);
    const status = res.data.status;
    return status;
  } catch (err) {
    return null;
  }
}

export async function deletepermanentMessageApi(messageId: number) {
  try {
    const res = await axios.delete(`api/v1/messages/inbox/${messageId}`);
    const status = res.data.status;
    return status;
  } catch (err) {
    return null;
  }
}

export async function deleteTemporaryMessageApi(messageId: number) {
  try {
    const res = await axios.delete(`api/v1/messages/${messageId}`);
    const status = res.data.status;
    return status;
  } catch (err) {
    return null;
  }
}

export async function getSave() {
  try {
    const res = await axios.get(`api/v1/messages/inbox`);
    const data = res.data;
    return data;
  } catch (err) {
    return null;
  }
}

export async function getSent() {
  try {
    const res = await axios.get(`api/v1/messages/sent`);
    const data = res.data;
    return data;
  } catch (err) {
    return null;
  }
}

export async function getSentMessageDetailApi(messageId: number) {
  try {
    const res = await axios.get(`api/v1/messages/sent/${messageId}`);
    const data = res.data;
    return data;
  } catch (err) {
    return null;
  }
}

export async function getMessageHeartApi() {
  try {
    const res = await axios.get("api/v1/hearts/user-hearts");
    const data = res.data;
    return data;
  } catch (err) {
    return null;
  }
}

export async function getTotalHeartApi() {
  try {
    const res = await axios.get("api/v1/home/total-count");
    const data = res.data;
    return data.data.totalHeartCount;
  } catch (err) {
    return null;
  }
}

export async function responseHeartApi({
  messageId,
  emojiId,
}: IResponseHeartTypes) {
  try {
    const res = await axios.post(
      `api/v1/messages/${messageId}/emojis/${emojiId}`
    );
    const data = res.data;
    return data;
  } catch (err) {
    return null;
  }
}

export async function reportMessageApi(messageId: number, body: string) {
  try {
    const res = await axios.post(`api/v1/messages/${messageId}/reports`, body);
    const data = res.data;
    return data;
  } catch (err) {
    return err;
  }
}
