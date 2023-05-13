import React, { useEffect, useState } from "react";
import { Route, Routes } from "react-router";
import { BrowserRouter } from "react-router-dom";

import "./styles/App.css";
import ErrorPage from "./pages/ErrorPage";
import HeartBoard from "./pages/HeartBoard";
import HeartGuide from "./pages/HeartGuide";
import Heartwriting from "./pages/Heartwriting";
import Kakao from "./pages/Kakao";
import Home from "./pages/Home";
import Manual from "./pages/Manual";
import ReceivedHeart from "./pages/ReceivedHeart";
import SentHeart from "./pages/SentHeart";
import Settings from "./pages/Settings";
import Navbar from "./components/navbar/Navbar";
import Google from "./pages/Google";
import { Socket, io } from "socket.io-client";
import { useRecoilValue } from "recoil";
import { isLoginAtom } from "./atoms/userAtoms";
import HeartTest from "./pages/HeartTest";
import HeartTestResult from "./pages/HeartTestResult";
import Twitter from "./pages/Twitter";

declare global {
  interface Window {
    Kakao: any;
  }
}

function App() {
  const isLogin = useRecoilValue(isLoginAtom)

  let socket:Socket|null;
  
  if (isLogin) {
    socket = io("https://heart-ing.com", { path: "/ws" });
  } else {
    socket = null
  }

  function setScreenSize() {
    let vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`); //"--vh"라는 속성으로 정의해준다.
  }
  
  useEffect(() => {
    setScreenSize();
    return () => {
      if (socket) {
        socket.on("disconnect", () => {
          console.log("disconnect");
        });
      }
    }
  }, []); //처음 마운트될때 값을 계산하도록 함수를 호출한다

  // useEffect(() => {
  //     if (!isLogin && socket) {
  //       socket.close()
  //     }
  //   }, [isLogin]); 

  
  return (
    <div className="App bg-hrtColorBackground text-hrtColorOutline">
      <BrowserRouter>
        <Navbar socket={socket}/>
        <Routes>
          <Route index element={<Home />} />
          <Route path="/*" element={<ErrorPage />} />
          <Route path="/heartboard/user" element={<HeartBoard socket={socket}/>} />
          <Route path="/heartguide" element={<HeartGuide />} />
          <Route path="/heartwriting" element={<Heartwriting socket={socket}/>} />
          <Route path="/oauth2/code/kakao" element={<Kakao />} />
          <Route path="/oauth2/code/google" element={<Google />} />
          <Route path="/oauth2/code/twitter" element={<Twitter />} />
          <Route path="/manual" element={<Manual />} />
          <Route path="/receivedheart" element={<ReceivedHeart socket={socket}/>} />
          <Route path="/sentheart" element={<SentHeart socket={socket}/>} />
          <Route path="/profilesettings" element={<Settings />} />
          <Route path="/hearttest" element={<HeartTest />} />
          <Route path="/heartresult" element={<HeartTestResult />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
