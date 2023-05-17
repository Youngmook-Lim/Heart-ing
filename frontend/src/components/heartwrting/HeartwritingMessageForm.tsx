import React, { useState } from "react";

function HeartwritingMessageForm({ ...props }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [countTitle, setCountTitle] = useState(0);
  const [countContent, setCountContent] = useState(0);

  const onTitleHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.currentTarget.value.length > 12) {
      const currentTitle = e.currentTarget.value.substr(0, 12);
      props.setTitle(currentTitle);
      setTitle(currentTitle);
      setCountTitle(12);
    } else {
      const currentTitle = e.currentTarget.value;
      props.setTitle(currentTitle);
      setTitle(currentTitle);
      setCountTitle(e.currentTarget.value.length);
    }
  };

  const onContentHandler = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (e.currentTarget.value.length > 100) {
      const currentContent = e.currentTarget.value.substr(0, 100);
      props.setContent(currentContent);
      setContent(currentContent);
      setCountContent(100);
    } else {
      const currentContent = e.currentTarget.value;
      props.setContent(currentContent);
      setContent(currentContent);
      setCountContent(e.currentTarget.value.length);
    }
  };

  return (
    <div>
      <div className="relative">
        <input
          type="text"
          value={title}
          className="w-72 mt-5 mb-2 text-center border-b-2 border-hrtColorPink outline-none"
          onChange={onTitleHandler}
          placeholder="제목을 필수로 입력해주세요"
        />
        {title ? (
          <span className="text-gray-400 absolute bottom-2 right-8">
            {countTitle}/12
          </span>
        ) : null}
      </div>
      <div className="w-72 mx-auto text-left mb-5">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          strokeWidth="1.5"
          stroke="currentColor"
          className="inline w-4 h-4 mx-1 text-hrtColorPink"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z"
          />
        </svg>
        <span className="text-sm text-hrtColorPink">
          전달하는 하트는 24시간 동안 유지되며, 수정•삭제할 수 없습니다
        </span>
      </div>
      <div className="py-2 text-right mb-5 ">
        <textarea
          value={content}
          className="block w-72 h-48 text-center mx-auto p-2 border-2 border-hrtColorPink outline-none rounded-md resize-none"
          onChange={onContentHandler}
          placeholder="전하고 싶은 마음이 있다면,&#13;&#10;메세지를 작성해보세요"
        />
        <span className="text-gray-400 pr-10">{countContent}/100</span>
      </div>
    </div>
  );
}

export default HeartwritingMessageForm;
