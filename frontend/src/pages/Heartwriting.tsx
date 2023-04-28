import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { IMessageSendTypes } from '../types/messageType';
import { getUserInfo } from '../features/userInfo';
import { useRecoilValue } from 'recoil';
import { currentUserIdAtom } from '../atoms/userAtoms';
import { getMessageHeartApi, sendMessageApi } from '../features/api/messageApi';
import HeartwritingSelectHeart from '../components/heartwrting/HeartwritingSelectHeart';
import HeartwritingMessageForm from '../components/heartwrting/HeartwritingMessageForm';

function Heartwriting() {
  const navigate = useNavigate();
  const currenUserId = useRecoilValue(currentUserIdAtom)
  const [heartList, setHeartList] = useState([])
  const [isSelected, setIsSelected] = useState(false)
  const [selectedHeartId, setSelectedHeartId] = useState('')

  async function sendMessage(messageInfo: IMessageSendTypes) {
    const status = await sendMessageApi(messageInfo)
    if (status === 'success') {
      alert('메세지 성공!')
      navigate(`/heartboard/user?id=${currenUserId}`)
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
    getHeartList()
    setIsSelected(false)
  }, [])

  return (
    <div>
      <div>로고</div>
      {isSelected ?
        <div>
          <HeartwritingMessageForm onSendingHandler={sendMessage} onSettingMode={setMode} selectedHeart={selectedHeartId}/>
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
