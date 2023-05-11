import { useRecoilValue } from "recoil"
import { isSelectedEmojiUrlAtom } from "../../atoms/messageAtoms"

interface propType { 
  emojiUrl: string 
}

function MessageModalHeartEmoji ({emojiUrl}:propType) {

  const isSelectedEmojiUrl = useRecoilValue(isSelectedEmojiUrlAtom)

  return (
    <>
      <img className="absolute left-1 bottom-1" src={isSelectedEmojiUrl} alt="emoji" />
    </>
  )
}

export default MessageModalHeartEmoji