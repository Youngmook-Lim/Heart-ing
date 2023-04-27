import React from "react";
import { useState } from "react";

import HeartItemIcon from "../common/HeartItemIcon";

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
          <div className="text-xs text-hrtColorPink absolute inset-x-px top-0">
            touch
          </div>
          <div className="flex justify-center absolute inset-x-px top-2">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 20 20"
              fill="currentColor"
              className="w-5 h-5 text-hrtColorPink text-xs"
            >
              <path
                fill-rule="evenodd"
                d="M5.23 7.21a.75.75 0 011.06.02L10 11.168l3.71-3.938a.75.75 0 111.08 1.04l-4.25 4.5a.75.75 0 01-1.08 0l-4.25-4.5a.75.75 0 01.02-1.06z"
                clip-rule="evenodd"
              />
            </svg>
          </div>
          <div className="flex justify-center p-0">
            <HeartItemIcon id={props.heartId} />
          </div>
          {/* <div>반응 : {props.emojiUrl}</div> */}
        </button>
      ) : (
        <button onClick={() => switchInfoMode()} className="h-24">
          <div className="text-4xl">{props.heartName}</div>
          <div>{props.heartContext}</div>
        </button>
      )}
    </div>
  );
}

export default MessageModalHeart;
