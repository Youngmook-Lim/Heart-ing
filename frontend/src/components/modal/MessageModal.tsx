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
import ButtonIcon from "../common/ButtonIcon";

// 더미데이터
const messageExample: IMessageDetailTypes = {
  messageId: "message id",
  title: "메시지 제목입니다",
  heartId: 1,
  heartName: "heart name",
  heartUrl: "url",
  emojiId: "emoji id",
  emojiName: "emoji name",
  emojiUrl: "emoji url",
  isRead: false,
  isStored: false,
  createdDate: "00000",
  expiredDate: "00000",
  isReported: false,
  content:
    "100자를 채우기 위한 여정... 쉽지 않다; 하지만 최대 길이에 맞춰서 레이아웃을 짜야하니까 어쩔 수 없어! 꽉 찼을 때랑 널널하게 찼을 때를 모두 고려해서 예쁜 높이 찾자~!",
  shortDescription: "하트 짧은 한줄 설명",
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
      <div className="modal-header bg-hrtColorOutline border-hrtColorOutline mb-4 flex">
        <div className="flex-auto">마음 읽기</div>
        <button onClick={() => closeModal()} className="flex-none">
          <ButtonIcon id={0} />
        </button>
      </div>
      {/* <p>{selectedMessageId} 메시지 / 지금은 더미데이터</p> */}
      <div className="mx-6">
        <MessageModalHeart
          heartId={messageData.heartId}
          heartUrl={messageData.heartUrl}
          heartName={messageData.heartName}
          heartContext={messageData.shortDescription}
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
      </div>
    </div>
  );
}

export default MessageModal;
