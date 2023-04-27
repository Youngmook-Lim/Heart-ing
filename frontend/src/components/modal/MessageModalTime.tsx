import React from "react";

import MessageModalTimeTimer from "./MessageModalTimeTimer";

function MessageModalTime({ ...props }) {
  // recent : 24시간 리스트에서 읽을 때 => 타이머
  // save : 영구보관에서 읽을 때 => 수신날짜
  // send : 보낸메시지에서 읽을 때 => 타이머

  return (
    <div>
      {props.mode !== "save" ? (
        <div>
          {" "}
          <MessageModalTimeTimer remainTime={18000} />
        </div>
      ) : (
        <div className="text-2xl">{props.createdDate}</div>
      )}
    </div>
  );
}

export default MessageModalTime;
