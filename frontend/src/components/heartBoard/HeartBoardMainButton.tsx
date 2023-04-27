import React from "react";

function BottomButton({ ...props }) {
  const isMyBoard = props.isMyBoard;
  console.log(props.userProfile);
  return (
    <button className="bg-hrtColorYellow px-8 h-16 rounded border-2 border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)]">
      {isMyBoard ? (
        <div className="text-2xl">공유하기</div>
      ) : (
        <div>
          <div className="flex text-base pt-2 justify-center leading-3">
            <p className="text-hrtColorPink">{props.userProfile.nickname}</p>
            <p>님에게</p>
          </div>
          <div className="text-2xl">마음 보내기</div>
        </div>
      )}
    </button>
  );
}

export default BottomButton;
