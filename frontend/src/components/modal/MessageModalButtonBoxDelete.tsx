import React from "react";
import { useRecoilValue, useSetRecoilState } from "recoil";

import {
  deleteTemporaryMessageApi,
  deletepermanentMessageApi,
} from "../../features/api/messageApi";
import {
  readMessageAtom,
  selectedMessageIdAtom,
} from "../../atoms/messageAtoms";

function MessageModalButtonBoxDelete({ ...props }) {
  const messageId = useRecoilValue(selectedMessageIdAtom);
  const setReadMessageAtom = useSetRecoilState(readMessageAtom);

  const onDeleteHandler = async (e: React.MouseEvent<HTMLDivElement>) => {
    if (window.confirm('정말 삭제하시겠습니까?')) {
      if (props.mode === "recent") {
        const status = await deleteTemporaryMessageApi(messageId);
        if (status === "success") {
          alert("메세지를 삭제했습니다");
        }
      } else {
        const status = await deletepermanentMessageApi(messageId);
        if (status === "success") {
          alert("메세지를 삭제했습니다");
        }
      }
      setReadMessageAtom(false);
    }
  };

  return (
    <div className="modal-button text-hrtColorNewRed cursor-pointer" onClick={onDeleteHandler}>
      삭제
    </div>
  );
}

export default MessageModalButtonBoxDelete;
