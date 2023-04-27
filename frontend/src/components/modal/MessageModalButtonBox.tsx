import React from "react";

import { IMessageModalTypes } from "../../types/messageType";

import MessageModalButtonBoxClose from "./MessageModalButtonBoxClose";
import MessageModalButtonBoxDelete from "./MessageModalButtonBoxDelete";
import MessageModalButtonBoxEmoji from "./MessageModalButtonBoxEmoji";
import MessageModalButtonBoxSave from "./MessageModalButtonBoxSave";

function MessageModalButtonBox({ mode, isExpired }: IMessageModalTypes) {
  // recent : 24시간 리스트에서 읽을 때 => 삭제, 보관, 반응
  // save : 영구보관에서 읽을 때 => 삭제, 닫기, 반응
  // send : 보낸메시지에서 읽을 때 => 닫기
  // 반응은 만료되지 않은 메시지에서만 가능

  return (
    <div className="flex space-x-2 py-6">
      {mode === "send" || mode === "save" ? (
        <MessageModalButtonBoxClose />
      ) : null}
      {mode === "recent" ? <MessageModalButtonBoxDelete mode={mode}/> : null}
      {(mode === "recent" || mode === "save") && !isExpired ? (
        <MessageModalButtonBoxSave />
      ) : null}
      {mode === "save" || mode === "recent" ? (
        <MessageModalButtonBoxEmoji />
      ) : null}
    </div>
  );
}

export default MessageModalButtonBox;
