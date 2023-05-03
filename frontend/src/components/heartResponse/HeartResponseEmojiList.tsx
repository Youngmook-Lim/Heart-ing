import HeartResponseEmojiListItem from "./HeartResponseEmojiListItem"
import touch_arrow from "../../assets/images/png/touch_arrow.png"
import { isOpenEmojiListAtom } from "../../atoms/messageAtoms"
import { useSetRecoilState } from "recoil";

interface propsType {
  onEmojiHandler: (emojiId: number) => void;
}

function HeartResponseEmojiList({ onEmojiHandler }: propsType) {

  const setIsOpenEmojiList = useSetRecoilState(isOpenEmojiListAtom)

  const closeModalEmojiBox = () => {
    setIsOpenEmojiList(false);
  };

  return (
    <>
      <div className="heartBoard border-hrtColorPink relative">
        <div className="w-auto bg-hrtColorPink border-hrtColorPink flex justify-between z-40">
          <div className="text-white text-sm mx-2">반응하기</div>
          <button className="w-6 text-white" onClick={closeModalEmojiBox}>x</button>
        </div>
        <div className="grid grid-cols-5 py-2">
          {Array.from({ length: 5 }, (_, index) => (
            <HeartResponseEmojiListItem key={index} id={index + 1} onEmojiHandler={onEmojiHandler} />
          ))}
        </div>
      </div>
      <div className="flex justify-end pr-2">
        <img src={touch_arrow} alt="touch_arrow"
        className="w-6" />
      </div>
    </>
  )
}

export default HeartResponseEmojiList