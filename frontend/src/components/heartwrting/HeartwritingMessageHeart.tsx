import React from "react";
import HeartwritingSelectedHeartItem from "./HeartwritingSelectedHeartItem";

function HeartwritingMessageHeart({ ...props }) {
  return (
    <div className="text-3xl">
      <HeartwritingSelectedHeartItem
        heartInfo={props.selectedHeartInfo}
        isSelected={props.isSelected}
      />
    </div>
  );
}

export default HeartwritingMessageHeart;
