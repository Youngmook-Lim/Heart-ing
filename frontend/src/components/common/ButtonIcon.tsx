import React from "react";

import { ReactComponent as ClosePurple } from "../../assets/images/pixel/button/close_purple_1.svg";
import { ReactComponent as CloseYellow } from "../../assets/images/pixel/button/close_yellow_1.svg";

type propType = { id: number };

function ButtonIcon(id: propType) {
  const index = id.id;
  const IconList = [
    <>
      <ClosePurple className="w-8 h-8" />
    </>,
    <>
      <CloseYellow className="w-8 h-8" />
    </>,
  ];
  return IconList[index];
}

export default ButtonIcon;
