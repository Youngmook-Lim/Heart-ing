import React, { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { Socket } from "socket.io-client";

import NavbarSide from "./NavbarSide";
import NavbarSideContent from "./NavbarSideContent";

import { isLoginAtom } from "../../atoms/userAtoms";
import { getUserInfo } from "../../features/userInfo";
import NavbarNotification from "./NavbarNotification";
import Logo from "../../assets/images/logo/logo_line.png";
import { getNotificationApi, readNotificationApi } from "../../features/api/userApi";

interface MyObject {
  [key: string]: any;
}

function Navbar({socket}:{socket:Socket|null}) {
  const navigate = useNavigate();

  const isLogin = useRecoilValue(isLoginAtom);
  // const [notiIsOpen, notiRef, notiHandler] = useDetectClose(false)
  const [notiIsOpen, setNotiIsOpen] = useState(false);
  const [isNew, setIsNew] = useState(false);
  const [receivedList, setReceivedList] = useState({});

  const onNotiHandler = (e: MouseEvent) => {
    setNotiIsOpen(!notiIsOpen);
  };

  const onNotiButtonHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    setNotiIsOpen(!notiIsOpen);
    setIsNew(false);
  };

  const onNavigateHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    navigate("/");
  };

  const getData = async () => {
    if (!isLogin) return;
    const data = await getNotificationApi();
    if (data.status === 'success') {
      const notiData: MyObject = { trueList: [], falseList: [] };
      for (let i = 0; i < Object.keys(data.data.notificationList).length; i++) {
        if (data.data.notificationList[i].isChecked) {
          notiData.trueList[i] = data.data.notificationList[i];
        } else {
          notiData.falseList[i] = data.data.notificationList[i];
        }
      }
      setReceivedList(notiData);
    }
  };

  const readNotification = async (notificationId: number) => {
    const data = await readNotificationApi(notificationId);
  }

  const onSocket = () => {
    if (socket) {
      if (isLogin) {
        socket.emit("join-room", getUserInfo().userId);
        socket.on("receive-message", (data) => {
          // console.log("받은 메시지:", data);
          getData();
        });
      }
      socket.on("disconnect", () => {
        // console.log("웹소켓 서버 연결 해제");
      });
    }
  };

  useEffect(() => {
    onSocket();
    if (isLogin) {
      getData();
    }
  }, [isLogin]);

  return (
    <div>
      <nav className="navHeight">
        <div className={`flex justify-between w-[calc(100%-2.7rem)]`}>
          <div onClick={onNavigateHandler}>
            <img src={Logo} alt="test" className="w-16 m-2" />
          </div>
          {isLogin ? (
            <div>
              <div
                className="w-6 m-2 my-4 flex-none relative"
                onClick={onNotiButtonHandler}
              >
                {isNew ? (
                  <div className="bg-hrtColorNewRed w-3	h-3	rounded-full border-2	border-white absolute z-10 top-0 right-0"></div>
                ) : null}
                <svg
                  fill="currentColor"
                  viewBox="0 0 20 20"
                  xmlns="http://www.w3.org/2000/svg"
                  aria-hidden="true"
                >
                  <path
                    clipRule="evenodd"
                    fillRule="evenodd"
                    d="M10 2a6 6 0 00-6 6c0 1.887-.454 3.665-1.257 5.234a.75.75 0 00.515 1.076 32.91 32.91 0 003.256.508 3.5 3.5 0 006.972 0 32.903 32.903 0 003.256-.508.75.75 0 00.515-1.076A11.448 11.448 0 0116 8a6 6 0 00-6-6zM8.05 14.943a33.54 33.54 0 003.9 0 2 2 0 01-3.9 0z"
                  ></path>
                </svg>
              </div>
              {notiIsOpen ? (
                <NavbarNotification
                  onNotiHandler={onNotiHandler}
                  setNotiIsOpen={setNotiIsOpen}
                  readNotification={readNotification}
                  notiData={receivedList}
                />
              ) : null}
            </div>
          ) : null}
        </div>
        <NavbarSide width={60}>
          <NavbarSideContent />
        </NavbarSide>
      </nav>
    </div>
  );
}

export default Navbar;
