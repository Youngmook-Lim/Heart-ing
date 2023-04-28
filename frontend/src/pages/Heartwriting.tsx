import React, { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { IMessageSendTypes } from '../types/messageType';
import { getUserInfo } from '../features/userInfo';
import { useRecoilValue } from 'recoil';
import { getMessageHeartApi, sendMessageApi } from '../features/api/messageApi';
import HeartwritingSelectHeart from '../components/heartwrting/HeartwritingSelectHeart';
import HeartwritingMessage from '../components/heartwrting/HeartwritingMessage';
import { getProfile } from "../features/api/userApi";
import LogoEffect from "../assets/images/logo/logo_effect.png";

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
      <div className="container mx-auto px-6 py-8">
        <img src={LogoEffect} alt="test" className="w-full px-14 my-5" />
        <div className="modal border-hrtColorPink">
          <div className="modal-header bg-hrtColorPink border-hrtColorPink">
            마음 보내기
          </div>
          {isSelected ?
            <HeartwritingMessage onSendingHandler={sendMessage} onSettingMode={setMode} selectedHeart={selectedHeartId} userId={userId}/>
            : 
            <HeartwritingSelectHeart onHeartNumberHandler={setHeartNumber} onSettingMode={setMode} heartList={heartList}/>
          }
        </div>
      {isSelected ?
        // <button className="bg-hrtColorYellow px-8 h-16 rounded-xl border-2 border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)] my-5">
        //   <div className="text-2xl">전달하기</div>
        // </button>
        null
        :
        <button onClick={setMode} className="bg-hrtColorYellow px-8 h-16 rounded-xl border-2 border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)] my-5" disabled={selectedHeartId ? false : true}>
          <div className="text-2xl">다음</div>
        </button>
      }
    </div>
  )
}

export default Heartwriting
