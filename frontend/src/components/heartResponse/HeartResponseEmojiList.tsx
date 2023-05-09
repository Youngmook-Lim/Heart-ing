import { useSetRecoilState } from "recoil";
import HeartResponseEmojiListItem from "./HeartResponseEmojiListItem"
import touch_arrow from "../../assets/images/png/touch_arrow.png"
import { isOpenEmojiListAtom } from "../../atoms/messageAtoms"

interface propsType {
  messageEmojiId: number,
  onEmojiHandler: (emojiId: number) => void;
}

function HeartResponseEmojiList({ messageEmojiId, onEmojiHandler }: propsType) {

  const setIsOpenEmojiList = useSetRecoilState(isOpenEmojiListAtom)

  const closeModalEmojiBox = () => {
    setIsOpenEmojiList(false);
  };

  return (
    <div className="flex flex-col w-full">
      <div className="heartBoard border-hrtColorPink z-40">
        <div className="w-auto bg-hrtColorPink border-hrtColorPink flex justify-between">
          <div className="text-white text-sm mx-2">반응하기</div>
          <button className="w-6 text-white" onClick={closeModalEmojiBox}>x</button>
        </div>
        <div className="grid grid-cols-5 py-2">
          {[1,2,3,4,5].map((id) => (
            <HeartResponseEmojiListItem 
            key={id} 
            id={id} 
            messageEmojiId={ messageEmojiId } 
            onEmojiHandler={ onEmojiHandler } 
            onCloseModalEmojiBoxHandler={ closeModalEmojiBox } />
          ))}
        </div>
      </div>
      <div className="flex justify-end pr-2">
        <img src={touch_arrow} alt="touch_arrow"
        className="w-6" />
      </div>
    </div>
  )
}

export default HeartResponseEmojiList