import React from "react";
import { useEffect } from "react";

import { useRecoilValue, useSetRecoilState } from "recoil";
// import { isLoginAtom, userNicknameAtom } from "../atoms/userAtoms";
import { readMessageAtom, isMyBoardAtom } from "../atoms/messageAtoms";

import HeartBoardList from "../components/heartBoard/HeartBoardList";
import HeartBoardMainButton from "../components/heartBoard/HeartBoardMainButton";
import MessageModal from "../components/modal/MessageModal";

function HeartBoard() {
  // 로그인 유무 확인
  // const isLogin = useRecoilValue(isLoginAtom);
  const isLogin = true;
  // const userNickname = useRecoilValue(userNicknameAtom);
  const userNickname = "1";

  // 메시지 읽는 모달
  const readMessage = useRecoilValue(readMessageAtom);

  // 로그인 했고, 닉네임이 보드 주인과 같으면 isMyBoard=true
  const boardNickname = "1"; // 보드 주인 불러올 부분
  const setIsMyBoard = useSetRecoilState(isMyBoardAtom);
  const isMyBoard = useRecoilValue(isMyBoardAtom);

  useEffect(() => {
    setIsMyBoard(isLogin && userNickname === boardNickname ? true : false);
  });

  return (
    <div>
      하트보드 페이지 입니다
      <HeartBoardList />
      {isMyBoard ? (
        <HeartBoardMainButton context={"공유하기"} />
      ) : (
        <HeartBoardMainButton context={"마음 보내기"} />
      )}
      {isLogin ? null : <button>나의 마음 수신함 만들러가기</button>}
      {readMessage ? <MessageModal mode={"recent"} /> : null}
    </div>
  );
}

export default HeartBoard;
