import React, { useState } from 'react'
import { useNavigate } from "react-router-dom";
import { IMessageSendTypes } from '../types/messageType';
import { getUserInfo } from '../features/userInfo';
import { useRecoilValue } from 'recoil';
import { currentUserIdAtom } from '../atoms/userAtoms';
import { sendMessage } from '../features/api/messageApi';

function Heartwriting() {
  const navigate = useNavigate();

  const [heartNumber, setHeartNumber] = useState(0)
  const [title, setTitle] = useState('')
  const [content, setContent] = useState('')
  const currenUserId = useRecoilValue(currentUserIdAtom)

  const onSubmitHandler = async(e: React.FocusEvent<HTMLFormElement>) => {
    e.preventDefault();

    const messageInfo: IMessageSendTypes = {
      heartId: heartNumber,
      senderId: getUserInfo().userId,
      receiverId: currenUserId,
      title: title,
      content: content,
    }

    const data = await sendMessage(messageInfo)
    console.log(data)

    if (data.status === 'success') {
      alert('메세지 성공!')
      navigate(`/heartboard/user?id=${currenUserId}`)
    } 
  }

  const onHeartNumberHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const currentNumber = Number(e.currentTarget.value)
    setHeartNumber(currentNumber)
  }

  const onTitleHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const currentTitle = e.currentTarget.value
    setTitle(currentTitle)
  }

  const onContentHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const currentContent = e.currentTarget.value
    setContent(currentContent)
  }

  return (
    <div>
      <form onSubmit={onSubmitHandler}>
        <label>하트번호</label>
        <input type="number" onChange={onHeartNumberHandler}/>
        <br/>
        <label>제목</label>
        <input type="text" onChange={onTitleHandler}/>
        <br/>
        <label>내용</label>
        <input type="text" onChange={onContentHandler}/>
        <br/>
        <button>하트보내기</button>
      </form>
    </div>
  )
}

export default Heartwriting
