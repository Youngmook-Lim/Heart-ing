import React, { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { useRecoilState, useRecoilValue } from "recoil";
import { isLoginAtom, sharingAtom } from "../atoms/userAtoms";
import { isMyBoardAtom, readMessageAtom } from "../atoms/messageAtoms";
import { isFirstTimeAtom, isPopupShowAtom } from "../atoms/popupAtoms";

import { getUserInfo } from "../features/userInfo";
import { getProfile } from "../features/api/userApi";
import { getReceived } from "../features/api/messageApi";

import HeartBoardList from "../components/heartBoard/HeartBoardList";
import HeartBoardMainButton from "../components/heartBoard/HeartBoardMainButton";
import MessageModal from "../components/modal/MessageModal";
import HeartBoardProfileBox from "../components/heartBoard/HeartBoardProfileBox";
import BackgroundHeart from "../assets/images/png/background_heart.png";
import NonLoggedPopup from "../components/popUp/NonLoggedPopup";
import SharingModal from "../components/modal/SharingModal";
import { Socket } from "socket.io-client";

function HeartBoard({ socket }: { socket: Socket | null }) {
  const navigate = useNavigate();

  const [userProfile, setUserProfile] = useState({});
  const [receivedList, setReceivedList] = useState({});
  // const setIsMyBoard = useSetRecoilState(isMyBoardAtom);
  const [totalCount, setTotalCount] = useState(0);

  const [isMyBoard, setIsMyBoard] = useRecoilState(isMyBoardAtom);
  const [readMessage, setReadMessage] = useRecoilState(readMessageAtom); // 메시지 읽는 모달 on/off
  const [isPopupShow, setIsPopupShow] = useRecoilState(isPopupShowAtom);
  const isFirstTime = useRecoilValue(isFirstTimeAtom);
  const isSharing = useRecoilValue(sharingAtom);

  const isLogin = useRecoilValue(isLoginAtom); // 로그인 유무 확인

  // 하트보드 주인 userId 뽑아서 프로필 가져오기
  let params = new URL(document.URL).searchParams;
  let userId = params.get("id");
  const getUserProfile = useCallback(
    async (userId: string | null) => {
      if (!userId) return;
      const data = await getProfile(userId);
      if (data.status === "success") {
        setUserProfile(data.data);
        setTotalCount(data.data.messageTotal);
      } else {
        navigate("/notfound");
      }
    },
    [navigate]
  );

  // userId로 최근 메시지 리스트 가져오기
  const getRecivedMessages = useCallback(async (userId: string | null) => {
    if (!userId) return;
    const data = await getReceived(userId);
    if (data.status === "success") {
      setReceivedList(data.data.messageList);
    }
  }, []);

  // 내 userId localStorage에서 가져오기
  const myId = getUserInfo().userId;

  const checkPopupClose = (): boolean => {
    const popupNoShow = localStorage.getItem("popupNoShow");

    if (popupNoShow !== "true") {
      return true;
    } else {
      return false;
    }
  };

  useEffect(() => {
    // 로그인 했고, 닉네임이 보드 주인과 같으면 isMyBoard=true
    if (isLogin) {
      setIsMyBoard(userId === myId ? true : false);
    } else {
      if (isFirstTime) {
        checkPopupClose() ? setIsPopupShow(true) : setIsPopupShow(false);
      }
    }
    getUserProfile(userId);
    getRecivedMessages(userId);
    setReadMessage(false);
    return () => {
      setReadMessage(false);
    };
  }, [
    isLogin,
    myId,
    userId,
    setIsMyBoard,
    getUserProfile,
    setReadMessage,
    getRecivedMessages,
  ]);

  useEffect(() => {
    getRecivedMessages(userId);
    getUserProfile(userId);
  }, [getRecivedMessages, getUserProfile, userId, readMessage]);

  const outsideHeightStyle = {
    height: `calc((var(--vh, 1vh) * 100) - ${isMyBoard ? 12 : 7}rem)`,
  };
  const innerHeightStyle = {
    height: `calc((var(--vh, 1vh) * 100) - ${isMyBoard ? 15 : 10}rem)`,
  };

  return (
    <div className="container fullHeight mx-auto p-6 pb-8 h-[calc((var(--vh, 1vh) * 100)-8rem)]">
      {isPopupShow ? <NonLoggedPopup /> : null}
      <div
        className="heartBoard border-hrtColorPink relative"
        style={outsideHeightStyle}
      >
        <div className="sticky top-0 w-auto heartBoard-header bg-hrtColorPink border-hrtColorPink flex justify-between my-2 z-10 cursor-default">
          <div>하트 수신함</div>
          <div className="">
            <div className="text-xs text-right h-6 -my-1 -mt-2 cursor-default">
              누적 수신
            </div>
            <div className="flex -mt-1.5">
              <svg
                fill="currentColor"
                viewBox="0 0 20 20"
                xmlns="http://www.w3.org/2000/svg"
                aria-hidden="true"
                className="h-6 my-2 mx-1 pb-1"
              >
                <path d="M9.653 16.915l-.005-.003-.019-.01a20.759 20.759 0 01-1.162-.682 22.045 22.045 0 01-2.582-1.9C4.045 12.733 2 10.352 2 7.5a4.5 4.5 0 018-2.828A4.5 4.5 0 0118 7.5c0 2.852-2.044 5.233-3.885 6.82a22.049 22.049 0 01-3.744 2.582l-.019.01-.005.003h-.002a.739.739 0 01-.69.001l-.002-.001z"></path>
              </svg>
              {totalCount}
              <p className="text-sm">개</p>
            </div>
          </div>
        </div>
        <div
          className="whitespace-nowrap overflow-auto scrollbar-hide"
          style={innerHeightStyle}
        >
          <div className="pb-2">
            <HeartBoardProfileBox userProfile={userProfile} />
          </div>
          {isMyBoard ? null : (
            <div className="relative flex justify-center">
              <img src={BackgroundHeart} alt="test" className="h-60" />
              <div className="absolute inset-x-px top-1/3">
                <HeartBoardMainButton
                  userProfile={userProfile}
                  userId={userId}
                />
              </div>
            </div>
          )}
          <HeartBoardList receivedList={receivedList} />
        </div>
        {isMyBoard ? (
          <div className="mt-4">
            <HeartBoardMainButton userProfile={userProfile} userId={userId} />
          </div>
        ) : null}
      </div>
      {isSharing ? <SharingModal /> : null}
      {readMessage ? <MessageModal mode={"recent"} socket={socket} /> : null}
    </div>
  );
}

export default HeartBoard;
