import { isSelectedEmojiUrlAtom } from "../../atoms/messageAtoms"
import { useRecoilState } from "recoil"

interface propType { 
  emojiUrl: string 
}

function MessageModalHeartEmoji ({emojiUrl}:propType) {

  const [ isSelectedEmojiUrl, setIsSelectedEmojiUrl ] = useRecoilState(isSelectedEmojiUrlAtom)

  if(isSelectedEmojiUrlAtom === null) {
    setIsSelectedEmojiUrl(emojiUrl)
  }

  return (
    <>
      <img className="absolute w-6 right-3 bottom-3" src={isSelectedEmojiUrl} alt="emoji" />
    </>
  )
}

export default MessageModalHeartEmoji