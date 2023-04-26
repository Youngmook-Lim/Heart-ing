import React from "react";
import { useState } from "react";

function MessageModalHeart({ ...props }) {
  const [isIconMode, setIsIconMode] = useState(true);

  const switchInfoMode = () => {
    setIsIconMode(!isIconMode);
  };

  return (
    <div>
      <div>click</div>
      {isIconMode ? (
        <button onClick={() => switchInfoMode()}>
          하트 이미지 : {props.heartUrl}
          <div>반응 : {props.emojiUrl}</div>
        </button>
      ) : (
        <button onClick={() => switchInfoMode()}>
          하트 제목 : {props.heartName}
          <div>하트 설명 : {props.heartContext}</div>
        </button>
      )}
    </div>
  );
}

export default MessageModalHeart;
