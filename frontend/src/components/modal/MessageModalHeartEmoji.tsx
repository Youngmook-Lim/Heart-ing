import { useEffect } from "react"
import { useRecoilState } from "recoil"
import { isSelectedEmojiUrlAtom } from "../../atoms/messageAtoms"

interface propType { 
  emojiUrl: string 
}

function MessageModalHeartEmoji ({emojiUrl}:propType) {

  const [ isSelectedEmojiUrl, setIsSelectedEmojiUrl ] = useRecoilState(isSelectedEmojiUrlAtom)

  useEffect(() => {
    if(isSelectedEmojiUrlAtom === null) {
      setIsSelectedEmojiUrl(() => emojiUrl)
    }
    
  }, [])

  return (
    <>
      <img className="absolute left-1 bottom-1" src={isSelectedEmojiUrl} alt="emoji" />
    </>
  )
}

export default MessageModalHeartEmoji