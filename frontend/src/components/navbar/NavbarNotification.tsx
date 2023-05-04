import React from "react";

import { getUserInfo } from "../../features/userInfo";
import { IGetMessageListTypes } from "../../types/messageType";
import NavbarNotificationItem from "./NavbarNotificationItem";
import PurpleCloseButton from "../../assets/images/pixel/button/close_purple_1.svg";
import { useNavigate } from "react-router";

function NavbarNotification({ ...props }) {
  const navigate = useNavigate();

  const closeNoti = (e: React.MouseEvent<HTMLButtonElement>) => {
    props.setNotiIsOpen(false);
  };

  const onClickHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    props.setNotiIsOpen(false);
    navigate(`/heartboard/user?id=${getUserInfo().userId}`);
  };

  // useEffect(() => {
  //   const handleClick = (e: MouseEvent) => {
  //     console.log('커런트',notiRef.current)
  //     if (notiRef.current && !notiRef.current.contains(e.target as Node)) {
  //       props.onNotiHandler();
  //       console.log('바깥 눌렀다')
  //     }
  //   };
  //   window.addEventListener('mousedown', handleClick);
  //   return () => window.removeEventListener('mousedown', handleClick);
  // }, [notiRef]);

  return (
    <div className="border-hrtColorOutline mx-auto w-72 h-32 absolute right-12 z-50">
      <div className="modal relative">
        <div className="bg-hrtColorOutline border-hrtColorOutline mb-4 flex justify-between">
          <div className="text-white h-8 text-lg leading-8 text-left pl-2 pr-1 shadow-lg shadow-purple">
            알림
          </div>
          <button onClick={closeNoti} className="flex-none">
            <img
              src={PurpleCloseButton}
              alt="button"
              className="w-6 h-6 mr-1"
            />
          </button>
        </div>
        <div className="overflow-y-scroll scrollbar-hidden h-32 mx-3">
          <div className="flex flex-col items-start text-left">
            <p className="text-xs">• 읽지 않은 알림</p>
            {Object.keys(props.notiData.falseList).length ? (
              <div>
                {props.notiData.falseList.map(
                  (message: IGetMessageListTypes) => (
                    <NavbarNotificationItem
                      messageInfo={message}
                      onClickHandler={onClickHandler}
                    />
                  )
                )}
              </div>
            ) : (
              <p className="text-sm ml-2 mb-1">읽지 않은 메세지가 없습니다</p>
            )}
          </div>
          <div className="flex flex-col items-start">
            <p className="text-xs">• 지난 알림</p>
            {props.notiData.trueList.map((message: IGetMessageListTypes) => (
              <div>
                <NavbarNotificationItem
                  messageInfo={message}
                  onClickHandler={onClickHandler}
                />
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default NavbarNotification;
