import React from "react";

import { getUserInfo } from "../../features/userInfo";
import { IGetNotificationListTypes } from "../../types/messageType";
import NavbarNotificationItem from "./NavbarNotificationItem";
import PurpleCloseButton from "../../assets/images/pixel/button/close_purple_1.svg";
import { useNavigate } from "react-router";

function NavbarNotification({ ...props }) {
  const navigate = useNavigate();

  const closeNoti = (e: React.MouseEvent<HTMLButtonElement>) => {
    props.setNotiIsOpen(false);
  };

  const onClickHandler = (e: IGetNotificationListTypes) => {
    props.setNotiIsOpen(false);
    props.readNotification(e.notificationId);
    if (e.type === "R") {
      navigate(`/heartboard/user?id=${getUserInfo().userId}`);
    } else {
      if (e.type === "E") {
        navigate("/sentheart");
      } else {
        navigate("/heartguide");
      }
    }
  };

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
                  (message: IGetNotificationListTypes) => (
                    <div
                      onClick={() => {
                        onClickHandler(message);
                      }}
                    >
                      <NavbarNotificationItem messageInfo={message} />
                    </div>
                  )
                )}
              </div>
            ) : (
              <p className="text-sm ml-2 mb-1">읽지 않은 알림이 없습니다</p>
            )}
          </div>
          <div className="flex flex-col items-start">
            <p className="text-xs">• 지난 알림</p>
            {Object.keys(props.notiData.trueList).length ? (
              <div>
                {props.notiData.trueList.map(
                  (message: IGetNotificationListTypes) => (
                    <div
                      onClick={() => {
                        onClickHandler(message);
                      }}
                    >
                      <NavbarNotificationItem messageInfo={message} />
                    </div>
                  )
                )}
              </div>
            ) : (
              <p className="text-sm ml-2 mb-1">24시간 내의 알림이 없습니다</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default NavbarNotification;
