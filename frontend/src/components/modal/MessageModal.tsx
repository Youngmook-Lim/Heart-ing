import React from "react";
import { AxiosError } from "axios";
import { useState, useEffect } from "react";

import { useRecoilValue, useSetRecoilState, useRecoilState } from "recoil";
import {
  readMessageAtom,
  selectedMessageIdAtom,
  isOpenEmojiListAtom,
  isSelectedEmojiIdAtom,
  isSelectedEmojiUrlAtom,
  isOpenReportingAtom,
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
  deleteTemporaryMessageApi,
} from "../../features/api/messageApi";
import HeartResponseEmojiList from "../heartResponse/HeartResponseEmojiList";

function MessageModal({ ...props }) {
  const setReadMessageAtom = useSetRecoilState(readMessageAtom);
  const selectedMessageId = useRecoilValue(selectedMessageIdAtom);
  const [isOpenEmojiList, setIsOpenEmojiList] =
    useRecoilState(isOpenEmojiListAtom);
  const setIsSelectedEmojiUrl = useSetRecoilState(isSelectedEmojiUrlAtom);
  const setIsSelectedEmojiId = useSetRecoilState(isSelectedEmojiIdAtom);

  const [isOpenReporting, setIsOpenReporting] =
    useRecoilState(isOpenReportingAtom);

  const setIsOpenReporting = useSetRecoilState(isOpenReportingAtom)
  
  const [messageData, setMessageData] = useState<IMessageDetailTypes>();

  // selectedMessageId로 상세 메시지 정보 가져오기
  async function getRecivedMessages(selectedMessageId: number | null) {
    if (!selectedMessageId) return;
    const data = await getMessageDetail(selectedMessageId);
    if (data.status === "success") {
      setMessageData(data.data);
      setIsSelectedEmojiUrl(data.data.emojiUrl);
    }
  }

  async function getSentMessageDetail(selectedMessageId: number | null) {
    if (!selectedMessageId) return;
    const data = await getSentMessageDetailApi(selectedMessageId);
    if (data.status === "success") {
      setMessageData(data.data);
      setIsSelectedEmojiUrl(data.data.emojiUrl);
    }
  }

  async function onEmojiHandler(emojiId: number) {
    const EmojiInfo: IResponseHeartTypes = {
      messageId: selectedMessageId,
      emojiId: emojiId,
    };
    const data = await responseHeartApi(EmojiInfo);
    if (data.status === "success") {
      setIsSelectedEmojiUrl(() => data.data.emojiUrl);
      if (data.data.senderId) {
        if (props.socket && props.socket.connected) {
          props.socket.emit("send-message", data.data.senderId, data.data);
        }
      }
    }
  }

  async function onDeleteHandler() {
    const status = await deleteTemporaryMessageApi(selectedMessageId);
    if (status === "success") {
      alert("메세지를 삭제했습니다");
      setReadMessageAtom(false);
    }
  }

  //신고하기 api
  async function reportMessage(content: string) {
    const body: string = content;

    const data = await reportMessageApi(selectedMessageId, body)
    if (data.status === 'success') {
        if (window.confirm("신고가 정상적으로 요청 되었습니다. 해당 메세지를 삭제하시겠습니까?")) {
          onDeleteHandler()
        }
        setIsOpenReporting(false);
      } else {
      if (data && (data as AxiosError).response?.status === 400){
        alert("해당 메세지는 이미 신고 되었습니다.")
      } else {
        alert("신고하기가 실패했습니다. 나중에 다시 시도해주세요.");
      }
      setIsOpenReporting(false);
    }
  }

  const onOpenReporting = () => {
    setIsOpenReporting(true);
  };

  useEffect(() => {
    // 여기서 selectedMessageId의 메시지 정보를 가져옵니다
    if (props.mode === "sent") {
      getSentMessageDetail(selectedMessageId);
    } else {
      getRecivedMessages(selectedMessageId);
    }

    return () => {
      setIsOpenEmojiList(false);
      setIsSelectedEmojiId(0);
      setIsSelectedEmojiUrl("");
    };
  }, [props.mode, selectedMessageId]);

  // 메시지 모달을 닫습니다
  const closeModal = () => {
    setReadMessageAtom(false);
    setIsOpenEmojiList(false);
  };

  const curr = new Date();
  const utc = curr.getTime() + curr.getTimezoneOffset() * 60 * 1000;
  const KR_TIME_DIFF = 9 * 60 * 60 * 1000; //한국 시간(KST)은 UTC시간보다 9시간 더 빠름
  const kr_curr = new Date(utc + KR_TIME_DIFF); //utc 밀리초 값에 9시간을 더함

  if (messageData) {
    const expiredDate = new Date(messageData.expiredDate);
    const isExpired = kr_curr > expiredDate; // 현재 시간보다 만료 시간이 더 과거이면 만료됨

    let color = "bg-[#43316b] shadow-[0_0_0_0.25rem_#43316b]";
    let borderColor = "shadow-[0_0_0_0.25rem_#43316b]";
    if (messageData.heartId === 1) {
      color = "bg-[#FF9904] shadow-[0_0_0_0.25rem_#FF9904]";
      borderColor = "shadow-[0_0_0_0.25rem_#FF9904]";
    } else if (messageData.heartId === 2) {
      borderColor = "shadow-[0_0_0_0.25rem_#5365D7]";
      color = "bg-[#5365D7] shadow-[0_0_0_0.25rem_#5365D7]";
    } else if (messageData.heartId === 3) {
      borderColor = "shadow-[0_0_0_0.25rem_#50CA19]";
      color = "bg-[#50CA19] shadow-[0_0_0_0.25rem_#50CA19]";
    } else if (messageData.heartId === 4) {
      color = "bg-[#FF62A1] shadow-[0_0_0_0.25rem_#FF62A1]";
      borderColor = "shadow-[0_0_0_0.25rem_#FF62A1]";
    } else if (messageData.heartId === 5) {
      color = "bg-[#F2303C] shadow-[0_0_0_0.25rem_#F2303C]";
      borderColor = "shadow-[0_0_0_0.25rem_#F2303C]";
    }

    return (
      <div className="App w-full fixed left-0 top-0 bg-black bg-opacity-30 text-center flex  items-center justify-center z-40">
        <div className={`container bg-white ${borderColor} m-6 max-w-xs`}>
          <div
            className={`modal-header ${borderColor} ${color} border-hrtColorOutline mb-4 flex`}
          >
            <div className="flex-auto">하트 읽기</div>
            <button onClick={() => closeModal()} className="flex-none">
              <ButtonIcon id={1} />
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
              mode={props.mode}
            />
            {props.mode === "sent" ? null : (
              <div
                className="text-2xs text-right text-hrtColorNewGray cursor-pointer"
                onClick={onOpenReporting}
              >
                신고하기
              </div>
            )}
            <MessageModalTextbox
              mode={props.mode}
              title={messageData.title}
              content={messageData.content}
              onReportMessage={reportMessage}
            />
            <div className="absolute top-40 w-full h-auto">
              {isOpenEmojiList ? (
                <HeartResponseEmojiList
                  messageEmojiId={messageData.emojiId}
                  onEmojiHandler={onEmojiHandler}
                />
              ) : null}
            </div>
            <MessageModalButtonBox
              mode={props.mode}
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
