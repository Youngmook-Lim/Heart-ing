import React from "react";
import { useState, useEffect } from "react";

import { useRecoilValue, useSetRecoilState, useRecoilState } from "recoil";
import {
  readMessageAtom,
  selectedMessageIdAtom,
  isOpenEmojiListAtom,
  isSelectedEmojiIdAtom,
  isSelectedEmojiUrlAtom,
  reportContentAtom,
} from "../../atoms/messageAtoms";

import {
  IMessageDetailTypes,
  IMessageModalTypes,
  IResponseHeartTypes,
} from "../../types/messageType";

import MessageModalButtonBox from "./MessageModalButtonBox";
import MessageModalHeart from "./MessageModalHeart";
import MessageModalTextbox from "./MessageModalTextbox";
import MessageModalTime from "./MessageModalTime";
import ButtonIcon from "../common/ButtonIcon";
import {
  getMessageDetail,
  getSentMessageDetailApi,
  responseHeartApi,
  reportMessageApi,
} from "../../features/api/messageApi";
import HeartResponseEmojiList from "../heartResponse/HeartResponseEmojiList";

function MessageModal({ mode }: IMessageModalTypes) {

  const setReadMessageAtom = useSetRecoilState(readMessageAtom);
  const selectedMessageId = useRecoilValue(selectedMessageIdAtom);
  const [ isOpenEmojiList, setIsOpenEmojiList ]  = useRecoilState(isOpenEmojiListAtom);
  const setIsSelectedEmojiUrl  = useSetRecoilState(isSelectedEmojiUrlAtom)
  const setIsSelectedEmojiId = useSetRecoilState(isSelectedEmojiIdAtom)
  const reportContent = useRecoilValue(reportContentAtom)
  
  const [messageData, setMessageData] = useState<IMessageDetailTypes>();

  // selectedMessageId로 상세 메시지 정보 가져오기
  async function getRecivedMessages(selectedMessageId: number | null) {
    if (!selectedMessageId) return;
    const data = await getMessageDetail(selectedMessageId);
    if (data.status === "success") {
      setMessageData(data.data);
      setIsSelectedEmojiUrl(data.data.emojiUrl)
    }
  }

  async function getSentMessageDetail(selectedMessageId: number | null) {
    if (!selectedMessageId) return;
    const data = await getSentMessageDetailApi(selectedMessageId);
    if (data.status === "success") {
      setMessageData(data.data);
      setIsSelectedEmojiUrl(data.data.emojiUrl)
    }
  }

  async function onEmojiHandler(emojiId:number) {
    const EmojiInfo: IResponseHeartTypes = {
      messageId: selectedMessageId,
      emojiId: emojiId,
    }
    const data = await responseHeartApi(EmojiInfo)
    if (data.status === 'success') {
      setIsSelectedEmojiUrl(() => data.data.emojiUrl)
    }
  }

  async function reportMessage(messageId:number) {
    const body: string = reportContent

    const data = await reportMessageApi(messageId, body)
    if (data.status === 'success') {
      console.log("너어~~ 나쁜 말 한거 다 말했어~!~!")
    }
  }
    
    useEffect(() => {
      // 여기서 selectedMessageId의 메시지 정보를 가져옵니다
      if (mode === "sent") {
        getSentMessageDetail(selectedMessageId);
        
      } else {
        getRecivedMessages(selectedMessageId);
      }

      return () => {
        setIsOpenEmojiList(false)
        setIsSelectedEmojiId(0)
        setIsSelectedEmojiUrl("")
      }
    }, [mode, selectedMessageId]);
    
    // 메시지 모달을 닫습니다
    const closeModal = () => {
      setReadMessageAtom(false);
      setIsOpenEmojiList(false)
    };
    

  const curr = new Date();
  const utc = curr.getTime() + curr.getTimezoneOffset() * 60 * 1000;
  const KR_TIME_DIFF = 9 * 60 * 60 * 1000; //한국 시간(KST)은 UTC시간보다 9시간 더 빠름
  const kr_curr = new Date(utc + KR_TIME_DIFF); //utc 밀리초 값에 9시간을 더함

  if (messageData) {
    const expiredDate = new Date(messageData.expiredDate);
    const isExpired = kr_curr > expiredDate; // 현재 시간보다 만료 시간이 더 과거이면 만료됨
    return (
      <div className="App w-full fixed left-0 top-0 bg-black bg-opacity-30 text-center flex  items-center justify-center z-40">
        <div className="container modal border-hrtColorOutline m-6 max-w-xs">
          <div className="modal-header bg-hrtColorOutline border-hrtColorOutline mb-4 flex">
            <div className="flex-auto">하트 읽기</div>
            <button onClick={() => closeModal()} className="flex-none">
              <ButtonIcon id={0} />
            </button>
          </div>
          {/* <p>{selectedMessageId} 메시지 / 지금은 더미데이터</p> */}
          <div className="relative mx-6">
            <MessageModalHeart
              heartId={messageData.heartId}
              heartUrl={messageData.heartUrl}
              heartName={messageData.heartName}
              heartContext={messageData.shortDescription}
              emojiUrl={messageData.emojiUrl}
            />
            <MessageModalTime
              kr_curr={kr_curr}
              createdDate={messageData.createdDate}
              expiredDate={messageData.expiredDate}
              mode={mode}
            />
            <MessageModalTextbox
              title={messageData.title}
              content={messageData.content}
            />
            <div className="absolute top-40 w-full h-auto">
              {isOpenEmojiList ? <HeartResponseEmojiList messageEmojiId={ messageData.emojiId } onEmojiHandler={ onEmojiHandler }/> : null }
            </div>
            <MessageModalButtonBox
              mode={mode}
              isExpired={isExpired}
              isStored={messageData?.isStored}
            />
          </div>
        </div>
      </div>
    );
  } else {
    return <div></div>;
  }
}

export default MessageModal;
