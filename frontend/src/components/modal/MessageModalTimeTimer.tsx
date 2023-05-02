import React from "react";

import { useEffect, useState } from "react";

type propType = { remainTime: number };

function MessageModalTimeTimer({ remainTime }: propType) {
  const [time, setTime] = useState(0); // 남은 시간 (단위: 초)

  useEffect(() => {
    setTime(remainTime);
  }, [remainTime]);

  useEffect(() => {
    const timer = setInterval(() => {
      setTime((prev) => prev - 1);
    }, 1000);
    return () => clearInterval(timer);
  }, [time]);

  const getHours = (time: number) => {
    const hours = Math.floor(time / 60 / 60) % 24;
    if (hours < 10) {
      return "0" + String(hours);
    } else {
      return String(hours);
    }
  };

  const getMinutes = (time: number) => {
    const minutes = Math.floor(time / 60) % 60;
    if (minutes < 10) {
      return "0" + String(minutes);
    } else {
      return String(minutes);
    }
  };

  const getSeconds = (time: number) => {
    const seconds = Number(time % 60);
    if (seconds < 10) {
      return "0" + String(seconds);
    } else {
      return String(seconds);
    }
  };

  if (time && time > 0) {
    return (
      <div>
        <div className="text-4xl">
          <span>{getHours(time)}</span>
          <span>:</span>
          <span>{getMinutes(time)}</span>
          <span>:</span>
          <span>{getSeconds(time)}</span>
        </div>
      </div>
    );
  } else {
    return <div className="text-sm">남은시간을 불러오는 중 ...</div>;
  }
}

export default MessageModalTimeTimer;
