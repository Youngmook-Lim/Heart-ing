import React, { useState } from 'react'
import { IMessageSendTypes } from '../../types/messageType';
import { getUserInfo } from '../../features/userInfo';

function HeartwritingMessageForm({...props}) {
  const [title, setTitle] = useState('')
  const [content, setContent] = useState('')

  const onSubmitHandler = async(e: React.FocusEvent<HTMLFormElement>) => {
    e.preventDefault();

    const messageInfo: IMessageSendTypes = {
      heartId: Number(props.selectedHeart),
      senderId: getUserInfo().userId,
      receiverId: props.userId,
      title: title,
      content: content,
    }

    props.onSendingHandler(messageInfo)
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
        <label>제목</label>
        <input type="text" onChange={onTitleHandler}/>
        <br/>
        <label>내용</label>
        <input type="text" onChange={onContentHandler}/>
        <br/>
        <button className="bg-hrtColorYellow px-8 h-16 rounded-xl border-2 border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)]">
          <div className="text-2xl">전달하기</div>
        </button>
      </form>
    </div>
  )
}

export default HeartwritingMessageForm
