import { useRecoilState } from "recoil";
import ResponseEmojiIcon from "../common/ResponseEmojiIcon"
import { isSelectedEmojiIdAtom } from "../../atoms/messageAtoms"
import { useEffect } from "react";

interface propsType {
  id: number,
  onEmojiHandler: (emojiId: number) => void,
  messageEmojiId: number,
  onCloseModalEmojiBoxHandler: () => void,
}

function HeartResponseEmojiListItem({ id, onEmojiHandler, messageEmojiId, onCloseModalEmojiBoxHandler }: propsType) {
  const [ isSelectedEmojiId, setIsSelectedEmojiId ] = useRecoilState(isSelectedEmojiIdAtom)
  
  useEffect(() => {
    if( isSelectedEmojiId === 0) {
      setIsSelectedEmojiId(messageEmojiId)
    }
  }, [])


  const getEmojiId = () => {
    setIsSelectedEmojiId(() => id)
    onEmojiHandler(id)
    onCloseModalEmojiBoxHandler()
  }

  return (
    <>
      <div className="relative flex justify-center cursor-pointer" onClick={getEmojiId}>
        <div className="absolute z-50">
          <ResponseEmojiIcon id={id} />
        </div>
        { isSelectedEmojiId === id ? <div className="w-11 h-11 bg-hrtColorSelectedEmoji rounded-full transform"></div> : <div className="w-11 h-11"></div> }
      </div>
    </>
  )
}

export default HeartResponseEmojiListItem