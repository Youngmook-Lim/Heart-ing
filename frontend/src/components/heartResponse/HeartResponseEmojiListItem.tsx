import { useRecoilState } from "recoil";
import ResponseEmojiIcon from "../common/ResponseEmojiIcon"
import { isSelectedEmojiIdAtom } from "../../atoms/messageAtoms"

interface propsType {
  id: number,
  onEmojiHandler: (emojiId: number) => void;
  messageEmojiId: number,
}

function HeartResponseEmojiListItem({ id, onEmojiHandler, messageEmojiId }: propsType) {

  const [ isSelectedEmojiId, setIsSelectedEmojiId ] = useRecoilState(isSelectedEmojiIdAtom)

  const getEmojiId = async() => {
    setIsSelectedEmojiId(() => id)
    onEmojiHandler(id)
  }

  return (
    <>
      <div className="relative flex justify-center" onClick={getEmojiId}>
        <div className="absolute z-50">
          <ResponseEmojiIcon id={id} />
        </div>
        { isSelectedEmojiId === id || messageEmojiId === id ? <div className="w-11 h-11 bg-hrtColorLightPink rounded-full transform"></div> : <div className="w-11 h-11"></div> }
      </div>
    </>
  )
}

export default HeartResponseEmojiListItem