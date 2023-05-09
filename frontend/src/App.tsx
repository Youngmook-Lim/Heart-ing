import React, { useEffect } from "react";
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
import Setting from "./pages/Setting";
import Navbar from "./components/navbar/Navbar";
import Google from "./pages/Google";
import NonLoggedPopup from "./components/popUp/NonLoggedPopup";

function App() {
  function setScreenSize() {
    let vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`); //"--vh"라는 속성으로 정의해준다.
  }

  useEffect(() => {
    setScreenSize();
  }); //처음 마운트될때 값을 계산하도록 함수를 호출한다

  return (
    <div className="App bg-hrtColorBackground text-hrtColorOutline">
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route index element={<Home />} />
          <Route path="/*" element={<ErrorPage />} />
          <Route path="/heartboard/user" element={<HeartBoard />} />
          <Route path="/heartguide" element={<HeartGuide />} />
          <Route path="/heartwriting" element={<Heartwriting />} />
          <Route path="/oauth2/code/kakao" element={<Kakao />} />
          <Route path="/oauth2/code/google" element={<Google />} />
          <Route path="/manual" element={<Manual />} />
          <Route path="/receivedheart" element={<ReceivedHeart />} />
          <Route path="/sentheart" element={<SentHeart />} />
          <Route path="/setting" element={<Setting />} />
          <Route path="/popup" element={<NonLoggedPopup />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
