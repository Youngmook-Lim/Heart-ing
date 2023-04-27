import React, { useEffect, useState } from "react";

import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";

import { readMessageAtom } from "../atoms/messageAtoms";

import HeartBoxHeader from "../components/heartBox/HeartBoxHeader";
import HeartBoxList from "../components/heartBox/HeartBoxList";
import { getInbox } from "../features/api/messageApi";
import MessageModal from "../components/modal/MessageModal";

function ReceivedHeart() {
  const [inboxList, setInboxList] = useState({});
  const readMessage = useRecoilValue(readMessageAtom); // 메시지 읽는 모달 on/off

  async function getInboxMessages() {
    const data = await getInbox();
    if (data.status === "success") {
      console.log(data.data.inboxList);
      setInboxList(data.data.inboxList);
    }
  }

  useEffect(() => {
    getInboxMessages();
  }, []);

  return (
    <div>
      받은하트함
      <HeartBoxHeader />
      <HeartBoxList inboxList={inboxList} />
      {readMessage ? <MessageModal mode={"save"} /> : null}
    </div>
  );
}

export default ReceivedHeart;
