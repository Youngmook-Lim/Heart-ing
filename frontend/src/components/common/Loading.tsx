import React from "react";

import "../../styles/Loading.css";

function Loading() {
  return (
    <div className="loadingHeight center">
      <div className="back"></div>
      <div className="heart"></div>
      <div className="text-hrtColorPink loadingText">로딩중</div>
    </div>
  );
}

export default Loading;
