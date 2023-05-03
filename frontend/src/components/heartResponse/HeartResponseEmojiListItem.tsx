import ResponseEmojiIcon from "../common/ResponseEmojiIcon"
import { isSelectedEmojiIdAtom } from "../../atoms/messageAtoms"
import { useRecoilState } from "recoil";

interface propsType {
  id: number,
  onEmojiHandler: (emojiId: number) => void;
}

function HeartResponseEmojiListItem({ id, onEmojiHandler }: propsType) {

  const [ isSelectedEmojiId, setIsSelectedEmojiId ] = useRecoilState(isSelectedEmojiIdAtom)

  const getEmojiId = () => {
    onEmojiHandler(id)
    setIsSelectedEmojiId(id)
  }

  return (
    <>
      <div className="relative z-10 flex justify-center" onClick={getEmojiId}>
      { isSelectedEmojiId === id ? <div className="absolute w-8 h-8 bg-hrtColorLightPink rounded-full transform z-22"></div> : null }
        <ResponseEmojiIcon id={id} />
      </div>
    </>
  )
}

export default HeartResponseEmojiListItem