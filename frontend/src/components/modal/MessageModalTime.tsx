import React from "react";

import MessageModalTimeTimer from "./MessageModalTimeTimer";

function MessageModalTime({ ...props }) {
  // 1. 현재 시간(Locale)
  const curr = new Date();
  console.log("현재시간(Locale) : " + curr + "<br>"); // 현재시간(Locale)
  // 2. UTC 시간 계산
  const utc = curr.getTime() + curr.getTimezoneOffset() * 60 * 1000;
  // 3. UTC to KST (UTC + 9시간)
  const KR_TIME_DIFF = 9 * 60 * 60 * 1000; //한국 시간(KST)은 UTC시간보다 9시간 더 빠르므로 9시간을 밀리초 단위로 변환.
  const kr_curr = new Date(utc + KR_TIME_DIFF); //UTC 시간을 한국 시간으로 변환하기 위해 utc 밀리초 값에 9시간을 더함.
  console.log("한국시간 : " + kr_curr); // 한국시간 GMT+0900 (한국 표준시)

  const expiredSec = Number(props.expiredDate.substr(17, 2));
  const expiredMin = Number(props.expiredDate.substr(14, 2));
  const expiredHour = Number(props.expiredDate.substr(11, 2));
  const expiredDay = Number(props.expiredDate.substr(8, 2));
  const nowSec = kr_curr.getSeconds();
  const nowMin = kr_curr.getMinutes();
  const nowHour = kr_curr.getHours();
  const nowDay = kr_curr.getDate();
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
        <div className="text-2xl">{props.createdDate}</div>
      )}
    </div>
  );
}

export default MessageModalTime;
