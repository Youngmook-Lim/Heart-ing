import React, { useEffect, useState } from "react";

import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { currentUserIdAtom, isLoginAtom, userNicknameAtom } from "../atoms/userAtoms";
import { readMessageAtom, isMyBoardAtom } from "../atoms/messageAtoms";

import { getUserInfo } from "../features/userInfo";
import { getProfile, modifyStatusMessage } from "../features/api/userApi";
import { getReceived, sendMessage } from "../features/api/messageApi";

import HeartBoardList from "../components/heartBoard/HeartBoardList";
import HeartBoardMainButton from "../components/heartBoard/HeartBoardMainButton";
import MessageModal from "../components/modal/MessageModal";
import HeartBoardProfileBox from "../components/heartBoard/HeartBoardProfileBox";
import { useNavigate } from "react-router-dom";
import { IUpdateProfileTypes } from "../types/userType";
import { modifyNickname } from "../features/api/userApi";

function HeartBoard() {
  const navigate = useNavigate()
  // const isLogin = true;

  const [userProfile, setUserProfile] = useState({})
  const [receivedList, setReceivedList] = useState({})
  const [isMyBoard, setIsMyBoard] = useRecoilState(isMyBoardAtom)
  const readMessage = useRecoilValue(readMessageAtom); // 메시지 읽는 모달 on/off
  const isLogin = useRecoilValue(isLoginAtom); // 로그인 유무 확인
  const setCurrentUserId = useSetRecoilState(currentUserIdAtom)

  // 하트보드 주인 userId 뽑아서 프로필 가져오기
  let params = new URL(document.URL).searchParams;
  let userId = params.get("id");

  async function getUserProfile(userId: string|null) {
    if (!userId) return
    const data = await getProfile(userId)
    if (data.status === 'success') {
      setUserProfile(data.data)
    } else {
      console.log('에러났당')
      navigate('/notfound')
    }
  }
  // useerId로 최근 메시지 리스트 가져오기
  async function getRecivedMessages(userId: string | null) {
    if (!userId) return;
    setCurrentUserId(userId)
    console.log(userId);
    console.log("최근 메시지 리스트 가져올거야");
    const data = await getReceived(userId);
    if (data.status === "success") {
      console.log(data.data);
      console.log(data.data.messageList);
      setReceivedList(data.data.messageList);
    }
  }

  async function updateProfile(profileInfo:IUpdateProfileTypes) {
    const nicknameBody = {nickname : profileInfo.nickname}
    const statusBody = {statusMessage : profileInfo.statusMessage}
    const nicknameStatus = await modifyNickname(nicknameBody)
    const statusMessageStatus = await modifyStatusMessage(statusBody)

    if (nicknameStatus === 'success' && statusMessageStatus === 'success') {
      getUserProfile(userId)
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

  // 테스트용 메시지 전송
  // async function postMessages(userId: string | null) {
  //   if (!userId) return;
  //   console.log(userId + "가 받는 메시지 테스트 중입니다");
  //   const body: IMessageSendTypes = {
  //     heartId: 1,
  //     senderId: userId,
  //     receiverId: userId,
  //     title: "메시지테스트",
  //     content: "내용이보일까요",
  //   };
  //   const data = await sendMessage(body);
  //   if (data.status === "success") {
  //     console.log(data);
  //   }
  // }
  // postMessages(myId);

  return (
    <div className="container mx-auto px-6 py-8">
      <div className="modal border-hrtColorPink">
        <div className="modal-header bg-hrtColorPink border-hrtColorPink">
          마음 수신함
        </div>
        <HeartBoardProfileBox onChangeProfile={updateProfile} userProfile={userProfile} />
        {isMyBoard ? (
          <HeartBoardMainButton context={"공유하기"} />
        ) : (
          <HeartBoardMainButton context={"마음 보내기"} />
        )}
        <HeartBoardList receivedList={receivedList} />
      </div>
      {isLogin ? null : <button>나의 마음 수신함 만들러가기</button>}
      {readMessage ? <MessageModal mode={"recent"} /> : null}
    </div>
  );
}

export default HeartBoard;
