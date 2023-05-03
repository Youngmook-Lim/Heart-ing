import React, { useEffect, useRef } from "react";
import { useRecoilValue } from "recoil";

import { isLoginAtom } from "../../atoms/userAtoms";
import { getUserInfo } from "../../features/userInfo";

function NavbarNotification({...props}) {
  const notiRef = useRef<HTMLDivElement>(null);
  const isLogin = useRecoilValue(isLoginAtom)


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