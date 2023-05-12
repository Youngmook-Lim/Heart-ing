import React from "react";
import { useSetRecoilState } from "recoil";
import { readMessageAtom } from "../../atoms/messageAtoms";

function MessageModalButtonBoxClose() {
  const setReadMessageAtom = useSetRecoilState(readMessageAtom);

  // 메시지 모달을 닫습니다
  const closeModal = () => {
    setReadMessageAtom(false);
  };

  return (
    <div className="modal-button cursor-pointer" onClick={() => closeModal()}>
      닫기
    </div>
  );
}

export default MessageModalButtonBoxClose;
