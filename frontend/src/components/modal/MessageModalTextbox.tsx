import React from "react";

function MessageModalTextbox({ ...props }) {
  return (
    <div>
      <div>{props.title}</div>
      <div>{props.content}</div>
    </div>
  );
}

export default MessageModalTextbox;
