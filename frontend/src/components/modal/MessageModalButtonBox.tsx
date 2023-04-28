import React from "react";

import { IMessageModalTypes } from "../../types/messageType";

import MessageModalButtonBoxClose from "./MessageModalButtonBoxClose";
import MessageModalButtonBoxDelete from "./MessageModalButtonBoxDelete";
import MessageModalButtonBoxEmoji from "./MessageModalButtonBoxEmoji";
import MessageModalButtonBoxSave from "./MessageModalButtonBoxSave";

function MessageModalButtonBox({
  mode,
  isExpired,
  isStored,
}: IMessageModalTypes) {
  // recent : 24시간 리스트에서 읽을 때 => 삭제, 보관, 반응
  // save : 영구보관에서 읽을 때 => 삭제, 닫기, 반응
  // send : 보낸메시지에서 읽을 때 => 닫기
  // 반응은 만료되지 않은 메시지에서만 가능
  // 이미 보관된 메시지는 보관이 뜨지 않음

  return (
    <div className="flex justify-center space-x-2 py-6">
      {mode === "send" || mode === "save" ? (
        <MessageModalButtonBoxClose />
      ) : null}
      {mode === "recent" || mode === "save" ? (
        <MessageModalButtonBoxDelete mode={mode} />
      ) : null}
      {mode === "recent" ? (
        <MessageModalButtonBoxSave isStored={isStored} />
      ) : null}
      {(mode === "save" || mode === "recent") && !isExpired ? (
        <MessageModalButtonBoxEmoji />
      ) : null}
    </div>
  );
}

export default MessageModalButtonBox;
