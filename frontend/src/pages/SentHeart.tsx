import React, { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";

import { readMessageAtom } from "../atoms/messageAtoms";
import { getSent } from "../features/api/messageApi";

import HeartBoxHeader from "../components/heartBox/HeartBoxHeader";
import HeartBoxList from "../components/heartBox/HeartBoxList";
import MessageModal from "../components/modal/MessageModal";
import { Socket } from "socket.io-client";

function SentHeart({socket}:{socket:Socket|null}) {
  const [inboxList, setInboxList] = useState({});
  const readMessage = useRecoilValue(readMessageAtom); // 메시지 읽는 모달 on/off

  async function getInboxMessages() {
    const data = await getSent();
    if (data.status === "success") {
      setInboxList(data.data.sentMessageList);
    }
  }

  useEffect(() => {
    getInboxMessages();
  }, []);

  return (
    <div className="container mx-auto px-6 fullHeight overflow-auto">
      <HeartBoxHeader mode={"sent"} />
      <HeartBoxList inboxList={inboxList} />
      {readMessage ? <MessageModal mode={"sent"} socket={socket}/> : null}
    </div>
  );
}

export default SentHeart;
