import React from "react";

import LogoEffect from "../../assets/images/logo/logo_effect.png";

function HeartBoxHeader({ ...props }) {
  if (props.mode) {
    return (
      <>
        <img src={LogoEffect} alt="test" className="w-full px-14" />
        <div>
          <div className="text-4xl py-3 textShadow">
            {props.mode === "received" ? (
              <p className="text-hrtColorYellow">하트 저장소</p>
            ) : (
              <p className="text-hrtColorYellow purple">보낸 하트</p>
            )}
          </div>
        </div>
      </>
    );
  } else {
    return <div></div>;
  }
}

export default HeartBoxHeader;
