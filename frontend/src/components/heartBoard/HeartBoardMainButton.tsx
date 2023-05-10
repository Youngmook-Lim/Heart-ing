import { useRecoilValue, useSetRecoilState } from "recoil";
import { isMyBoardAtom } from "../../atoms/messageAtoms";
import { useNavigate } from "react-router-dom";
import { sharingAtom, userNicknameAtom } from "../../atoms/userAtoms";
import SharingModal from "../modal/SharingModal";

function BottomButton({ ...props }) {
  const navigate = useNavigate();
  const isMyBoard = useRecoilValue(isMyBoardAtom);
  const userNickname = useRecoilValue(userNicknameAtom);
  const setIsSharing = useSetRecoilState(sharingAtom)

  const onNavigateHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    navigate(`/heartwriting?id=${props.userId}`);
  };

  const onCopyHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    setIsSharing(true)
  };

  return (
    <button className="bg-hrtColorYellow px-8 h-16 rounded-xl border-2 border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)]">
      {isMyBoard ? (
        <div className="text-2xl" onClick={onCopyHandler}>
          나의 수신함 공유하기
        </div>
      ) : (
        <div onClick={onNavigateHandler}>
          <div className="flex text-base pt-2 justify-center leading-3">
            <p className="text-hrtColorPink">{props.userProfile.nickname}</p>
            <p>님에게</p>
          </div>
          <div className="text-2xl">하트 보내기</div>
        </div>
      )}
    </button>
  );
}

export default BottomButton;
