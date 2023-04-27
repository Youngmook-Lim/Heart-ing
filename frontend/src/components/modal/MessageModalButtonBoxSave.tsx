import React from "react";
import { saveMessageApi } from "../../features/api/messageApi";
import { useRecoilValue } from "recoil";
import { selectedMessageIdAtom } from "../../atoms/messageAtoms";

function MessageModalButtonBoxSave() {
  const messageId = useRecoilValue(selectedMessageIdAtom)

  const onSaveHandler = async(e: React.MouseEvent<HTMLDivElement>) => {
    const status = await saveMessageApi(messageId)
    if (status === 'success') {
      alert('메세지를 저장했습니다')
    }
  }

  return (
    <div className="modal-button text-hrtColorOutline bg-hrtColorPurple" onClick={onSaveHandler}>
      저장
    </div>
  );
}

export default MessageModalButtonBoxSave;
