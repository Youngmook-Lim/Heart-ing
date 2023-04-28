import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import {
  currentUserIdAtom,
  isLoginAtom,
  userNicknameAtom,
} from "../atoms/userAtoms";
import { readMessageAtom, isMyBoardAtom } from "../atoms/messageAtoms";

import { IUpdateProfileTypes } from "../types/userType";

import { getUserInfo } from "../features/userInfo";
import { getProfile, modifyStatusMessage } from "../features/api/userApi";
import { getReceived } from "../features/api/messageApi";
import { modifyNickname } from "../features/api/userApi";

import HeartBoardList from "../components/heartBoard/HeartBoardList";
import HeartBoardMainButton from "../components/heartBoard/HeartBoardMainButton";
import MessageModal from "../components/modal/MessageModal";
import HeartBoardProfileBox from "../components/heartBoard/HeartBoardProfileBox";
import BackgroundHeart from "../assets/images/png/background_heart.png";

function HeartBoard() {
  const navigate = useNavigate();

  const [userProfile, setUserProfile] = useState({});
  const [receivedList, setReceivedList] = useState({});
  const [isMyBoard, setIsMyBoard] = useRecoilState(isMyBoardAtom);
  const readMessage = useRecoilValue(readMessageAtom); // 메시지 읽는 모달 on/off
  const isLogin = useRecoilValue(isLoginAtom); // 로그인 유무 확인
  const setCurrentUserId = useSetRecoilState(currentUserIdAtom);
  const setUserNickname = useSetRecoilState(userNicknameAtom);

  // 하트보드 주인 userId 뽑아서 프로필 가져오기
  let params = new URL(document.URL).searchParams;
  let userId = params.get("id");

  async function getUserProfile(userId: string | null) {
    if (!userId) return;
    const data = await getProfile(userId);
    if (data.status === "success") {
      setUserProfile(data.data);
    } else {
      console.log("에러났당"); 
      navigate("/notfound");
    }
  }
  // useerId로 최근 메시지 리스트 가져오기
  async function getRecivedMessages(userId: string | null) {
    if (!userId) return;
    setCurrentUserId(userId);
    console.log(userId);
    const data = await getReceived(userId);
    if (data.status === "success") {
      setReceivedList(data.data.messageList);
    }
  }

  async function updateProfile(profileInfo: IUpdateProfileTypes) {
    const nicknameBody = { nickname: profileInfo.nickname };
    const statusBody = { statusMessage: profileInfo.statusMessage };
    const nicknameStatus = await modifyNickname(nicknameBody);
    const statusMessageStatus = await modifyStatusMessage(statusBody);

    if (nicknameStatus === "success" && statusMessageStatus === "success") {
      getUserProfile(userId);
      setUserNickname(profileInfo.nickname);
    }
  }
  // 내 userId localStorage에서 가져오기
  const myId = getUserInfo().userId;

  useEffect(() => {
    // 로그인 했고, 닉네임이 보드 주인과 같으면 isMyBoard=true
    setIsMyBoard(isLogin && userId === myId ? true : false);
    getUserProfile(userId);
    getRecivedMessages(userId);
  }, []);

  return (
    <div className="container mx-auto px-6 py-8">
      <div className="modal border-hrtColorPink">
        <div className="modal-header bg-hrtColorPink border-hrtColorPink">
          마음 수신함
        </div>
        <HeartBoardProfileBox
          onChangeProfile={updateProfile}
          userProfile={userProfile}
        />
        <div className="relative flex justify-center">
          <img src={BackgroundHeart} alt="test" className="max-h-60" />
          <div className="absolute inset-x-px top-1/3">
            {isMyBoard ? (
              <HeartBoardMainButton
                userProfile={userProfile}
                isMyboard={true}
              />
            ) : (
              <HeartBoardMainButton
                userProfile={userProfile}
                isMyboard={false}
              />
            )}
          </div>
        </div>
        <HeartBoardList receivedList={receivedList} />
      </div>
      {isLogin ? null : <button>나의 마음 수신함 만들러가기</button>}
      {readMessage ? <MessageModal mode={"recent"} /> : null}
    </div>
  );
}

export default HeartBoard;
