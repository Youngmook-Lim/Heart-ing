import React from "react";

import MessageModalTimeTimer from "./MessageModalTimeTimer";

function MessageModalTime({ ...props }) {
  const expiredSec = Number(props.expiredDate.substr(17, 2));
  const expiredMin = Number(props.expiredDate.substr(14, 2));
  const expiredHour = Number(props.expiredDate.substr(11, 2));
  const expiredDay = Number(props.expiredDate.substr(8, 2));
  const nowSec = props.kr_curr.getSeconds();
  const nowMin = props.kr_curr.getMinutes();
  const nowHour = props.kr_curr.getHours();
  const nowDay = props.kr_curr.getDate();
  const total =
    expiredSec +
    expiredMin * 60 +
    expiredHour * 60 * 60 +
    (expiredDay - nowDay) * 60 * 60 * 24 -
    (nowSec + nowMin * 60 + nowHour * 60 * 60);

  return (
    <div>
      {props.mode !== "save" ? (
        <div>
          {" "}
          <MessageModalTimeTimer remainTime={total} />
        </div>
      ) : (
        <div className="text-2xl">{props.createdDate.substr(0, 10)}</div>
      )}
    </div>
  );
}

export default MessageModalTime;
