import { useRecoilValue } from "recoil";
import { isMyBoardAtom } from "../../atoms/messageAtoms";

function BottomButton({ ...props }) {
  const isMyBoard = useRecoilValue(isMyBoardAtom);
  console.log(isMyBoard);

  return (
    <button className="bg-hrtColorYellow px-8 h-16 rounded-xl border-2 border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)]">
      {isMyBoard ? (
        <div className="text-2xl">공유하기{isMyBoard}</div>
      ) : (
        <div>
          <div className="flex text-base pt-2 justify-center leading-3">
            <p className="text-hrtColorPink">{props.userProfile.nickname}</p>
            <p>님에게</p>
          </div>
          <div className="text-2xl">마음 보내기{isMyBoard}</div>
        </div>
      )}
    </button>
  );
}

export default BottomButton;
