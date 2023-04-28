import React, { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { IMessageSendTypes } from '../types/messageType';
import { getUserInfo } from '../features/userInfo';
import { useRecoilValue } from 'recoil';
import { getMessageHeartApi, sendMessageApi } from '../features/api/messageApi';
import HeartwritingSelectHeart from '../components/heartwrting/HeartwritingSelectHeart';
import HeartwritingMessageForm from '../components/heartwrting/HeartwritingMessageForm';
import { getProfile } from "../features/api/userApi";

function Heartwriting() {
  const navigate = useNavigate();
  const [heartList, setHeartList] = useState([])
  const [isSelected, setIsSelected] = useState(false)
  const [selectedHeartId, setSelectedHeartId] = useState('')
  
  let params = new URL(document.URL).searchParams;
  let userId = params.get("id");
  const getUserProfile = useCallback(
    async (userId: string | null) => {
      if (!userId) {
        console.log("에러났당");
        navigate("/notfound");
      } else {
        const data = await getProfile(userId);
        if (data.status !== "success") {
          console.log("에러났당");
          navigate("/notfound");
        }
      }
    },
    [navigate]
  );

  async function sendMessage(messageInfo: IMessageSendTypes) {
    const status = await sendMessageApi(messageInfo)
    if (status === 'success') {
      alert('메세지 성공!')
      navigate(`/heartboard/user?id=${userId}`)
    }
  }

  async function getHeartList() {
    const data = await getMessageHeartApi()
    if (data.status === 'success') {
      setHeartList(data.data.heartList)
    }
  }

  function setHeartNumber(heartId:string) {
    setSelectedHeartId(heartId)
  } 

  function setMode() {
    setIsSelected(!isSelected)
  }

  useEffect(() => {
    getUserProfile(userId)
    getHeartList()
    setIsSelected(false)
  }, [])

  return (
    <div>
      <div>로고</div>
      {isSelected ?
        <div>
          <HeartwritingMessageForm onSendingHandler={sendMessage} onSettingMode={setMode} selectedHeart={selectedHeartId} userId={userId}/>
        </div>
        :
        <div>  
          <HeartwritingSelectHeart onHeartNumberHandler={setHeartNumber} onSettingMode={setMode} heartList={heartList}/>
        </div>
        
      }
    </div>
  )
}

export default Heartwriting
