import React, { useEffect, useRef } from "react";
import { useRecoilValue } from "recoil";
import { io } from 'socket.io-client';
import { isLoginAtom } from "../../atoms/userAtoms";
import { getUserInfo } from "../../features/userInfo";

function NavbarNotification({...props}) {
  const notiRef = useRef<HTMLDivElement>(null);
  const isLogin = useRecoilValue(isLoginAtom)

  const onSocket = () => {
    if (isLogin) {
      const socket = io("https://heart-ing.com", { path: "/ws" });
      socket.on("connect", () => {
        console.log("웹소켓 서버에 연결");
        socket.emit('join-room', getUserInfo().userId);
      })
      socket.on("receive-message", (data) => {
        console.log("받은 메시지:", data);
      });
    }

  };

    useEffect(() => {
      onSocket()
    }, [])
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
    <div>
      짠~알람창
    </div>
  )
}

export default NavbarNotification