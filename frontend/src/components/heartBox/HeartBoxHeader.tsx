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
              <p className="text-hrtColorYellow">마음 영구 보관함</p>
            ) : (
              <p className="text-hrtColorYellow purple">내가 보낸 마음</p>
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
