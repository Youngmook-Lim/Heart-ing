import React, { useState } from "react";
import { saveMessageApi } from "../../features/api/messageApi";
import { useRecoilValue } from "recoil";
import { selectedMessageIdAtom } from "../../atoms/messageAtoms";

function MessageModalButtonBoxSave({ ...props }) {
  const messageId = useRecoilValue(selectedMessageIdAtom);
  const [isStored, setIsStored] = useState(props.isStored);

  const onSaveHandler = async (e: React.MouseEvent<HTMLDivElement>) => {
    const status = await saveMessageApi(messageId);
    if (status === "success") {
      alert("메세지를 저장했습니다");
      setIsStored(true);
    }
  };

  return (
    <>
      {isStored ? (
        <div className="modal-button bg-hrtColorLightPurple text-hrtColorOutline700 cursor-default">
          저장 완료
        </div>
      ) : (
        <div
          className="modal-button text-hrtColorOutline bg-hrtColorPurple cursor-pointer"
          onClick={(e) => {
            onSaveHandler(e);
            props.changeIsStored(true);
          }}
        >
          저장
        </div>
      )}
    </>
  );
}

export default MessageModalButtonBoxSave;
