import React, { useState } from 'react'
import { IMessageSendTypes } from '../../types/messageType';
import { getUserInfo } from '../../features/userInfo';

function HeartwritingMessageForm({...props}) {
  const [title, setTitle] = useState('')
  const [content, setContent] = useState('')
  
  const onTitleHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.currentTarget.value.length > 8) {
      const currentTitle = e.currentTarget.value.substr(0, 8);
      props.setTitle(currentTitle)
      setTitle(currentTitle)
    } else {
      const currentTitle = e.currentTarget.value
      props.setTitle(currentTitle)
      setTitle(currentTitle)
    }
  }

  const onContentHandler = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (e.currentTarget.value.length > 100) {
      const currentContent = e.currentTarget.value.substr(0, 100);
      props.setContent(currentContent)
      setContent(currentContent)
    } else {
      const currentContent = e.currentTarget.value
      props.setContent(currentContent)
      setContent(currentContent)
    }
  }

  return (
    <div>
      <input type="text" value={title} className='w-72 mt-5 mb-2 text-center border-b-2 border-hrtColorPink outline-none' onChange={onTitleHandler} placeholder='제목을 입력해주세요'/>
      <br/>
      <div className='w-72 mx-auto text-left mb-5'>
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="inline w-4 h-4 mx-1">
          <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z" />
        </svg>
        <span className='text-sm text-hrtColorPink'>
          전달하는 마음은 24시간 동안 유지되며, 수정•삭제할 수 없습니다
        </span>
      </div>
      <textarea value={content} className="block w-72 h-40 text-center mx-auto py-2 mb-10 border-2 border-hrtColorPink outline-none rounded-md" onChange={onContentHandler} placeholder='전하고 싶은 마음이 있다면,&#13;&#10;메세지를 작성해보세요'/>
    </div>
  )
}

export default HeartwritingMessageForm
