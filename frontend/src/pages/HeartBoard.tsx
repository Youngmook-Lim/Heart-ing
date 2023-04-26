import React, { useEffect, useState } from "react";

import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { isLoginAtom, userNicknameAtom } from "../atoms/userAtoms";
import { readMessageAtom, isMyBoardAtom } from "../atoms/messageAtoms";

import HeartBoardList from "../components/heartBoard/HeartBoardList";
import HeartBoardMainButton from "../components/heartBoard/HeartBoardMainButton";
import MessageModal from "../components/modal/MessageModal";
import { getUserInfo } from "../features/userInfo";
import HeartBoardProfileBox from "../components/heartBoard/HeartBoardProfileBox";
import { getProfile } from "../features/api/userApi";

function HeartBoard() {
  // 로그인 유무 확인
  const isLogin = useRecoilValue(isLoginAtom);
  // const isLogin = true;

  // 하트보드 주인 userId 뽑아서 프로필 가져오기
  let params = new URL(document.URL).searchParams;
  let userId = params.get("id");
  const [userProfile, setUserProfile] = useState({})

  async function getUserProfile(userId: string|null) {
    if (!userId) return
    const data = await getProfile(userId)
    if (data.status === 'success') {
      setUserProfile(data.data)
    }
  }

  // 내 userId localStorage에서 가져오기
  const myId = getUserInfo().userId


  
  const userNickname = useRecoilValue(userNicknameAtom);
  // const userNickname = "1";

  // 메시지 읽는 모달
  const readMessage = useRecoilValue(readMessageAtom);

  // 로그인 했고, 닉네임이 보드 주인과 같으면 isMyBoard=true
  // const boardNickname = "1"; // 보드 주인 불러올 부분
  // const setIsMyBoard = useSetRecoilState(isMyBoardAtom);
  // const isMyBoard = useRecoilValue(isMyBoardAtom);
  // 38, 39행을 합쳐서 41행과 같이 쓸 수 있답니다~
  const [isMyBoard, setIsMyBoard] = useRecoilState(isMyBoardAtom)

  useEffect(() => {
    setIsMyBoard(isLogin && userId === myId ? true : false);
    getUserProfile(userId)
  }, []);

  return (
    <div className="container mx-auto px-8 py-8">
      <div className="modal border-hrtColorPink">
        <div className="modal-header bg-hrtColorPink">마음 수신함</div>
        <HeartBoardProfileBox userProfile={userProfile}/>
        <HeartBoardList />
        {isMyBoard ? (
          <HeartBoardMainButton context={"공유하기"} />
        ) : (
          <HeartBoardMainButton context={"마음 보내기"} />
        )}
      </div>
      {isLogin ? null : <button>나의 마음 수신함 만들러가기</button>}
      {readMessage ? <MessageModal mode={"recent"} /> : null}
    </div>
  );
}

export default HeartBoard;
