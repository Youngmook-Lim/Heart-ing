import { useRecoilValue } from "recoil";
import { isMyBoardAtom } from "../../atoms/messageAtoms";
import { useNavigate } from "react-router-dom";
import { userNicknameAtom } from "../../atoms/userAtoms";

function BottomButton({ ...props }) {
  const navigate = useNavigate();
  const isMyBoard = useRecoilValue(isMyBoardAtom);
  const userNickname = useRecoilValue(userNicknameAtom);

  const onNavigateHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    navigate(`/heartwriting?id=${props.userId}`);
  };

  const onCopyHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    if (navigator.clipboard) {
      // (IE는 사용 못하고, 크롬은 66버전 이상일때 사용 가능합니다.)
      navigator.clipboard
        .writeText(window.location.href)
        .then(() => {
          alert(
            `${userNickname}님의 하트 수신함이 복사되었습니다.\n친구들에게 공유해보세요!`
          );
        })
        .catch(() => {
          alert("잠시 후 다시 시도해주세요.");
        });
    } else {
      if (!document.queryCommandSupported("copy")) {
        return alert("복사하기가 지원되지 않는 브라우저입니다.");
      }

      const textarea = document.createElement("textarea");
      textarea.value = window.location.href;

      document.body.appendChild(textarea);
      textarea.focus();
      textarea.select();
      document.execCommand("copy");
      document.body.removeChild(textarea);
      alert(
        `${userNickname}님의 하트 수신함이 복사되었습니다.\n친구들에게 공유해보세요!`
      );
    }
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
