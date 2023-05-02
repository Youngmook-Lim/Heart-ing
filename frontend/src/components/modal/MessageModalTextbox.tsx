import React from "react";

function MessageModalTextbox({ ...props }) {
  return (
    <div className="bg-hrtColorLightPurple p-4 mt-2 text-start rounded-sm">
      <div className="text-xl pb-2">{props.title}</div>
      <div className="text-base	leading-5	whitespace-pre-wrap">{props.content}</div>
    </div>
  );
}

export default MessageModalTextbox;
