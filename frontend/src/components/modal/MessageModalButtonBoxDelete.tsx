import React from "react";
import { useRecoilValue } from "recoil";

import { deleteTemporaryMessageApi, deletepermanentMessageApi } from "../../features/api/messageApi";
import { selectedMessageIdAtom } from "../../atoms/messageAtoms";

function MessageModalButtonBoxDelete({...props}) {
  const messageId = useRecoilValue(selectedMessageIdAtom)

  const onDeleteHandler = async(e: React.MouseEvent<HTMLDivElement>) => {
    if (props.mode === 'recent') {
      const status = await deleteTemporaryMessageApi(messageId)
      if (status === 'success') {
        alert('메세지를 삭제했습니다')
      }
    } else {
      const status = await deletepermanentMessageApi(messageId)
      if (status === 'success') {
        alert('메세지를 삭제했습니다')
      }
    }
  }

  return <div className="modal-button text-hrtColorNewRed" onClick={onDeleteHandler}>삭제</div>;
}

export default MessageModalButtonBoxDelete;
