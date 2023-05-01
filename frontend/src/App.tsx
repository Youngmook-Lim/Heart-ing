import React from "react";
import { Route, Routes } from "react-router";
import { BrowserRouter } from "react-router-dom";

import "./styles/App.css";
import ErrorPage from "./pages/ErrorPage";
import HeartBoard from "./pages/HeartBoard";
import HeartGuide from "./pages/HeartGuide";
import Heartwriting from "./pages/Heartwriting";
import Kakao from "./pages/Kakao";
import Manual from "./pages/Manual";
import ReceivedHeart from "./pages/ReceivedHeart";
import SentHeart from "./pages/SentHeart";
import Setting from "./pages/Setting";
import Navbar from "./components/navbar/Navbar";
import ManualSection from "./components/manual/ManualSection"

function App() {
  return (
    <div className="App bg-hrtColorBackground text-hrtColorOutline h-full min-h-screen">
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route index element={<Manual />} />
          <Route path="/*" element={<ErrorPage />} />
          <Route path="/heartboard/user" element={<HeartBoard />} />
          <Route path="/heartguide" element={<HeartGuide />} />
          <Route path="/heartwriting" element={<Heartwriting />} />
          <Route path="/oauth2/code/kakao" element={<Kakao />} />
          <Route path="/manual" element={<Manual />} />
          <Route path="/manual2" element={<ManualSection />} />
          <Route path="/receivedheart" element={<ReceivedHeart />} />
          <Route path="/sentheart" element={<SentHeart />} />
          <Route path="/setting" element={<Setting />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
