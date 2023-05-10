import React, { useCallback } from "react";
import { useEffect, useState } from "react";
import { ITotalHeartPropsTypes } from "../../types/messageType";

function ManualHomeCount({ onGetTotalHeart }: ITotalHeartPropsTypes) {
  const [totalHeartCnt, setTotalHeartCnt] = useState<number>(0);

  const handleGetTotalHeartCnt = useCallback(async () => {
    const totalCnt = await onGetTotalHeart();
    setTotalHeartCnt(totalCnt);
  }, [onGetTotalHeart]);

  useEffect(() => {
    handleGetTotalHeartCnt();

    const interval = setInterval(async () => {
      handleGetTotalHeartCnt();
    }, 3000);

    return () => {
      clearInterval(interval);
    };
  }, [handleGetTotalHeartCnt]);

  return (
    <div className="text-xl textShadow my-6">
      <p className="purple text-white">지금까지</p>
      <span className="flex justify-center text-white items-center textShadow my-2">
        <p className="mr-1 white text-hrtColorPink tracking-widest" id="number">
          {totalHeartCnt}
        </p>
        {/* <p className="purple">총</p> */}
        <p className="purple text-white">개의 마음이 전해졌어요!</p>
      </span>
    </div>
  );
}

export default React.memo(ManualHomeCount);
