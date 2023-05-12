import React, { useState, useEffect, useRef } from "react";

import manual_home_intro_1 from "../../assets/images/png/manual_home_intro_1.png";
import manual_home_intro_2 from "../../assets/images/png/manual_home_intro_2.png";
import manual_home_scroll from "../../assets/images/png/manual_home_scroll.png";
import { useHref } from "react-router-dom";

function ManualHomeIntro() {
  const [scrollPosition, setScrollPosition] = useState(0);
  const updateScroll = () => {
    setScrollPosition(window.scrollY || document.documentElement.scrollTop);
  };

  const introRef = useRef<HTMLDivElement>(null);
  const onMoreClick = () => {
    introRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    window.addEventListener("scroll", updateScroll);
  }, []);

  return (
    <div className="flex flex-col mb-6 w-full">
      <div
        className={`flex justify-center items-center pt-2 ${
          scrollPosition < 90 ? "shake-vertical" : "scale-out-center hidden"
        }`}
        onClick={onMoreClick}
        ref={introRef}
      >
        <img
          src={manual_home_scroll}
          alt="manual_home_scroll"
          className="p-2 max-h-24"
        />
      </div>
      <div
        className={`pt-2 ${
          scrollPosition > 90 ? "slide-in-bottom" : "collapse"
        }`}
      >
        <span className="flex justify-center items-center my-2">
          <p className="border-b border-hrtColorPink w-1/4 my-4"></p>
          <p className="mx-4 text-sm text-hrtColorPink textShadow-none">
            하팅! 알아보기
          </p>
          <p className="border-b border-hrtColorPink w-1/4 my-4"></p>
        </span>
      </div>
      <div
        className={`mt-2 mb-6 ${
          scrollPosition > 100 ? "slide-in-bottom" : "collapse"
        }`}
      >
        <span className="text-xl flex justify-center text-white items-center textShadow ">
          <p className="mr-1 white text-hrtColorPink pr-2">
            익명 메시지 서비스
          </p>
          <p className="mr-1 white text-hrtColorPink">하팅!</p>
        </span>
        <span className="text-lg flex justify-center text-white items-center textShadow my-2 ">
          <p className="mr-1 purple pr-2">하트는</p>
          <p className="mr-1 white text-hrtColorPink  ">24시간</p>
          <p className="mr-1 purple">이 지나면 사라져요</p>
        </span>
        <div className="flex flex-col items-center mx-8">
          <img
            src={manual_home_intro_1}
            alt="manual_home_intro_1"
            className="p-2 max-h-64"
          />
        </div>
      </div>

      <div
        className={`mt-2 mb-4 ${
          scrollPosition > 330 ? "slide-in-bottom" : "collapse"
        }`}
      >
        <div className="flex flex-col items-center mx-8">
          <span className="text-lg flex justify-center text-white items-center textShadow my-2 ">
            <p className="mr-1 purple pr-2">나의 하트 수신함을 만들어</p>
          </span>
          <span className="text-xl flex justify-center text-white items-center textShadow my-2 ">
            <p className="mr-1 purple pr-2">친구들에게</p>
            <p className="mr-1 white text-hrtColorPink  ">공유</p>
            <p className="mr-1 purple">해보세요!</p>
          </span>
          <img
            src={manual_home_intro_2}
            alt="gmanual_home_intro_2"
            className="p-2 max-h-80"
          />
        </div>
      </div>
      <a
        className={`mx-8 mb-24 bg-hrtColorYellow rounded-xl border-2 border-hrtColorPink ${
          scrollPosition > 330 ? "heartbeat" : "collapse"
        } `}
        href="https://heart-ing.com/heartboard/user?id=3yqolax1ee"
      >
        <p className="py-2 text-hrtColorPink">개발팀의 하트 수신함 보러가기</p>
      </a>
    </div>
  );
}

export default ManualHomeIntro;
