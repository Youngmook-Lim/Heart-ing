import React, { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { io } from 'socket.io-client';

import NavbarSide from "./NavbarSide";
import NavbarSideContext from "./NavbarSideContext";

import { isLoginAtom } from "../../atoms/userAtoms";
import { getUserInfo } from "../../features/userInfo";
import useDetectClose from "../../features/hook/useDetectClose";
import NavbarNotification from "./NavbarNotification";
import Logo from "../../assets/images/logo/logo_line.png";

function Navbar() {
  const navigate = useNavigate()
  
  const isLogin = useRecoilValue(isLoginAtom)
  // const [notiIsOpen, notiRef, notiHandler] = useDetectClose(false)
  const [notiIsOpen, setNotiIsOpen] = useState(false)
  const myId = getUserInfo().userId

  const onNotiHandler = (e: MouseEvent) => {
    setNotiIsOpen(!notiIsOpen)
  }

  const onNotiButtonHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    setNotiIsOpen(!notiIsOpen)
  }
  
  const onNavigateHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    navigate('/')
  }

    const onSocket = () => {
    if (isLogin) {
      const socket = io("https://heart-ing.com", { path: "/ws" });
      socket.on("connect", () => {
        console.log("회원 웹소켓 서버에 연결");
        socket.emit('join-room', getUserInfo().userId);
      })

      socket.on("receive-message", (data) => {
        console.log("받은 메시지:", data);
      });
    } else {
      const socket = io("https://heart-ing.com", { path: "/ws" });
      socket.on("connect", () => {
        console.log("비회원 웹소켓 서버에 연결");
        socket.emit('join-room', 'anonymous');
      })
    }

  };

    useEffect(() => {
      onSocket()
    }, [])
  
  return (
    <div>
      <nav className="">
        <div className={`flex justify-between w-[calc(100%-2.7rem)]`}>
          <div onClick={onNavigateHandler}>
            <img src={Logo} alt="test" className="w-16 m-2" />
          </div>
          {isLogin ? 
          <div>
            <div 
              className="w-6 m-2 my-4 flex-none"
              onClick={onNotiButtonHandler}>
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
            {notiIsOpen ? 
            <NavbarNotification onNotiHandler={onNotiHandler} />
            : null}
            </div>
            :null}
        </div>
        <NavbarSide width={60}>
          <NavbarSideContext />
        </NavbarSide>
      </nav>
    </div>
  );
}

export default Navbar;
