import React from "react";
import { useState } from "react";

import { IMessageModalTypes } from "../../types/messageType";

import MessageModalButtonBoxClose from "./MessageModalButtonBoxClose";
import MessageModalButtonBoxDelete from "./MessageModalButtonBoxDelete";
import MessageModalButtonBoxEmoji from "./MessageModalButtonBoxEmoji";
import MessageModalButtonBoxSave from "./MessageModalButtonBoxSave";

function MessageModalButtonBox({ ...props }: IMessageModalTypes) {
  // recent : 24시간 리스트에서 읽을 때 => 삭제, 보관, 반응
  // save : 영구보관에서 읽을 때 => 삭제, 닫기, 반응
  // sent : 보낸메시지에서 읽을 때 => 닫기
  // 반응은 만료되지 않은 메시지에서만 가능
  // 이미 보관된 메시지는 보관이 뜨지 않음

  const [isStored, setIsStored] = useState(props.isStored);
  const changeIsStored = (value: boolean) => {
    setIsStored(value);
  };

  return (
    <div className="flex justify-center space-x-2 py-6">
      {(props.mode === "recent" && !isStored) || props.mode === "save" ? (
        <MessageModalButtonBoxDelete mode={props.mode} />
      ) : null}
      {props.mode === "recent" ? (
        <MessageModalButtonBoxSave
          isStored={isStored}
          changeIsStored={changeIsStored}
        />
      ) : null}
      {props.mode === "sent" || props.mode === "save" ? (
        <MessageModalButtonBoxClose />
      ) : null}
      {(props.mode === "save" || props.mode === "recent") &&
      !props.isExpired ? (
        <MessageModalButtonBoxEmoji />
      ) : null}
    </div>
  );
}

export default MessageModalButtonBox;
