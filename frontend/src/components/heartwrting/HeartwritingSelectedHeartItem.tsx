import React from "react";

function HeartwritingSelectedHeartItem({ ...props }) {
  return (
    <div>
      <div className="place-content-center py-2">
        {props.heartInfo.isLocked ? (
          <div className="flex justify-center opacity-30">
            <img src={props.heartInfo.heartUrl} alt="" />
          </div>
        ) : (
          <div className="flex justify-center relative">
            <img src={props.heartInfo.heartUrl} alt="" />
          </div>
        )}
        <div className="px-2 leading-5 tracking-tight">
          {" "}
          {props.heartInfo.name}{" "}
        </div>
        {props.isSelected ? (
          <div className="text-base mt-2 opacity-70">
            "{props.heartInfo.shortDescription}"
          </div>
        ) : null}
      </div>
    </div>
  );
}

export default HeartwritingSelectedHeartItem;
