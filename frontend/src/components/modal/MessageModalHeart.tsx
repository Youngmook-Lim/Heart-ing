import React from "react";
import { useState } from "react";

import HeartItemIcon from "../common/HeartItemIcon";
import TouchArrow from "../../assets/images/png/touch_arrow.png";

function MessageModalHeart({ ...props }) {
  const [isIconMode, setIsIconMode] = useState(true);

  const switchInfoMode = () => {
    setIsIconMode(!isIconMode);
  };

  return (
    <div>
      {isIconMode ? (
        <button
          onClick={() => switchInfoMode()}
          className="place-content-center relative pt-2 mt-1"
        >
          <div className="text-base text-hrtColorPink absolute inset-x-px -top-1">
            touch
          </div>
          <div className="flex justify-center absolute inset-x-px top-4">
            <img src={TouchArrow} alt="test" className="w-2.5" />
          </div>
          <div className="flex justify-center p-0">
            <HeartItemIcon id={props.heartId} />
          </div>
          {/* <div>반응 : {props.emojiUrl}</div> */}
        </button>
      ) : (
        <button onClick={() => switchInfoMode()} className="h-24">
          <div className="text-4xl textShadow text-white">
            <p className="text-hrtColorYellow">{props.heartName}</p>
          </div>
          <div>{props.heartContext}</div>
        </button>
      )}
    </div>
  );
}

export default MessageModalHeart;
