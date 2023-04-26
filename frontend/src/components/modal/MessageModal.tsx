import React from "react";
import { useState, useEffect } from "react";

import { useRecoilValue, useSetRecoilState } from "recoil";
import {
  readMessageAtom,
  selectedMessageIdAtom,
} from "../../atoms/messageAtoms";

import {
  IMessageDetailTypes,
  IMessageModalTypes,
} from "../../types/messageType";

import MessageModalButtonBox from "./MessageModalButtonBox";
import MessageModalHeart from "./MessageModalHeart";
import MessageModalTextbox from "./MessageModalTextbox";
import MessageModalTime from "./MessageModalTime";

// 더미데이터
const messageExample: IMessageDetailTypes = {
  messageId: "message id",
  title: "메시지 제목입니다",
  heartId: "heart id",
  heartName: "heart name",
  heartUrl: "url",
  emojiId: "emoji id",
  emojiName: "emoji name",
  emojiUrl: "emoji url",
  content: "안녕친구야 만나서 반가워 나는 메시지 더미 내용이야",
  isRead: false,
  isStored: false,
  createdDate: "00000",
  expiredDate: "00000",
  isReported: false,
};

function MessageModal({ mode }: IMessageModalTypes) {
  const setReadMessageAtom = useSetRecoilState(readMessageAtom);
  const selectedMessageId = useRecoilValue(selectedMessageIdAtom);

  const [messageData, setMessageData] = useState(messageExample);

  useEffect(() => {
    // 여기서 selectedMessageId의 메시지 정보를 가져옵니다
    setMessageData(messageExample);
  }, []);

  // 메시지 모달을 닫습니다
  const closeModal = () => {
    setReadMessageAtom(false);
  };

  return (
    <div className="modal border-hrtColorOutline">
      <div className="modal-header bg-hrtColorOutline">마음 읽기</div>
      여긴 모달창 {selectedMessageId} 의 메시지 정보를 불러올 것입니다
      <p>임시로 더미데이터를 prop으로 보냅니다</p>
      <p>{messageData.heartId}번 하트를 불러옵니다</p>
      <MessageModalHeart
        heartUrl={messageData.heartUrl}
        heartName={messageData.heartName}
        heartContext={"하트 한줄 설명이 없어요!!!"}
        emojiUrl={messageData.emojiUrl}
      />
      <MessageModalTime
        createdDate={messageData.createdDate}
        expiredDate={messageData.expiredDate}
        mode={mode}
      />
      <MessageModalTextbox
        title={messageData.title}
        content={messageData.content}
      />
      <MessageModalButtonBox mode={mode} isExpired={false} />
      <button onClick={() => closeModal()}>모달닫기</button>
    </div>
  );
}

export default MessageModal;
