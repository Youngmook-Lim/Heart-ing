import React from "react";
import { isOpenEmojiListAtom } from "../../atoms/messageAtoms"
import { useRecoilState } from "recoil";

function MessageModalButtonBoxEmoji() {
  const [ isOpenEmojiList, setIsOpenEmojiList ] = useRecoilState(isOpenEmojiListAtom)

  const onSetIsOpenEmojiList = () => {
    setIsOpenEmojiList(!isOpenEmojiList)
  }

  return (
        <div className={`cursor-pointer border-2 rounded border-hrtColorOutline w-10 h-10 leading-9 flex-none p-1 justify-center items-center ${
          isOpenEmojiList ? "bg-hrtColorPink" : "bg-hrtColorYellow"
        }`}
          onClick={onSetIsOpenEmojiList}>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 20 20"
            fill="currentColor"
            className="w-7 h-7 m-auto align-middle"
          >
            <path
              fillRule="evenodd"
              d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.536-4.464a.75.75 0 10-1.061-1.061 3.5 3.5 0 01-4.95 0 .75.75 0 00-1.06 1.06 5 5 0 007.07 0zM9 8.5c0 .828-.448 1.5-1 1.5s-1-.672-1-1.5S7.448 7 8 7s1 .672 1 1.5zm3 1.5c.552 0 1-.672 1-1.5S12.552 7 12 7s-1 .672-1 1.5.448 1.5 1 1.5z"
              clipRule="evenodd"
            />
          </svg>
        </div>
  );
}

export default MessageModalButtonBoxEmoji;
